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
import javax.servlet.http.HttpServletRequest;
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
import ob.debitos.simp.mapper.IReporteInterfaceContableMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteInterfaceContable;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.reporte.ReporteInterfaceContable;
import ob.debitos.simp.model.websocket.Message;
import ob.debitos.simp.model.websocket.MessageDecoder;
import ob.debitos.simp.model.websocket.MessageEncoder;
import ob.debitos.simp.service.IReporteInterfaceContableService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.utilitario.ExportFilterOutputStream;
import ob.debitos.simp.utilitario.ExportacionUtil;
import ob.debitos.simp.utilitario.PaginacionUtil;
import ob.debitos.simp.utilitario.StringsUtils;
import ob.debitos.simp.service.IExportacionPOIService;

@Service
@ServerEndpoint(value = "/interfaceContable", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class ReporteInterfaceContableService implements IReporteInterfaceContableService
{
    private @Autowired Logger logger;
    private @Autowired IReporteInterfaceContableMapper reporteInterfaceContableMapper;

    private @Autowired IExportacionPOIService<ReporteInterfaceContable> exportInterfaceContable;

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
    @Truncable(clase = ReporteInterfaceContable.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteInterfaceContable> buscarInterfaceContablePorCriterio(CriterioBusquedaReporteInterfaceContable criterioBusquedaReporteInterfaceContable)
    {
        return this.reporteInterfaceContableMapper.buscarInterfaceContablePorCriterio(criterioBusquedaReporteInterfaceContable);
    }

    private static final String[][] cabeceraExportacionInterfaceContable = {
            {"Fecha Proceso"        ,"fechaProceso"           ,""                                 ,"formatFecha"            ,"-1"},
            {"Institución"          ,"idInstitucion"          ,"descInstitucion"                  ,"formatCadena"           ,"-1"},
            {"Empresa"              ,"idEmpresa"              ,"descripcionEmpresa"               ,"formatCadena"           ,"-1"},
            {"Cliente"              ,"idCliente"              ,"descripcionCliente"               ,"formatCadena"           ,"-1"},
            {"Membresía"            ,"membresia"              ,"descMembresia"                    ,"formatCadena"           ,"-1"},
            {"Servicio"             ,"claseServicio"          ,"descClaseServicio"                ,"formatCadena"           ,"-1"},
            {"Origen"               ,"origen"                 ,"descOrigen"                       ,"formatCadena"           ,"-1"},
            {"Clase Transacción"    ,"claseTransaccion"       ,"descripcionClaseTransaccion"      ,"formatCadena"           ,"-1"},
            {"Código Transacción"   ,"codigoTransaccion"      ,"descripcionCodigoTransaccion"     ,"formatCadena"           ,"-1"},
            {"Operación"            ,"operacion"              ,""                                 ,"formatCadena"           ,"-1"},
            {"Inst. Emisora"        ,"institucionEmisora"     ,"descripcionInstitucionEmisora"    ,"formatCadena"           ,"-1"},
            {"Inst. Receptora"      ,"institucionReceptora"   ,"descripcionInstitucionReceptora"  ,"formatCadena"           ,"-1"},
            {"ID THB"               ,"idThb"                  ,""                                 ,"formatCadena"           ,"-1"},
            {"Número Tarjeta"       ,"numeroTarjeta"          ,""                                 ,"formatCadena"           ,"-1"},
            {"Fecha Transacción"    ,"fechaTransaccionCadena" ,"horaTransaccion"                  ,"formatCadenaCentrada"   ,"-1"},
            {"Moneda Transacción"   ,"monedaTransaccion"      ,"descMonedaTransaccion"            ,"formatCadena"           ,"-1"},
            {"Importe Transacción"  ,"montoTransaccion"       ,""                                 ,"formatMonto"            ,"-1"},
            {"Moneda Compensación"  ,"monedaCompensacion"     ,"descMonedaCompensacion"           ,"formatCadena"           ,"-1"},
            {"Importe Compensación" ,"montoCompensacion"      ,""                                 ,"formatMonto"            ,"-1"},
            {"Tipo Cambio SBS"      ,"tipoCambioSBS"          ,""                                 ,"formatComision"         ,"-1"},
            {"Cta. Cargo"           ,"cuentaCargo"            ,"descripcionCuentaCargo"           ,"formatCadena"           ,"-1"},
            {"Cta. Abono"           ,"cuentaAbono"            ,"descripcionCuentaAbono"           ,"formatCadena"           ,"-1"}
    };

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generarReporteInterfaceContable(List<ReporteInterfaceContable> list, CriterioBusquedaReporteInterfaceContable criterio, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso"  ,criterio.getDescFechaProceso()},
                {"Institución"    ,criterio.getDescripcionInstitucion()},
                {"Empresa"        ,criterio.getDescripcionEmpresa()},
                {"Cliente"        ,criterio.getDescripcionCliente()}
        };
        this.exportInterfaceContable.generarExportacionNormal("Reporte Interface Contable", list, filtros, cabeceraExportacionInterfaceContable, false, 3, 85, request, response);
    }

    private boolean construirReporteInterfaceContable(CriterioPaginacion<CriterioBusquedaReporteInterfaceContable, ?> criterioPaginacion, SXSSFWorkbook sxssfWorkbook, int indiceSheetActual, List<ReporteInterfaceContable> reporte)
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
        estiloBackGrey.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
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

        CellStyle estiloCriterioValor = ExportacionUtil.crearEstiloBasico(sxssfWorkbook, fontBold);
        estiloCriterioValor.setAlignment(HorizontalAlignment.CENTER);

        CellStyle estiloFecha = sxssfWorkbook.createCellStyle();
        estiloFecha.setAlignment(HorizontalAlignment.CENTER);
        estiloFecha.setDataFormat(sxssfWorkbook.createDataFormat().getFormat("dd/MM/yyyy"));

        CellStyle estiloSimple = ExportacionUtil.crearEstiloBasico(sxssfWorkbook, fontNormal);

        CellStyle estiloCantidad = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "#,##0", fontNormal);
        estiloCantidad.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        estiloCantidad.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        CellStyle estiloNumeroGrey = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "#0", fontNormal);

        estiloNumeroGrey.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        estiloNumeroGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle decimalDos = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CellStyle decimalCuatro = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.0000;[Red]-#,##0.0000;[Black]#,##0.0000", fontNormal);

        logger.info("Preparando consulta al VIEW ReporteInterfaceContable");
        try
        {
            String cab[] = { "Fecha Proceso", "Empresa", "Cliente", "THB", "Membresía", "Código Transacción", "Clase Transacción", "Operación", "Institución Emisora", "Institución Receptora", "Tipo Cambio SBS", "Fecha Transacción", "Hora Txn",
                    "Número Tarjeta", "Moneda Txn", "Monto Txn", "Moneda Compensación", "Monto Compensación", "Cuenta Cargo", "Cuenta Abono" };

            sheet.createFreezePane(0, 6);
            sheet.setAutoFilter(new CellRangeAddress(5, 5, 1, cab.length));

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;
            double totalFilas = 0;
            int numeroFilaContenidoTable = 6;
            boolean seObtuvoTotalFilas = false;

            row = sheet.createRow(3);
            cell = row.createCell(1);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha Proceso");
            cell = row.createCell(2);
            cell.setCellStyle(estiloCriterioValor);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescFechaProceso());

            row = sheet.createRow(5);
            for (int i = 0; i < cab.length; i++)
            {
                cell = row.createCell(i + 1);
                cell.setCellStyle(estiloCab);
                cell.setCellValue(cab[i]);
            }

            logger.info("Ejecutando consulta: {}", indiceSheetActual);

            try
            {
                List<ReporteInterfaceContable> resultSet = reporte;

                int tamReporte = resultSet.size();

                for (int j = 0; j < tamReporte; j++)
                {
                    if (!seObtuvoTotalFilas)
                    {
                        totalFilas = tamReporte;
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
                    cell.setCellValue(resultSet.get(j).getFechaProceso() == null ? "" : formatter.format(resultSet.get(j).getFechaProceso()));
                    cell = row.createCell(2);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(
                            StringsUtils.concatenarCadena(resultSet.get(j).getIdEmpresa() == null ? "" : resultSet.get(j).getIdEmpresa(), " - ", resultSet.get(j).getDescripcionEmpresa() == null ? "" : resultSet.get(j).getDescripcionEmpresa()));
                    cell = row.createCell(3);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(
                            StringsUtils.concatenarCadena(resultSet.get(j).getIdCliente() == null ? "" : resultSet.get(j).getIdCliente(), " - ", resultSet.get(j).getDescripcionCliente() == null ? "" : resultSet.get(j).getDescripcionCliente()));
                    cell = row.createCell(4);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.get(j).getIdThb() == null ? "" : resultSet.get(j).getIdThb());
                    cell = row.createCell(5);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(StringsUtils.concatenarCadena(resultSet.get(j).getMembresia() == null ? "" : resultSet.get(j).getMembresia(), " - ", resultSet.get(j).getDescMembresia() == null ? "" : resultSet.get(j).getDescMembresia()));
                    cell = row.createCell(6);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(StringsUtils.concatenarCadena(resultSet.get(j).getCodigoTransaccion() == null ? "" : resultSet.get(j).getCodigoTransaccion(), " - ",
                            resultSet.get(j).getDescripcionCodigoTransaccion() == null ? "" : resultSet.get(j).getDescripcionCodigoTransaccion()));
                    cell = row.createCell(7);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(StringsUtils.concatenarCadena(resultSet.get(j).getClaseTransaccion() == null ? "" : resultSet.get(j).getClaseTransaccion(), " - ",
                            resultSet.get(j).getDescripcionClaseTransaccion() == null ? "" : resultSet.get(j).getDescripcionClaseTransaccion()));
                    cell = row.createCell(8);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.get(j).getOperacion() == null ? "" : resultSet.get(j).getOperacion());
                    cell = row.createCell(9);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(StringsUtils.concatenarCadena(resultSet.get(j).getInstitucionEmisora() == null ? "" : resultSet.get(j).getInstitucionEmisora(), " - ",
                            resultSet.get(j).getDescripcionInstitucionEmisora() == null ? "" : resultSet.get(j).getDescripcionInstitucionEmisora()));
                    cell = row.createCell(10);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(StringsUtils.concatenarCadena(resultSet.get(j).getInstitucionReceptora() == null ? "" : resultSet.get(j).getInstitucionReceptora(), " - ",
                            resultSet.get(j).getDescripcionInstitucionReceptora() == null ? "" : resultSet.get(j).getDescripcionInstitucionReceptora()));
                    cell = row.createCell(11);
                    if (resultSet.get(j).getTipoCambioSBS() == null)
                    {
                        cell.setCellStyle(estiloSimple);
                        cell.setCellValue("-");
                    } else
                    {
                        cell.setCellStyle(decimalCuatro);
                        cell.setCellValue(resultSet.get(j).getTipoCambioSBS());
                    }
                    cell = row.createCell(12);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.get(j).getFechaTransaccion() == null ? "" : formatter.format(resultSet.get(j).getFechaTransaccion()));
                    cell = row.createCell(13);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.get(j).getHoraTransaccion() == null ? "" : resultSet.get(j).getHoraTransaccion());
                    cell = row.createCell(14);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(resultSet.get(j).getNumeroTarjeta() == null ? "" : resultSet.get(j).getNumeroTarjeta());
                    cell = row.createCell(15);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(StringsUtils.concatenarCadena(resultSet.get(j).getMonedaTransaccion() == null ? "" : resultSet.get(j).getMonedaTransaccion(), " - ",
                            resultSet.get(j).getDescMonedaTransaccion() == null ? "" : resultSet.get(j).getDescMonedaTransaccion()));
                    cell = row.createCell(16);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.get(j).getMontoTransaccion() == null ? 0.0 : resultSet.get(j).getMontoTransaccion());
                    cell = row.createCell(17);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(StringsUtils.concatenarCadena(resultSet.get(j).getMonedaCompensacion() == null ? "" : resultSet.get(j).getMonedaCompensacion(), " - ",
                            resultSet.get(j).getDescMonedaCompensacion() == null ? "" : resultSet.get(j).getDescMonedaCompensacion()));
                    cell = row.createCell(18);
                    cell.setCellStyle(decimalDos);
                    cell.setCellValue(resultSet.get(j).getMontoCompensacion() == null ? 0.0 : resultSet.get(j).getMontoCompensacion());
                    cell = row.createCell(19);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(StringsUtils.concatenarCadena(resultSet.get(j).getCuentaCargo() == null ? "" : resultSet.get(j).getCuentaCargo(), " - ",
                            resultSet.get(j).getDescripcionCuentaCargo() == null ? "" : resultSet.get(j).getDescripcionCuentaCargo()));
                    cell = row.createCell(20);
                    cell.setCellStyle(estiloSimple);
                    cell.setCellValue(StringsUtils.concatenarCadena(resultSet.get(j).getCuentaAbono() == null ? "" : resultSet.get(j).getCuentaAbono(), " - ",
                            resultSet.get(j).getDescripcionCuentaAbono() == null ? "" : resultSet.get(j).getDescripcionCuentaAbono()));

                    numeroFilaContenidoTable++;
                    numeroFila++;

                }
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

}
