package ob.debitos.simp.service.impl.reporte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFFormulaEvaluator;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteComisionCuadroBancoAdministradorMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumen;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.reporte.ReporteComision;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.model.reporte.ReporteTransaccion;
import ob.debitos.simp.model.websocket.Message;
import ob.debitos.simp.model.websocket.MessageDecoder;
import ob.debitos.simp.model.websocket.MessageEncoder;
import ob.debitos.simp.service.IReporteComisionCuadroBancoAdministradorService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.utilitario.ExportFilterOutputStream;
import ob.debitos.simp.utilitario.PaginacionUtil;
import ob.debitos.simp.utilitario.Verbo;

@Service
@ServerEndpoint(value = "/cuadreBancoAdministradorResumen", encoders = {
        MessageEncoder.class }, decoders = { MessageDecoder.class })
public class ReporteComisionCuadroBancoAdministradorService
        implements IReporteComisionCuadroBancoAdministradorService
{
    private @Autowired IReporteComisionCuadroBancoAdministradorMapper reporteMapper;

    private @Autowired Logger logger;
    private static final List<Session> sessions = new ArrayList<>();
    private static int MAX = 1000000;

    private static boolean cancelado = false;
    boolean reporteCompleto = false;
    private double procesandoRegistro;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarComisionesCuadroBancoAdministrador(
            CriterioBusquedaResumen criterioBusquedaResumen)
    {
        criterioBusquedaResumen.setCodigoMoneda(0);
        criterioBusquedaResumen.setVerbo(Verbo.COMISION_BANCO_ADMINISTRADOR);
        return reporteMapper.buscarComisionesCuadroBancoAdministrador(
                criterioBusquedaResumen);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarSumarioCompensacion(
            CriterioBusquedaResumen criterioBusquedaResumen)
    {
        return this.reporteMapper
                .buscarSumarioCompensacion(criterioBusquedaResumen);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void construirResumenCuadreBancoAdministradorExportacion(
            CriterioBusquedaResumen criterioBusqueda,
            SXSSFWorkbook sxssfWorkbook)
    {
        reporteCompleto = false;
        reporteCompleto = construirPagina(criterioBusqueda, sxssfWorkbook);
        try
        {
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message(
                            procesandoRegistro, reporteCompleto, false));
                }
            }
        } catch (EncodeException e)
        {
            logger.error("cancelado: {}", e.getMessage());
        } catch (Exception e)
        {
            logger.error("Ocurrio un error al tratar de generar el reporte: {}",
                    e.getMessage());
        } finally
        {
            cancelado = false;
            reporteCompleto = false;
            procesandoRegistro = 0;
        }
    }

    public void descargarResumenCuadreBancoAdministrador(
            CriterioBusquedaResumen criterioBusquedaResumen,
            HttpServletResponse httpServletResponse) throws IOException
    {
        criterioBusquedaResumen.setVerbo(Verbo.COMISION_BANCO_ADMINISTRADOR);
        String fileNameZip = "Reporte Resumen Cuadre Banco Administrador.zip";
        String fileName = "Reporte Resumen Cuadre Banco Administrador";
        httpServletResponse.setContentType("application/zip");
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + fileNameZip);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(
                byteArrayOutputStream);
        ServletOutputStream servletOutputStream = httpServletResponse
                .getOutputStream();

        XSSFWorkbook workbook = new XSSFWorkbook(
                getClass().getClassLoader().getResourceAsStream(
                        "xlsx/reporteResumenCuadreBancoAdministrador.xlsx"));

        logger.info("criterios: {}", criterioBusquedaResumen);
        logger.info("Iniciando el proceso de generación del reporte");

        SXSSFWorkbook sxssfWorkbook = null;

        try
        {
            sxssfWorkbook = new SXSSFWorkbook(workbook, -1, Boolean.FALSE,
                    Boolean.TRUE);

            this.construirResumenCuadreBancoAdministradorExportacion(
                    criterioBusquedaResumen, sxssfWorkbook);

            ZipEntry zipEntry = new ZipEntry(fileName + ".xlsx");
            zipOutputStream.putNextEntry(zipEntry);
            sxssfWorkbook.write(new ExportFilterOutputStream(zipOutputStream));
            zipOutputStream.closeEntry();

            zipOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(bytes);
            servletOutputStream.flush();

            logger.info("El reporte se generó correctamente");
        } catch (Exception e)
        {
            logger.error("Ocurrio un error al tratar de generar el reporte: {}",
                    e);
        }
    }

    private boolean construirPagina(CriterioBusquedaResumen criterioBusqueda,
            SXSSFWorkbook sxssfWorkbook)
    {

        boolean reporteHoja1 = false;
        boolean reporteHoja2 = false;

        List<ReporteMoneda> monedas = this.reporteMapper
                .buscarComisionesCuadroBancoAdministrador(criterioBusqueda);

        int tamMonedas = monedas.size();

        if (tamMonedas > 0)
        {
            for (int i = 0; i < tamMonedas; i++)
            {
                if (monedas.get(i).getCodigoMoneda() == 604)
                {
                    Sheet sheet = sxssfWorkbook.getSheetAt(0);
                    sheet.setDefaultRowHeight((short) 380);
                    reporteHoja1 = construir(sheet, sxssfWorkbook, 0,
                            criterioBusqueda, monedas.get(i));
                } else if (monedas.get(i).getCodigoMoneda() == 840)
                {
                    Sheet sheet = sxssfWorkbook.getSheetAt(1);
                    sheet.setDefaultRowHeight((short) 380);
                    reporteHoja2 = construir(sheet, sxssfWorkbook, 1,
                            criterioBusqueda, monedas.get(i));
                }
            }
            if (tamMonedas == 1)
            {
                if (monedas.get(0).getCodigoMoneda() == 604)
                {
                    Sheet sheet = sxssfWorkbook.getSheetAt(1);
                    sheet.setDefaultRowHeight((short) 380);
                    reporteHoja1 = construir(sheet, sxssfWorkbook, 1,
                            criterioBusqueda, null);
                } else
                {
                    Sheet sheet = sxssfWorkbook.getSheetAt(0);
                    sheet.setDefaultRowHeight((short) 380);
                    reporteHoja1 = construir(sheet, sxssfWorkbook, 0,
                            criterioBusqueda, null);
                }
                procesandoRegistro = 100.0;
            }
        } else
        {
            for (int i = 0; i < 2; i++)
            {
                Sheet sheet = sxssfWorkbook.getSheetAt(i);
                sheet.setDefaultRowHeight((short) 380);
                reporteHoja1 = construir(sheet, sxssfWorkbook, i,
                        criterioBusqueda, null);
            }
            return true;
        }

        return reporteHoja1 || reporteHoja2;
    }

    boolean construir(Sheet sheet, SXSSFWorkbook sxssfWorkbook, int numPagina,
            CriterioBusquedaResumen criterioBusqueda, ReporteMoneda monedas)
    {
        Font fontNormal = sxssfWorkbook.createFont();
        fontNormal.setFontName("Segoe UI");

        Font fontBold = sxssfWorkbook.createFont();
        fontBold.setFontName("Segoe UI");
        fontBold.setBold(true);

        Font fontWhite = sxssfWorkbook.createFont();
        fontWhite.setFontName("Segoe UI");
        fontWhite.setColor(IndexedColors.WHITE.getIndex());

        Font fontWhiteBold = sxssfWorkbook.createFont();
        fontWhiteBold.setFontName("Segoe UI");
        fontWhiteBold.setColor(IndexedColors.WHITE.getIndex());
        fontWhiteBold.setBold(true);

        CellStyle estiloBack = sxssfWorkbook.createCellStyle();
        estiloBack.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        estiloBack.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBack.setBorderTop(BorderStyle.THIN);
        estiloBack.setBorderRight(BorderStyle.THIN);
        estiloBack.setBorderBottom(BorderStyle.THIN);
        estiloBack.setBorderLeft(BorderStyle.THIN);
        estiloBack.setFont(fontNormal);

        CellStyle estiloNormal = this.crearEstiloBasico(sxssfWorkbook,
                fontNormal);

        CellStyle estiloCab = this.crearEstiloBasico(sxssfWorkbook, fontWhite);
        estiloCab.setAlignment(HorizontalAlignment.CENTER);
        estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle estiloMembresia = this.crearEstiloBasico(sxssfWorkbook,
                fontWhiteBold);
        estiloMembresia.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        estiloMembresia.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle estiloCantidad = this.crearEstiloNumero(sxssfWorkbook,
                "#,##0", fontNormal);

        CellStyle estiloDecimal4 = this.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.0000;[Red]-#,##0.0000;[Black]#,##0.0000",
                fontNormal);

        CellStyle estiloDecimal2 = this.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        // estilos para los totales
        CellStyle estiloCantidadBold = this.crearEstiloNumero(sxssfWorkbook,
                "#,##0", fontBold);
        estiloCantidadBold.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloCantidadBold.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle estiloDecimal2Color = this.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);
        estiloDecimal2Color.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloDecimal2Color.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle estiloDecimal2Bold = this.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontBold);
        estiloDecimal2Bold.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloDecimal2Bold.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle estiloDecimal4Bold = this.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.0000;[Red]-#,##0.0000;[Black]#,##0.0000",
                fontBold);
        estiloDecimal4Bold.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloDecimal4Bold.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CriterioPaginacion<CriterioBusquedaResumen, ?> criterioPaginacion = PaginacionUtil
                .getCriterioPaginacionParaReporteXLSX(criterioBusqueda, 0, MAX);

        int filaActual = 9;
        int numeroFila = 1;
        try
        {

            sheet.createFreezePane(0, 9);
            sheet.setAutoFilter(new CellRangeAddress(8, 8, 1, 16));
            sheet.addMergedRegion(new CellRangeAddress(7, 7, 8, 17));

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;

            row = sheet.createRow(3);
            cell = row.createCell(1);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha de Proceso");
            cell = row.createCell(2);
            cell.setCellStyle(estiloNormal);
            cell.setCellValue(formatter.format(criterioPaginacion
                    .getCriterioBusqueda().getFechaProceso()));
            cell = row.createCell(4);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Empresa");
            cell = row.createCell(5);
            cell.setCellStyle(estiloNormal);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionEmpresa());
            cell = row.createCell(7);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Cliente");
            cell = row.createCell(8);
            cell.setCellStyle(estiloNormal);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionCliente());
            cell = row.createCell(10);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(11);
            cell.setCellStyle(estiloNormal);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionInstitucion());

            row = sheet.createRow(7);
            cell = row.createCell(8);
            cell.setCellStyle(estiloCab);
            cell.setCellValue("COMISIONES");

            String cab[] = { "Código Membresía", "Clase Servicio", "Origen",
                    "Clase Transacción", "Código Transacción", "Cantidad",
                    "Monto", "THB", "EST", "REC", "OPE", "ISA", "OIF", "ING",
                    "COI", "TIC", "INT", "TOTAL" };

            row = sheet.createRow(8);
            for (int i = 0; i < cab.length; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue(cab[i]);
            }

            boolean seObtuvoTotalFilas = false;
            double totalFilas = 0;
            procesandoRegistro = 0;

            logger.info("Ejecutando consulta.");

            try
            {
                if (monedas != null)
                {
                    List<ReporteTransaccion> resultSet = monedas
                            .getTransacciones();

                    int tamTxns = resultSet.size();

                    for (int j = 0; j < tamTxns; j++)
                    {
                        if (!seObtuvoTotalFilas)
                        {
                            totalFilas = tamTxns;
                            seObtuvoTotalFilas = true;
                            logger.info(
                                    "El total de filas de la consulta es: {}",
                                    (int) totalFilas);
                        }

                        procesandoRegistro = (float) (numeroFila / totalFilas)
                                * 100;

                        for (Session session : sessions)
                        {
                            if (session.isOpen())
                            {
                                session.getBasicRemote()
                                        .sendObject(new Message(
                                                procesandoRegistro,
                                                reporteCompleto, false));
                            }
                        }

                        row = sheet.createRow(filaActual);
                        cell = row.createCell(1);
                        cell.setCellStyle(estiloNormal);
                        cell.setCellValue(concatenarCodigoDescripcion(
                                resultSet.get(j).getCodigoMembresia(),
                                resultSet.get(j).getDescripcionMembresia()));
                        cell = row.createCell(2);
                        cell.setCellStyle(estiloNormal);
                        cell.setCellValue(concatenarCodigoDescripcion(
                                resultSet.get(j).getCodigoClaseServicio(),
                                resultSet.get(j)
                                        .getDescripcionClaseServicio()));
                        cell = row.createCell(3);
                        cell.setCellStyle(estiloNormal);
                        cell.setCellValue(concatenarCodigoDescripcion(
                                Integer.toString(
                                        resultSet.get(j).getCodigoOrigen()),
                                resultSet.get(j).getDescripcionOrigen()));
                        cell = row.createCell(4);
                        cell.setCellStyle(estiloNormal);
                        cell.setCellValue(concatenarCodigoDescripcion(
                                Integer.toString(
                                        resultSet.get(j).getClaseTransaccion()),
                                resultSet.get(j)
                                        .getDescripcionClaseTransaccion()));
                        cell = row.createCell(5);
                        cell.setCellStyle(estiloNormal);
                        cell.setCellValue(concatenarCodigoDescripcion(
                                Integer.toString(resultSet.get(j)
                                        .getCodigoTransaccion()),
                                resultSet.get(j)
                                        .getDescripcionCodigoTransaccion()));
                        cell = row.createCell(6);
                        cell.setCellStyle(estiloCantidad);
                        cell.setCellValue(
                                resultSet.get(j).getCantidadTransaccion());
                        cell = row.createCell(7);
                        cell.setCellStyle(estiloDecimal2);
                        cell.setCellValue(resultSet.get(j).getMonto());

                        List<ReporteComision> comisiones = resultSet.get(j)
                                .getComisiones();

                        int tamComisiones = comisiones.size();

                        double totalSuma = resultSet.get(j).getMonto();

                        double com1, com2, com3, com4, com5, com6, com10, com11,
                                com12, com14;
                        com1 = com2 = com3 = com4 = com5 = com6 = com10 = com11 = com12 = com14 = 0.0;

                        for (int k = 0; k < tamComisiones; k++)
                        {
                            String signo = comisiones.get(k)
                                    .getRegistroContable();
                            double comis = comisiones.get(k).getComision();

                            if (signo != null)
                            {
                                if (signo.equals("C"))
                                {
                                    comis = comis * (-1);
                                }
                            }

                            int idConceptoComision = comisiones.get(k)
                                    .getIdConceptoComision() == null
                                            ? -1
                                            : comisiones.get(k)
                                                    .getIdConceptoComision()
                                                    .intValue();

                            switch (idConceptoComision)
                            {
                            case 1:
                                com1 += comis;
                                cell = row.createCell(8);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com1);
                                break;
                            case 2:
                                totalSuma += comis;
                                com2 += comis;
                                cell = row.createCell(9);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com2);
                                break;
                            case 3:
                                totalSuma += comis;
                                com3 += comis;
                                cell = row.createCell(10);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com3);
                                break;
                            case 4:
                                totalSuma += comis;
                                com4 += comis;
                                cell = row.createCell(11);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com4);
                                break;
                            case 5:
                                totalSuma += comis;
                                com5 += comis;
                                cell = row.createCell(12);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com5);
                                break;
                            case 6:
                                com6 += comis;
                                cell = row.createCell(13);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com6);
                                break;
                            case 10:
                                totalSuma += comis;
                                com10 += comis;
                                cell = row.createCell(14);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com10);
                                break;
                            case 11:
                                totalSuma += comis;
                                com11 += comis;
                                cell = row.createCell(15);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com11);
                                break;
                            case 12:
                                totalSuma += comis;
                                com12 += comis;
                                cell = row.createCell(16);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com12);
                                break;
                            case 14:
                                totalSuma += comis;
                                com14 += comis;
                                cell = row.createCell(17);
                                cell.setCellStyle(estiloDecimal4);
                                cell.setCellValue(com14);
                                break;
                            default:
                                break;
                            }
                        }
                        for (int l = 8; l < 18; l++)
                        {
                            Cell celda = row.getCell(l);
                            if (celda == null)
                            {
                                celda = row.createCell(l);
                                celda.setCellStyle(estiloDecimal4);
                                celda.setCellValue(0.0);
                            }
                        }
                        cell = row.createCell(18);
                        cell.setCellStyle(estiloDecimal4);
                        cell.setCellValue(totalSuma);

                        filaActual++;
                        numeroFila++;
                    }
                    row = sheet.createRow(5);
                    cell = row.createCell(5);
                    cell.setCellStyle(estiloCab);
                    cell.setCellValue("TOTAL");
                    cell = row.createCell(6);
                    cell.setCellStyle(estiloCantidad);
                    cell.setCellFormula(
                            "SUM(G10:G" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(7);
                    cell.setCellStyle(estiloDecimal2);
                    cell.setCellFormula(
                            "SUM(H10:H" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(8);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(I10:I" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(9);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(J10:J" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(10);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(K10:K" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(11);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(L10:L" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(12);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(M10:M" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(13);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(N10:N" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(14);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(O10:O" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(15);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(P10:P" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(16);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(Q10:Q" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(17);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(R10:R" + ((int) totalFilas + 9) + ")");
                    cell = row.createCell(18);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellFormula(
                            "SUM(S10:S" + ((int) totalFilas + 9) + ")");

                    SXSSFFormulaEvaluator
                            .evaluateAllFormulaCells(sxssfWorkbook);
                }
            } catch (IllegalStateException e)
            {
                logger.error("La conexión ha sido cancelada: {}",
                        e.getMessage());
            } catch (ClientAbortException e)
            {
                logger.error("La conexión cancelada: {}", e.getMessage());
            } catch (Exception e)
            {
                logger.error("Ocurrió un error: {}", e.getMessage());
            }
            logger.info("Consulta {} terminada: {}", numPagina);

            return (criterioPaginacion.getStart()
                    + criterioPaginacion.getLength()) >= totalFilas;
        } catch (Exception e)
        {
            logger.error("Error: {}", e.getMessage());
            throw new SimpException(
                    "Ocurrió un error al recuperar los registros de la base de datos");
        }
    }

    private CellStyle crearEstiloBasico(SXSSFWorkbook sxssfWorkbook, Font font)
    {
        CellStyle estilo = sxssfWorkbook.createCellStyle();

        estilo.setAlignment(HorizontalAlignment.LEFT);
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setFont(font);

        return estilo;
    }

    private CellStyle crearEstiloNumero(SXSSFWorkbook sxssfWorkbook,
            String formato, Font font)
    {
        CellStyle estilo = sxssfWorkbook.createCellStyle();

        estilo.setAlignment(HorizontalAlignment.RIGHT);
        estilo.setDataFormat(
                sxssfWorkbook.createDataFormat().getFormat(formato));
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setFont(font);

        return estilo;
    }

    private String validarString(String value)
    {
        return value != null ? value : "";
    }

    private String concatenarCodigoDescripcion(String codigo,
            String descripcion)
    {
        return StringUtils.join(validarString(codigo), " - ",
                validarString(descripcion));
    }

    @OnOpen
    public void open(Session session)
    {
        sessions.add(session);
    }

    public void message(Session session, Message message)
    {
        cancelado = message.getCancelado();
        if (cancelado)
        {
            sessions.clear();
        }
    }

    @OnClose
    public void close(Session session)
    {
        sessions.remove(session);
    }
}