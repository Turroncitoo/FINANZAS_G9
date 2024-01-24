package ob.debitos.simp.service.impl.reporte;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.cnd;
import static net.sf.dynamicreports.report.builder.DynamicReports.ctab;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.awt.Color;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabMeasureBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.builder.style.ConditionalStyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import ob.debitos.simp.mapper.IReporteCompensacionMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacion;
import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.reporte.ReporteCompensacionCanal;
import ob.debitos.simp.model.reporte.ReporteCompensacionExportacion;
import ob.debitos.simp.model.reporte.ReporteCompensacionInstitucion;
import ob.debitos.simp.model.reporte.ReporteCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.reporte.ReporteCompensacionReceptor;
import ob.debitos.simp.model.websocket.Message;
import ob.debitos.simp.model.websocket.MessageDecoder;
import ob.debitos.simp.model.websocket.MessageEncoder;
import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.service.IReporteCompensacionService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.utilitario.DatesUtils;
import ob.debitos.simp.utilitario.ExportacionUtil;
import ob.debitos.simp.utilitario.ReporteUtil;
import ob.debitos.simp.service.IExportacionPOIService;

import javax.servlet.http.HttpServletRequest;

@Service
@ServerEndpoint(value = "/compensacion", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class ReporteCompensacionService implements IReporteCompensacionService
{
    private @Autowired Logger logger;
    private @Autowired DataSource dataSource;
    private @Autowired IMonedaService monedaService;
    private @Autowired IReporteCompensacionMapper reporteCompensacionMapper;

    private @Autowired IExportacionPOIService<ReporteCompensacionPorGiroDeNegocio> exportCompensacionPorGiroDeNegocio;
    private @Autowired IExportacionPOIService<ReporteCompensacionInstitucion> exportCompensacionInstitucion;
    private @Autowired IExportacionPOIService<ReporteCompensacionCanal> exportCompensacionCanal;

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
    public List<ReporteCompensacionCanal> buscarCompensacionCanal(CriterioBusquedaCompensacion criterioBusqueda)
    {
        criterioBusqueda.setVerbo(ReporteUtil.obtenerVerboPorTipoCompensacion(criterioBusqueda.getTipoCompensacion()));
        return reporteCompensacionMapper.reporteCanal(criterioBusqueda);
    }

    private static final String[][] cabeceraExportacionCanal = { { "Canal", "idCanal", "descripcionCanal", "formatCadena", "-1" }, { "Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1" },
            { "Cliente", "idCliente", "descCliente", "formatCadena", "-1" }, { "Cantidad", "cantidadCanal", "", "formatCantidad", "-1" }, { "Monto", "montoCanal", "", "formatMonto", "-1" }, { "THB", "comisionTHB", "", "formatComision", "-1" },
            { "EST", "comisionEST", "", "formatComision", "-1" }, { "REC", "comisionREC", "", "formatComision", "-1" }, { "OPE", "comisionOPE", "", "formatComision", "-1" }, { "ISA", "comisionISA", "", "formatComision", "-1" },
            { "OIF", "comisionOIF", "", "formatComision", "-1" }, { "ING", "comisionING", "", "formatComision", "-1" }, { "COI", "comisionCOI", "", "formatComision", "-1" }, { "TIC", "comisionTIC", "", "formatComision", "-1" },
            { "INT", "comisionINT", "", "formatComision", "-1" }, { "Total", "comisionTOTAL", "", "formatComision", "-1" } };

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarReporteCompensacionCanal(List<ReporteCompensacionCanal> list, CriterioBusquedaCompensacion criterioBusquedaTransaccionCompensacion, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = { { "Rol Transacción", criterioBusquedaTransaccionCompensacion.getDescripcionRolTransaccion() }, { "Fecha Proceso", criterioBusquedaTransaccionCompensacion.getDescripcionRangoFechas() },
                { "Respuesta Transacción", criterioBusquedaTransaccionCompensacion.getDescripcionCodigoRespuestaTransaccion() }, { "Moneda", criterioBusquedaTransaccionCompensacion.getDescripcionTipoMoneda() },
                { "Institución", criterioBusquedaTransaccionCompensacion.getDescripcionInstitucion() }, { "Empresa", criterioBusquedaTransaccionCompensacion.getDescripcionEmpresa() },
                { "Cliente", criterioBusquedaTransaccionCompensacion.getDescripcionCliente() } };
        this.exportCompensacionCanal.generarExportacionNormal("Reporte Compensación Emisor - Canal", list, filtros, cabeceraExportacionCanal, true, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteCompensacionInstitucion> buscarCompensacionInstitucion(CriterioBusquedaCompensacion criterioBusqueda)
    {
        criterioBusqueda.setVerbo(ReporteUtil.obtenerVerboPorTipoCompensacion(criterioBusqueda.getTipoCompensacion()));
        return reporteCompensacionMapper.reporteInstitucion(criterioBusqueda);
    }

    private static final String[][] cabeceraExportacionInstitucion = { { "Institución", "idInstitucion", "descripcionInstitucion", "formatCadena", "-1" }, { "Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1" },
            { "Cliente", "idCliente", "descCliente", "formatCadena", "-1" }, { "Cantidad", "cantidadInstitucion", "", "formatCantidad", "-1" }, { "Monto", "montoInstitucion", "", "formatMonto", "-1" },
            { "THB", "comisionTHB", "", "formatComision", "-1" }, { "EST", "comisionEST", "", "formatComision", "-1" }, { "REC", "comisionREC", "", "formatComision", "-1" }, { "OPE", "comisionOPE", "", "formatComision", "-1" },
            { "ISA", "comisionISA", "", "formatComision", "-1" }, { "OIF", "comisionOIF", "", "formatComision", "-1" }, { "ING", "comisionING", "", "formatComision", "-1" }, { "COI", "comisionCOI", "", "formatComision", "-1" },
            { "TIC", "comisionTIC", "", "formatComision", "-1" }, { "INT", "comisionINT", "", "formatComision", "-1" }, { "Total", "comisionTOTAL", "", "formatComision", "-1" } };

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarDetalleCompensacionInstitucion(List<ReporteCompensacionInstitucion> list, CriterioBusquedaCompensacion criterioBusqueda, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = { { "Rol Transacción", criterioBusqueda.getDescripcionRolTransaccion() }, { "Fecha Proceso", criterioBusqueda.getDescripcionRangoFechas() },
                { "Respuesta Transacción", criterioBusqueda.getDescripcionCodigoRespuestaTransaccion() }, { "Moneda", criterioBusqueda.getDescripcionTipoMoneda() }, { "Institución", criterioBusqueda.getDescripcionInstitucion() },
                { "Empresa", criterioBusqueda.getDescripcionEmpresa() }, { "Cliente", criterioBusqueda.getDescripcionCliente() } };
        this.exportCompensacionInstitucion.generarExportacionNormal("Reporte Compensación Emisor - Institución", list, filtros, cabeceraExportacionInstitucion, true, 3, 85, request, response);
    }

    @SuppressWarnings("unused")
    private boolean construirReporteDetalleCompensacionInstitucion(CriterioPaginacion<CriterioBusquedaCompensacion, ?> criterioPaginacion, SXSSFWorkbook sxssfWorkbook, int indiceSheetActual)
    {
        Sheet sheet = sxssfWorkbook.getSheetAt(0);

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

        CellStyle estiloCab = sxssfWorkbook.createCellStyle();
        estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCab.setAlignment(HorizontalAlignment.CENTER);
        estiloCab.setBorderTop(BorderStyle.THIN);
        estiloCab.setBorderRight(BorderStyle.THIN);
        estiloCab.setBorderBottom(BorderStyle.THIN);
        estiloCab.setBorderLeft(BorderStyle.THIN);
        estiloCab.setFont(font);

        CellStyle estiloCantidad = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "#,##0", fontNormal);

        CellStyle estiloCriterioValor = ExportacionUtil.crearEstiloBasico(sxssfWorkbook, fontBold);
        estiloCriterioValor.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estiloSimple = ExportacionUtil.crearEstiloBasico(sxssfWorkbook, fontNormal);

        CellStyle decimalDos = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CellStyle decimalCuatro = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.0000;[Red]-#,##0.0000;[Black]#,##0.0000", fontNormal);

        logger.info("Preparando consulta al SP REPORT_GESTION_COMPENSACIONES_EMISOR");

        try (Connection conn = this.dataSource.getConnection(); CallableStatement cs = conn.prepareCall("{CALL REPORT_GESTION_COMPENSACIONES_EMISOR(?, ?, ?, ?, ?, ?, ?, ?, ?)}"))
        {
            cs.setString(1, criterioPaginacion.getCriterioBusqueda().getVerbo());
            cs.setInt(2, criterioPaginacion.getCriterioBusqueda().getRolTransaccion());
            cs.setInt(3, criterioPaginacion.getCriterioBusqueda().getCodigoRespuestaTransaccion());
            cs.setInt(4, criterioPaginacion.getCriterioBusqueda().getTipoMoneda());
            cs.setDate(5, DatesUtils.convertUtilToSql(criterioPaginacion.getCriterioBusqueda().getFechaInicio()));
            cs.setDate(6, DatesUtils.convertUtilToSql(criterioPaginacion.getCriterioBusqueda().getFechaFin()));
            cs.setInt(7, criterioPaginacion.getCriterioBusqueda().getIdInstitucion());
            cs.setString(8, criterioPaginacion.getCriterioBusqueda().getIdEmpresa());
            cs.setString(9, criterioPaginacion.getCriterioBusqueda().getIdCliente());

            sheet.createFreezePane(0, 11);
            sheet.setAutoFilter(new CellRangeAddress(10, 10, 1, 16));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 6, 15));
            sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 3));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 3, 4));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 10, 11));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 3, 4));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 10, 11));

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;

            double totalFilas = 0;
            int numeroFilaContenidoTable = 11;
            boolean seObtuvoTotalFilas = false;

            row = sheet.createRow(3);
            // CRITERIO - Rol Transacción
            cell = row.createCell(2);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Rol Transacción");
            cell = row.createCell(3);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionRolTransaccion());
            row.createCell(4).setCellStyle(estiloCriterioValor);
            // CRITERIO - Respuesta Transacción
            cell = row.createCell(6);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Respuesta de Transacción");
            cell = row.createCell(7);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionCodigoRespuestaTransaccion());
            // CRITERIO - Institución
            cell = row.createCell(9);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(10);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionInstitucion());
            row.createCell(11).setCellStyle(estiloCriterioValor);

            row = sheet.createRow(4);

            // CRITERIO - Fecha Proceso
            cell = row.createCell(2);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha Proceso");
            cell = row.createCell(3);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(formatter.format(criterioPaginacion.getCriterioBusqueda().getFechaInicio()) + " - " + formatter.format(criterioPaginacion.getCriterioBusqueda().getFechaFin()));
            row.createCell(4).setCellStyle(estiloCriterioValor);
            // CRITERIO - Moneda
            cell = row.createCell(6);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Moneda");
            cell = row.createCell(7);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionTipoMoneda());
            // CRITERIO - Empresa
            cell = row.createCell(9);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Empresa");
            cell = row.createCell(10);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionEmpresa());
            row.createCell(11).setCellStyle(estiloCriterioValor);

            cell = row.createCell(13);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Cliente");
            cell = row.createCell(14);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionCliente());

            row = sheet.createRow(9);
            cell = row.createCell(6);
            cell.setCellStyle(estiloCab);
            cell.setCellValue("Comisiones");

            String cab[] = { "Institución Receptora", "Empresa", "Cliente", "Cantidad", "Monto", "THB", "EST", "REC", "OPE", "ISA", "OIF", "ING", "COI", "TIC", "INT", "Total" };
            row = sheet.createRow(10);
            for (int i = 0; i < cab.length; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue(cab[i]);
            }

            logger.info("Ejecutando consulta: {}", indiceSheetActual);

            try (ResultSet resultSet = cs.executeQuery();)
            {
                while (resultSet.next())
                {
                    if (!seObtuvoTotalFilas)
                    {
                        totalFilas = this.reporteCompensacionMapper.reporteInstitucion(criterioPaginacion.getCriterioBusqueda()).size();
                        seObtuvoTotalFilas = true;
                        logger.info("El total de filas de la consulta es: {}", (int) totalFilas);
                    }
                    procesandoRegistro = (float) (numeroFila / totalFilas) * 100;
                    for (Session session : sessions)
                    {
                        if (session.isOpen())
                        {
                            session.getBasicRemote().sendObject(new Message(procesandoRegistro, reporteCompleto, false));
                        }
                    }
                    row = sheet.createRow(numeroFilaContenidoTable);

                    cell = row.createCell(1);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.getString("idInstitucion") + "-" + resultSet.getString("descripcionInstitucion"));

                    cell = row.createCell(2);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.getString("idEmpresa") + " - " + resultSet.getString("descEmpresa"));

                    cell = row.createCell(3);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.getString("idCliente") + " - " + resultSet.getString("descCliente"));

                    cell = row.createCell(4);
                    cell.setCellStyle(estiloCantidad);
                    cell.setCellValue(resultSet.getDouble("cantidadInstitucion"));
                    cell = row.createCell(5);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("montoInstitucion"));
                    cell = row.createCell(6);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionTHB"));
                    cell = row.createCell(7);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionEST"));
                    cell = row.createCell(8);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionREC"));
                    cell = row.createCell(9);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionOPE"));
                    cell = row.createCell(10);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionISA"));
                    cell = row.createCell(11);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionOIF"));
                    cell = row.createCell(12);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionING"));
                    cell = row.createCell(13);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionCOI"));
                    cell = row.createCell(14);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionTIC"));
                    cell = row.createCell(15);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionINT"));
                    cell = row.createCell(16);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("comisionTOTAL"));

                    numeroFilaContenidoTable++;
                    numeroFila++;
                }
                row = sheet.createRow(7);
                cell = row.createCell(1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue("TOTAL");

                cell = row.createCell(4);
                cell.setCellStyle(estiloCantidad);
                cell.setCellFormula("SUM(E12:E" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(5);
                cell.setCellStyle(decimalDos);
                cell.setCellFormula("SUM(F12:F" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(6);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(G12:G" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(7);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(H12:H" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(8);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(I12:I" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(9);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(J12:J" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(10);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(K12:K" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(11);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(L12:L" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(12);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(M12:M" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(13);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(N12:N" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(14);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(O12:O" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(15);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(P12:P" + ((int) totalFilas + 11) + ")");
                cell = row.createCell(16);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(Q12:Q" + ((int) totalFilas + 11) + ")");
            } catch (Exception e)
            {
                logger.error("Ocurrió un error: {}", e.getMessage());
            }

            logger.info("Consulta {} terminada", indiceSheetActual);
            return (criterioPaginacion.getStart() + criterioPaginacion.getLength()) >= totalFilas;

        } catch (Exception e)
        {
            logger.error("Error: {}", e.getMessage());
            throw new SimpException("Ocurrió un error al recuperar los registros de la base de datos");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteCompensacionReceptor> buscarCompensaciones(CriterioBusquedaCompensacion criterioBusqueda)
    {
        criterioBusqueda.setVerbo(ReporteUtil.obtenerVerboPorTipoCompensacion(criterioBusqueda.getTipoCompensacion()));
        List<ReporteCompensacionReceptor> compensaciones = reporteCompensacionMapper.buscarCompensaciones(criterioBusqueda);
        return compensaciones;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ReporteCompensacionPorGiroDeNegocio> buscarCompensacionEmisorPorPorGiroDeNegocio(CriterioBusquedaCompensacionPorGiroDeNegocio criterioBusqueda)
    {
        return this.reporteCompensacionMapper.buscarCompensacionEmisorPorPorGiroDeNegocio(criterioBusqueda);
    }

    private static final String[][] cabeceraExportacionGiroNegocio = { 
    		{ "Fecha Proceso", "fechaProceso", "", "formatFecha", "-1" }, 
    		{ "Institución", "idInstitucion", "descripcionInstitucion", "formatCadena", "-1" },
            { "Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1" }, 
            { "Cliente", "idCliente", "descCliente", "formatCadena", "-1" }, 
            { "Giro Negocio", "giroNegocio", "descGiroNegocio", "formatCadena", "-1" },
            { "Logo", "idLogo", "descLogoBin", "formatCadena", "-1" }, 
            { "Producto", "codigoProducto", "descProducto", "formatCadena", "-1" }, 
            { "Membresía", "idMembresia", "descMembresia", "formatCadena", "-1" },
            { "Servicio", "idClaseServicio", "descClaseServicio", "formatCadena", "-1" }, 
            { "Origen", "idOrigen", "descOrigen", "formatCadena", "-1" }, 
            { "Moneda", "monedaCompensacion", "descMonedaCompensacion", "formatCadena", "-1" },
            { "Cantidad", "cantidad", "", "formatCantidad", "-1" }, 
            { "Monto", "monto", "", "formatMonto", "-1" }, 
            { "Comisión", "comision", "", "formatComision", "-1" } };

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void exportarReporteCompensacionEmisorPorPorGiroDeNegocio(List<ReporteCompensacionPorGiroDeNegocio> list, CriterioBusquedaCompensacionPorGiroDeNegocio criterioBusqueda, HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        String[][] filtros = { { "Fecha Proceso", criterioBusqueda.getDescripcionRangoDeFechas() }, { "Institución", criterioBusqueda.getDescripcionInstitucion() }, { "Empresa", criterioBusqueda.getDescripcionEmpresa() },
                { "Clientes", criterioBusqueda.getDescripcionCliente() } };
        
        this.exportCompensacionPorGiroDeNegocio.generarExportacionNormal("Reporte Compensación Giro de Negocio", list, filtros, cabeceraExportacionGiroNegocio, false, 3, 85, request, response);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteCompensacionExportacion> buscarCompensacionesReporte(CriterioBusquedaCompensacion criterioBusqueda)
    {
        criterioBusqueda.setVerbo(ReporteUtil.obtenerVerboPorTipoCompensacion(criterioBusqueda.getTipoCompensacion()));
        List<ReporteCompensacionExportacion> compensaciones = reporteCompensacionMapper.buscarCompensacionesReporte(criterioBusqueda);
        return compensaciones;
    }

    @SuppressWarnings("unused")
    private boolean construirReporteCompensacionEmisorCanal(CriterioPaginacion<CriterioBusquedaCompensacion, ?> criterioPaginacion, SXSSFWorkbook sxssfWorkbook, int indiceSheetActual)
    {

        Sheet sheet = sxssfWorkbook.getSheetAt(0);

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

        CellStyle estiloCab = sxssfWorkbook.createCellStyle();
        estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCab.setAlignment(HorizontalAlignment.CENTER);
        estiloCab.setBorderTop(BorderStyle.THIN);
        estiloCab.setBorderRight(BorderStyle.THIN);
        estiloCab.setBorderBottom(BorderStyle.THIN);
        estiloCab.setBorderLeft(BorderStyle.THIN);
        estiloCab.setFont(font);

        CellStyle estiloCantidad = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "#,##0", fontNormal);

        CellStyle estiloCriterioValor = ExportacionUtil.crearEstiloBasico(sxssfWorkbook, fontBold);
        estiloCriterioValor.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estiloSimple = ExportacionUtil.crearEstiloBasico(sxssfWorkbook, fontNormal);

        CellStyle estiloNumero = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "#,##0", fontNormal);

        CellStyle decimalDos = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CellStyle decimalCuatro = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.0000;[Red]-#,##0.0000;[Black]#,##0.0000", fontNormal);

        CellStyle decimalCuatroBlack = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.0000;[Red]-#,##0.0000;[Black]#,##0.0000", fontBold);

        logger.info("Preparando consulta al SP REPORT_GESTION_COMPENSACIONES_EMISOR");
        try (Connection conn = this.dataSource.getConnection(); CallableStatement cs = conn.prepareCall("{CALL REPORT_GESTION_COMPENSACIONES_EMISOR(?, ?, ?, ?, ?, ?, ?, ?, ?)}"))
        {
            cs.setString(1, criterioPaginacion.getCriterioBusqueda().getVerbo());
            cs.setInt(2, criterioPaginacion.getCriterioBusqueda().getRolTransaccion());
            cs.setInt(3, criterioPaginacion.getCriterioBusqueda().getCodigoRespuestaTransaccion());
            cs.setInt(4, criterioPaginacion.getCriterioBusqueda().getTipoMoneda());
            cs.setDate(5, DatesUtils.convertUtilToSql(criterioPaginacion.getCriterioBusqueda().getFechaInicio()));
            cs.setDate(6, DatesUtils.convertUtilToSql(criterioPaginacion.getCriterioBusqueda().getFechaFin()));
            cs.setInt(7, criterioPaginacion.getCriterioBusqueda().getIdInstitucion());
            cs.setString(8, criterioPaginacion.getCriterioBusqueda().getIdEmpresa());
            cs.setString(9, criterioPaginacion.getCriterioBusqueda().getIdCliente());

            sheet.createFreezePane(0, 14);
            sheet.setAutoFilter(new CellRangeAddress(14, 14, 1, 15));
            sheet.addMergedRegion(new CellRangeAddress(13, 13, 6, 15));

            sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 3));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 10, 11));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 12, 13));
            sheet.addMergedRegion(new CellRangeAddress(7, 7, 2, 3));
            sheet.addMergedRegion(new CellRangeAddress(7, 7, 10, 11));
            sheet.addMergedRegion(new CellRangeAddress(7, 7, 12, 13));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 2, 3));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 10, 11));
            sheet.addMergedRegion(new CellRangeAddress(9, 9, 12, 13));

            sheet.addMergedRegion(new CellRangeAddress(11, 11, 1, 3));

            Row row = null;
            Cell cell = null;
            double totalFilas = 0;
            int numeroFilaContenidoTable = 15;
            boolean seObtuvoTotalFilas = false;

            // ROW 3
            row = sheet.createRow(3);
            cell = row.createCell(1);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(2);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionInstitucion());
            row.createCell(3).setCellStyle(estiloCriterioValor);

            // ROW 5
            row = sheet.createRow(5);
            cell = row.createCell(1);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Rol Transacción");
            cell = row.createCell(2);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionRolTransaccion());
            row.createCell(3).setCellStyle(estiloCriterioValor);

            cell = row.createCell(10);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha Proceso");
            row.createCell(11).setCellStyle(estiloCriterioValor);
            cell = row.createCell(12);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionRangoFechas());
            row.createCell(13).setCellStyle(estiloCriterioValor);

            // ROW 7
            row = sheet.createRow(7);
            cell = row.createCell(1);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Respuesta Transacción");
            cell = row.createCell(2);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionCodigoRespuestaTransaccion());
            row.createCell(3).setCellStyle(estiloCriterioValor);

            cell = row.createCell(10);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Moneda");
            row.createCell(11).setCellStyle(estiloCriterioValor);
            cell = row.createCell(12);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionTipoMoneda());
            row.createCell(13).setCellStyle(estiloCriterioValor);

            // ROW 9
            row = sheet.createRow(9);
            cell = row.createCell(1);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Empresa");
            cell = row.createCell(2);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionEmpresa());
            row.createCell(3).setCellStyle(estiloCriterioValor);

            cell = row.createCell(10);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Cliente");
            row.createCell(11).setCellStyle(estiloCriterioValor);
            cell = row.createCell(12);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionCliente());
            row.createCell(13).setCellStyle(estiloCriterioValor);

            // ROW 12
            row = sheet.createRow(13);
            cell = row.createCell(6);
            cell.setCellStyle(estiloCab);
            cell.setCellValue("COMISIONES");

            String cab[] = { "Canal", "Empresa", "Cliente", "Cantidad", "Monto", "THB", "EST", "REC", "OPE", "ISA", "OIF", "ING", "COI", "TIC", "INT", "Total" };

            row = sheet.createRow(14);
            for (int i = 0; i < cab.length; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue(cab[i]);
            }

            logger.info("Ejecutando consulta: {}", indiceSheetActual);

            try (ResultSet resultSet = cs.executeQuery();)
            {
                while (resultSet.next())
                {
                    if (!seObtuvoTotalFilas)
                    {
                        totalFilas = this.reporteCompensacionMapper.reporteCanal(criterioPaginacion.getCriterioBusqueda()).size();
                        seObtuvoTotalFilas = true;
                        logger.info("El total de filas de la consulta es: {}", (int) totalFilas);
                    }

                    procesandoRegistro = (float) (numeroFila / totalFilas) * 100;

                    for (Session session : sessions)
                    {
                        if (session.isOpen())
                        {
                            session.getBasicRemote().sendObject(new Message(procesandoRegistro, reporteCompleto, false));
                        }
                    }

                    row = sheet.createRow(numeroFilaContenidoTable);

                    cell = row.createCell(1);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.getString("idCanal") + " - " + resultSet.getString("descripcionCanal"));

                    cell = row.createCell(2);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.getString("idEmpresa") + " - " + resultSet.getString("descEmpresa"));

                    cell = row.createCell(3);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.getString("idCliente") + " - " + resultSet.getString("descCliente"));

                    cell = row.createCell(4);
                    cell.setCellStyle(estiloNumero);
                    cell.setCellValue(resultSet.getString("cantidadCanal"));

                    cell = row.createCell(5);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.getDouble("montoCanal"));

                    cell = row.createCell(6);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionTHB"));

                    cell = row.createCell(7);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionEST"));

                    cell = row.createCell(8);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionREC"));

                    cell = row.createCell(9);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionOPE"));

                    cell = row.createCell(10);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionISA"));

                    cell = row.createCell(11);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionOIF"));

                    cell = row.createCell(12);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionING"));

                    cell = row.createCell(13);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionCOI"));

                    cell = row.createCell(14);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionTIC"));

                    cell = row.createCell(15);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionINT"));

                    cell = row.createCell(16);
                    cell.setCellStyle(decimalCuatro);
                    cell.setCellValue(resultSet.getDouble("comisionTOTAL"));

                    numeroFilaContenidoTable++;
                    numeroFila++;
                }
                row = sheet.createRow(11);
                cell = row.createCell(1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue("TOTAL");

                cell = row.createCell(4);
                cell.setCellStyle(estiloCantidad);
                cell.setCellFormula("SUM(E15:E" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(5);
                cell.setCellStyle(decimalDos);
                cell.setCellFormula("SUM(F15:F" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(6);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(G15:G" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(7);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(H15:H" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(8);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(I15:I" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(9);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(J15:J" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(10);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(K15:K" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(11);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(L15:L" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(12);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(M15:M" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(13);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(N15:N" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(14);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(O15:O" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(15);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(P15:P" + ((int) totalFilas + 14) + ")");

                cell = row.createCell(16);
                cell.setCellStyle(decimalCuatro);
                cell.setCellFormula("SUM(Q15:Q" + ((int) totalFilas + 14) + ")");

            } catch (Exception e)
            {
                logger.error("Ocurrió un error: {}", e.getMessage());
            }

            logger.info("Consulta {} terminada", indiceSheetActual);
            return (criterioPaginacion.getStart() + criterioPaginacion.getLength()) >= totalFilas;

        } catch (Exception e)
        {
            logger.error("Error: {}", e.getMessage());
            throw new SimpException("Ocurrió un error al recuperar los registros de la base de datos");
        }

    }

    public JasperReportBuilder reporteDinamico(CriterioBusquedaCompensacion criterioBusqueda)
    {
        List<ReporteCompensacionExportacion> compensaciones = buscarCompensacionesReporte(criterioBusqueda);
        return build(compensaciones, criterioBusqueda);
    }

    private JasperReportBuilder build(List<ReporteCompensacionExportacion> compensaciones, CriterioBusquedaCompensacion criterioBusqueda)
    {
        export.xlsxExporter("reporte.xlsx").setDetectCellType(true).setIgnorePageMargins(true).setWhitePageBackground(false).setRemoveEmptySpaceBetweenColumns(true);
        StyleBuilder columnTitleStyle = stl.style().bold().setBorder(stl.penThin()).setTextAlignment(HorizontalTextAlignment.CENTER, VerticalTextAlignment.MIDDLE);
        StyleBuilder titleMembresiaStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(104, 244, 141)).setFontSize(10);
        StyleBuilder titleAtmStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(141, 180, 226)).setFontSize(10);
        StyleBuilder atmAndTotalStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(197, 217, 241)).setFontSize(10);
        StyleBuilder titleFondosStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(217, 217, 217)).setFontSize(10);
        StyleBuilder titleComisionesStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(196, 215, 155)).setFontSize(10);
        CrosstabMeasureBuilder<Integer> cantidadMeasure = ctab.measure("CANT", "cantidad", Integer.class, Calculation.SUM);
        cantidadMeasure.setTitleStyle(titleFondosStyle);
        CrosstabMeasureBuilder<Double> montoMeasure = ctab.measure("MONTO", "monto", Double.class, Calculation.SUM);
        montoMeasure.setTitleStyle(titleFondosStyle);
        CrosstabMeasureBuilder<Double> intMeasure = ctab.measure("INT", "comisionInt", Double.class, Calculation.SUM);
        intMeasure.setTitleStyle(titleComisionesStyle);
        CrosstabMeasureBuilder<Double> gasMeasure = ctab.measure("GAS", "comisionGas", Double.class, Calculation.SUM);
        gasMeasure.setTitleStyle(titleComisionesStyle);
        CrosstabMeasureBuilder<Double> opeMeasure = ctab.measure("OPE", "comisionOpe", Double.class, Calculation.SUM);
        opeMeasure.setTitleStyle(titleComisionesStyle);
        CrosstabMeasureBuilder<Double> surMeasure = ctab.measure("SUR", "comisionSur", Double.class, Calculation.SUM);
        surMeasure.setTitleStyle(titleComisionesStyle);
        CrosstabMeasureBuilder<Double> totalMembresiaMeasure = ctab.measure("TOTAL MEMB.", "totalMembresia", Double.class, Calculation.SUM);
        totalMembresiaMeasure.setTitleStyle(titleComisionesStyle);

        negativoStyle(cantidadMeasure);
        negativoStyle(montoMeasure);
        negativoStyle(intMeasure);
        negativoStyle(gasMeasure);
        negativoStyle(opeMeasure);
        negativoStyle(surMeasure);
        negativoStyle(totalMembresiaMeasure);

        // Crosstab
        CrosstabRowGroupBuilder<Integer> rowIdAtmGroup = ctab.rowGroup("idAtm", Integer.class).setShowTotal(false).setHeaderStyle(atmAndTotalStyle).setTotalHeaderStyle(atmAndTotalStyle);
        CrosstabRowGroupBuilder<String> rowDescripcionAtmGroup = ctab.rowGroup("descripcionAtm", String.class).setTotalHeader("Total por Membresia").setHeaderStyle(atmAndTotalStyle);
        CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup("descripcionMembresia", String.class).setHeaderStyle(titleMembresiaStyle);
        CrosstabBuilder crosstab = ctab.crosstab().headerCell(cmp.text("ATM").setStyle(titleAtmStyle)).rowGroups(rowDescripcionAtmGroup, rowIdAtmGroup).columnGroups(columnGroup)
                .measures(cantidadMeasure, montoMeasure, intMeasure, gasMeasure, opeMeasure, surMeasure, totalMembresiaMeasure).setGrandTotalStyle(atmAndTotalStyle).setGroupTotalStyle(atmAndTotalStyle);
        return report().addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "6").addProperty(JasperProperty.EXPORT_XLS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, "true").addProperty(JasperProperty.EXPORT_XLS_WHITE_PAGE_BACKGROUND, "false")
                .addProperty(JasperProperty.EXPORT_XLS_DETECT_CELL_TYPE, "true").addProperty(JasperProperty.EXPORT_IGNORE_PAGE_MARGINS, "true").ignorePageWidth().ignorePagination().title(createTitle()).pageHeader(createPageHeader(criterioBusqueda))
                .summary(crosstab).setDataSource(compensaciones);
    }

    @SuppressWarnings(value = { "rawtypes", "unchecked" })
    private void negativoStyle(CrosstabMeasureBuilder measure)
    {
        ConditionalStyleBuilder measureCondition = stl.conditionalStyle(cnd.smaller(measure, 0)).setBackgroundColor(new Color(255, 151, 151));

        StyleBuilder comisionesStyle = stl.style().conditionalStyles(measureCondition).setBorder(stl.penThin()).setBackgroundColor(Color.WHITE).setFontSize(10);
        measure.setStyle(comisionesStyle);
    }

    private ComponentBuilder<?, ?> createPageHeader(CriterioBusquedaCompensacion criterio)
    {
        StyleBuilder criterioStyle = stl.style().bold().setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE).setFontSize(10);
        String fechaInicio = new SimpleDateFormat("dd/MM/yyyy").format(criterio.getFechaInicio());
        String fechaFin = new SimpleDateFormat("dd/MM/yyyy").format(criterio.getFechaFin());
        String transaccion = (criterio.getCodigoRespuestaTransaccion() == 0) ? "APROBADAS" : (criterio.getCodigoRespuestaTransaccion() == 998) ? "DESAPROBADAS" : "TODAS";
        String moneda = (criterio.getTipoMoneda() == 999) ? "TODAS" : monedaService.buscarPorCodigoMoneda(criterio.getTipoMoneda()).get(0).getDescripcion();
        return cmp.horizontalList().add(cmp.text("FECHA INICIO: " + fechaInicio).setStyle(criterioStyle)).add(cmp.text("FECHA FIN: " + fechaFin).setStyle(criterioStyle)).add(cmp.text(("RPTA TRANSACCION: " + transaccion)).setStyle(criterioStyle))
                .add(cmp.text(("TIPO MONEDA: " + moneda)).setStyle(criterioStyle)).newRow();
    }

    private ComponentBuilder<?, ?> createTitle()
    {
        StyleBuilder titleStyle = stl.style().bold().setTextAlignment(HorizontalTextAlignment.LEFT, VerticalTextAlignment.MIDDLE).setFontSize(15);
        return cmp.horizontalList().add(cmp.text("REPORTE COMPENSACION RECEPTOR").setStyle(titleStyle).setWidth(20)).newRow();
    }
}