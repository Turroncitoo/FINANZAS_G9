// package ob.debitos.simp.service.impl.reporte;

// import ob.debitos.simp.mapper.IReporteResumenMovimientoMapper;
// import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableEmisor;
// import ob.debitos.simp.model.paginacion.CriterioPaginacion;
// import ob.debitos.simp.model.reporte.ReporteComision;
// import ob.debitos.simp.model.reporte.ReporteResumenLogContableEmisor;
// import ob.debitos.simp.model.websocket.Message;
// import ob.debitos.simp.model.websocket.MessageDecoder;
// import ob.debitos.simp.model.websocket.MessageEncoder;
// import ob.debitos.simp.service.IReporteLogContableEmisorResumenService;
// import ob.debitos.simp.service.excepcion.SimpException;
// import ob.debitos.simp.utilitario.ExportFilterOutputStream;
// import ob.debitos.simp.utilitario.PaginacionUtil;
// import org.apache.catalina.connector.ClientAbortException;
// import org.apache.commons.lang3.StringUtils;
// import org.apache.poi.ss.usermodel.*;
// import org.apache.poi.ss.util.CellRangeAddress;
// import org.apache.poi.ss.util.CellReference;
// import org.apache.poi.xssf.streaming.SXSSFWorkbook;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.slf4j.Logger;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Propagation;
// import org.springframework.transaction.annotation.Transactional;
// import ob.debitos.simp.model.reporte.ReporteMoneda;

// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.zip.ZipEntry;
// import java.util.zip.ZipOutputStream;
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletResponse;
// import javax.websocket.*;
// import javax.websocket.server.ServerEndpoint;

// @Service
// @ServerEndpoint(value = "/logContableEmisorResumen", encoders = {
//         MessageEncoder.class }, decoders = { MessageDecoder.class })
// public class ReporteLogContableEmisorService
//         implements IReporteLogContableEmisorResumenService
// {

//     private @Autowired IReporteResumenMovimientoMapper reporteResumenMovimientoMapper;
//     private @Autowired Logger logger;

//     private static final int MAX = 1000000;
//     boolean reporteCompleto = false;

//     private static final List<Session> sessions = new ArrayList<>();

//     double procesandoRegistro = 0;
//     private static boolean cancelado = false;

//     @Override
//     @Transactional(propagation = Propagation.REQUIRES_NEW)
//     public void exportarLogContableEmisor(
//             CriterioBusquedaResumenLogContableEmisor criterioBusqueda,
//             HttpServletResponse response) throws IOException
//     {
//         String fileNameZip = "Reporte Log Contable Emisor.zip";
//         String fileName = "Reporte Log Contable Emisor";

//         response.setContentType("application/zip");
//         response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,
//                 HttpHeaders.CONTENT_DISPOSITION);
//         response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
//                 "attachment;filename=" + fileNameZip);

//         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//         ZipOutputStream zipOutputStream = new ZipOutputStream(
//                 byteArrayOutputStream);
//         ServletOutputStream servletOutputStream = response.getOutputStream();

//         XSSFWorkbook workbook = new XSSFWorkbook(
//                 getClass().getClassLoader().getResourceAsStream(
//                         "xlsx/reporteResumenLogContableEmisor.xlsx"));

//         logger.info("Criterios: {}", criterioBusqueda);
//         logger.info(
//                 "Iniciando el proceso de consulta y generación del reporte");

//         SXSSFWorkbook sxssfWorkbook;

//         try
//         {
//             sxssfWorkbook = new SXSSFWorkbook(workbook, -1, Boolean.FALSE,
//                     Boolean.TRUE);
//             this.construirLogContableEmisorResumenExportacion(criterioBusqueda,
//                     sxssfWorkbook);

//             ZipEntry zipEntry = new ZipEntry(fileName + ".xlsx");
//             zipOutputStream.putNextEntry(zipEntry);
//             sxssfWorkbook.write(new ExportFilterOutputStream(zipOutputStream));
//             zipOutputStream.closeEntry();

//             zipOutputStream.close();
//             byte[] bytes = byteArrayOutputStream.toByteArray();
//             servletOutputStream.write(bytes);
//             servletOutputStream.flush();

//             logger.info("El reporte se generó correctamente");
//         } catch (ClientAbortException e)
//         {
//             logger.error("cancelado: {} ", e.getMessage());

//         } catch (Exception e)
//         {
//             logger.error("Ocurrio un error al tratar de generar el reporte: {}",
//                     e);
//         }
//     }

//     @Transactional(propagation = Propagation.REQUIRES_NEW)
//     public void construirLogContableEmisorResumenExportacion(
//             CriterioBusquedaResumenLogContableEmisor criterioBusqueda,
//             SXSSFWorkbook sxssfWorkbook)
//     {
//         reporteCompleto = false;
//         reporteCompleto = construirPagina(criterioBusqueda, sxssfWorkbook);

//         try
//         {
//             if (!cancelado)
//             {
//                 for (Session session : sessions)
//                 {
//                     session.getBasicRemote().sendObject(new Message(
//                             procesandoRegistro, reporteCompleto, false));
//                 }
//             }
//         } catch (EncodeException e)
//         {
//             logger.error("cancelado: {}", e.getMessage());
//         } catch (Exception e)
//         {
//             logger.error("Ocurrio un error al tratar de generar el reporte: {}",
//                     e.getMessage());
//         } finally
//         {
//             cancelado = false;
//             reporteCompleto = false;
//             procesandoRegistro = 0;
//         }

//     }

//     private boolean construirPagina(
//             CriterioBusquedaResumenLogContableEmisor criterioBusqueda,
//             SXSSFWorkbook sxssfWorkbook)
//     {
//         boolean reporteHoja1 = false;
//         boolean reporteHoja2 = false;

//         List<ReporteMoneda> monedas = this.reporteResumenMovimientoMapper
//                 .buscarResumenLogContableEmisor(criterioBusqueda);

//         int tmMonedas = monedas.size();

//         if (tmMonedas > 0)
//         {
//             for (int i = 0; i < tmMonedas; i++)
//             {
//                 if (monedas.get(i).getCodigoMoneda() == 604)
//                 {
//                     Sheet sheet = sxssfWorkbook.getSheetAt(0);
//                     sheet.setDefaultRowHeight((short) 380);
//                     reporteHoja1 = construir(sheet, sxssfWorkbook, 0,
//                             criterioBusqueda, monedas.get(i));
//                 } else if (monedas.get(i).getCodigoMoneda() == 840)
//                 {
//                     Sheet sheet = sxssfWorkbook.getSheetAt(1);
//                     sheet.setDefaultRowHeight((short) 380);
//                     reporteHoja2 = construir(sheet, sxssfWorkbook, 1,
//                             criterioBusqueda, monedas.get(i));
//                 }
//             }
//             if (tmMonedas == 1)
//             {
//                 if (monedas.get(0).getCodigoMoneda() == 604)
//                 {
//                     Sheet sheet = sxssfWorkbook.getSheetAt(1);
//                     sheet.setDefaultRowHeight((short) 380);
//                     reporteHoja1 = construir(sheet, sxssfWorkbook, 1,
//                             criterioBusqueda, null);
//                 } else
//                 {
//                     Sheet sheet = sxssfWorkbook.getSheetAt(0);
//                     sheet.setDefaultRowHeight((short) 380);
//                     reporteHoja1 = construir(sheet, sxssfWorkbook, 0,
//                             criterioBusqueda, null);
//                 }
//                 procesandoRegistro = 100.0;
//             }
//         } else
//         {
//             for (int i = 0; i < 2; i++)
//             {
//                 Sheet sheet = sxssfWorkbook.getSheetAt(i);
//                 sheet.setDefaultRowHeight((short) 380);
//                 reporteHoja1 = construir(sheet, sxssfWorkbook, i,
//                         criterioBusqueda, null);
//             }
//             return true;
//         }

//         return reporteHoja1 || reporteHoja2;
//     }

//     boolean construir(Sheet sheet, SXSSFWorkbook sxssfWorkbook, int numPagina,
//             CriterioBusquedaResumenLogContableEmisor criterioBusqueda,
//             ReporteMoneda monedas)
//     {
//         Font fontNormal = sxssfWorkbook.createFont();
//         fontNormal.setFontName("Segoe UI");

//         Font fontBold = sxssfWorkbook.createFont();
//         fontBold.setFontName("Segoe UI");
//         fontBold.setBold(true);

//         Font fontWhite = sxssfWorkbook.createFont();
//         fontWhite.setFontName("Segoe UI");
//         fontWhite.setColor(IndexedColors.WHITE.getIndex());

//         Font fontWhiteBold = sxssfWorkbook.createFont();
//         fontWhiteBold.setFontName("Segoe UI");
//         fontWhiteBold.setColor(IndexedColors.WHITE.getIndex());
//         fontWhiteBold.setBold(true);

//         CellStyle estiloNormal = this.crearEstiloBasico(sxssfWorkbook,
//                 fontNormal);
//         CellStyle estiloFecha = this.crearEstiloFecha(sxssfWorkbook,
//                 fontNormal);
//         CellStyle estiloCriterio = this.crearEstiloBasico(sxssfWorkbook,
//                 fontBold);

//         CellStyle estiloCab = this.crearEstiloBasico(sxssfWorkbook, fontWhite);
//         estiloCab.setAlignment(HorizontalAlignment.CENTER);
//         estiloCab.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
//         estiloCab.setFillPattern(FillPatternType.SOLID_FOREGROUND);

//         CellStyle estiloMembresia = this.crearEstiloBasico(sxssfWorkbook,
//                 fontWhiteBold);
//         estiloMembresia.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
//         estiloMembresia.setFillPattern(FillPatternType.SOLID_FOREGROUND);

//         CellStyle estiloCantidad = this.crearEstiloNumero(sxssfWorkbook,
//                 "#,##0", fontNormal);

//         CellStyle estiloDecimal4 = this.crearEstiloNumero(sxssfWorkbook,
//                 "[Blue]#,##0.0000;[Red]-#,##0.0000;[Black]#,##0.0000",
//                 fontNormal);

//         CellStyle estiloDecimal2 = this.crearEstiloNumero(sxssfWorkbook,
//                 "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);

//         // estilos para los totales
//         CellStyle estiloCantidadBold = this.crearEstiloNumero(sxssfWorkbook,
//                 "#,##0", fontBold);
//         estiloCantidadBold.setFillForegroundColor(
//                 IndexedColors.GREY_25_PERCENT.getIndex());
//         estiloCantidadBold.setFillPattern(FillPatternType.SOLID_FOREGROUND);

//         CellStyle estiloDecimal2Color = this.crearEstiloNumero(sxssfWorkbook,
//                 "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontNormal);
//         estiloDecimal2Color.setFillForegroundColor(
//                 IndexedColors.GREY_25_PERCENT.getIndex());
//         estiloDecimal2Color.setFillPattern(FillPatternType.SOLID_FOREGROUND);

//         CellStyle estiloDecimal2Bold = this.crearEstiloNumero(sxssfWorkbook,
//                 "[Blue]#,##0.00;[Red]-#,##0.00;[Black]#,##0.00", fontBold);
//         estiloDecimal2Bold.setFillForegroundColor(
//                 IndexedColors.GREY_25_PERCENT.getIndex());
//         estiloDecimal2Bold.setFillPattern(FillPatternType.SOLID_FOREGROUND);

//         CellStyle estiloDecimal4Bold = this.crearEstiloNumero(sxssfWorkbook,
//                 "[Blue]#,##0.0000;[Red]-#,##0.0000;[Black]#,##0.0000",
//                 fontBold);
//         estiloDecimal4Bold.setFillForegroundColor(
//                 IndexedColors.GREY_25_PERCENT.getIndex());
//         estiloDecimal4Bold.setFillPattern(FillPatternType.SOLID_FOREGROUND);

//         CriterioPaginacion<CriterioBusquedaResumenLogContableEmisor, ?> criterioPaginacion = PaginacionUtil
//                 .getCriterioPaginacionParaReporteXLSX(criterioBusqueda, 0, MAX);

//         int filaActual = 11;
//         int inicioFilaReporte = 11;
//         int numeroFila = 1;

//         try
//         {
//             Row row;
//             Cell cell;

//             row = sheet.createRow(3);

//             cell = row.createCell(1);
//             cell.setCellStyle(estiloCriterio);
//             cell.setCellValue("Membresía");
//             cell = row.createCell(2);
//             cell.setCellStyle(estiloNormal);
//             cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
//                     .getDescripcionMembresia());

//             cell = row.createCell(5);
//             cell.setCellStyle(estiloCriterio);
//             cell.setCellValue("Clase Servicio");
//             cell = row.createCell(6);
//             cell.setCellStyle(estiloNormal);
//             cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
//                     .getDescripcionClaseServicio());

//             cell = row.createCell(8);
//             cell.setCellStyle(estiloCriterio);
//             cell.setCellValue("Rol Txn");
//             cell = row.createCell(9);
//             cell.setCellStyle(estiloNormal);
//             cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
//                     .getDescripcionRolTransaccion());

//             row = sheet.createRow(5);

//             cell = row.createCell(1);
//             cell.setCellStyle(estiloCriterio);
//             cell.setCellValue("Canales");
//             cell = row.createCell(2);
//             cell.setCellStyle(estiloNormal);
//             cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
//                     .getDescripcionCanal());

//             cell = row.createCell(5);
//             cell.setCellStyle(estiloCriterio);
//             cell.setCellValue("Clase Transacción");
//             cell = row.createCell(6);
//             cell.setCellStyle(estiloNormal);
//             cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
//                     .getDescripcionClaseTransaccion());

//             cell = row.createCell(8);
//             cell.setCellStyle(estiloCriterio);
//             cell.setCellValue("Código Transacción");
//             cell = row.createCell(9);
//             cell.setCellStyle(estiloNormal);
//             cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
//                     .getDescripcionCodigoTransaccion());

//             cell = row.createCell(11);
//             cell.setCellStyle(estiloCriterio);
//             cell.setCellValue("Fecha Inicio - Fin");
//             cell = row.createCell(12);
//             cell.setCellStyle(estiloNormal);
//             cell.setCellValue(criterioPaginacion.getCriterioBusqueda()
//                     .getDescripcionFechas());

//             String[] cab = { "Fecha Txn", "Membresía", "Servicio", "Rol",
//                     "Canal", "Origen", "BIN", "SubBin", "Clase Transacción",
//                     "Código Transacción", "Giro de Negocio", "Receptor",
//                     "Respuesta", "Cantidad", "Monto" };

//             String[] conceptosComision = { "THB", "EST", "REC", "OPE", "ISA",
//                     "OIF", "ING", "COI", "TIC", "INT" };

//             row = sheet.createRow(9);
//             cell = row.createCell(cab.length + 1);
//             cell.setCellStyle(estiloCab);
//             cell.setCellValue("COMISIONES");
//             sheet.addMergedRegion(new CellRangeAddress(9, 9, cab.length + 1,
//                     cab.length + conceptosComision.length));

//             row = sheet.createRow(10);

//             for (int i = 0; i < cab.length; i++)
//             {
//                 cell = row.createCell(i + 1);
//                 cell.setCellStyle(estiloCab);
//                 cell.setCellValue(cab[i]);
//             }

//             sheet.createFreezePane(0, 11);
//             sheet.setAutoFilter(new CellRangeAddress(10, 10, 1,
//                     cab.length + conceptosComision.length + 1));

//             for (int i = 0; i < conceptosComision.length; i++)
//             {
//                 cell = row.createCell(cab.length + 1 + i);
//                 cell.setCellStyle(estiloCab);
//                 cell.setCellValue(conceptosComision[i]);
//             }

//             cell = row.createCell(cab.length + conceptosComision.length + 1);
//             cell.setCellStyle(estiloCab);
//             cell.setCellValue("Total");

//             boolean seObtuvoTotalFilas = false;
//             double totalFilas = 0;
//             procesandoRegistro = 0;

//             logger.info("Ejecutando consulta.");

//             try
//             {
//                 if (monedas != null)
//                 {
//                     List<ReporteResumenLogContableEmisor> resultSet = monedas
//                             .getResumenesLogContableEmisor();
//                     int tLogContableEmisor = resultSet.size();

//                     for (ReporteResumenLogContableEmisor reporteResumenLogContableEmisor : resultSet)
//                     {
//                         if (!seObtuvoTotalFilas)
//                         {
//                             totalFilas = tLogContableEmisor;
//                             seObtuvoTotalFilas = true;
//                             logger.info(
//                                     "El total de filas de la consulta es: {}",
//                                     (int) totalFilas);
//                         }
//                         procesandoRegistro = (float) (numeroFila / totalFilas)
//                                 * 100;

//                         for (Session session : sessions)
//                         {
//                             if (session.isOpen())
//                             {
//                                 session.getBasicRemote()
//                                         .sendObject(new Message(
//                                                 procesandoRegistro,
//                                                 reporteCompleto, false));
//                             }
//                         }
//                         row = sheet.createRow(filaActual);

//                         cell = row.createCell(1);
//                         cell.setCellStyle(estiloFecha);
//                         cell.setCellValue(reporteResumenLogContableEmisor
//                                 .getFechaProceso());

//                         cell = row.createCell(2);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 reporteResumenLogContableEmisor
//                                         .getCodigoMembresia(),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionMembresia()));

//                         cell = row.createCell(3);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 reporteResumenLogContableEmisor
//                                         .getCodigoClaseServicio(),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionClaseServicio()));

//                         cell = row.createCell(4);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 reporteResumenLogContableEmisor
//                                         .getRolTransaccion() == null
//                                                 ? ""
//                                                 : Integer.toString(
//                                                         reporteResumenLogContableEmisor
//                                                                 .getRolTransaccion()),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionRolTransaccion() == null
//                                                 ? ""
//                                                 : reporteResumenLogContableEmisor
//                                                         .getDescripcionRolTransaccion()));

//                         cell = row.createCell(5);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 reporteResumenLogContableEmisor
//                                         .getIdCanal() == null
//                                                 ? ""
//                                                 : Integer.toString(
//                                                         reporteResumenLogContableEmisor
//                                                                 .getIdCanal()),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionCanal()));

//                         cell = row.createCell(6);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 Integer.toString(reporteResumenLogContableEmisor
//                                         .getCodigoOrigen()),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionOrigen()));

//                         cell = row.createCell(7);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 reporteResumenLogContableEmisor.getCodigoBIN(),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionBIN()));

//                         cell = row.createCell(8);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 reporteResumenLogContableEmisor
//                                         .getCodigoSubBIN(),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionSubBIN()));

//                         cell = row.createCell(9);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 Integer.toString(reporteResumenLogContableEmisor
//                                         .getCodigoClaseTransaccion()),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionClaseTransaccion()));

//                         cell = row.createCell(10);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 Integer.toString(reporteResumenLogContableEmisor
//                                         .getCodigoTransaccion()),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionCodigoTransaccion()));

//                         cell = row.createCell(11);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 reporteResumenLogContableEmisor
//                                         .getCodigoGiroNegocio(),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionGiroNegocio()));

//                         cell = row.createCell(12);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 Integer.toString(reporteResumenLogContableEmisor
//                                         .getCodigoInstitucionReceptor()),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionInstitucionReceptor()));

//                         cell = row.createCell(13);
//                         cell.setCellStyle(estiloNormal);
//                         cell.setCellValue(concatenarCodigoDescripcion(
//                                 Integer.toString(reporteResumenLogContableEmisor
//                                         .getCodigoRespuesta()),
//                                 reporteResumenLogContableEmisor
//                                         .getDescripcionCodigoRespuesta()));

//                         cell = row.createCell(14);
//                         cell.setCellStyle(estiloCantidad);
//                         cell.setCellValue((reporteResumenLogContableEmisor
//                                 .getCantidad()));

//                         cell = row.createCell(15);
//                         cell.setCellStyle(estiloDecimal2);
//                         cell.setCellValue(
//                                 reporteResumenLogContableEmisor.getMonto());

//                         List<ReporteComision> comisiones = reporteResumenLogContableEmisor
//                                 .getComisiones();

//                         for (ReporteComision comisione : comisiones)
//                         {
//                             String signo = comisione.getRegistroContable();
//                             double comis = comisione.getComision();

//                             if (signo.equals("C"))
//                             {
//                                 comis = comis * (-1);
//                             }

//                             switch (comisione.getIdConceptoComision())
//                             {
//                             case 1:
//                                 cell = row.createCell(16);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             case 2:
//                                 cell = row.createCell(17);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             case 3:
//                                 cell = row.createCell(18);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             case 4:
//                                 cell = row.createCell(19);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             case 5:
//                                 cell = row.createCell(20);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             case 6:
//                                 cell = row.createCell(21);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             case 10:
//                                 cell = row.createCell(22);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             case 11:
//                                 cell = row.createCell(23);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             case 12:
//                                 cell = row.createCell(24);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             case 14:
//                                 cell = row.createCell(25);
//                                 cell.setCellStyle(estiloDecimal4);
//                                 cell.setCellValue(comis);
//                                 break;
//                             default:
//                                 break;
//                             }
//                         }

//                         for (int l = 16; l < 26; l++)
//                         {
//                             Cell celda = row.getCell(l);
//                             if (celda == null)
//                             {
//                                 celda = row.createCell(l);
//                                 celda.setCellStyle(estiloDecimal4);
//                                 celda.setCellValue(0.0);
//                             }
//                         }
//                         cell = row.createCell(26);
//                         cell.setCellStyle(estiloDecimal4);
//                         cell.setCellFormula("SUM("
//                                 + CellReference
//                                         .convertNumToColString(cab.length + 1)
//                                 + (filaActual + 1) + ":"
//                                 + CellReference.convertNumToColString(
//                                         cab.length + conceptosComision.length)
//                                 + (filaActual + 1) + ")");

//                         filaActual++;
//                         numeroFila++;
//                     }
//                     row = sheet.getRow(7);
//                     if (row == null)
//                     {
//                         row = sheet.createRow(7);
//                     }

//                     cell = row.createCell(13);
//                     cell.setCellStyle(estiloCab);
//                     cell.setCellValue("Total");

//                     cell = row.createCell(14);
//                     cell.setCellStyle(estiloCantidadBold);
//                     cell.setCellFormula("SUM(O" + (inicioFilaReporte + 1) + ":O"
//                             + filaActual + ")");

//                     cell = row.createCell(15);
//                     cell.setCellStyle(estiloDecimal2Bold);
//                     cell.setCellFormula("SUM(P" + (inicioFilaReporte + 1) + ":P"
//                             + filaActual + ")");

//                     for (int i = 0; i <= conceptosComision.length; i++)
//                     {
//                         cell = row.createCell(16 + i);
//                         cell.setCellStyle(estiloDecimal4Bold);
//                         cell.setCellFormula("SUM("
//                                 + CellReference.convertNumToColString(16 + i)
//                                 + (inicioFilaReporte + 1) + ":"
//                                 + CellReference.convertNumToColString(16 + i)
//                                 + filaActual + ")");
//                     }
//                 }
//             } catch (IllegalStateException e)
//             {
//                 logger.error("La conexión ha sido cancelada: {}",
//                         e.getMessage());
//             } catch (ClientAbortException e)
//             {
//                 logger.error("La conexión cancelada: {}", e.getMessage());
//             } catch (Exception e)
//             {
//                 logger.error("Ocurrió un error: {}", e.getMessage());
//             }
//             logger.info("Consulta {} terminada: {}", numPagina);

//             return (criterioPaginacion.getStart()
//                     + criterioPaginacion.getLength()) >= totalFilas;
//         } catch (Exception e)
//         {
//             logger.error("Error: {}", e.getMessage());
//             throw new SimpException(
//                     "Ocurrió un error al recuperar los registros de la base de datos");
//         }

//     }

//     private CellStyle crearEstiloBasico(SXSSFWorkbook sxssfWorkbook, Font font)
//     {
//         CellStyle estilo = sxssfWorkbook.createCellStyle();

//         estilo.setAlignment(HorizontalAlignment.LEFT);
//         estilo.setBorderTop(BorderStyle.THIN);
//         estilo.setBorderRight(BorderStyle.THIN);
//         estilo.setBorderBottom(BorderStyle.THIN);
//         estilo.setBorderLeft(BorderStyle.THIN);
//         estilo.setFont(font);

//         return estilo;
//     }

//     private CellStyle crearEstiloFecha(SXSSFWorkbook sxssfWorkbook, Font font)
//     {
//         CellStyle estilo = sxssfWorkbook.createCellStyle();

//         estilo.setAlignment(HorizontalAlignment.CENTER);
//         estilo.setDataFormat(
//                 sxssfWorkbook.createDataFormat().getFormat("dd/MM/yyyy"));
//         estilo.setBorderTop(BorderStyle.THIN);
//         estilo.setBorderRight(BorderStyle.THIN);
//         estilo.setBorderBottom(BorderStyle.THIN);
//         estilo.setBorderLeft(BorderStyle.THIN);
//         estilo.setFont(font);

//         return estilo;
//     }

//     private CellStyle crearEstiloNumero(SXSSFWorkbook sxssfWorkbook,
//             String formato, Font font)
//     {
//         CellStyle estilo = sxssfWorkbook.createCellStyle();

//         estilo.setAlignment(HorizontalAlignment.RIGHT);
//         estilo.setDataFormat(
//                 sxssfWorkbook.createDataFormat().getFormat(formato));
//         estilo.setBorderTop(BorderStyle.THIN);
//         estilo.setBorderRight(BorderStyle.THIN);
//         estilo.setBorderBottom(BorderStyle.THIN);
//         estilo.setBorderLeft(BorderStyle.THIN);
//         estilo.setFont(font);

//         return estilo;
//     }

//     private String validarString(String value)
//     {
//         return value != null ? value : "";
//     }

//     private String concatenarCodigoDescripcion(String codigo,
//             String descripcion)
//     {
//         return StringUtils.join(validarString(codigo), " - ",
//                 validarString(descripcion));
//     }

//     @OnOpen
//     public void open(Session session)
//     {
//         sessions.add(session);
//     }

//     @OnMessage
//     public void message(Message message)
//     {
//         cancelado = message.getCancelado();
//         if (cancelado)
//         {
//             sessions.clear();
//         }
//     }

//     @OnClose
//     public void close(Session session)
//     {
//         sessions.remove(session);
//     }

// }
