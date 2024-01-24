package ob.debitos.simp.service.impl.reporte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.EncodeException;
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

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import ob.debitos.simp.mapper.IReporteContableMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleContable;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenContable;
import ob.debitos.simp.model.mantenimiento.Moneda;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.reporte.ReporteDetalleContable;
import ob.debitos.simp.model.reporte.ReporteItemDetalleContable;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.model.reporte.ReporteResumenContable;
import ob.debitos.simp.model.websocket.Message;
import ob.debitos.simp.model.websocket.MessageDecoder;
import ob.debitos.simp.model.websocket.MessageEncoder;
import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.service.IReporteContableService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.utilitario.ExportFilterOutputStream;
import ob.debitos.simp.utilitario.ExportacionUtil;
import ob.debitos.simp.utilitario.PaginacionUtil;

/**
 * Esta es una clase de servicio {@literal @Service}, cuyo objetivo es obtener
 * los montos de débito y crédito de cada asiento contable asignado en el
 * proceso de contabilización.
 * <p>
 * Esta clase implementa los métodos definidos por la interface
 * {@link IReporteContableService}.
 * 
 * @author Carla Ulloa
 */
@Service
@ServerEndpoint(value = "/reporteContable", encoders = {
        MessageEncoder.class }, decoders = { MessageDecoder.class })
public class ReporteContableService implements IReporteContableService
{
    private @Autowired IReporteContableMapper reporteContableMapper;
    private @Autowired IMonedaService monedaService;
    private static final String TIPO_RESUMEN = "RESUMEN";
    private static final String TIPO_PERIODO = "PERIODO";
    private @Autowired Logger logger;

    private static int MAX = 1000000;
    boolean reporteCompleto = false;

    private static final List<Session> sessions = new ArrayList<>();

    double totalArchivos = 0;
    double procesandoRegistro = 0;
    int numeroFila = 1;
    boolean tieneData = false;

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarResumenContable(
            CriterioBusquedaResumenContable criterioBusqueda)
    {
        criterioBusqueda.setTipoReporte(TIPO_RESUMEN);
        return this.reporteContableMapper
                .buscarResumenContable(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ReporteDetalleContable> buscarDetalleContableVariosCriterios(
            CriterioBusquedaDetalleContable criterioBusqueda)
    {
        List<ReporteDetalleContable> reporteDetalle = new ArrayList<>();
        for (String criterio : criterioBusqueda.getCriterios())
        {
            List<ReporteMoneda> reporteMoneda = this
                    .buscarDetalleContableUnCriterio(criterioBusqueda,
                            criterio);
            ReporteDetalleContable detalle = ReporteDetalleContable.builder()
                    .criterio(criterio).reporteMoneda(reporteMoneda).build();
            reporteDetalle.add(detalle);
        }
        return reporteDetalle;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ReporteMoneda> buscarDetalleContableUnCriterio(
            CriterioBusquedaDetalleContable criterioBusqueda, String criterio)
    {
        criterioBusqueda.setVerbo("DETALLE_" + criterio);
        return this.reporteContableMapper
                .buscarDetalleContable(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarResumenPorPeriodoContable(
            CriterioBusquedaResumenContable criterioBusqueda)
    {
        criterioBusqueda.setTipoReporte(TIPO_PERIODO);
        return this.reporteContableMapper
                .buscarResumenPorPeriodoContable(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteResumenContable> buscarResumenPorPeriodoContableParaPDF(
            CriterioBusquedaResumenContable criterioBusqueda)
    {
        criterioBusqueda.setTipoReporte(TIPO_PERIODO);
        return this.reporteContableMapper
                .buscarResumenPorPeriodoContableParaReporte(criterioBusqueda);
    }

    @Override
    public void exportarResumenContable(
            CriterioBusquedaResumenContable criterioBusqueda,
            HttpServletResponse response) throws IOException
    {
        criterioBusqueda.setTipoReporte(TIPO_RESUMEN);
        String fileNameZip = "Reporte Contable Resumen.zip";
        String fileName = "Reporte Contable Resumen";
        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + fileNameZip);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(
                byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();

        XSSFWorkbook workbook = new XSSFWorkbook(getClass().getClassLoader()
                .getResourceAsStream("xlsx/reporteContableResumen.xlsx"));

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
                CriterioPaginacion<CriterioBusquedaResumenContable, ?> criterioPaginacion = PaginacionUtil
                        .getCriterioPaginacionParaReporteXLSX(criterioBusqueda,
                                inicio, MAX);
                logger.info(
                        "Se preparó los criterios de paginación {} para el sheet {}",
                        criterioPaginacion, indiceSheetActual);

                sxssfWorkbook = new SXSSFWorkbook(workbook,
                        SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
                logger.info("Generando reporte: {}", indiceSheetActual);

                reporteCompleto = this.construirReporteResumenContable(
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
            logger.error("cancelado: {}", e.getMessage());
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

    private boolean construirReporteResumenContable(
            CriterioPaginacion<CriterioBusquedaResumenContable, ?> criterioPaginacion,
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

        CellStyle decimalDos = ExportacionUtil.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CellStyle decimalDosGrey = ExportacionUtil.crearEstiloNumero(
                sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00",
                fontNormal);
        decimalDosGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        decimalDosGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        logger.info("Preparando consulta al SP P_GENERA_RESUMEN_CONTABLE_TPP");
        try
        {
            sheet.createFreezePane(0, 9);
            sheet.setAutoFilter(new CellRangeAddress(8, 8, 1, 6));

            // Format de Date a dd/MM/yyyy
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;

            double totalFilas = 0;
            int numeroFilaContenidoTable = 9;
            boolean seObtuvoTotalFilas = false;

            // ROW 4
            row = sheet.createRow(5);
            cell = row.createCell(1);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha");
            cell = row.createCell(2);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionRangoFechas());

            cell = row.createCell(4);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(5);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionInstitucion());

            String cab[] = { "Moneda", "Fecha Proceso", "Cuenta Contable",
                    "Descripción Cuenta", "Monto Débito", "Monto Crédito" };

            row = sheet.createRow(8);
            for (int i = 0; i < cab.length; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue(cab[i]);
            }

            logger.info("Ejecutando consulta: {}", indiceSheetActual);

            try
            {
                List<ReporteMoneda> monedas = this.reporteContableMapper
                        .buscarResumenContable(
                                criterioPaginacion.getCriterioBusqueda());

                for (int i = 0; i < monedas.size(); i++)
                {
                    double totalDebito = 0;
                    double totalCredito = 0;

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, 6));
                    row = sheet.createRow(numeroFilaContenidoTable);

                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBack);
                    cell.setCellValue(monedas.get(i).getCodigoMoneda() + " - "
                            + monedas.get(i).getDescripcionMoneda());
                    row.createCell(2).setCellStyle(estiloBack);
                    row.createCell(3).setCellStyle(estiloBack);
                    row.createCell(4).setCellStyle(estiloBack);
                    row.createCell(5).setCellStyle(estiloBack);
                    row.createCell(6).setCellStyle(estiloBack);

                    numeroFilaContenidoTable++;
                    numeroFila++;

                    List<ReporteResumenContable> resultSet = monedas.get(i)
                            .getCuentas();
                    int tamReporte = resultSet.size();
                    for (int j = 0; j < tamReporte; j++)
                    {
                        if (!seObtuvoTotalFilas)
                        {
                            totalFilas = 0;
                            for (int k = 0; k < monedas.size(); k++)
                            {
                                totalFilas += monedas.get(k).getCuentas()
                                        .size();
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
                        cell.setCellValue(formatter
                                .format(resultSet.get(j).getFechaProceso()));

                        cell = row.createCell(3);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCuentaContable());

                        cell = row.createCell(4);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getDescripcionCuenta());

                        cell = row.createCell(5);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getMontoDebito());

                        cell = row.createCell(6);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getMontoCredito());

                        totalDebito += resultSet.get(j).getMontoDebito();
                        totalCredito += resultSet.get(j).getMontoCredito();

                        numeroFilaContenidoTable++;
                        numeroFila++;

                    }

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, 4));
                    row = sheet.createRow(numeroFilaContenidoTable);
                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBackGrey);
                    cell.setCellValue("TOTAL");
                    row.createCell(2).setCellStyle(estiloBackGrey);
                    row.createCell(3).setCellStyle(estiloBackGrey);
                    row.createCell(4).setCellStyle(estiloBackGrey);

                    cell = row.createCell(5);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalDebito);

                    cell = row.createCell(6);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalCredito);

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
    public void exportarDetalleContable(
            CriterioBusquedaDetalleContable criterioBusqueda,
            HttpServletResponse response) throws IOException
    {

        String fileNameZip = "Reporte Contable Detalle.zip";
        String fileName = "Reporte Contable Detalle";
        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + fileNameZip);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(
                byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();

        XSSFWorkbook workbook = new XSSFWorkbook(getClass().getClassLoader()
                .getResourceAsStream("xlsx/reporteContableDetalle.xlsx"));

        logger.info("criterios: {}", criterioBusqueda);
        logger.info("Iniciando el proceso de generación del reporte");

        SXSSFWorkbook sxssfWorkbook = null;

        try
        {
            sxssfWorkbook = new SXSSFWorkbook(workbook, -1, Boolean.FALSE,
                    Boolean.TRUE);

            this.construirLibroReporteDetalleContable(criterioBusqueda,
                    sxssfWorkbook);

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void construirLibroReporteDetalleContable(
            CriterioBusquedaDetalleContable criterioBusqueda,
            SXSSFWorkbook sxssfWorkbook)
    {
        sxssfWorkbook.removeSheetAt(0);
        List<String> criterios = criterioBusqueda.getCriterios();

        if (!criterioBusqueda.getTipoVisualizacion().equalsIgnoreCase("J"))
        {
            for (int i = 0; i < criterios.size(); i++)
            {
                reporteCompleto = false;
                criterioBusqueda.setVerbo("DETALLE_" + criterios.get(i));
                CriterioPaginacion<CriterioBusquedaDetalleContable, ?> criterioPaginacion = PaginacionUtil
                        .getCriterioPaginacionParaReporteXLSX(criterioBusqueda,
                                0, MAX);
                List<ReporteMoneda> monedas = this.reporteContableMapper
                        .buscarDetalleContable(
                                criterioPaginacion.getCriterioBusqueda());
                sxssfWorkbook
                        .createSheet(criterioPaginacion.getCriterioBusqueda()
                                .getDescripcionCriterios().get(i));
                reporteCompleto = construirHojaReporteDetalleContable(
                        criterioPaginacion, monedas, sxssfWorkbook, i);
            }
        } else
        {
            List<String> criteriosDetalle = this
                    .obtenerCriteriosDetalle(criterios);
            List<String> descripcionCriterios = this
                    .obtenerDescripciones(criteriosDetalle);
            List<Moneda> monedas = this.monedaService.buscarTodos();
            List<ReporteDetalleContable> reporteDetalleContable = this
                    .buscarDetalleContableVariosCriterios(criterioBusqueda);
            List<ReporteDetalleContable> reporteDetalleContableJunto = this
                    .obtenerReporteJunto(criteriosDetalle, descripcionCriterios,
                            monedas, reporteDetalleContable);

            criterioBusqueda.setDescripcionCriterios(descripcionCriterios);
            criterioBusqueda.setCriterios(criteriosDetalle);
            CriterioPaginacion<CriterioBusquedaDetalleContable, ?> criterioPaginacion = PaginacionUtil
                    .getCriterioPaginacionParaReporteXLSX(criterioBusqueda, 0,
                            MAX);

            for (int i = 0; i < reporteDetalleContableJunto.size(); i++)
            {
                List<ReporteMoneda> monedasReporte = reporteDetalleContableJunto
                        .get(i).getReporteMoneda();
                sxssfWorkbook
                        .createSheet(criterioPaginacion.getCriterioBusqueda()
                                .getDescripcionCriterios().get(i));
                reporteCompleto = construirHojaReporteDetalleContable(
                        criterioPaginacion, monedasReporte, sxssfWorkbook, i);
            }

        }
        if (tieneData)
        {
            procesandoRegistro = 100.0;
        }
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
            tieneData = false;
        }
    }

    private boolean construirHojaReporteDetalleContable(
            CriterioPaginacion<CriterioBusquedaDetalleContable, ?> criterioPaginacion,
            List<ReporteMoneda> monedas, SXSSFWorkbook sxssfWorkbook,
            int numCriterio)
    {
        Sheet sheet = sxssfWorkbook.getSheetAt(numCriterio);
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

        CellStyle decimalDos = ExportacionUtil.crearEstiloNumero(sxssfWorkbook,
                "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CellStyle decimalDosGrey = ExportacionUtil.crearEstiloNumero(
                sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00",
                fontNormal);
        decimalDosGrey.setFillForegroundColor(
                IndexedColors.GREY_25_PERCENT.getIndex());
        decimalDosGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        int numeroFila = 1;

        try
        {

            String cab[] = { "Moneda", "Fecha Proceso", "Cuenta Contable",
                    "Descripción Cuenta", "Membresía", "Servicio", "Origen",
                    "Clase Transacción", "Código Transacción",
                    "Descripción Comisión", "Monto Débito", "Monto Crédito" };

            String cabM[] = { "Moneda", "Fecha Proceso", "Cuenta Contable",
                    "Descripción Cuenta", "Membresía", "Servicio", "Origen",
                    "Clase Transacción", "Código Transacción",
                    "Descripción Comisión", "Monto Débito", "Monto Crédito",
                    "Código Evento Marca Internacional",
                    "Distribución Cobro Marca Internacional",
                    "Número Factura Marca", "Tarifa Marca Internacional",
                    "Total Unidades", "Indicador Distribución Cobro",
                    "Indicador Unidades", "Valor Factura Marca Internacional",
                    "Secuencia Registro Factura Marca" };

            int cantidadCabecera = 0;
            boolean isMiscelaneos = criterioPaginacion.getCriterioBusqueda()
                    .getCriterios().get(numCriterio).equalsIgnoreCase("M");
            if (isMiscelaneos)
            {
                cantidadCabecera = cabM.length;
            } else
            {
                cantidadCabecera = cab.length;
            }

            sheet.createFreezePane(0, 7);
            sheet.setAutoFilter(
                    new CellRangeAddress(6, 6, 1, cantidadCabecera));
            sheet.addMergedRegion(
                    new CellRangeAddress(0, 1, 1, cantidadCabecera));

            // Format de Date a dd/MM/yyyy
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;

            double totalFilas = 0;
            int numeroFilaContenidoTable = 7;

            boolean seObtuvoTotalFilas = false;
            row = sheet.createRow(0);
            cell = row.createCell(1);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue("REPORTE CONTABLE " + criterioPaginacion
                    .getCriterioBusqueda().getDescripcionCriterios()
                    .get(numCriterio).toUpperCase());

            for (int celda = 2; celda <= cantidadCabecera; celda++)
            {
                row.createCell(celda).setCellStyle(estiloCriterioValor);
            }

            row = sheet.createRow(1);
            for (int celda = 1; celda <= cantidadCabecera; celda++)
            {
                row.createCell(celda).setCellStyle(estiloBack);
            }

            row = sheet.createRow(4);
            cell = row.createCell(3);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha");
            cell = row.createCell(4);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionRangoFechas());

            cell = row.createCell(6);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(7);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
                    .getDescripcionInstitucion());

            row = sheet.createRow(6);
            for (int i = 0; i < cantidadCabecera; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);

                if (criterioPaginacion.getCriterioBusqueda().getCriterios()
                        .get(numCriterio).equalsIgnoreCase("M"))
                {
                    cell.setCellValue(cabM[i]);
                } else
                {
                    cell.setCellValue(cab[i]);
                }

            }

            procesandoRegistro = 0;

            logger.info("Ejecutando consulta.");

            try
            {
                for (int i = 0; i < monedas.size(); i++)
                {

                    double totalDebito = 0;
                    double totalCredito = 0;

                    double tarifa = 0;
                    double valorFactura = 0;

                    sheet.addMergedRegion(new CellRangeAddress(
                            numeroFilaContenidoTable, numeroFilaContenidoTable,
                            1, cantidadCabecera));
                    row = sheet.createRow(numeroFilaContenidoTable);

                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBack);
                    cell.setCellValue(monedas.get(i).getCodigoMoneda() + " - "
                            + monedas.get(i).getDescripcionMoneda());

                    for (int celda = 2; celda <= cantidadCabecera; celda++)
                    {
                        row.createCell(celda).setCellStyle(estiloBack);
                    }

                    numeroFilaContenidoTable++;
                    numeroFila++;

                    List<ReporteItemDetalleContable> resultSet = monedas.get(i)
                            .getCuentasDetalle();
                    int tamReporte = resultSet.size();

                    for (int j = 0; j < tamReporte; j++)
                    {
                        if (!seObtuvoTotalFilas)
                        {
                            totalFilas = 0;
                            for (int k = 0; k < monedas.size(); k++)
                            {
                                totalFilas += monedas.get(k).getCuentasDetalle()
                                        .size();
                            }
                            seObtuvoTotalFilas = true;
                            logger.info(
                                    "El total de filas de la consulta es: {}",
                                    (int) totalFilas);
                            totalFilas += 1 + 2 * (monedas.size() - 1);
                        }

                        if (totalFilas > 0)
                        {
                            tieneData = true;
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
                        cell.setCellValue(formatter
                                .format(resultSet.get(j).getFechaProceso()));

                        cell = row.createCell(3);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j).getCuentaContable());

                        cell = row.createCell(4);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getDescripcionCuenta());

                        cell = row.createCell(5);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getDescripcionMembresia());

                        cell = row.createCell(6);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getDescripcionServicio());

                        cell = row.createCell(7);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getDescripcionOrigen());

                        cell = row.createCell(8);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getIdClaseTransaccion() + " - "
                                + resultSet.get(j)
                                        .getDescripcionClaseTransaccion());

                        cell = row.createCell(9);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(resultSet.get(j)
                                .getIdCodigoTransaccion() + " - "
                                + resultSet.get(j)
                                        .getDescripcionCodigoTransaccion());

                        cell = row.createCell(10);
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue(
                                resultSet.get(j).getDescripcionComision());

                        cell = row.createCell(11);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getMontoDebito());

                        cell = row.createCell(12);
                        cell.setCellStyle(decimalDos);
                        cell.setCellValue(resultSet.get(j).getMontoCredito());

                        if (isMiscelaneos)
                        {
                            cell = row.createCell(13);
                            cell.setCellStyle(estiloSimple);
                            cell.setCellValue(resultSet.get(j)
                                    .getCodigoEventoMarcaInternacional() != null
                                            ? resultSet.get(j)
                                                    .getCodigoEventoMarcaInternacional()
                                            : "");

                            cell = row.createCell(14);
                            cell.setCellStyle(estiloSimple);
                            if (resultSet.get(j)
                                    .getDistribucionCobroMarcaInternacional() != null)
                            {
                                cell.setCellValue(resultSet.get(j)
                                        .getDistribucionCobroMarcaInternacional());
                            } else
                            {
                                cell.setCellValue("");
                            }

                            cell = row.createCell(15);
                            cell.setCellStyle(estiloSimple);
                            cell.setCellValue(resultSet.get(j)
                                    .getNumeroFacturaMarca() != null
                                            ? resultSet.get(j)
                                                    .getNumeroFacturaMarca()
                                            : "");

                            cell = row.createCell(16);
                            cell.setCellStyle(decimalDos);
                            cell.setCellValue(resultSet.get(j)
                                    .getTarifaMarcaInternacional());

                            cell = row.createCell(17);
                            cell.setCellStyle(estiloSimple);
                            if (resultSet.get(j).getTotalUnidades() != null)
                            {
                                cell.setCellValue(
                                        resultSet.get(j).getTotalUnidades());
                            } else
                            {
                                cell.setCellValue("");
                            }

                            cell = row.createCell(18);
                            cell.setCellStyle(estiloSimple);
                            cell.setCellValue(resultSet.get(j)
                                    .getIndicadorDistribucionCobro() != null
                                            ? resultSet.get(j)
                                                    .getIndicadorDistribucionCobro()
                                            : "");

                            cell = row.createCell(19);
                            cell.setCellStyle(estiloSimple);
                            if (resultSet.get(j).getIndicadorUnidades() != null)
                            {
                                cell.setCellValue(resultSet.get(j)
                                        .getIndicadorUnidades());
                            } else
                            {
                                cell.setCellValue("");
                            }

                            cell = row.createCell(20);
                            cell.setCellStyle(decimalDos);
                            cell.setCellValue(resultSet.get(j)
                                    .getValorFacturaMarcaInternacional());

                            cell = row.createCell(21);
                            cell.setCellStyle(estiloSimple);
                            cell.setCellValue(resultSet.get(j)
                                    .getSecuenciaRegistroFacturaMarca() != null
                                            ? resultSet.get(j)
                                                    .getSecuenciaRegistroFacturaMarca()
                                            : "");

                            tarifa += resultSet.get(j)
                                    .getTarifaMarcaInternacional();
                            valorFactura += resultSet.get(j)
                                    .getValorFacturaMarcaInternacional();
                        }

                        totalDebito += resultSet.get(j).getMontoDebito();
                        totalCredito += resultSet.get(j).getMontoCredito();

                        numeroFilaContenidoTable++;
                        numeroFila++;

                    }

                    sheet.addMergedRegion(
                            new CellRangeAddress(numeroFilaContenidoTable,
                                    numeroFilaContenidoTable, 1, 10));
                    row = sheet.createRow(numeroFilaContenidoTable);
                    cell = row.createCell(1);
                    cell.setCellStyle(estiloBackGrey);
                    cell.setCellValue("TOTAL");
                    row.createCell(2).setCellStyle(estiloBackGrey);
                    row.createCell(3).setCellStyle(estiloBackGrey);
                    row.createCell(4).setCellStyle(estiloBackGrey);
                    row.createCell(5).setCellStyle(estiloBackGrey);
                    row.createCell(6).setCellStyle(estiloBackGrey);
                    row.createCell(7).setCellStyle(estiloBackGrey);
                    row.createCell(8).setCellStyle(estiloBackGrey);
                    row.createCell(9).setCellStyle(estiloBackGrey);
                    row.createCell(10).setCellStyle(estiloBackGrey);

                    cell = row.createCell(11);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalDebito);

                    cell = row.createCell(12);
                    cell.setCellStyle(decimalDosGrey);
                    cell.setCellValue(totalCredito);

                    if (isMiscelaneos)
                    {
                        cell = row.createCell(13);
                        cell.setCellStyle(decimalDosGrey);

                        cell = row.createCell(14);
                        cell.setCellStyle(decimalDosGrey);

                        cell = row.createCell(15);
                        cell.setCellStyle(decimalDosGrey);

                        cell = row.createCell(16);
                        cell.setCellStyle(decimalDosGrey);
                        cell.setCellValue(tarifa);

                        cell = row.createCell(17);
                        cell.setCellStyle(decimalDosGrey);

                        cell = row.createCell(18);
                        cell.setCellStyle(decimalDosGrey);

                        cell = row.createCell(19);
                        cell.setCellStyle(decimalDosGrey);

                        cell = row.createCell(20);
                        cell.setCellStyle(decimalDosGrey);
                        cell.setCellValue(valorFactura);

                        cell = row.createCell(21);
                        cell.setCellStyle(decimalDosGrey);
                    }
                    numeroFilaContenidoTable++;
                    numeroFila++;
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
            logger.info("Consulta {} terminada: {}", numCriterio);

            for (int i = 1; i <= cantidadCabecera; i++)
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

    private List<String> obtenerCriteriosDetalle(List<String> criteriosBusqueda)
    {
        String antValor = null;
        List<String> criterios = new ArrayList<>();
        for (int i = 0; i < criteriosBusqueda.size(); i++)
        {
            String valor = criteriosBusqueda.get(i).substring(0, 1);
            if (!valor.equals(antValor))
            {
                criterios.add(valor);
                antValor = valor;
            }
        }
        return criterios;
    }

    private String obtenerDescripcion(String criterio)
    {
        String descripcion = "";
        switch (criterio)
        {
        case "A":
            descripcion = "ADQUIRIENTE";
            break;
        case "E":
            descripcion = "EMISOR";
            break;
        case "M":
            descripcion = "MISCELANEOS";
            break;
        default:
            descripcion = "";
        }
        return descripcion;
    }

    private List<String> obtenerDescripciones(List<String> criterios)
    {
        List<String> descripcionCriterios = new ArrayList<>();
        for (String c : criterios)
        {
            descripcionCriterios.add(this.obtenerDescripcion(c));
        }
        return descripcionCriterios;
    }

    private List<ReporteDetalleContable> obtenerReporteJunto(
            List<String> criterios, List<String> descripcionCriterios,
            List<Moneda> monedas,
            List<ReporteDetalleContable> reportesDetalleContable)
    {
        List<ReporteDetalleContable> out = new ArrayList<>();
        for (int x = 0; x < criterios.size(); x++)
        {
            String c = criterios.get(x);
            ReporteDetalleContable rdc = ReporteDetalleContable.builder()
                    .criterio(descripcionCriterios.get(x)).build();
            List<ReporteMoneda> rm = new ArrayList<>();
            for (Moneda moneda : monedas)
            {
                ReporteMoneda repMoneda = ReporteMoneda.builder()
                        .codigoMoneda(moneda.getCodigoMoneda())
                        .descripcionMoneda(moneda.getDescripcion())
                        .cuentasDetalle(new ArrayList<>()).build();
                for (ReporteDetalleContable reporteDetalle : reportesDetalleContable)
                {
                    if (reporteDetalle.getCriterio().substring(0, 1).equals(c))
                    {
                        ReporteMoneda reporteMoneda = reporteDetalle
                                .getReporteMoneda().stream()
                                .filter(r -> moneda.getCodigoMoneda()
                                        .equals(r.getCodigoMoneda()))
                                .findFirst().orElse(null);
                        if (reporteMoneda != null)
                            repMoneda.getCuentasDetalle()
                                    .addAll(reporteMoneda.getCuentasDetalle());
                    }
                }
                rm.add(repMoneda);
            }
            rdc.setReporteMoneda(rm);
            out.add(rdc);
        }
        return out;
    }

    public void generarPdfReporteContableResumenPorPeriodo(
            CriterioBusquedaResumenContable criterioBusqueda,
            HttpServletResponse response)
            throws IOException, JRException, SQLException
    {
        List<ReporteResumenContable> reporte = this
                .buscarResumenPorPeriodoContableParaPDF(criterioBusqueda);

        List<JasperPrint> jprintlist = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();

        params.put("titulo", "REPORTE BALANCE CONTABLE - RESUMEN POR PERIODO");
        params.put("descripcionFechas",
                criterioBusqueda.getDescripcionRangoFechas());
        params.put("descripcionInstitucion",
                criterioBusqueda.getDescripcionInstitucion());

        InputStream jrxmlStream = getClass().getClassLoader()
                .getResourceAsStream(
                        "/jrxml/rpt_ReporteBalanceContablePorPeriodo.jrxml");
        JasperReport jasperReport = JasperCompileManager
                .compileReport(jrxmlStream);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(reporte);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                params, jrDataSource);
        jprintlist.add(jasperPrint);

        jrxmlStream.close();
        String fileNamePdf = "Reporte Balance Contable Por Periodo.pdf";
        response.setContentType("application/x-pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
                HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "inline;filename=" + fileNamePdf);

        final OutputStream outStream = response.getOutputStream();

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(jprintlist));
        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(outStream));
        exporter.exportReport();
    }
}