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
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.IExtornosControversiasMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaExtornosControversias;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.reporte.ReporteExtornosControversias;
import ob.debitos.simp.model.websocket.Message;
import ob.debitos.simp.model.websocket.MessageDecoder;
import ob.debitos.simp.model.websocket.MessageEncoder;
import ob.debitos.simp.service.IReporteExtornosControversiasService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.utilitario.ExportFilterOutputStream;
import ob.debitos.simp.utilitario.PaginacionUtil;
import ob.debitos.simp.service.IExportacionPOIService;

@Service
@ServerEndpoint(value = "/extornosControversias", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class ReporteExtornosControversiasService implements IReporteExtornosControversiasService
{

    private @Autowired IExtornosControversiasMapper extornosControversiasMapper;

    private @Autowired IExportacionPOIService<ReporteExtornosControversias> exportExtornosControversias;

    private @Autowired Logger logger;
    private static final List<Session> sessions = new ArrayList<>();
    private static int MAX = 1000000;

    private static boolean cancelado = false;
    boolean reporteCompleto = false;
    private double procesandoRegistro;

    @Override
    @Truncable(clase = ReporteExtornosControversias.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteExtornosControversias> buscar(CriterioBusquedaExtornosControversias criterio)
    {
        return this.extornosControversiasMapper.buscar(criterio);
    }

    private static final String[][] cabeceraExportacionExtornosControversias = {
            {"Fecha Proceso"            ,"fechaProceso"                 ,""                             ,"formatFecha"            ,"-1"},
            {"Institución"              ,"codigoInstitucion"            ,"descInstitucion"              ,"formatCadena"           ,"-1"},
            {"Empresa"                  ,"idEmpresa"                    ,"descEmpresa"                  ,"formatCadena"           ,"-1"},
            {"Cliente"                  ,"idCliente"                    ,"descCliente"                  ,"formatCadena"           ,"-1"},
            {"Rol Transacción"          ,"codigoRolTransaccion"         ,"descripcionRol"               ,"formatCadena"           ,"-1"},
            {"Membresía"                ,"codigoMembresia"              ,"descripcionMembresia"         ,"formatCadena"           ,"-1"},
            {"Servicio"                 ,"codigoClaseServicio"          ,"descripcionServicio"          ,"formatCadena"           ,"-1"},
            {"Origen"                   ,"codigoOrigen"                 ,"descripcionOrigen"            ,"formatCadena"           ,"-1"},
            {"Clase Transacción"        ,"idClaseTransaccion"           ,"descripcionClaseTransaccion"  ,"formatCadena"           ,"-1"},
            {"Código Transacción"       ,"idCodigoTransaccion"          ,"descripcionCodigoTransaccion" ,"formatCadena"           ,"-1"},
            {"Código Respuesta"         ,"codigoRespuesta"              ,"descripcionRespuesta"         ,"formatCadena"           ,"-1"},
            {"Inst. Emisora"            ,"codigoInstitucionEmisora"     ,"descripcionEmisor"            ,"formatCadena"           ,"-1"},
            {"Inst. Receptora"          ,"codigoInstitucionReceptora"   ,"descripcionReceptor"          ,"formatCadena"           ,"-1"},
            {"Secuencia"                ,"secuenciaTransaccion"         ,""                             ,"formatCadena"           ,"-1"},
            {"Número Tarjeta"           ,"numeroTarjeta"                ,""                             ,"formatCadena"           ,"-1"},
            {"Número Cuenta"            ,"numeroCuenta"                 ,""                             ,"formatCadena"           ,"-1"},
            {"Fecha Transacción"        ,"fechaTransaccionCadena"       ,"horaTransaccion"              ,"formatCadenaCentrada"   ,"-1"},
            {"Moneda"                   ,"codigoMoneda"                 ,"descripcionMoneda"            ,"formatCadena"           ,"-1"},
            {"Importe"                  ,"valorCompensacion"            ,""                             ,"formatMonto"            ,"-1"},
            {"Referencia Intercambio"   ,"referenciaIntercambio"        ,""                             ,"formatCadena"           ,"-1"},
            {"Nombre Afiliado"          ,"nombreAfiliado"               ,""                             ,"formatCadena"           ,"-1"},
            {"Ciudad Afiliado"          ,"ciudadAfiliado"               ,""                             ,"formatCadena"           ,"-1"},
            {"Fondo Cargo"              ,"fondosCargo"                  ,"descFondosCargo"              ,"formatCadena"           ,"-1"},
            {"Fondo Abono"              ,"fondosAbono"                  ,"descFondosAbono"              ,"formatCadena"           ,"-1"},
            {"Estado Contable"          ,"contadorFondos"               ,"descripcionEstadoContable"    ,"formatCadena"           ,"-1"},
            {"Cta. Cargo"               ,"cuentaCargo"                  ,""                             ,"formatCadena"           ,"-1"},
            {"Cta. Abono"               ,"cuentaAbono"                  ,""                             ,"formatCadena"           ,"-1"}
    };

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void descargarReporteExtornosControversias(List<ReporteExtornosControversias> list, CriterioBusquedaExtornosControversias criterio, HttpServletRequest request, HttpServletResponse response) throws IOException
    {   
        String[][] filtros = {
                {"Fecha Proceso"          ,criterio.getDescripcionRangoFechas()},
                {"Institución"            ,criterio.getDescripcionInstitucion()},
                {"Empresa"                ,criterio.getDescripcionEmpresa()},
                {"Cliente"                ,criterio.getDescripcionCliente()},
                {"Rol Transacción"        ,criterio.getDescripcionRolTransaccion()},
                {"Código Respuesta"       ,criterio.getDescripcionCodigoRespuesta()},
                {"Número Tarjeta"         ,criterio.getNumeroTarjeta()},
                {"Referencia Intercambio" ,criterio.getReferenciaIntercambio()}
        };
        this.exportExtornosControversias.generarExportacionNormal("Reporte Extornos Controversias", list, filtros, cabeceraExportacionExtornosControversias, false, 3, 85, request, response);
    };

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void construirResumenCuadreBancoAdministradorExportacion(CriterioBusquedaExtornosControversias criterioBusqueda, List<ReporteExtornosControversias> resultSet, SXSSFWorkbook sxssfWorkbook)
    {
        reporteCompleto = false;
        reporteCompleto = construirPagina(criterioBusqueda, resultSet, sxssfWorkbook, 0);
        try
        {
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message(procesandoRegistro, reporteCompleto, false));
                }
            }
        } catch (EncodeException e)
        {
            logger.error("cancelado: {}", e.getMessage());
        } catch (Exception e)
        {
            logger.error("Ocurrio un error al tratar de generar el reporte: {}", e.getMessage());
        } finally
        {
            cancelado = false;
            reporteCompleto = false;
            procesandoRegistro = 0;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private boolean construirPagina(CriterioBusquedaExtornosControversias criterioBusqueda, List<ReporteExtornosControversias> resultSet, SXSSFWorkbook sxssfWorkbook, int numPagina)
    {
        Sheet sheet = sxssfWorkbook.getSheetAt(numPagina);
        sheet.setDefaultRowHeight((short) 380);
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();

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

        CellStyle estiloNormal = this.crearEstiloBasico(sxssfWorkbook, fontNormal);

        CellStyle estiloCab = this.crearEstiloBasico(sxssfWorkbook, fontWhite);
        estiloCab.setAlignment(HorizontalAlignment.CENTER);
        estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle estiloDecimal2 = this.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

        CriterioPaginacion<CriterioBusquedaExtornosControversias, ?> criterioPaginacion = PaginacionUtil.getCriterioPaginacionParaReporteXLSX(criterioBusqueda, 0, MAX);

        int filaActual = 9;
        int numeroFila = 1;

        try
        {
            sheet.createFreezePane(0, 9);
            sheet.setAutoFilter(new CellRangeAddress(8, 8, 1, 25));

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            Row row = null;
            Cell cell = null;

            row = sheet.createRow(3);
            cell = row.createCell(1);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Fecha de Proceso");
            cell = row.createCell(2);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionRangoFechas());
            cell = row.createCell(4);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Número de Tarjeta");
            cell = row.createCell(5);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getNumeroTarjeta());
            cell = row.createCell(7);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Rol Transacción");
            cell = row.createCell(8);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionRolTransaccion());

            row = sheet.createRow(5);
            cell = row.createCell(1);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Institución");
            cell = row.createCell(2);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getDescripcionInstitucion());
            cell = row.createCell(4);
            cell.setCellStyle(estiloBack);
            cell.setCellValue("Referencia Intercambio");
            cell = row.createCell(5);
            cell.setCellValue(criterioPaginacion.getCriterioBusqueda().getReferenciaIntercambio());

            String cab[] = { "Secuencia Transacción", "Rol Transacción", "Membresía", "Clase Servicio", "Origen", "Clase Transacción", "Código Transacción", "Número Tarjeta", "Referencia Intercambio", "Fecha Proceso", "Institución Emisora",
                    "Institución Receptora", "Moneda", "Valor Compensación", "Número Cuenta", "Fecha Transacción", "Nombre Afiliado", "Ciudad Afiliado", "Fondos Cargo", "Fondos Abono", "Respuesta", "Contador Fondos", "Estado Contable",
                    "Cuenta Cargo", "Cuenta Abono" };

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

                int tamReporte = resultSet.size();

                for (int i = 0; i < tamReporte; i++)
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

                    row = sheet.createRow(filaActual);
                    cell = row.createCell(1);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getSecuenciaTransaccion());
                    cell = row.createCell(2);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getCodigoRolTransaccion().toString(), resultSet.get(i).getDescripcionRol()));
                    cell = row.createCell(3);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getCodigoMembresia(), resultSet.get(i).getDescripcionMembresia()));
                    cell = row.createCell(4);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getCodigoClaseServicio(), resultSet.get(i).getDescripcionServicio()));
                    cell = row.createCell(5);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getCodigoOrigen().toString(), resultSet.get(i).getDescripcionOrigen()));
                    cell = row.createCell(6);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getIdClaseTransaccion().toString(), resultSet.get(i).getDescripcionClaseTransaccion()));
                    cell = row.createCell(7);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getIdCodigoTransaccion().toString(), resultSet.get(i).getDescripcionCodigoTransaccion()));
                    cell = row.createCell(8);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getNumeroTarjeta());

                    cell = row.createCell(9);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getReferenciaIntercambio());
                    cell = row.createCell(10);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(formatter.format(resultSet.get(i).getFechaProceso()));
                    cell = row.createCell(11);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getCodigoInstitucionEmisora().toString(), resultSet.get(i).getDescripcionEmisor()));
                    cell = row.createCell(12);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getCodigoInstitucionReceptora().toString(), resultSet.get(i).getDescripcionReceptor()));
                    cell = row.createCell(13);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getCodigoMoneda().toString(), resultSet.get(i).getDescripcionMoneda()));
                    cell = row.createCell(14);
                    cell.setCellStyle(estiloDecimal2);
                    cell.setCellValue(resultSet.get(i).getValorCompensacion());
                    cell = row.createCell(15);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getNumeroCuenta());
                    cell = row.createCell(16);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(formatter.format(resultSet.get(i).getFechaTransaccion()));
                    cell = row.createCell(17);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getNombreAfiliado());
                    cell = row.createCell(18);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getCiudadAfiliado());
                    cell = row.createCell(19);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getFondosCargo());
                    cell = row.createCell(20);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getFondosAbono());
                    cell = row.createCell(21);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(concatenarCodigoDescripcion(resultSet.get(i).getCodigoRespuesta(), resultSet.get(i).getDescripcionRespuesta()));
                    cell = row.createCell(22);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getContadorFondos());
                    cell = row.createCell(23);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getDescripcionEstadoContable());
                    cell = row.createCell(24);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getCuentaCargo());
                    cell = row.createCell(25);
                    cell.setCellStyle(estiloNormal);
                    cell.setCellValue(resultSet.get(i).getCuentaAbono());

                    filaActual++;
                    numeroFila++;
                }
            } catch (IllegalStateException e)
            {
                logger.error("La conexión ha sido cancelada: {}", e.getMessage());
            } catch (ClientAbortException e)
            {
                logger.error("La conexión cancelada: {}", e.getMessage());
            } catch (Exception e)
            {
                logger.error("Ocurrió un error: {}", e.getMessage());
            }
            logger.info("Consulta {} terminada: {}", numPagina);

            for (int i = 1; i <= cab.length; i++)
            {
                sheet.autoSizeColumn(i);
            }

            return (criterioPaginacion.getStart() + criterioPaginacion.getLength()) >= totalFilas;
        } catch (Exception e)
        {
            logger.error("Error: {}", e.getMessage());
            throw new SimpException("Ocurrió un error al recuperar los registros de la base de datos");
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

    private CellStyle crearEstiloNumero(SXSSFWorkbook sxssfWorkbook, String formato, Font font)
    {
        CellStyle estilo = sxssfWorkbook.createCellStyle();

        estilo.setAlignment(HorizontalAlignment.RIGHT);
        estilo.setDataFormat(sxssfWorkbook.createDataFormat().getFormat(formato));
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

    private String concatenarCodigoDescripcion(String codigo, String descripcion)
    {
        return StringUtils.join(validarString(codigo), " - ", validarString(descripcion));
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
