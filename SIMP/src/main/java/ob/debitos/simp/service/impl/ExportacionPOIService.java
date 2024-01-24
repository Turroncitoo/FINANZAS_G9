package ob.debitos.simp.service.impl;

import com.google.common.net.HttpHeaders;
import ob.debitos.simp.model.mantenimiento.ConceptoComision;
import ob.debitos.simp.model.mantenimiento.Moneda;
import ob.debitos.simp.model.reporte.ReporteComision;
import ob.debitos.simp.model.websocket.Message;
import ob.debitos.simp.model.websocket.MessageDecoder;
import ob.debitos.simp.model.websocket.MessageEncoder;
import ob.debitos.simp.service.IExportacionPOIService;
import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.utilitario.ExportFilterOutputStream;
import ob.debitos.simp.utilitario.ExportacionUtil;
import ob.debitos.simp.utilitario.StringsUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFFormulaEvaluator;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Proporciona e implementa métodos genéricos comunes para las clase de servicio
 * de exportacion de reportes y consultas.
 * <p>
 *
 * @param <V> La clase a la cual se generará la exportación de este servicio
 * @author Anthony Oroche
 */
@Service
@ServerEndpoint(value = "/exportacionPOI", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class ExportacionPOIService<V> implements IExportacionPOIService<V>
{

    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired IMonedaService monedaService;
    private @Autowired Logger logger;

    public static final String CONTENT_TYPE = "application/zip";
    public static final String ZIP = ".zip";
    public static final String COMISIONES = "Comisiones";
    public static final String TOTAL = "Total";

    public static final String ComTOTAL = "Comisión Total";
    public static final String ABONO = "A";
    public static final String CARGO = "C";
    public static final String TIPO_LETRA = "Segoe UI";

    public static final String F_FECHA = "formatFecha";
    public static final String F_CADENA = "formatCadena";
    public static final String F_CADENA_DERECHA = "formatCadenaDerecha";
    public static final String F_CADENA_CENTRADA = "formatCadenaCentrada";
    public static final String F_INDICADOR = "formatIndicador";
    public static final String F_SI_NO = "formatSiNo";
    public static final String F_CANTIDAD = "formatCantidad";
    public static final String F_MONTO = "formatMonto";
    public static final String F_COMISION = "formatComision";
    public static final String F_CADENA_ESPACIO = "formatCadenaE";
    public static final String F_MONTO_O_COMIS = "formatMontoComis";
    public static final String F_CONCEPTO_COMISION = "formatConceptoComis";

    private static final List<Session> sessions = new ArrayList<>();
    private static boolean cancelado = false;
    private static int MAX = 1000000;
    private static int TIPO_REPORTE_1 = 1;
    private static int TIPO_REPORTE_2 = 2;
    private static int TIPO_REPORTE_3 = 3;
    boolean reporteCompleto = false;
    double procesandoRegistro = 0;
    int totalArchivos = 0;
    int totalRegistros = 0;
    int totalFilasArchivo = 0;
    int numeroFila = 1;
    int agregarTotalesReg = 0;
    int anchoAdicionado = 1750;
    int agregarTipoReporteReg = 0;

    private final Integer TAMANIO_DEFAULT = 7000;
    private final Integer TAMANIO_COMIS = 5500;
    private final Integer TAMANIO_MINIMO = 4000;
    private final Integer TAMANIO_MAXIMO = 20000;

    private Font font;
    private Font fontSiNo;
    private Font fontBold;
    private Font fontNormal;
    private CellStyle estiloFecha;
    private CellStyle estiloCentro;
    private CellStyle estiloDerecho;
    private CellStyle estiloNormal;
    private CellStyle estiloNormalFiltro;
    private CellStyle estiloCab;
    private CellStyle estiloNumero;
    private CellStyle estiloDecimal;
    private CellStyle estiloDecimal4;
    private CellStyle estiloSiNo;
    private CellStyle estiloIndicador;

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

    public ExportacionPOIService()
    {
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void generarExportacionNormal(String nombreArchivo, List<V> lista, String[][] filtros, String[][] cabeceras, boolean agregarTotales, int tipoReporte, int zoom, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String fileNameZip = StringsUtils.concatenarCadena(nombreArchivo, ZIP);
        String fileName = nombreArchivo;
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileNameZip);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        //byte[] imgBytes = parametroGeneralService.buscarLogo();
        SXSSFWorkbook sxssfWorkbook = null;
        Integer tamanioListaSoles = ExportacionPOIService.calcularTamanioLista(lista);
        totalRegistros = tamanioListaSoles;
        totalFilasArchivo = 0;
        Integer ListaMasGrande = tamanioListaSoles;
        totalArchivos = (int) Math.ceil((double) ListaMasGrande / MAX);
        logger.info("Iniciando el proceso de generación del reporte, con un total de archivos: {}.", totalArchivos);
        @SuppressWarnings("unchecked")
        List<V>[] listas = ExportacionPOIService.partition(lista, MAX, totalArchivos);
        try
        {
            int inicio = 0;
            int indiceSheetActual = 1;
            int i = 0;
            do
            {
                XSSFWorkbook workbook = new XSSFWorkbook();
                workbook.createSheet(nombreArchivo);
                sxssfWorkbook = new SXSSFWorkbook(workbook, SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
                logger.info(StringsUtils.concatenarCadena("Generando ", nombreArchivo, ": {}."), indiceSheetActual);
                int hoja = 0;
                reporteCompleto = this.construirExportacionNormal((listas == null) ? null : listas[i], sxssfWorkbook, inicio, indiceSheetActual, filtros, cabeceras, agregarTotales, hoja, tipoReporte, zoom);
                this.validarTipoReporte(workbook, request, tipoReporte, hoja, fileName/*, imgBytes*/);
                ZipEntry zipEntry = new ZipEntry(fileName + "_" + indiceSheetActual + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                sxssfWorkbook.write(new ExportFilterOutputStream(zipOutputStream));
                zipOutputStream.closeEntry();
                inicio += MAX;
                indiceSheetActual++;
                i++;
            } while (!reporteCompleto);
            logger.info(StringsUtils.concatenarCadena("¿", nombreArchivo, " Completo?: {}", (reporteCompleto == true || procesandoRegistro >= 100) ? " con [{}] registros" : "", "."),
                    (reporteCompleto == true || procesandoRegistro >= 100) ? "SI" : "NO", totalRegistros);
            zipOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(bytes);
            servletOutputStream.flush();
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message((reporteCompleto == true || procesandoRegistro >= 100) ? ((procesandoRegistro == 0) ? 0 : 100) : procesandoRegistro, reporteCompleto, false));
                }
            }

        }
        catch (Exception e)
        {
            logger.error(StringsUtils.concatenarCadena("Ocurrio un error al tratar de generar ", nombreArchivo, ": {}."), e);
        }
        finally
        {
            cancelado = false;
            reporteCompleto = false;
            totalArchivos = 0;
            procesandoRegistro = 0;
            numeroFila = 1;
        }
    }

    public boolean construirExportacionNormal(List<V> lista, SXSSFWorkbook sxssfWorkbook, int inicio, int indiceSheetActual, String[][] filtros, String[][] cab, boolean agregarTotales, int hoja, int tipoReporte, int zoom) throws Exception
    {
        logger.info(StringsUtils.concatenarCadena("Consulta {} iniciando..."), indiceSheetActual);
        Sheet sheet = null;
        sheet = sxssfWorkbook.getSheetAt(hoja);
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
        sheet.setZoom((zoom < 10 || zoom > 400) ? 100 : zoom);
        int filaInicial = 1;
        int columnaInicial = 1;
        this.configurarEstilos(sxssfWorkbook);
        Integer tamanioLista = ExportacionPOIService.calcularTamanioLista(lista);
        totalFilasArchivo = totalFilasArchivo + tamanioLista;
        Row row = null;
        Cell cell = null;
        if (tipoReporte >= 1 && tipoReporte <= 3)
        {
            if (tipoReporte == TIPO_REPORTE_1)
            {
                agregarTipoReporteReg = -6;
            }
            if (tipoReporte == TIPO_REPORTE_2)
            {
                agregarTipoReporteReg = -2;
                row = sheet.createRow(filaInicial + agregarTipoReporteReg + 3);
                this.agregarFiltros(filtros, row, cell);
            }
            if (tipoReporte == TIPO_REPORTE_3)
            {
                agregarTipoReporteReg = 0;
                row = sheet.createRow(filaInicial + agregarTipoReporteReg + 3);
                this.agregarFiltros(filtros, row, cell);
            }
        }
        if (agregarTotales)
        {
            row = sheet.createRow(filaInicial + agregarTipoReporteReg + 5);
            this.agregarTotalesNormal(lista, cab, filaInicial + agregarTipoReporteReg, row, cell);
            agregarTotalesReg = 0;
        } else
        {
            agregarTotalesReg = -2;
        }
        row = sheet.createRow(filaInicial + agregarTotalesReg + agregarTipoReporteReg + 7);
        this.agregarCabecaras(cab, filaInicial + agregarTotalesReg + agregarTipoReporteReg, sheet, row, cell);
        this.agregarRegistrosCabecerasNormal(lista, cab, null, 100, filaInicial + agregarTotalesReg + agregarTipoReporteReg, sheet, row, cell);
        // if (agregarTotales)
        // {
        //     SXSSFFormulaEvaluator.evaluateAllFormulaCells(sxssfWorkbook, false);
        // }
        logger.info("Consulta {} terminada con '{}' registros.", indiceSheetActual, tamanioLista);
        sheet.setColumnWidth(columnaInicial - 1, 840);
        for (int i = columnaInicial; i <= ((cab.length >= (filtros.length * 3)) ? cab.length : (filtros.length * 3)); i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, ((sheet.getColumnWidth(i) + anchoAdicionado) > TAMANIO_MAXIMO) ? TAMANIO_MAXIMO : (sheet.getColumnWidth(i) + anchoAdicionado));
        }
        sheet.createFreezePane(0, filaInicial + agregarTotalesReg + agregarTipoReporteReg + 8);
        sheet.setAutoFilter(new CellRangeAddress(filaInicial + agregarTotalesReg + agregarTipoReporteReg + 7, filaInicial + agregarTotalesReg + agregarTipoReporteReg + 7, 1, cab.length));
        return totalFilasArchivo >= totalRegistros;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void generarExportacionNormalComis(String nombreArchivo, String nombreListaComisiones, List<V> lista, String[][] filtros, String[][] cabeceras, List<ConceptoComision> conceptosComision, int tipoReporte, int zoom,
                                              HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String fileNameZip = StringsUtils.concatenarCadena(nombreArchivo, ZIP);
        String fileName = nombreArchivo;
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileNameZip);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        //byte[] imgBytes = parametroGeneralService.buscarLogo();
        SXSSFWorkbook sxssfWorkbook = null;
        Integer tamanioListaSoles = ExportacionPOIService.calcularTamanioLista(lista);
        totalRegistros = tamanioListaSoles;
        totalFilasArchivo = 0;
        Integer ListaMasGrande = tamanioListaSoles;
        totalArchivos = (int) Math.ceil((double) ListaMasGrande / MAX);
        logger.info("Iniciando el proceso de generación del reporte, con un total de archivos: {}.", totalArchivos);
        @SuppressWarnings("unchecked")
        List<V>[] listas = ExportacionPOIService.partition(lista, MAX, totalArchivos);
        try
        {
            int inicio = 0;
            int indiceSheetActual = 1;
            int i = 0;
            do
            {
                XSSFWorkbook workbook = new XSSFWorkbook();
                workbook.createSheet(nombreArchivo);
                sxssfWorkbook = new SXSSFWorkbook(workbook, SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
                logger.info(StringsUtils.concatenarCadena("Generando ", nombreArchivo, ": {}."), indiceSheetActual);
                int hoja = 0;
                reporteCompleto = this.construirExportacionNormalComis((listas == null) ? null : listas[i], sxssfWorkbook, inicio, indiceSheetActual, conceptosComision, filtros, cabeceras, nombreListaComisiones, true, hoja, tipoReporte, zoom);
                this.validarTipoReporte(workbook, request, tipoReporte, hoja, fileName/*, imgBytes*/);
                ZipEntry zipEntry = new ZipEntry(fileName + "_" + indiceSheetActual + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                sxssfWorkbook.write(new ExportFilterOutputStream(zipOutputStream));
                zipOutputStream.closeEntry();
                inicio += MAX;
                indiceSheetActual++;
                i++;
            } while (!reporteCompleto);
            logger.info(StringsUtils.concatenarCadena("¿", nombreArchivo, " Completo?: {}", (reporteCompleto == true || procesandoRegistro >= 100) ? " con [{}] registros" : "", "."),
                    (reporteCompleto == true || procesandoRegistro >= 100) ? "SI" : "NO", totalRegistros);
            zipOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(bytes);
            servletOutputStream.flush();
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message((reporteCompleto == true || procesandoRegistro >= 100) ? ((procesandoRegistro == 0) ? 0 : 100) : procesandoRegistro, reporteCompleto, false));
                }
            }
        }
        catch (Exception e)
        {
            logger.error(StringsUtils.concatenarCadena("Ocurrio un error al tratar de generar ", nombreArchivo, ": {}."), e);
        }
        finally
        {
            cancelado = false;
            reporteCompleto = false;
            totalArchivos = 0;
            procesandoRegistro = 0;
            numeroFila = 1;
        }
    }

    public boolean construirExportacionNormalComis(List<V> lista, SXSSFWorkbook sxssfWorkbook, int inicio, int indiceSheetActual, List<ConceptoComision> conceptosComision, String[][] filtros, String[][] cab, String nombreListaComisiones,
                                                   boolean agregarTotales, int hoja, int tipoReporte, int zoom) throws Exception
    {
        logger.info(StringsUtils.concatenarCadena("Consulta {} iniciando..."), indiceSheetActual);
        Sheet sheet = null;
        sheet = sxssfWorkbook.getSheetAt(hoja);
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
        sheet.setZoom((zoom < 10 || zoom > 400) ? 100 : zoom);
        int filaInicial = 1;
        int columnaInicial = 1;
        this.configurarEstilos(sxssfWorkbook);
        Integer tamanioLista = ExportacionPOIService.calcularTamanioLista(lista);
        Integer tamanioComis = ExportacionPOIService.calcularTamanioLista(conceptosComision);
        totalFilasArchivo = totalFilasArchivo + tamanioLista;
        Row row = null;
        Cell cell = null;
        if (tipoReporte >= 1 && tipoReporte <= 3)
        {
            if (tipoReporte == TIPO_REPORTE_1)
            {
                agregarTipoReporteReg = -6;
            }
            if (tipoReporte == TIPO_REPORTE_2)
            {
                agregarTipoReporteReg = -2;
                row = sheet.createRow(filaInicial + agregarTipoReporteReg + 3);
                this.agregarFiltros(filtros, row, cell);
            }
            if (tipoReporte == TIPO_REPORTE_3)
            {
                agregarTipoReporteReg = 0;
                row = sheet.createRow(filaInicial + agregarTipoReporteReg + 3);
                this.agregarFiltros(filtros, row, cell);
            }
        }
        if (agregarTotales)
        {
            row = sheet.createRow(filaInicial + agregarTipoReporteReg + 5);
            this.agregarTotalesConComis(lista, cab, tamanioComis, filaInicial + agregarTipoReporteReg, row, cell);
            agregarTotalesReg = 0;
        } else
        {
            agregarTotalesReg = -2;
        }
        row = sheet.createRow(filaInicial + agregarTotalesReg + agregarTipoReporteReg + 7);
        this.agregarCabecarasConComisiones(cab, conceptosComision, filaInicial + agregarTotalesReg + agregarTipoReporteReg, sheet, row, cell);
        this.agregarRegistrosCabecerasConComis(lista, cab, conceptosComision, null, 100, filaInicial + agregarTotalesReg + agregarTipoReporteReg, nombreListaComisiones, sheet, row, cell);
        // if (agregarTotales)
        // {
        //     SXSSFFormulaEvaluator.evaluateAllFormulaCells(sxssfWorkbook, false);
        // }
        logger.info("Consulta {} terminada con '{}' registros.", indiceSheetActual, tamanioLista);
        sheet.setColumnWidth(columnaInicial - 1, 840);
        for (int i = columnaInicial; i <= ((cab.length >= (filtros.length * 3)) ? cab.length : (filtros.length * 3)) + conceptosComision.size(); i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, ((sheet.getColumnWidth(i) + anchoAdicionado) > TAMANIO_MAXIMO) ? TAMANIO_MAXIMO : (sheet.getColumnWidth(i) + anchoAdicionado));
        }
        sheet.createFreezePane(0, filaInicial + agregarTotalesReg + agregarTipoReporteReg + 9);
        sheet.setAutoFilter(new CellRangeAddress(filaInicial + agregarTotalesReg + agregarTipoReporteReg + 8, filaInicial + agregarTotalesReg + agregarTipoReporteReg + 8, 1, cab.length + tamanioComis + 1));
        return totalFilasArchivo >= totalRegistros;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void generarExportacionMonedaComis(String nombreArchivo, String nombreListaComisiones, List<V> listaSoles, List<V> listaDolares, String[][] filtros, String[][] cabeceras, List<ConceptoComision> conceptosComision, int tipoReporte, int zoom,
                                              HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String fileNameZip = StringsUtils.concatenarCadena(nombreArchivo, ZIP);
        String fileName = nombreArchivo;
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileNameZip);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        //byte[] imgBytes = parametroGeneralService.buscarLogo();
        List<Moneda> listaMonedas = this.monedaService.buscarTodos();
        SXSSFWorkbook sxssfWorkbook = null;
        Integer tamanioListaSoles = ExportacionPOIService.calcularTamanioLista(listaSoles);
        Integer tamanioListaDolares = ExportacionPOIService.calcularTamanioLista(listaDolares);
        totalRegistros = tamanioListaSoles + tamanioListaDolares;
        totalFilasArchivo = 0;
        Integer ListaMasGrande = (tamanioListaSoles > tamanioListaDolares) ? tamanioListaSoles : tamanioListaDolares;
        totalArchivos = (int) Math.ceil((double) ListaMasGrande / MAX);
        logger.info("Iniciando el proceso de generación del reporte, con un total de archivos: {}.", totalArchivos);
        @SuppressWarnings("unchecked")
        List<V>[] listasSoles = ExportacionPOIService.partition(listaSoles, MAX, totalArchivos);
        @SuppressWarnings("unchecked")
        List<V>[] listasDolares = ExportacionPOIService.partition(listaDolares, MAX, totalArchivos);
        try
        {
            int inicio = 0;
            int indiceSheetActual = 1;
            int i = 0;
            do
            {
                XSSFWorkbook workbook = new XSSFWorkbook();
                for (Moneda moneda : listaMonedas)
                {
                    workbook.createSheet(StringsUtils.concatenarCadena(moneda.getCodigoMoneda(), " - ", moneda.getDescripcion()));
                }
                sxssfWorkbook = new SXSSFWorkbook(workbook, SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
                logger.info(StringsUtils.concatenarCadena("Generando ", nombreArchivo, ": {}."), indiceSheetActual);
                int j = 0;
                for (Moneda moneda : listaMonedas)
                {
                    reporteCompleto = this.construirExportacionMonedaComis((moneda.getCodigoMoneda() == 604) ? (listasSoles == null) ? null : listasSoles[i] : (listasDolares == null) ? null : listasDolares[i], sxssfWorkbook, inicio,
                            indiceSheetActual, conceptosComision, filtros, cabeceras, moneda, this.porcetaje((listasSoles == null) ? null : listasSoles[i], (listasDolares == null) ? null : listasDolares[i])[j], nombreListaComisiones, true, j,
                            tipoReporte, zoom);

                    this.validarTipoReporte(workbook, request, tipoReporte, j, fileName/*, imgBytes*/);
                    j++;
                }
                ZipEntry zipEntry = new ZipEntry(fileName + "_" + indiceSheetActual + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                sxssfWorkbook.write(new ExportFilterOutputStream(zipOutputStream));
                zipOutputStream.closeEntry();
                inicio += MAX;
                indiceSheetActual++;
                i++;
            } while (!reporteCompleto);
            logger.info(StringsUtils.concatenarCadena("¿", nombreArchivo, " Completo?: {}", (reporteCompleto == true || procesandoRegistro >= 100) ? " con [{}] registros" : "", "."),
                    (reporteCompleto == true || procesandoRegistro >= 100) ? "SI" : "NO", totalRegistros);
            zipOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(bytes);
            servletOutputStream.flush();
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message((reporteCompleto == true || procesandoRegistro >= 100) ? ((procesandoRegistro == 0) ? 0 : 100) : procesandoRegistro, reporteCompleto, false));
                }
            }

        }
        catch (Exception e)
        {
            logger.error(StringsUtils.concatenarCadena("Ocurrio un error al tratar de generar ", nombreArchivo, ": {}."), e);
        }
        finally
        {
            cancelado = false;
            reporteCompleto = false;
            totalArchivos = 0;
            procesandoRegistro = 0;
            numeroFila = 1;
        }
    }

    public boolean construirExportacionMonedaComis(List<V> lista, SXSSFWorkbook sxssfWorkbook, int inicio, int indiceSheetActual, List<ConceptoComision> conceptosComision, String[][] filtros, String[][] cab, Moneda moneda, double porcetajeMoneda,
                                                   String nombreListaComisiones, boolean agregarTotales, int hoja, int tipoReporte, int zoom) throws Exception
    {
        logger.info(StringsUtils.concatenarCadena("Consulta {} con moneda: ", StringsUtils.concatenarCadena(moneda.getCodigoMoneda(), " - ", moneda.getDescripcion()), " iniciando..."), indiceSheetActual);
        Sheet sheet = null;
        sheet = sxssfWorkbook.getSheetAt(hoja);
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
        sheet.setZoom((zoom < 10 || zoom > 400) ? 100 : zoom);
        int filaInicial = 1;
        int columnaInicial = 1;
        this.configurarEstilos(sxssfWorkbook);
        Integer tamanioComis = ExportacionPOIService.calcularTamanioLista(conceptosComision);
        Integer tamanioListaSoles = ExportacionPOIService.calcularTamanioLista(lista);
        totalFilasArchivo = totalFilasArchivo + tamanioListaSoles;
        Row row = null;
        Cell cell = null;
        if (tipoReporte >= 1 && tipoReporte <= 3)
        {
            if (tipoReporte == TIPO_REPORTE_1)
            {
                agregarTipoReporteReg = -6;
            }
            if (tipoReporte == TIPO_REPORTE_2)
            {
                agregarTipoReporteReg = -2;
                row = sheet.createRow(filaInicial + agregarTipoReporteReg + 3);
                this.agregarFiltros(filtros, row, cell);
            }
            if (tipoReporte == TIPO_REPORTE_3)
            {
                agregarTipoReporteReg = 0;
                row = sheet.createRow(filaInicial + agregarTipoReporteReg + 3);
                this.agregarFiltros(filtros, row, cell);
            }
        }
        if (agregarTotales)
        {
            row = sheet.createRow(filaInicial + agregarTipoReporteReg + 5);
            this.agregarTotalesConComis(lista, cab, tamanioComis, filaInicial + agregarTipoReporteReg, row, cell);
            agregarTotalesReg = 0;
        } else
        {
            agregarTotalesReg = -2;
        }
        row = sheet.createRow(filaInicial + agregarTotalesReg + agregarTipoReporteReg + 7);
        this.agregarCabecarasConComisiones(cab, conceptosComision, filaInicial + agregarTotalesReg + agregarTipoReporteReg, sheet, row, cell);
        this.agregarRegistrosCabecerasConComis(lista, cab, conceptosComision, moneda, porcetajeMoneda, filaInicial + agregarTotalesReg + agregarTipoReporteReg, nombreListaComisiones, sheet, row, cell);
        logger.info(StringsUtils.concatenarCadena("Consulta {} con moneda: ", StringsUtils.concatenarCadena(moneda.getCodigoMoneda(), " - ", moneda.getDescripcion()), " terminada con '", tamanioListaSoles, "' registros."), indiceSheetActual);
        sheet.setColumnWidth(columnaInicial - 1, 840);
        for (int i = columnaInicial; i <= ((cab.length >= (filtros.length * 3)) ? cab.length : (filtros.length * 3)) + conceptosComision.size(); i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, ((sheet.getColumnWidth(i) + anchoAdicionado) > TAMANIO_MAXIMO) ? TAMANIO_MAXIMO : (sheet.getColumnWidth(i) + anchoAdicionado));
        }
        sheet.createFreezePane(0, filaInicial + agregarTotalesReg + agregarTipoReporteReg + 9);
        sheet.setAutoFilter(new CellRangeAddress(filaInicial + agregarTotalesReg + agregarTipoReporteReg + 8, filaInicial + agregarTotalesReg + agregarTipoReporteReg + 8, 1, cab.length + tamanioComis + 1));
        return totalFilasArchivo >= totalRegistros;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void generarExportacionMonedaNormal(String nombreArchivo, List<V> listaSoles, List<V> listaDolares, String[][] filtros, String[][] cabeceras, boolean agregarTotales, int tipoReporte, int zoom, HttpServletRequest request,
                                               HttpServletResponse response) throws IOException
    {
        String fileNameZip = StringsUtils.concatenarCadena(nombreArchivo, ZIP);
        String fileName = nombreArchivo;
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileNameZip);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        //byte[] imgBytes = parametroGeneralService.buscarLogo();
        List<Moneda> listaMonedas = this.monedaService.buscarTodos();
        SXSSFWorkbook sxssfWorkbook = null;
        Integer tamanioListaSoles = ExportacionPOIService.calcularTamanioLista(listaSoles);
        Integer tamanioListaDolares = ExportacionPOIService.calcularTamanioLista(listaDolares);
        totalRegistros = tamanioListaSoles + tamanioListaDolares;
        totalFilasArchivo = 0;
        Integer ListaMasGrande = (tamanioListaSoles > tamanioListaDolares) ? tamanioListaSoles : tamanioListaDolares;
        totalArchivos = (int) Math.ceil((double) ListaMasGrande / MAX);
        logger.info("Iniciando el proceso de generación del reporte, con un total de archivos: {}.", totalArchivos);
        @SuppressWarnings("unchecked")
        List<V>[] listasSoles = ExportacionPOIService.partition(listaSoles, MAX, totalArchivos);
        @SuppressWarnings("unchecked")
        List<V>[] listasDolares = ExportacionPOIService.partition(listaDolares, MAX, totalArchivos);
        try
        {
            int inicio = 0;
            int indiceSheetActual = 1;
            int i = 0;
            do
            {
                XSSFWorkbook workbook = new XSSFWorkbook();
                for (Moneda moneda : listaMonedas)
                {
                    workbook.createSheet(StringsUtils.concatenarCadena(moneda.getCodigoMoneda(), " - ", moneda.getDescripcion()));
                }
                sxssfWorkbook = new SXSSFWorkbook(workbook, SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
                logger.info(StringsUtils.concatenarCadena("Generando ", nombreArchivo, ": {}."), indiceSheetActual);
                int j = 0;
                for (Moneda moneda : listaMonedas)
                {
                    reporteCompleto = this.construirExportacionMonedaNormal((moneda.getCodigoMoneda() == 604) ? (listasSoles == null) ? null : listasSoles[i] : (listasDolares == null) ? null : listasDolares[i], sxssfWorkbook, inicio,
                            indiceSheetActual, filtros, cabeceras, moneda, this.porcetaje((listasSoles == null) ? null : listasSoles[i], (listasDolares == null) ? null : listasDolares[i])[j], agregarTotales, j, tipoReporte, zoom);
                    this.validarTipoReporte(workbook, request, tipoReporte, j, fileName/*, imgBytes*/);
                    j++;
                }
                ZipEntry zipEntry = new ZipEntry(fileName + "_" + indiceSheetActual + ".xlsx");
                zipOutputStream.putNextEntry(zipEntry);
                sxssfWorkbook.write(new ExportFilterOutputStream(zipOutputStream));
                zipOutputStream.closeEntry();
                inicio += MAX;
                indiceSheetActual++;
                i++;
            } while (!reporteCompleto);
            logger.info(StringsUtils.concatenarCadena("¿", nombreArchivo, " Completo?: {}", (reporteCompleto == true || procesandoRegistro >= 100) ? " con [{}] registros" : "", "."),
                    (reporteCompleto == true || procesandoRegistro >= 100) ? "SI" : "NO", totalRegistros);
            zipOutputStream.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            servletOutputStream.write(bytes);
            servletOutputStream.flush();
            if (!cancelado)
            {
                for (Session session : sessions)
                {
                    session.getBasicRemote().sendObject(new Message((reporteCompleto == true || procesandoRegistro >= 100) ? ((procesandoRegistro == 0) ? 0 : 100) : procesandoRegistro, reporteCompleto, false));
                }
            }

        }
        catch (Exception e)
        {
            logger.error(StringsUtils.concatenarCadena("Ocurrio un error al tratar de generar ", nombreArchivo, ": {}."), e);
        }
        finally
        {
            cancelado = false;
            reporteCompleto = false;
            totalArchivos = 0;
            procesandoRegistro = 0;
            numeroFila = 1;
        }
    }

    public boolean construirExportacionMonedaNormal(List<V> lista, SXSSFWorkbook sxssfWorkbook, int inicio, int indiceSheetActual, String[][] filtros, String[][] cab, Moneda moneda, double porcetajeMoneda, boolean agregarTotales, int hoja,
                                                    int tipoReporte, int zoom) throws Exception
    {
        logger.info(StringsUtils.concatenarCadena("Consulta {} con moneda: ", StringsUtils.concatenarCadena(moneda.getCodigoMoneda(), " - ", moneda.getDescripcion()), " iniciando..."), indiceSheetActual);
        Sheet sheet = null;
        sheet = sxssfWorkbook.getSheetAt(hoja);
        ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
        sheet.setZoom((zoom < 10 || zoom > 400) ? 100 : zoom);
        int filaInicial = 1;
        int columnaInicial = 1;
        this.configurarEstilos(sxssfWorkbook);
        Integer tamanioListaSoles = ExportacionPOIService.calcularTamanioLista(lista);
        totalFilasArchivo = totalFilasArchivo + tamanioListaSoles;
        Row row = null;
        Cell cell = null;
        if (tipoReporte >= 1 && tipoReporte <= 3)
        {
            if (tipoReporte == TIPO_REPORTE_1)
            {
                agregarTipoReporteReg = -6;
            }
            if (tipoReporte == TIPO_REPORTE_2)
            {
                agregarTipoReporteReg = -2;
                row = sheet.createRow(filaInicial + agregarTipoReporteReg + 3);
                this.agregarFiltros(filtros, row, cell);
            }
            if (tipoReporte == TIPO_REPORTE_3)
            {
                agregarTipoReporteReg = 0;
                row = sheet.createRow(filaInicial + agregarTipoReporteReg + 3);
                this.agregarFiltros(filtros, row, cell);
            }
        }
        if (agregarTotales)
        {
            row = sheet.createRow(filaInicial + agregarTipoReporteReg + 5);
            this.agregarTotalesNormal(lista, cab, filaInicial + agregarTipoReporteReg, row, cell);
            agregarTotalesReg = 0;
        } else
        {
            agregarTotalesReg = -2;
        }
        row = sheet.createRow(filaInicial + agregarTotalesReg + agregarTipoReporteReg + 7);
        this.agregarCabecaras(cab, filaInicial + agregarTotalesReg + agregarTipoReporteReg, sheet, row, cell);
        this.agregarRegistrosCabecerasNormal(lista, cab, moneda, porcetajeMoneda, filaInicial + agregarTotalesReg + agregarTipoReporteReg, sheet, row, cell);
        logger.info(StringsUtils.concatenarCadena("Consulta {} con moneda: ", StringsUtils.concatenarCadena(moneda.getCodigoMoneda(), " - ", moneda.getDescripcion()), " terminada con '", tamanioListaSoles, "' registros."), indiceSheetActual);
        sheet.setColumnWidth(columnaInicial - 1, 840);
        for (int i = columnaInicial; i <= ((cab.length >= (filtros.length * 3)) ? cab.length : (filtros.length * 3)); i++)
        {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, ((sheet.getColumnWidth(i) + anchoAdicionado) > TAMANIO_MAXIMO) ? TAMANIO_MAXIMO : (sheet.getColumnWidth(i) + anchoAdicionado));
        }
        sheet.createFreezePane(0, filaInicial + agregarTotalesReg + agregarTipoReporteReg + 8);
        sheet.setAutoFilter(new CellRangeAddress(filaInicial + agregarTotalesReg + agregarTipoReporteReg + 7, filaInicial + agregarTotalesReg + agregarTipoReporteReg + 7, 1, cab.length));
        return totalFilasArchivo >= totalRegistros;
    }

    private void agregarRegistrosCabecerasConComis(List<V> lista, String[][] cab, List<ConceptoComision> conceptosComision, Moneda moneda, double porcetaje, Integer filaInicial, String nombreListaComisiones, Sheet sheet, Row row, Cell cell)
            throws IllegalAccessException, NoSuchFieldException, IOException, EncodeException
    {
        Integer tamanioLista = ExportacionPOIService.calcularTamanioLista(lista);
        Integer tamanioComis = ExportacionPOIService.calcularTamanioLista(conceptosComision);
        try
        {
            if (!lista.isEmpty())
            {
                int k = 0;
                for (int i = 0; i < tamanioLista; i++)
                {
                    procesandoRegistro = (((double) numeroFila / tamanioLista) * porcetaje);
                    for (Session session : sessions)
                    {
                        this.sendObjectSession(session);
                    }
                    row = sheet.createRow(filaInicial + 9 + k);
                    Class<?> clase = lista.get(i).getClass();
                    Field[] a = new Field[cab.length];
                    Field[] b = new Field[cab.length];
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < cab.length; j++)
                    {
                        if (!cab[j][1].equals(""))
                        {
                            try
                            {
                                a[j] = clase.getDeclaredField(cab[j][1]);
                                a[j].setAccessible(true);
                                sb.append(a[j].get(lista.get(i)).toString());
                            }
                            catch (NullPointerException e)
                            {
                                sb.append("");
                            }
                        }
                        if (!cab[j][2].equals(""))
                        {
                            try
                            {
                                b[j] = clase.getDeclaredField(cab[j][2]);
                                b[j].setAccessible(true);
                                sb.append(" - ").append(b[j].get(lista.get(i)).toString());
                            }
                            catch (NullPointerException e)
                            {
                                sb.append("");
                            }
                        }
                        cell = row.createCell(j + 1);
                        cell.setCellStyle(this.validarEstilo(cab[j][3]));
                        switch (cab[j][3])
                        {
                            case F_FECHA:
                                cell.setCellValue((Date) a[j].get(lista.get(i)));
                                break;
                            case F_CADENA:
                            case F_CADENA_CENTRADA:
                            case F_INDICADOR:
                                cell.setCellValue(sb.toString());
                                break;
                            case F_SI_NO:
                                cell.setCellValue((sb.toString().equals("0") || sb.toString().toUpperCase().equals("NO") || sb.toString().equals("false")) ? "NO" : "SI");
                                break;
                            case F_CADENA_ESPACIO:
                                cell.setCellValue(sb.toString().replace(" - ", " "));
                                break;
                            case F_CONCEPTO_COMISION:
                                cell.setCellValue(sb.toString().replace("0 - ", ""));
                                break;
                            case F_CANTIDAD:
                                if (!sb.toString().isEmpty())
                                {
                                    cell.setCellValue(Integer.parseInt(sb.toString()));
                                }
                                break;
                            case F_MONTO:
                            case F_COMISION:
                            case F_MONTO_O_COMIS:
                                if (!sb.toString().isEmpty())
                                {
                                    cell.setCellValue(Double.parseDouble(sb.toString()));
                                }
                                break;
                            default:
                                break;
                        }
                        sb.setLength(0);
                    }
                    k++;
                    Field x = clase.getDeclaredField(nombreListaComisiones);
                    x.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    List<ReporteComision> listaComis = (List<ReporteComision>) x.get(lista.get(i));
                    double sumatotalComis = 0;
                    for (int n = cab.length; n < cab.length + tamanioComis; n++)
                    {
                        cell = row.createCell(n + 1);
                        cell.setCellStyle(estiloDecimal4);
                        if (listaComis.isEmpty())
                        {
                            cell.setCellValue(0);
                        } else
                        {
                            for (int m = 0; m < ExportacionPOIService.calcularTamanioLista(listaComis); m++)
                            {
                                if (conceptosComision.get(n - cab.length).getIdConceptoComision().equals(listaComis.get(m).getIdConceptoComision()))
                                {
                                    double aux;
                                    if (listaComis.get(m).getRegistroContable().equals(ABONO))
                                    {
                                        aux = listaComis.get(m).getComision();
                                    } else if (listaComis.get(m).getRegistroContable().equals(CARGO))
                                    {
                                        aux = -listaComis.get(m).getComision();
                                    } else
                                    {
                                        aux = 0;
                                    }
                                    sumatotalComis += aux;
                                    cell.setCellValue(aux);
                                }
                            }
                        }
                        if (this.isCellEmpty(cell))
                        {
                            cell.setCellValue((double) cell.getNumericCellValue());
                        }
                    }
                    cell = row.createCell(cab.length + tamanioComis + 1);
                    cell.setCellStyle(estiloDecimal4);
                    cell.setCellValue(sumatotalComis);
                    numeroFila++;
                }
            }
        }
        catch (NullPointerException e)
        {
            if (moneda == null)
            {
                logger.info("No se puede registrar en el archivo, porque la lista esta vacia");
            } else
            {
                logger.info("No se puede registrar en el archivo, porque la lista {} - {} esta vacia", moneda.getCodigoMoneda(), moneda.getDescripcion());
            }
        }
    }

    @SuppressWarnings("deprecation")
    public boolean isCellEmpty(Cell cell)
    {
        if (cell == null)
        {
            return true;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
        {
            return true;
        }
        return cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().trim().isEmpty();
    }

    private void agregarRegistrosCabecerasNormal(List<V> lista, String[][] cab, Moneda moneda, double porcetaje, Integer filaInicial, Sheet sheet, Row row, Cell cell) throws IllegalAccessException, NoSuchFieldException, IOException, EncodeException
    {
        Integer tamanioLista = ExportacionPOIService.calcularTamanioLista(lista);
        try
        {
            if (!lista.isEmpty())
            {
                int k = 0;
                for (int i = 0; i < tamanioLista; i++)
                {
                    procesandoRegistro = (((double) numeroFila / tamanioLista) * porcetaje);
                    for (Session session : sessions)
                    {
                        this.sendObjectSession(session);
                    }
                    row = sheet.createRow(filaInicial + 8 + k);
                    Class<?> clase = lista.get(i).getClass();
                    Field[] a = new Field[cab.length];
                    Field[] b = new Field[cab.length];
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < cab.length; j++)
                    {
                        if (!cab[j][1].equals(""))
                        {
                            try
                            {
                                a[j] = clase.getDeclaredField(cab[j][1]);
                                a[j].setAccessible(true);
                                sb.append(a[j].get(lista.get(i)).toString());
                            }
                            catch (NullPointerException e)
                            {
                                sb.append("");
                            }
                        }
                        if (!cab[j][2].equals(""))
                        {
                            try
                            {
                                b[j] = clase.getDeclaredField(cab[j][2]);
                                b[j].setAccessible(true);
                                sb.append(" - ").append(b[j].get(lista.get(i)).toString());
                            }
                            catch (NullPointerException e)
                            {
                                sb.append("");
                            }
                        }
                        cell = row.createCell(j + 1);
                        cell.setCellStyle(this.validarEstilo(cab[j][3]));
                        switch (cab[j][3])
                        {
                            case F_FECHA:
                                cell.setCellValue((Date) a[j].get(lista.get(i)));
                                break;
                            case F_CADENA:
                            case F_CADENA_CENTRADA:
                            case F_INDICADOR:
                                cell.setCellValue(sb.toString());
                                break;
                            case F_SI_NO:
                                cell.setCellValue((sb.toString().equals("0") || sb.toString().toUpperCase().equals("NO") || sb.toString().equals("false")) ? "NO" : "SI");
                                break;
                            case F_CADENA_ESPACIO:
                                cell.setCellValue(sb.toString().replace(" - ", " "));
                                break;
                            case F_CONCEPTO_COMISION:
                                cell.setCellValue(sb.toString().replace("0 - ", ""));
                                break;
                            case F_CANTIDAD:
                                if (!sb.toString().isEmpty())
                                {
                                    cell.setCellValue(Integer.parseInt(sb.toString()));
                                }
                                break;
                            case F_MONTO:
                            case F_COMISION:
                            case F_MONTO_O_COMIS:
                                if (!sb.toString().isEmpty())
                                {
                                    cell.setCellValue(Double.parseDouble(sb.toString()));
                                }
                                break;
                            default:
                                break;
                        }
                        sb.setLength(0);
                    }
                    k++;
                    numeroFila++;
                }
            }
        }
        catch (NullPointerException e)
        {
            if (moneda == null)
            {
                logger.info("No se puede registrar en el archivo, porque la lista esta vacia");
            } else
            {
                logger.info("No se puede registrar en el archivo, porque la lista {} - {} esta vacia", moneda.getCodigoMoneda(), moneda.getDescripcion());
            }
        }
    }

    private void agregarFiltros(String[][] filtros, Row row, Cell cell)
    {
        int j = 0;
        for (String[] filtro : filtros)
        {
            cell = row.createCell(j + 1);
            cell.setCellStyle(estiloCab);
            cell.setCellValue(filtro[0]);
            cell = row.createCell(j + 2);
            cell.setCellStyle(estiloNormalFiltro);
            cell.setCellValue(filtro[1]);
            j += 3;
        }
    }

    private void agregarCabecarasConComisiones(String[][] cab, List<ConceptoComision> conceptosComision, Integer filaInicial, Sheet sheet, Row row, Cell cell)
    {
        Integer tamanioComis = ExportacionPOIService.calcularTamanioLista(conceptosComision);
        cell = row.createCell(cab.length + 1);
        cell.setCellStyle(estiloCab);
        cell.setCellValue(COMISIONES);
        sheet.addMergedRegion(new CellRangeAddress(filaInicial + 7, filaInicial + 7, cab.length + 1, cab.length + tamanioComis));
        row = sheet.createRow(filaInicial + 8);
        for (int i = 0; i < cab.length; i++)
        {
            sheet.setColumnWidth(i + 1, this.validarTamanio(cab[i][4]));
            cell = row.createCell(i + 1);
            cell.setCellStyle(estiloCab);
            cell.setCellValue(cab[i][0]);
        }
        for (int i = cab.length; i < cab.length + tamanioComis; i++)
        {
            sheet.setColumnWidth(i + 1, this.TAMANIO_COMIS);
            cell = row.createCell(i + 1);
            cell.setCellStyle(estiloCab);
            cell.setCellValue(conceptosComision.get(i - cab.length).getAbreviatura());
        }
        sheet.setColumnWidth(cab.length + tamanioComis + 1, this.TAMANIO_COMIS);
        cell = row.createCell(cab.length + tamanioComis + 1);
        cell.setCellStyle(estiloCab);
        cell.setCellValue(ComTOTAL);
    }

    private void agregarCabecaras(String[][] cab, Integer filaInicial, Sheet sheet, Row row, Cell cell)
    {
        row = sheet.createRow(filaInicial + 7);
        for (int i = 0; i < cab.length; i++)
        {
            sheet.setColumnWidth(i + 1, this.validarTamanio(cab[i][4]));
            cell = row.createCell(i + 1);
            cell.setCellStyle(estiloCab);
            cell.setCellValue(cab[i][0]);
        }
    }

    private boolean validar(String valor)
    {
        return valor.equals(F_CANTIDAD) || valor.equals(F_MONTO) || valor.equals(F_COMISION);
    }

    private void agregarTotalesConComis(List<V> lista, String[][] cab, Integer lenConceptosComision, Integer filaInicial, Row row, Cell cell)
    {
        int length = ExportacionPOIService.calcularTamanioLista(lista);
        int lengthCab = cab.length;
        int cantidadTotalCab = 0;
        if (this.validar(cab[lengthCab - 1][3]))
        {
            cantidadTotalCab++;
            for (int i = lengthCab - 2; i > -1; i--)
            {
                if (this.validar(cab[i][3]))
                {
                    if (this.validar(cab[i][3]) == this.validar(cab[i + 1][3]))
                    {
                        cantidadTotalCab++;
                    } else
                    {
                        break;
                    }
                }
            }
        }
        cell = row.createCell(lengthCab - cantidadTotalCab);
        cell.setCellStyle(estiloCab);
        cell.setCellValue(TOTAL);
        String formula = "";
        int totalFilasSumar = 0;
        int init = filaInicial + 8;
        for (int i = lengthCab - cantidadTotalCab; i < lengthCab + lenConceptosComision + 1; i++)
        {
            formula = "SUM(" + CellReference.convertNumToColString(cell.getColumnIndex() + 1) + (init + 2) + ":" + CellReference.convertNumToColString(cell.getColumnIndex() + 1) + (totalFilasSumar + init + length + 1) + ")";
            cell = row.createCell(i + 1);
            cell.setCellFormula(formula);
            if (i < lengthCab)
            {
                cell.setCellStyle(this.validarEstilo(cab[i][3]));
            } else
            {
                cell.setCellStyle(this.validarEstilo(F_COMISION));
            }
        }
    }

    private void agregarTotalesNormal(List<V> lista, String[][] cab, Integer filaInicial, Row row, Cell cell)
    {
        int length = ExportacionPOIService.calcularTamanioLista(lista);
        int lengthCab = cab.length;
        int cantidadTotalCab = 0;
        if (this.validar(cab[lengthCab - 1][3]))
        {
            cantidadTotalCab++;
            for (int i = lengthCab - 2; i > -1; i--)
            {
                if (this.validar(cab[i][3]))
                {
                    if (this.validar(cab[i][3]) == this.validar(cab[i + 1][3]))
                    {
                        cantidadTotalCab++;
                    } else
                    {
                        break;
                    }
                }
            }
        }
        cell = row.createCell(lengthCab - cantidadTotalCab);
        cell.setCellStyle(estiloCab);
        cell.setCellValue(TOTAL);
        String formula = "";
        int totalFilasSumar = 0;
        int init = filaInicial + 8;
        for (int i = lengthCab - cantidadTotalCab; i < lengthCab; i++)
        {
            formula = "SUM(" + CellReference.convertNumToColString(cell.getColumnIndex() + 1) + (init + 1) + ":" + CellReference.convertNumToColString(cell.getColumnIndex() + 1) + (totalFilasSumar + init + length) + ")";
            cell = row.createCell(i + 1);
            cell.setCellFormula(formula);
            cell.setCellStyle(this.validarEstilo(cab[i][3]));
        }
    }

    private CellStyle validarEstilo(String formato)
    {
        switch (formato)
        {
            case F_FECHA:
                return estiloFecha;
            case F_CADENA:
            case F_CADENA_ESPACIO:
            case F_CONCEPTO_COMISION:
                return estiloNormal;
            case F_CADENA_DERECHA:
                return estiloDerecho;
            case F_CADENA_CENTRADA:
                return estiloCentro;
            case F_SI_NO:
                return estiloSiNo;
            case F_INDICADOR:
                return estiloIndicador;
            case F_CANTIDAD:
                return estiloNumero;
            case F_MONTO:
                return estiloDecimal;
            case F_COMISION:
            case F_MONTO_O_COMIS:
                return estiloDecimal4;
            default:
                logger.info("Formato ingresado para el estilo de la celda: [{}] es inválido.", formato);
                break;
        }
        return null;
    }

    private Integer validarTamanio(String tamanio)
    {
        int tam = Integer.parseInt(tamanio);
        if (tam == -1 || tam < this.TAMANIO_MINIMO)
        {
            return this.TAMANIO_DEFAULT;
        } else if (tam > this.TAMANIO_MAXIMO)
        {
            return this.TAMANIO_DEFAULT;
        } else
        {
            return tam;
        }
    }

    @SuppressWarnings("deprecation")
    private void configurarEstilos(SXSSFWorkbook sxssfWorkbook)
    {
        font = sxssfWorkbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);

        fontSiNo = sxssfWorkbook.createFont();
        fontSiNo.setBold(true);

        fontBold = sxssfWorkbook.createFont();
        fontBold.setFontName(TIPO_LETRA);
        fontBold.setBold(true);

        fontNormal = sxssfWorkbook.createFont();
        fontNormal.setFontName(TIPO_LETRA);

        estiloFecha = sxssfWorkbook.createCellStyle();
        estiloFecha.setAlignment(CellStyle.ALIGN_CENTER);
        estiloFecha.setDataFormat(sxssfWorkbook.createDataFormat().getFormat("dd/MM/yyyy"));
        estiloFecha.setBorderTop(CellStyle.BORDER_THIN);
        estiloFecha.setBorderRight(CellStyle.BORDER_THIN);
        estiloFecha.setBorderBottom(CellStyle.BORDER_THIN);
        estiloFecha.setBorderLeft(CellStyle.BORDER_THIN);

        estiloDerecho = sxssfWorkbook.createCellStyle();
        estiloDerecho.setAlignment(CellStyle.ALIGN_RIGHT);
        estiloDerecho.setBorderTop(CellStyle.BORDER_THIN);
        estiloDerecho.setBorderRight(CellStyle.BORDER_THIN);
        estiloDerecho.setBorderBottom(CellStyle.BORDER_THIN);
        estiloDerecho.setBorderLeft(CellStyle.BORDER_THIN);

        estiloCentro = sxssfWorkbook.createCellStyle();
        estiloCentro.setAlignment(CellStyle.ALIGN_CENTER);
        estiloCentro.setBorderTop(CellStyle.BORDER_THIN);
        estiloCentro.setBorderRight(CellStyle.BORDER_THIN);
        estiloCentro.setBorderBottom(CellStyle.BORDER_THIN);
        estiloCentro.setBorderLeft(CellStyle.BORDER_THIN);

        estiloSiNo = sxssfWorkbook.createCellStyle();
        estiloSiNo.setAlignment(CellStyle.ALIGN_CENTER);
        estiloSiNo.setBorderTop(CellStyle.BORDER_THIN);
        estiloSiNo.setBorderRight(CellStyle.BORDER_THIN);
        estiloSiNo.setBorderBottom(CellStyle.BORDER_THIN);
        estiloSiNo.setBorderLeft(CellStyle.BORDER_THIN);
        estiloSiNo.setFont(fontSiNo);

        estiloNormal = sxssfWorkbook.createCellStyle();
        estiloNormal.setBorderTop(CellStyle.BORDER_THIN);
        estiloNormal.setBorderRight(CellStyle.BORDER_THIN);
        estiloNormal.setBorderBottom(CellStyle.BORDER_THIN);
        estiloNormal.setBorderLeft(CellStyle.BORDER_THIN);

        estiloIndicador = sxssfWorkbook.createCellStyle();
        estiloIndicador.setAlignment(CellStyle.ALIGN_RIGHT);
        estiloIndicador.setBorderTop(CellStyle.BORDER_THIN);
        estiloIndicador.setBorderRight(CellStyle.BORDER_THIN);
        estiloIndicador.setBorderBottom(CellStyle.BORDER_THIN);
        estiloIndicador.setBorderLeft(CellStyle.BORDER_THIN);

        estiloNormalFiltro = sxssfWorkbook.createCellStyle();
        estiloNormalFiltro.setAlignment(CellStyle.ALIGN_CENTER);
        estiloNormalFiltro.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        estiloNormalFiltro.setBorderTop(CellStyle.BORDER_THIN);
        estiloNormalFiltro.setBorderRight(CellStyle.BORDER_THIN);
        estiloNormalFiltro.setBorderBottom(CellStyle.BORDER_THIN);
        estiloNormalFiltro.setBorderLeft(CellStyle.BORDER_THIN);

        estiloCab = sxssfWorkbook.createCellStyle();
        estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloCab.setFillPattern(CellStyle.SOLID_FOREGROUND);
        estiloCab.setAlignment(CellStyle.ALIGN_CENTER);
        estiloCab.setBorderTop(CellStyle.BORDER_THIN);
        estiloCab.setBorderRight(CellStyle.BORDER_THIN);
        estiloCab.setBorderBottom(CellStyle.BORDER_THIN);
        estiloCab.setBorderLeft(CellStyle.BORDER_THIN);
        estiloCab.setFont(font);

        estiloNumero = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "#,##0", fontNormal);
        estiloDecimal = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);
        estiloDecimal4 = ExportacionUtil.crearEstiloNumero(sxssfWorkbook, "[Blue]#,##0.0000;[Red]-#,##0.0000;[Black]#,##0.0000", fontNormal);
    }

    private void sendObjectSession(Session session) throws IOException, EncodeException
    {
        if (session.isOpen())
        {
            session.getBasicRemote().sendObject(new Message((reporteCompleto || procesandoRegistro >= 100) ? ((procesandoRegistro == 0) ? 0 : 100) : procesandoRegistro, reporteCompleto, false));
        }
    }

    private double[] porcetaje(List<V> listaSoles, List<V> listaDolares)
    {
        int tamanioSoles = ExportacionPOIService.calcularTamanioLista(listaSoles);
        int tamanioDolares = ExportacionPOIService.calcularTamanioLista(listaDolares);
        double porcentajeSoles = this.redondear2((((double) tamanioSoles / totalRegistros) * 100));
        double porcentajeDolares = this.redondear2((((double) tamanioDolares / totalRegistros) * 100));
        return new double[]{porcentajeSoles, porcentajeDolares};
    }

    private double redondear2(double a)
    {
        return Math.round(a * 100.0) / 100.0;
    }

    private static Integer calcularTamanioLista(List<?> lista)
    {
        Integer tamanio = null;
        try
        {
            tamanio = lista.size();
        }
        catch (NullPointerException e)
        {
            tamanio = 0;
        }
        return tamanio;
    }

    @SuppressWarnings("rawtypes")
    private static <V> List[] partition(List<V> list, int n, int totalArchivos)
    {
        int tamanio = calcularTamanioLista(list);
        if (tamanio == 0)
        {
            return null;
        } else
        {
            int m = list.size() / n;
            if (list.size() % n != 0)
            {
                m++;
            }
            List<List<V>> itr = ListUtils.partition(list, n);
            @SuppressWarnings("unchecked")
            List<V>[] partition = new ArrayList[m];
            for (int i = 0; i < m; i++)
            {
                partition[i] = new ArrayList<V>(itr.get(i));
            }
            if (partition.length < totalArchivos)
            {
                for (int i = partition.length; i < totalArchivos; i++)
                {
                    partition = Arrays.copyOf(partition, partition.length + i);
                }
            }
            return partition;
        }
    }

    private void validarTipoReporte(XSSFWorkbook workbook, HttpServletRequest request, int tipoReporte, int hoja, String fileName/*, byte[] imgBytes*/)
    {
        if (tipoReporte >= 1 && tipoReporte <= 3)
        {
            if (tipoReporte == TIPO_REPORTE_2)
            {
                ExportacionUtil.insertarNombreReporte(workbook, hoja, 8, fileName, 0);
            }
            if (tipoReporte == TIPO_REPORTE_3)
            {
                ExportacionUtil.insertarTitulo(request, workbook, hoja, 8, fileName);
                ExportacionUtil.insertarNombreReporte(workbook, hoja, 8, fileName, 2);
                //ExportacionUtil.insertarLogo(workbook, hoja, imgBytes, 8);
            }
        } else
        {
            logger.error("Tipo de reporte desconocido.");
        }
    }

}
