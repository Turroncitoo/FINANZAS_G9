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
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.catalina.connector.ClientAbortException;
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
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.net.HttpHeaders;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.mapper.IReporteCuadreCuentasPorPagarMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteCuadreCuentaPorPagar;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.reporte.ReporteCuadreCuentasPorPagarAutDetalle;
import ob.debitos.simp.model.reporte.ReporteCuadreCuentasPorPagarDetalle;
import ob.debitos.simp.model.reporte.ReporteCuadreCuentasPorPagarResumen;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.model.websocket.Message;
import ob.debitos.simp.model.websocket.MessageDecoder;
import ob.debitos.simp.model.websocket.MessageEncoder;
import ob.debitos.simp.service.IReporteCuadreCuentasPorPagarService;
import ob.debitos.simp.service.IUsuarioService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.utilitario.ExportFilterOutputStream;
import ob.debitos.simp.utilitario.ExportacionUtil;
import ob.debitos.simp.utilitario.PaginacionUtil;
import ob.debitos.simp.utilitario.StringsUtils;

@Service
@ServerEndpoint(value = "/cuadreCuentasPorPagar", encoders = {
        MessageEncoder.class }, decoders = { MessageDecoder.class })
public class ReporteCuadreCuentasPorPagarService
        implements IReporteCuadreCuentasPorPagarService
{
    private @Autowired IUsuarioService usuarioService;
    private @Autowired Logger logger;
    private @Autowired IReporteCuadreCuentasPorPagarMapper reporteCuadreCuentasPorPagarMapper;

    private static int MAX = 1000000;
    boolean reporteCompleto = false;

    private static final List<Session> sessions = new ArrayList<>();

    double totalArchivos = 0;
    double procesandoRegistro = 0;
    int numeroFila = 1;

    private static boolean cancelado = false;

    @OnOpen
    public void open(Session session)
    {
        sessions.add(session);
    }

    @OnMessage
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarResumen(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio)
    {
        return this.reporteCuadreCuentasPorPagarMapper.buscarResumen(criterio);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarDetalle(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio)
    {
        return this.reporteCuadreCuentasPorPagarMapper.buscarDetalle(criterio);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarAutorizacionesDelDia(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio)
    {
        return this.reporteCuadreCuentasPorPagarMapper
                .buscarAutorizacionesDelDia(criterio);
    }

    @Override
    @Truncable(clase = ReporteCuadreCuentasPorPagarAutDetalle.class, campoAnidado = "cuentasPorPagarAutDetalle")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarDetalleAutorizaciones(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio)
    {
        return this.reporteCuadreCuentasPorPagarMapper
                .buscarDetalleAutorizaciones(criterio);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarResumenCompensacion(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            HttpServletResponse response) throws IOException
    {
        String fileNameZip = "Reporte Cuadre de Cuentas por Pagar - Resumen Compensacion.zip";
        String fileName = "Reporte Cuadre de Cuentas por Pagar - Resumen Compensacion";

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + fileNameZip);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(
                byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();

        XSSFWorkbook workbook = new XSSFWorkbook(
                getClass().getClassLoader().getResourceAsStream(
                        "xlsx/reporteCuadreCuentasPorPagarResumen.xlsx"));

        logger.info("Criterios: {}", criterioBusqueda);
        logger.info(
                "Iniciando el proceso de consulta y generación del reporte");

        SXSSFWorkbook sxssfWorkbook = null;

        try
        {
            int inicio = 0;
            int indiceSheetActual = 1;

            do
            {
                logger.info("Estableciendo los criterios de paginación");
                CriterioPaginacion<CriterioBusquedaReporteCuadreCuentaPorPagar, ?> criterioPaginacion = PaginacionUtil
                        .getCriterioPaginacionParaReporteXLSX(criterioBusqueda,
                                inicio, MAX);
                logger.info(
                        "Se preparó los criterios de paginación {} para el sheet {}",
                        criterioPaginacion, indiceSheetActual);

                sxssfWorkbook = new SXSSFWorkbook(workbook,
                        SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
                logger.info("Generando reporte: {}", indiceSheetActual);

                reporteCompleto = this.construirReporteResumenCompensacion(
                        criterioPaginacion, sxssfWorkbook, indiceSheetActual);

                ZipEntry zipEntry = new ZipEntry(
                        fileName + "_" + indiceSheetActual + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                sxssfWorkbook
                        .write(new ExportFilterOutputStream(zipOutputStream));
                zipOutputStream.closeEntry();
                inicio += MAX;
                indiceSheetActual++;
            } while (!reporteCompleto);
            logger.info("¿Reporte Completo?: {}", reporteCompleto);
            zipOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(bytes);
            servletOutputStream.flush();
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message(
                            procesandoRegistro, reporteCompleto, false));
                }
            }
        } catch (ClientAbortException e)
        {
            logger.error("cancelado: {} ", e.getMessage());

        } catch (Exception e)
        {
            logger.error("Ocurrio un error al tratar de generar el reporte: {}",
                    e);
        } finally
        {
            cancelado = false;
            reporteCompleto = false;
            totalArchivos = 0;
            procesandoRegistro = 0;
            numeroFila = 1;
        }
    }

    private boolean construirReporteResumenCompensacion(
            CriterioPaginacion<CriterioBusquedaReporteCuadreCuentaPorPagar, ?> criterioPaginacion,
            SXSSFWorkbook sxssfWorkbook, int indiceSheetActual)
    {

        Sheet sheet = sxssfWorkbook.getSheetAt(0);
        sheet.setDefaultRowHeight((short) 380);
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();

        Font font = sxssfWorkbook.createFont();
        font.setFontName("Segoe UI");
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);

        Font fontBold = sxssfWorkbook.createFont();
        fontBold.setFontName("Segoe UI");
        fontBold.setBold(true);

        Font fontNormal = sxssfWorkbook.createFont();
        fontNormal.setFontName("Segoe UI");

        CellStyle estiloBack = sxssfWorkbook.createCellStyle();
        estiloBack.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        estiloBack.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBack.setBorderTop(BorderStyle.THIN);
        estiloBack.setBorderRight(BorderStyle.THIN);
        estiloBack.setBorderBottom(BorderStyle.THIN);
        estiloBack.setBorderLeft(BorderStyle.THIN);
        estiloBack.setFont(fontNormal);

        CellStyle estiloBackGrey = sxssfWorkbook.createCellStyle();
        estiloBackGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloBackGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBackGrey.setBorderTop(BorderStyle.THIN);
        estiloBackGrey.setBorderRight(BorderStyle.THIN);
        estiloBackGrey.setBorderBottom(BorderStyle.THIN);
        estiloBackGrey.setBorderLeft(BorderStyle.THIN);
        estiloBackGrey.setFont(fontNormal);

        CellStyle estiloCab = sxssfWorkbook.createCellStyle();
        estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCab.setAlignment(HorizontalAlignment.CENTER);
        estiloCab.setBorderTop(BorderStyle.THIN);
        estiloCab.setBorderRight(BorderStyle.THIN);
        estiloCab.setBorderBottom(BorderStyle.THIN);
        estiloCab.setBorderLeft(BorderStyle.THIN);
        estiloCab.setFont(font);

        CellStyle estiloCriterioValor = ExportacionUtil
                .crearEstiloBasico(sxssfWorkbook, fontBold);
        estiloCriterioValor.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estiloSimple = ExportacionUtil
                .crearEstiloBasico(sxssfWorkbook, fontNormal);

        CellStyle estiloNumero = ExportacionUtil
                .crearEstiloNumero(sxssfWorkbook, "#0", fontNormal);

        CellStyle estiloNumeroGrey = ExportacionUtil
                .crearEstiloNumero(sxssfWorkbook, "#0", fontNormal);
        estiloNumeroGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloNumeroGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle decimalDos = ExportacionUtil.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CellStyle decimalDosGrey = ExportacionUtil.crearEstiloNumero(
                sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00",
                fontNormal);
        decimalDosGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        decimalDosGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        logger.info(
                "Preparando consulta al VIEW ReporteCuadreCuentasPorPagarResumen");
        try
        {
            String cab[] = { "Moneda", "Fecha Proceso", "Fecha Transacción",
                    "Clase Transacción", "Cuenta Cargo", "Cuenta Abono",
                    "Cantidad Cargo", "Cantidad Abono", "Total Cargo",
                    "Total Abono", "Institución" };

            sheet.createFreezePane(0, 8);
            sheet.setAutoFilter(new CellRangeAddress(7, 7, 1, cab.length));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, cab.length));

            // Format de Date a dd/MM/yyyy
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;
            double totalFilas = 0;
            int numeroFilaContenidoTable = 8;
            boolean seObtuvoTotalFilas = false;

            row = sheet.createRow(0);
            cell = row.createCell(1);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(
                    "REPORTE DE CUADRE DE CUENTAS POR PAGAR  - RESUMEN COMPENSACION");
            for (int j = 2; j <= cab.length; j++)
            {
                row.createCell(j).setCellStyle(estiloCriterioValor);
            }

            row = sheet.createRow(1);
            for (int j = 1; j <= cab.length; j++)
            {
                row.createCell(j).setCellStyle(estiloCriterioValor);
            }

            row = sheet.createRow(5);
            cell = row.createCell(2);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha Inicio - Fin");
            cell = row.createCell(3);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionRangoFechas());

            cell = row.createCell(5);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(6);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionInstitucion());

            row = sheet.createRow(7);
            for (int i = 0; i < cab.length; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue(cab[i]);
            }

            logger.info("Ejecutando consulta: {}", indiceSheetActual);

            try
            {
                List<ReporteMoneda> monedas = this.buscarResumen(
                        criterioPaginacion.getCriterioBusqueda());

                for (int i = 0; i < monedas.size(); i++)
                {
                    double cantidadCargo = 0;
                    double cantidadAbono = 0;
                    double totalCargo = 0;
                    double totalAbono = 0;

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, cab.length));
                    row = sheet.createRow(numeroFilaContenidoTable);

                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBack);
                    cell.setCellValue(monedas.get(i).getCodigoMoneda() + " - "
                            + monedas.get(i).getDescripcionMoneda());
                    for (int j = 2; j <= cab.length; j++)
                    {
                        row.createCell(j).setCellStyle(estiloBack);
                    }

                    numeroFilaContenidoTable++;
                    numeroFila++;

                    List<ReporteCuadreCuentasPorPagarResumen> resultSet = monedas
                            .get(i).getCuentasPorPagarResumen();
                    int tamReporte = resultSet.size();
                    for (int j = 0; j < tamReporte; j++)
                    {
                        if (!seObtuvoTotalFilas)
                        {
                            totalFilas = 0;
                            for (int k = 0; k < monedas.size(); k++)
                            {
                                totalFilas += monedas.get(k)
                                        .getCuentasPorPagarResumen().size();
                            }
                            seObtuvoTotalFilas = true;
                            logger.info(
                                    "El total de filas de la consulta es: {}",
                                    (int) totalFilas);
                            totalFilas += 1 + 2 * (monedas.size() - 1);
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

                        row = sheet.createRow(numeroFilaContenidoTable);

                        cell = row.createCell(1);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(monedas.get(i).getCodigoMoneda()
                                + " - "
                                + monedas.get(i).getDescripcionMoneda());

                        cell = row.createCell(2);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getFechaProceso() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaProceso())
                                        : "");

                        cell = row.createCell(3);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getFechaTransaccion() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaTransaccion())
                                        : "");

                        cell = row.createCell(4);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getCodigoClaseTransaccion() + " - "
                                + resultSet.get(j)
                                        .getDescripcionClaseTransaccion());

                        cell = row.createCell(5);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCuentaCargo());

                        cell = row.createCell(6);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCuentaAbono());

                        cell = row.createCell(7);
                        cell.setCellStyle(estiloNumero);
                        cell.setCellValue(resultSet.get(j).getCantidadCargo());

                        cell = row.createCell(8);
                        cell.setCellStyle(estiloNumero);
                        cell.setCellValue(resultSet.get(j).getCantidadAbono());

                        cell = row.createCell(9);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getTotalCargo());

                        cell = row.createCell(10);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getTotalAbono());

                        cell = row.createCell(11);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getCodigoInstitucion() + " - "
                                + resultSet.get(j).getDescripcionInstitucion());

                        cantidadCargo += resultSet.get(j).getCantidadCargo();
                        cantidadAbono += resultSet.get(j).getCantidadAbono();
                        totalCargo += resultSet.get(j).getTotalCargo();
                        totalAbono += resultSet.get(j).getTotalAbono();

                        numeroFilaContenidoTable++;
                        numeroFila++;

                    }

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, 6));
                    row = sheet.createRow(numeroFilaContenidoTable);
                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBackGrey);
                    cell.setCellValue("TOTAL");

                    for (int k = 2; k < 7; k++)
                    {
                        row.createCell(k).setCellStyle(estiloBackGrey);
                    }

                    cell = row.createCell(7);
                    cell.setCellStyle(estiloNumeroGrey);
                    cell.setCellValue(cantidadCargo);

                    cell = row.createCell(8);
                    cell.setCellStyle(estiloNumeroGrey);
                    cell.setCellValue(cantidadAbono);

                    cell = row.createCell(9);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalCargo);

                    cell = row.createCell(10);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalAbono);

                    row.createCell(11).setCellStyle(estiloBackGrey);
                    numeroFilaContenidoTable++;
                    numeroFila++;
                }
            } catch (Exception e)
            {
                logger.error("Ocurrió un error: {}", e.getMessage());
            }

            logger.info("Consulta {} terminada", indiceSheetActual);

            for (int i = 1; i <= cab.length; i++)
            {
                sheet.autoSizeColumn(i);
            }

            return (criterioPaginacion.getStart()
                    + criterioPaginacion.getLength()) >= totalFilas;

        } catch (Exception e)
        {
            logger.error("Error: {}", e.getMessage());
            throw new SimpException(
                    "Ocurrió un error al recuperar los registros de la base de datos");
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarDetalleCompensacion(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            HttpServletResponse response) throws IOException
    {
        String fileNameZip = "Reporte Cuadre de Cuentas por Pagar - Detalle Compensacion.zip";
        String fileName = "Reporte Cuadre de Cuentas por Pagar - Detalle Compensacion";

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + fileNameZip);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(
                byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();

        XSSFWorkbook workbook = new XSSFWorkbook(
                getClass().getClassLoader().getResourceAsStream(
                        "xlsx/reporteCuadreCuentasPorPagarDetalle.xlsx"));

        logger.info("Criterios: {}", criterioBusqueda);
        logger.info(
                "Iniciando el proceso de consulta y generación del reporte");

        SXSSFWorkbook sxssfWorkbook = null;

        try
        {
            int inicio = 0;
            int indiceSheetActual = 1;

            do
            {
                logger.info("Estableciendo los criterios de paginación");
                CriterioPaginacion<CriterioBusquedaReporteCuadreCuentaPorPagar, ?> criterioPaginacion = PaginacionUtil
                        .getCriterioPaginacionParaReporteXLSX(criterioBusqueda,
                                inicio, MAX);
                logger.info(
                        "Se preparó los criterios de paginación {} para el sheet {}",
                        criterioPaginacion, indiceSheetActual);

                sxssfWorkbook = new SXSSFWorkbook(workbook,
                        SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
                logger.info("Generando reporte: {}", indiceSheetActual);

                reporteCompleto = this.construirReporteDetalleCompensacion(
                        criterioPaginacion, sxssfWorkbook, indiceSheetActual);

                ZipEntry zipEntry = new ZipEntry(
                        fileName + "_" + indiceSheetActual + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                sxssfWorkbook
                        .write(new ExportFilterOutputStream(zipOutputStream));
                zipOutputStream.closeEntry();
                inicio += MAX;
                indiceSheetActual++;
            } while (!reporteCompleto);
            logger.info("¿Reporte Completo?: {}", reporteCompleto);
            zipOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(bytes);
            servletOutputStream.flush();
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message(
                            procesandoRegistro, reporteCompleto, false));
                }
            }
        } catch (ClientAbortException e)
        {
            logger.error("cancelado: {} ", e.getMessage());

        } catch (Exception e)
        {
            logger.error("Ocurrio un error al tratar de generar el reporte: {}",
                    e);
        } finally
        {
            cancelado = false;
            reporteCompleto = false;
            totalArchivos = 0;
            procesandoRegistro = 0;
            numeroFila = 1;
        }
    }

    private boolean construirReporteDetalleCompensacion(
            CriterioPaginacion<CriterioBusquedaReporteCuadreCuentaPorPagar, ?> criterioPaginacion,
            SXSSFWorkbook sxssfWorkbook, int indiceSheetActual)
    {

        Sheet sheet = sxssfWorkbook.getSheetAt(0);
        sheet.setDefaultRowHeight((short) 380);
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();

        Font font = sxssfWorkbook.createFont();
        font.setFontName("Segoe UI");
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);

        Font fontBold = sxssfWorkbook.createFont();
        fontBold.setFontName("Segoe UI");
        fontBold.setBold(true);

        Font fontNormal = sxssfWorkbook.createFont();
        fontNormal.setFontName("Segoe UI");

        CellStyle estiloBack = sxssfWorkbook.createCellStyle();
        estiloBack.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        estiloBack.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBack.setBorderTop(BorderStyle.THIN);
        estiloBack.setBorderRight(BorderStyle.THIN);
        estiloBack.setBorderBottom(BorderStyle.THIN);
        estiloBack.setBorderLeft(BorderStyle.THIN);
        estiloBack.setFont(fontNormal);

        CellStyle estiloBackGrey = sxssfWorkbook.createCellStyle();
        estiloBackGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloBackGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBackGrey.setBorderTop(BorderStyle.THIN);
        estiloBackGrey.setBorderRight(BorderStyle.THIN);
        estiloBackGrey.setBorderBottom(BorderStyle.THIN);
        estiloBackGrey.setBorderLeft(BorderStyle.THIN);
        estiloBackGrey.setFont(fontNormal);

        CellStyle estiloCab = sxssfWorkbook.createCellStyle();
        estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCab.setAlignment(HorizontalAlignment.CENTER);
        estiloCab.setBorderTop(BorderStyle.THIN);
        estiloCab.setBorderRight(BorderStyle.THIN);
        estiloCab.setBorderBottom(BorderStyle.THIN);
        estiloCab.setBorderLeft(BorderStyle.THIN);
        estiloCab.setFont(font);

        CellStyle estiloCriterioValor = ExportacionUtil
                .crearEstiloBasico(sxssfWorkbook, fontBold);
        estiloCriterioValor.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estiloSimple = ExportacionUtil
                .crearEstiloBasico(sxssfWorkbook, fontNormal);

        CellStyle estiloNumero = ExportacionUtil
                .crearEstiloNumero(sxssfWorkbook, "#0", fontNormal);

        CellStyle estiloNumeroGrey = ExportacionUtil
                .crearEstiloNumero(sxssfWorkbook, "#0", fontNormal);
        estiloNumeroGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloNumeroGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle decimalDos = ExportacionUtil.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CellStyle decimalDosGrey = ExportacionUtil.crearEstiloNumero(
                sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00",
                fontNormal);
        decimalDosGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        decimalDosGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        logger.info(
                "Preparando consulta al VIEW ReporteCuadreCuentasPorPagarDetalle");
        try
        {
            String cab[] = { "Moneda", "Fecha Proceso", "Fecha Transacción",
                    "Origen Transacción", "Clase Transacción",
                    "Código Transacción", "Cuenta Cargo", "Cuenta Abono",
                    "Cantidad Cargo", "Cantidad Abono", "Total Cargo",
                    "Total Abono", "Institución" };

            sheet.createFreezePane(0, 8);
            sheet.setAutoFilter(new CellRangeAddress(7, 7, 1, cab.length));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, cab.length));

            // Format de Date a dd/MM/yyyy
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;
            double totalFilas = 0;
            int numeroFilaContenidoTable = 8;
            boolean seObtuvoTotalFilas = false;

            row = sheet.createRow(0);
            cell = row.createCell(1);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(
                    "REPORTE DE CUADRE DE CUENTAS POR PAGAR  - DETALLE COMPENSACION");
            for (int j = 2; j <= cab.length; j++)
            {
                row.createCell(j).setCellStyle(estiloCriterioValor);
            }

            row = sheet.createRow(1);
            for (int j = 1; j <= cab.length; j++)
            {
                row.createCell(j).setCellStyle(estiloCriterioValor);
            }

            row = sheet.createRow(5);
            cell = row.createCell(2);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha Inicio - Fin");
            cell = row.createCell(3);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionRangoFechas());

            cell = row.createCell(5);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(6);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionInstitucion());

            row = sheet.createRow(7);
            for (int i = 0; i < cab.length; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue(cab[i]);
            }

            logger.info("Ejecutando consulta: {}", indiceSheetActual);

            try
            {
                List<ReporteMoneda> monedas = this.buscarDetalle(
                        criterioPaginacion.getCriterioBusqueda());

                for (int i = 0; i < monedas.size(); i++)
                {
                    double cantidadCargo = 0;
                    double cantidadAbono = 0;
                    double totalCargo = 0;
                    double totalAbono = 0;

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, cab.length));
                    row = sheet.createRow(numeroFilaContenidoTable);

                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBack);
                    cell.setCellValue(monedas.get(i).getCodigoMoneda() + " - "
                            + monedas.get(i).getDescripcionMoneda());
                    for (int j = 2; j <= cab.length; j++)
                    {
                        row.createCell(j).setCellStyle(estiloBack);
                    }

                    numeroFilaContenidoTable++;
                    numeroFila++;

                    List<ReporteCuadreCuentasPorPagarDetalle> resultSet = monedas
                            .get(i).getCuentasPorPagarDetalle();
                    int tamReporte = resultSet.size();
                    for (int j = 0; j < tamReporte; j++)
                    {
                        if (!seObtuvoTotalFilas)
                        {
                            totalFilas = 0;
                            for (int k = 0; k < monedas.size(); k++)
                            {
                                totalFilas += monedas.get(k)
                                        .getCuentasPorPagarDetalle().size();
                            }
                            seObtuvoTotalFilas = true;
                            logger.info(
                                    "El total de filas de la consulta es: {}",
                                    (int) totalFilas);
                            totalFilas += 1 + 2 * (monedas.size() - 1);
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

                        row = sheet.createRow(numeroFilaContenidoTable);

                        cell = row.createCell(1);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(monedas.get(i).getCodigoMoneda()
                                + " - "
                                + monedas.get(i).getDescripcionMoneda());

                        cell = row.createCell(2);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getFechaProceso() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaProceso())
                                        : "");

                        cell = row.createCell(3);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getFechaTransaccion() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaTransaccion())
                                        : "");

                        cell = row.createCell(4);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getCodigoOrigenTransaccion() + " - "
                                + resultSet.get(j)
                                        .getDescripcionOrigenTransaccion());

                        cell = row.createCell(5);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getIdClaseTransaccion() + " - "
                                + resultSet.get(j)
                                        .getDescripcionClaseTransaccion());

                        cell = row.createCell(6);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getIdCodigoTransaccion() + " - "
                                + resultSet.get(j)
                                        .getDescripcionCodigoTransaccion());

                        cell = row.createCell(7);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCuentaCargo());

                        cell = row.createCell(8);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCuentaAbono());

                        cell = row.createCell(9);
                        cell.setCellStyle(estiloNumero);
                        cell.setCellValue(resultSet.get(j).getCantidadCargo());

                        cell = row.createCell(10);
                        cell.setCellStyle(estiloNumero);
                        cell.setCellValue(resultSet.get(j).getCantidadAbono());

                        cell = row.createCell(11);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getTotalCargo());

                        cell = row.createCell(12);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getTotalAbono());

                        cell = row.createCell(13);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getCodigoInstitucion() + " - "
                                + resultSet.get(j).getDescripcionInstitucion());

                        cantidadCargo += resultSet.get(j).getCantidadCargo();
                        cantidadAbono += resultSet.get(j).getCantidadAbono();
                        totalCargo += resultSet.get(j).getTotalCargo();
                        totalAbono += resultSet.get(j).getTotalAbono();

                        numeroFilaContenidoTable++;
                        numeroFila++;

                    }

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, 8));
                    row = sheet.createRow(numeroFilaContenidoTable);
                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBackGrey);
                    cell.setCellValue("TOTAL");

                    for (int k = 2; k < 9; k++)
                    {
                        row.createCell(k).setCellStyle(estiloBackGrey);
                    }

                    cell = row.createCell(9);
                    cell.setCellStyle(estiloNumeroGrey);
                    cell.setCellValue(cantidadCargo);

                    cell = row.createCell(10);
                    cell.setCellStyle(estiloNumeroGrey);
                    cell.setCellValue(cantidadAbono);

                    cell = row.createCell(11);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalCargo);

                    cell = row.createCell(12);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalAbono);

                    row.createCell(13).setCellStyle(estiloBackGrey);
                    numeroFilaContenidoTable++;
                    numeroFila++;
                }
            } catch (Exception e)
            {
                logger.error("Ocurrió un error: {}", e.getMessage());
            }

            logger.info("Consulta {} terminada", indiceSheetActual);

            for (int i = 1; i <= cab.length; i++)
            {
                sheet.autoSizeColumn(i);
            }

            return (criterioPaginacion.getStart()
                    + criterioPaginacion.getLength()) >= totalFilas;

        } catch (Exception e)
        {
            logger.error("Error: {}", e.getMessage());
            throw new SimpException(
                    "Ocurrió un error al recuperar los registros de la base de datos");
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarResumenAutorizaciones(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            HttpServletResponse response) throws IOException
    {
        String fileNameZip = "Reporte Cuadre de Cuentas por Pagar - Resumen Autorizaciones.zip";
        String fileName = "Reporte Cuadre de Cuentas por Pagar - Resumen Autorizaciones";

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + fileNameZip);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(
                byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();

        XSSFWorkbook workbook = new XSSFWorkbook(
                getClass().getClassLoader().getResourceAsStream(
                        "xlsx/reporteCuadreCuentasPorPagarAutDia.xlsx"));

        logger.info("Criterios: {}", criterioBusqueda);
        logger.info(
                "Iniciando el proceso de consulta y generación del reporte");

        SXSSFWorkbook sxssfWorkbook = null;

        try
        {
            int inicio = 0;
            int indiceSheetActual = 1;

            do
            {
                logger.info("Estableciendo los criterios de paginación");
                CriterioPaginacion<CriterioBusquedaReporteCuadreCuentaPorPagar, ?> criterioPaginacion = PaginacionUtil
                        .getCriterioPaginacionParaReporteXLSX(criterioBusqueda,
                                inicio, MAX);
                logger.info(
                        "Se preparó los criterios de paginación {} para el sheet {}",
                        criterioPaginacion, indiceSheetActual);

                sxssfWorkbook = new SXSSFWorkbook(workbook,
                        SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
                logger.info("Generando reporte: {}", indiceSheetActual);

                reporteCompleto = this.construirReporteResumenAutorizaciones(
                        criterioPaginacion, sxssfWorkbook, indiceSheetActual);

                ZipEntry zipEntry = new ZipEntry(
                        fileName + "_" + indiceSheetActual + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                sxssfWorkbook
                        .write(new ExportFilterOutputStream(zipOutputStream));
                zipOutputStream.closeEntry();
                inicio += MAX;
                indiceSheetActual++;
            } while (!reporteCompleto);
            logger.info("¿Reporte Completo?: {}", reporteCompleto);
            zipOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(bytes);
            servletOutputStream.flush();
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message(
                            procesandoRegistro, reporteCompleto, false));
                }
            }
        } catch (ClientAbortException e)
        {
            logger.error("cancelado: {} ", e.getMessage());

        } catch (Exception e)
        {
            logger.error("Ocurrio un error al tratar de generar el reporte: {}",
                    e);
        } finally
        {
            cancelado = false;
            reporteCompleto = false;
            totalArchivos = 0;
            procesandoRegistro = 0;
            numeroFila = 1;
        }
    }

    private boolean construirReporteResumenAutorizaciones(
            CriterioPaginacion<CriterioBusquedaReporteCuadreCuentaPorPagar, ?> criterioPaginacion,
            SXSSFWorkbook sxssfWorkbook, int indiceSheetActual)
    {
        Sheet sheet = sxssfWorkbook.getSheetAt(0);
        sheet.setDefaultRowHeight((short) 380);
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();

        Font font = sxssfWorkbook.createFont();
        font.setFontName("Segoe UI");
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);

        Font fontBold = sxssfWorkbook.createFont();
        fontBold.setFontName("Segoe UI");
        fontBold.setBold(true);

        Font fontNormal = sxssfWorkbook.createFont();
        fontNormal.setFontName("Segoe UI");

        CellStyle estiloBack = sxssfWorkbook.createCellStyle();
        estiloBack.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        estiloBack.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBack.setBorderTop(BorderStyle.THIN);
        estiloBack.setBorderRight(BorderStyle.THIN);
        estiloBack.setBorderBottom(BorderStyle.THIN);
        estiloBack.setBorderLeft(BorderStyle.THIN);
        estiloBack.setFont(fontNormal);

        CellStyle estiloBackGrey = sxssfWorkbook.createCellStyle();
        estiloBackGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloBackGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBackGrey.setBorderTop(BorderStyle.THIN);
        estiloBackGrey.setBorderRight(BorderStyle.THIN);
        estiloBackGrey.setBorderBottom(BorderStyle.THIN);
        estiloBackGrey.setBorderLeft(BorderStyle.THIN);
        estiloBackGrey.setFont(fontNormal);

        CellStyle estiloCab = sxssfWorkbook.createCellStyle();
        estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCab.setAlignment(HorizontalAlignment.CENTER);
        estiloCab.setBorderTop(BorderStyle.THIN);
        estiloCab.setBorderRight(BorderStyle.THIN);
        estiloCab.setBorderBottom(BorderStyle.THIN);
        estiloCab.setBorderLeft(BorderStyle.THIN);
        estiloCab.setFont(font);

        CellStyle estiloCriterioValor = ExportacionUtil
                .crearEstiloBasico(sxssfWorkbook, fontBold);
        estiloCriterioValor.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estiloSimple = ExportacionUtil
                .crearEstiloBasico(sxssfWorkbook, fontNormal);

        CellStyle estiloNumero = ExportacionUtil
                .crearEstiloNumero(sxssfWorkbook, "#0", fontNormal);

        CellStyle estiloNumeroGrey = ExportacionUtil
                .crearEstiloNumero(sxssfWorkbook, "#0", fontNormal);
        estiloNumeroGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloNumeroGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle decimalDos = ExportacionUtil.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CellStyle decimalDosGrey = ExportacionUtil.crearEstiloNumero(
                sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00",
                fontNormal);
        decimalDosGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        decimalDosGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        logger.info(
                "Preparando consulta al VIEW ReporteCuadreCuentasPorPagarAutDelDia");
        try
        {
            String cab[] = { "Moneda", "Fecha Proceso", "Fecha Transacción",
                    "Tipo Mensaje", "Conciliación", "Cuenta Cargo",
                    "Cuenta Abono", "Cantidad Cargo", "Cantidad Abono",
                    "Total Cargo", "Total Abono", "Institución" };

            sheet.createFreezePane(0, 8);
            sheet.setAutoFilter(new CellRangeAddress(7, 7, 1, cab.length));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, cab.length));

            // Format de Date a dd/MM/yyyy
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;
            double totalFilas = 0;
            int numeroFilaContenidoTable = 8;
            boolean seObtuvoTotalFilas = false;

            row = sheet.createRow(0);
            cell = row.createCell(1);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(
                    "REPORTE DE CUADRE DE CUENTAS POR PAGAR  - RESUMEN AUTORIZACIONES");
            for (int j = 2; j <= cab.length; j++)
            {
                row.createCell(j).setCellStyle(estiloCriterioValor);
            }

            row = sheet.createRow(1);
            for (int j = 1; j <= cab.length; j++)
            {
                row.createCell(j).setCellStyle(estiloCriterioValor);
            }

            row = sheet.createRow(5);
            cell = row.createCell(2);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha Inicio - Fin");
            cell = row.createCell(3);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionRangoFechas());

            cell = row.createCell(5);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(6);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionInstitucion());

            row = sheet.createRow(7);
            for (int i = 0; i < cab.length; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue(cab[i]);
            }

            logger.info("Ejecutando consulta: {}", indiceSheetActual);

            try
            {
                List<ReporteMoneda> monedas = this.buscarAutorizacionesDelDia(
                        criterioPaginacion.getCriterioBusqueda());

                for (int i = 0; i < monedas.size(); i++)
                {
                    double cantidadCargo = 0;
                    double cantidadAbono = 0;
                    double totalCargo = 0;
                    double totalAbono = 0;

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, cab.length));
                    row = sheet.createRow(numeroFilaContenidoTable);

                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBack);
                    cell.setCellValue(monedas.get(i).getCodigoMoneda() + " - "
                            + monedas.get(i).getDescripcionMoneda());
                    for (int j = 2; j <= cab.length; j++)
                    {
                        row.createCell(j).setCellStyle(estiloBack);
                    }

                    numeroFilaContenidoTable++;
                    numeroFila++;

                    List<ReporteCuadreCuentasPorPagarResumen> resultSet = monedas
                            .get(i).getCuentasPorPagarResumen();
                    int tamReporte = resultSet.size();
                    for (int j = 0; j < tamReporte; j++)
                    {
                        if (!seObtuvoTotalFilas)
                        {
                            totalFilas = 0;
                            for (int k = 0; k < monedas.size(); k++)
                            {
                                totalFilas += monedas.get(k)
                                        .getCuentasPorPagarResumen().size();
                            }
                            seObtuvoTotalFilas = true;
                            logger.info(
                                    "El total de filas de la consulta es: {}",
                                    (int) totalFilas);
                            totalFilas += 1 + 2 * (monedas.size() - 1);
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

                        row = sheet.createRow(numeroFilaContenidoTable);

                        cell = row.createCell(1);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(monedas.get(i).getCodigoMoneda()
                                + " - "
                                + monedas.get(i).getDescripcionMoneda());

                        cell = row.createCell(2);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getFechaProceso() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaProceso())
                                        : "");

                        cell = row.createCell(3);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getFechaTransaccion() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaTransaccion())
                                        : "");

                        cell = row.createCell(4);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getTipoMensaje());

                        cell = row.createCell(5);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getCodigoConciliacion() + " - "
                                        + resultSet.get(j)
                                                .getDescripcionConciliacion());

                        cell = row.createCell(6);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCuentaCargo());

                        cell = row.createCell(7);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCuentaAbono());

                        cell = row.createCell(8);
                        cell.setCellStyle(estiloNumero);
                        cell.setCellValue(resultSet.get(j).getCantidadCargo());

                        cell = row.createCell(9);
                        cell.setCellStyle(estiloNumero);
                        cell.setCellValue(resultSet.get(j).getCantidadAbono());

                        cell = row.createCell(10);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getTotalCargo());

                        cell = row.createCell(11);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getTotalAbono());

                        cell = row.createCell(12);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getCodigoInstitucion() + " - "
                                + resultSet.get(j).getDescripcionInstitucion());

                        cantidadCargo += resultSet.get(j).getCantidadCargo();
                        cantidadAbono += resultSet.get(j).getCantidadAbono();
                        totalCargo += resultSet.get(j).getTotalCargo();
                        totalAbono += resultSet.get(j).getTotalAbono();

                        numeroFilaContenidoTable++;
                        numeroFila++;

                    }

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, 7));
                    row = sheet.createRow(numeroFilaContenidoTable);
                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBackGrey);
                    cell.setCellValue("TOTAL");

                    for (int k = 2; k < 8; k++)
                    {
                        row.createCell(k).setCellStyle(estiloBackGrey);
                    }

                    cell = row.createCell(8);
                    cell.setCellStyle(estiloNumeroGrey);
                    cell.setCellValue(cantidadCargo);

                    cell = row.createCell(9);
                    cell.setCellStyle(estiloNumeroGrey);
                    cell.setCellValue(cantidadAbono);

                    cell = row.createCell(10);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalCargo);

                    cell = row.createCell(11);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalAbono);

                    row.createCell(12).setCellStyle(estiloBackGrey);
                    numeroFilaContenidoTable++;
                    numeroFila++;
                }
            } catch (Exception e)
            {
                logger.error("Ocurrió un error: {}", e.getMessage());
            }

            logger.info("Consulta {} terminada", indiceSheetActual);

            for (int i = 1; i <= cab.length; i++)
            {
                sheet.autoSizeColumn(i);
            }

            return (criterioPaginacion.getStart()
                    + criterioPaginacion.getLength()) >= totalFilas;

        } catch (Exception e)
        {
            logger.error("Error: {}", e.getMessage());
            throw new SimpException(
                    "Ocurrió un error al recuperar los registros de la base de datos");
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarDetalleAutorizaciones(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            HttpServletResponse response) throws IOException
    {
        String fileNameZip = "Reporte Cuadre de Cuentas por Pagar - Detalle Autorizaciones.zip";
        String fileName = "Reporte Cuadre de Cuentas por Pagar - Detalle Autorizaciones";

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + fileNameZip);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(
                byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();

        XSSFWorkbook workbook = new XSSFWorkbook(
                getClass().getClassLoader().getResourceAsStream(
                        "xlsx/reporteCuadreCuentasPorPagarAutDetalle.xlsx"));

        logger.info("Criterios: {}", criterioBusqueda);
        logger.info(
                "Iniciando el proceso de consulta y generación del reporte");
        
        
        boolean puedeVisualizarTarjeta = this.usuarioService.puedeVisualizarPAN(
                SecurityContextFacade.obtenerNombreUsuario());
        
        SXSSFWorkbook sxssfWorkbook = null;

        try
        {
            int inicio = 0;
            int indiceSheetActual = 1;

            do
            {
                logger.info("Estableciendo los criterios de paginación");
                CriterioPaginacion<CriterioBusquedaReporteCuadreCuentaPorPagar, ?> criterioPaginacion = PaginacionUtil
                        .getCriterioPaginacionParaReporteXLSX(criterioBusqueda,
                                inicio, MAX);
                logger.info(
                        "Se preparó los criterios de paginación {} para el sheet {}",
                        criterioPaginacion, indiceSheetActual);

                sxssfWorkbook = new SXSSFWorkbook(workbook,
                        SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
                logger.info("Generando reporte: {}", indiceSheetActual);

                reporteCompleto = this.construirReporteDetalleAutorizaciones(
                        criterioPaginacion, sxssfWorkbook, indiceSheetActual, puedeVisualizarTarjeta);

                ZipEntry zipEntry = new ZipEntry(
                        fileName + "_" + indiceSheetActual + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                sxssfWorkbook
                        .write(new ExportFilterOutputStream(zipOutputStream));
                zipOutputStream.closeEntry();
                inicio += MAX;
                indiceSheetActual++;
            } while (!reporteCompleto);
            logger.info("¿Reporte Completo?: {}", reporteCompleto);
            zipOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(bytes);
            servletOutputStream.flush();
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message(
                            procesandoRegistro, reporteCompleto, false));
                }
            }
        } catch (ClientAbortException e)
        {
            logger.error("cancelado: {} ", e.getMessage());

        } catch (Exception e)
        {
            logger.error("Ocurrio un error al tratar de generar el reporte: {}",
                    e);
        } finally
        {
            cancelado = false;
            reporteCompleto = false;
            totalArchivos = 0;
            procesandoRegistro = 0;
            numeroFila = 1;
        }
    }

    private boolean construirReporteDetalleAutorizaciones(
            CriterioPaginacion<CriterioBusquedaReporteCuadreCuentaPorPagar, ?> criterioPaginacion,
            SXSSFWorkbook sxssfWorkbook, int indiceSheetActual, boolean puedeVisualizarTarjeta)
    {
        Sheet sheet = sxssfWorkbook.getSheetAt(0);
        sheet.setDefaultRowHeight((short) 380);
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();

        Font font = sxssfWorkbook.createFont();
        font.setFontName("Segoe UI");
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);

        Font fontBold = sxssfWorkbook.createFont();
        fontBold.setFontName("Segoe UI");
        fontBold.setBold(true);

        Font fontNormal = sxssfWorkbook.createFont();
        fontNormal.setFontName("Segoe UI");

        CellStyle estiloBack = sxssfWorkbook.createCellStyle();
        estiloBack.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        estiloBack.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBack.setBorderTop(BorderStyle.THIN);
        estiloBack.setBorderRight(BorderStyle.THIN);
        estiloBack.setBorderBottom(BorderStyle.THIN);
        estiloBack.setBorderLeft(BorderStyle.THIN);
        estiloBack.setFont(fontNormal);

        CellStyle estiloBackGrey = sxssfWorkbook.createCellStyle();
        estiloBackGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloBackGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloBackGrey.setBorderTop(BorderStyle.THIN);
        estiloBackGrey.setBorderRight(BorderStyle.THIN);
        estiloBackGrey.setBorderBottom(BorderStyle.THIN);
        estiloBackGrey.setBorderLeft(BorderStyle.THIN);
        estiloBackGrey.setFont(fontNormal);

        CellStyle estiloCab = sxssfWorkbook.createCellStyle();
        estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCab.setAlignment(HorizontalAlignment.CENTER);
        estiloCab.setBorderTop(BorderStyle.THIN);
        estiloCab.setBorderRight(BorderStyle.THIN);
        estiloCab.setBorderBottom(BorderStyle.THIN);
        estiloCab.setBorderLeft(BorderStyle.THIN);
        estiloCab.setFont(font);

        CellStyle estiloCriterioValor = ExportacionUtil
                .crearEstiloBasico(sxssfWorkbook, fontBold);
        estiloCriterioValor.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estiloSimple = ExportacionUtil
                .crearEstiloBasico(sxssfWorkbook, fontNormal);

        CellStyle estiloNumeroGrey = ExportacionUtil
                .crearEstiloNumero(sxssfWorkbook, "#0", fontNormal);
        estiloNumeroGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        estiloNumeroGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle decimalDos = ExportacionUtil.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CellStyle decimalDosGrey = ExportacionUtil.crearEstiloNumero(
                sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00",
                fontNormal);
        decimalDosGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        decimalDosGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        logger.info(
                "Preparando consulta al VIEW ReporteCuadreCuentasPorPagarAutDetalle");
        try
        {
            String cab[] = { "Moneda", "Fecha Proceso", "Tipo Mensaje",
                    "N° Tarjeta", "N° Cuenta", "Fecha Adquiriente",
                    "Hora Adquiriente", "Fecha Local", "Hora Local", "Trace",
                    "Cód. Autorización", "Proceso", "Canal", "Adquiriente",
                    "BIN Adquiriente", "Respuesta", "Conciliación",
                    "Fecha Concilia Log", "Monto", "Institución" };

            sheet.createFreezePane(0, 8);
            sheet.setAutoFilter(new CellRangeAddress(7, 7, 1, cab.length));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, cab.length));

            // Format de Date a dd/MM/yyyy
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;
            double totalFilas = 0;
            int numeroFilaContenidoTable = 8;
            boolean seObtuvoTotalFilas = false;

            row = sheet.createRow(0);
            cell = row.createCell(1);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(
                    "REPORTE DE CUADRE DE CUENTAS POR PAGAR  - DETALLE AUTORIZACIONES");
            for (int j = 2; j <= cab.length; j++)
            {
                row.createCell(j).setCellStyle(estiloCriterioValor);
            }

            row = sheet.createRow(1);
            for (int j = 1; j <= cab.length; j++)
            {
                row.createCell(j).setCellStyle(estiloCriterioValor);
            }

            row = sheet.createRow(5);
            cell = row.createCell(2);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha Inicio - Fin");
            cell = row.createCell(3);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionRangoFechas());

            cell = row.createCell(5);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(6);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionInstitucion());

            row = sheet.createRow(7);
            for (int i = 0; i < cab.length; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue(cab[i]);
            }

            logger.info("Ejecutando consulta: {}", indiceSheetActual);

            try
            {
                List<ReporteMoneda> monedas = this.buscarDetalleAutorizaciones(
                        criterioPaginacion.getCriterioBusqueda());

                for (int i = 0; i < monedas.size(); i++)
                {
                    double totalMonto = 0;

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, cab.length));
                    row = sheet.createRow(numeroFilaContenidoTable);

                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBack);
                    cell.setCellValue(monedas.get(i).getCodigoMoneda() + " - "
                            + monedas.get(i).getDescripcionMoneda());
                    for (int j = 2; j <= cab.length; j++)
                    {
                        row.createCell(j).setCellStyle(estiloBack);
                    }

                    numeroFilaContenidoTable++;
                    numeroFila++;

                    List<ReporteCuadreCuentasPorPagarAutDetalle> resultSet = monedas
                            .get(i).getCuentasPorPagarAutDetalle();
                    int tamReporte = resultSet.size();
                    for (int j = 0; j < tamReporte; j++)
                    {
                        if (!seObtuvoTotalFilas)
                        {
                            totalFilas = 0;
                            for (int k = 0; k < monedas.size(); k++)
                            {
                                totalFilas += monedas.get(k)
                                        .getCuentasPorPagarAutDetalle().size();
                            }
                            seObtuvoTotalFilas = true;
                            logger.info(
                                    "El total de filas de la consulta es: {}",
                                    (int) totalFilas);
                            totalFilas += 1 + 2 * (monedas.size() - 1);
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

                        row = sheet.createRow(numeroFilaContenidoTable);

                        cell = row.createCell(1);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(monedas.get(i).getCodigoMoneda()
                                + " - "
                                + monedas.get(i).getDescripcionMoneda());

                        cell = row.createCell(2);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getFechaProceso() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaProceso())
                                        : "");

                        cell = row.createCell(3);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getTipoMensaje());

                        cell = row.createCell(4);
                        cell.setCellStyle(estiloSimple);
                        
                        if (puedeVisualizarTarjeta)
                        {
                            cell.setCellValue(resultSet.get(j).getNumeroTarjeta());
                        } else
                        {
                            cell.setCellValue(StringsUtils.maskCCNumber(
                                    resultSet.get(j).getNumeroTarjeta(), 6, 4));
                        }

                        cell = row.createCell(5);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getNumeroCuenta());

                        cell = row.createCell(6);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getFechaAdquirente() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaAdquirente())
                                        : "");

                        cell = row.createCell(7);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getHoraAdquirente());

                        cell = row.createCell(8);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getFechaLocal() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaLocal())
                                        : "");

                        cell = row.createCell(9);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getHoraLocal());

                        cell = row.createCell(10);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getNumeroTrace());

                        cell = row.createCell(11);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getCodigoAutorizacion());

                        cell = row.createCell(12);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCodigoProceso()
                                + " - "
                                + resultSet.get(j).getDescripcionProceso());

                        cell = row.createCell(13);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCodigoCanal()
                                + " - "
                                + resultSet.get(j).getDescripcionCanal());

                        cell = row.createCell(14);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getDescripcionAdquirente());

                        cell = row.createCell(15);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getBinAdquirente()
                                + " - "
                                + (resultSet.get(j)
                                        .getDescripcionBinAdquirente() == null
                                                ? ""
                                                : resultSet.get(j)
                                                        .getDescripcionBinAdquirente()));

                        cell = row.createCell(16);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCodigoRespuesta()
                                + " - "
                                + resultSet.get(j).getDescripcionRespuesta());

                        cell = row.createCell(17);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getCodigoConciliacion() + " - "
                                        + resultSet.get(j)
                                                .getDescripcionConciliacion());

                        cell = row.createCell(18);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getFechaConciliacionLog() != null
                                        ? formatter.format(resultSet.get(j)
                                                .getFechaConciliacionLog())
                                        : "");

                        cell = row.createCell(19);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getMonto());

                        cell = row.createCell(20);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getCodigoInstitucion() + " - "
                                + resultSet.get(j).getDescripcionInstitucion());

                        totalMonto += resultSet.get(j).getMonto();

                        numeroFilaContenidoTable++;
                        numeroFila++;

                    }

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, 18));
                    row = sheet.createRow(numeroFilaContenidoTable);
                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBackGrey);
                    cell.setCellValue("TOTAL");

                    for (int k = 2; k < 19; k++)
                    {
                        row.createCell(k).setCellStyle(estiloBackGrey);
                    }

                    cell = row.createCell(19);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalMonto);

                    row.createCell(20).setCellStyle(estiloBackGrey);
                    numeroFilaContenidoTable++;
                    numeroFila++;
                }
            } catch (Exception e)
            {
                logger.error("Ocurrió un error: {}", e.getMessage());
            }

            logger.info("Consulta {} terminada", indiceSheetActual);

            for (int i = 1; i <= cab.length; i++)
            {
                sheet.autoSizeColumn(i);
            }

            return (criterioPaginacion.getStart()
                    + criterioPaginacion.getLength()) >= totalFilas;

        } catch (Exception e)
        {
            logger.error("Error: {}", e.getMessage());
            throw new SimpException(
                    "Ocurrió un error al recuperar los registros de la base de datos");
        }

    }

}
