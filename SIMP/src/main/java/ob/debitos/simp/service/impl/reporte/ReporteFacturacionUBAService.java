package ob.debitos.simp.service.impl.reporte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.websocket.Message;
import ob.debitos.simp.model.websocket.MessageDecoder;
import ob.debitos.simp.model.websocket.MessageEncoder;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.utilitario.ExportFilterOutputStream;
import ob.debitos.simp.utilitario.ExportacionUtil;
import ob.debitos.simp.utilitario.PaginacionUtil;
import ob.debitos.simp.utilitario.Verbo;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
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

import ob.debitos.simp.mapper.IReporteFacturacionUBAMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteFacturacion;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.service.IReporteFacturacionUBAService;
import ob.debitos.simp.model.reporte.ReporteFacturacionResumen;
import ob.debitos.simp.model.reporte.ReporteFacturacionDetalle;
import ob.debitos.simp.model.reporte.ReporteFacturacionMiscelaneos;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import ob.debitos.simp.service.IExportacionPOIService;

@Service
@ServerEndpoint(value = "/facturacionUBA", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
public class ReporteFacturacionUBAService implements IReporteFacturacionUBAService
{

    private @Autowired IReporteFacturacionUBAMapper reporteFacturacionUBAMapper;
    
    private @Autowired IExportacionPOIService<ReporteFacturacionResumen> exportReporteFacturacionUbaResumenService;
    private @Autowired IExportacionPOIService<ReporteFacturacionDetalle> exportReporteFacturacionUbaDetalleService;
    private @Autowired IExportacionPOIService<ReporteFacturacionMiscelaneos> exportReporteFacturacionUbaMiscelaneosService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarResumen(CriterioBusquedaReporteFacturacion criterio)
    {
        criterio.setVerbo(Verbo.FACTURCION_UBA_RESUMEN);
        return this.reporteFacturacionUBAMapper.buscarResumen(criterio);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void descargarFacturacionUbaResumen(List<ReporteMoneda> reporte, CriterioBusquedaReporteFacturacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        List<ReporteFacturacionResumen> reporteSoles = null;
        List<ReporteFacturacionResumen> reporteDolares = null;

        if (!reporte.isEmpty())
        {
            if (reporte.size() == 1)
            {
                if (reporte.get(0).getCodigoMoneda() == 604)
                {
                    reporteSoles = reporte.get(0).getFacturacionResumen();
                } else if (reporte.get(0).getCodigoMoneda() == 840)
                {
                    reporteDolares = reporte.get(0).getFacturacionResumen();
                }
            } else if (reporte.size() > 1)
            {
                for (int k = 0; k < reporte.size(); k++)
                {
                    if (reporte.get(k).getCodigoMoneda() == 604)
                    {
                        reporteSoles = reporte.get(k).getFacturacionResumen();
                    } else if (reporte.get(k).getCodigoMoneda() == 840)
                    {
                        reporteDolares = reporte.get(k).getFacturacionResumen();
                    }
                }
            }
        }

        String[][] filtros = {
                {"Fecha Proceso"        ,criterio.getDescripcionFechaProceso()},
                {"Institución"          ,criterio.getDescripcionInstitucion()},
                {"Empresa"              ,criterio.getDescripcionEmpresa()},
                {"Cliente"              ,criterio.getDescripcionCliente()},
                {"Concepto Comisión"    ,criterio.getDescripcionConceptoComision()}
        };

        String[][] cabeceraExportacion = {
            {"Fecha Proceso"            ,"fechaProceso"         ,""                                         ,"formatFecha"      ,"-1"},
            {"Institución"              ,"codigoInstitucion"    ,"descripcionInstitucion"                   ,"formatCadena"     ,"-1"},
            {"Empresa"                  ,"idEmpresa"            ,"descEmpresa"                              ,"formatCadena"     ,"-1"},
            {"Cliente"                  ,"idCliente"            ,"descCliente"                              ,"formatCadena"     ,"-1"},
            {"Membresía"                ,"idMembresia"          ,"descMembresia"                            ,"formatCadena"     ,"-1"},
            {"Servicio"                 ,"idClaseServicio"      ,"descClaseServicio"                        ,"formatCadena"     ,"-1"},
            {"Código Facturación"       ,"codigoFacturacion"    ,"descCodigoFacturacion"                    ,"formatCadena"     ,"-1"},
            {"Concepto Comisión"        ,"idConceptoComision"   ,"descConceptoComision"                     ,"formatCadena"     ,"-1"},
            {"Cta. Cargo"               ,"cuentaCargo"          ,""                                         ,"formatCadena"     ,"-1"},
            {"Cta. Abono"               ,"cuentaAbono"          ,""                                         ,"formatCadena"     ,"-1"},
            {"Cantidad"                 ,"cantidad"             ,""                                         ,"formatCantidad"   ,"-1"},
            {"Comisión Importe"         ,"comisionImporte"      ,""                                         ,"formatComision"   ,"-1"},
            {"Comisión IGV"             ,"comisionIGV"          ,""                                         ,"formatComision"   ,"-1"},
            {"Comisión Total"           ,"comisionTotal"        ,""                                         ,"formatComision"   ,"-1"},
            {"Comisión Promedio"        ,"comisionPromedio"     ,""                                         ,"formatComision"   ,"-1"}
        };
        this.exportReporteFacturacionUbaResumenService.generarExportacionMonedaNormal("Reporte Facturación UNIBANCA - Resumen Facturación", reporteSoles, reporteDolares, filtros, cabeceraExportacion, true, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarDetalle(CriterioBusquedaReporteFacturacion criterio)
    {
        criterio.setVerbo(Verbo.FACTURCION_UBA_DETALLE);
        return this.reporteFacturacionUBAMapper.buscarDetalle(criterio);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void descargarFacturacionUbaDetalle(List<ReporteMoneda> reporte, CriterioBusquedaReporteFacturacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        List<ReporteFacturacionDetalle> reporteSoles = null;
        List<ReporteFacturacionDetalle> reporteDolares = null;

        if (!reporte.isEmpty())
        {
            if (reporte.size() == 1)
            {
                if (reporte.get(0).getCodigoMoneda() == 604)
                {
                    reporteSoles = reporte.get(0).getFacturacionDetalle();
                } else if (reporte.get(0).getCodigoMoneda() == 840)
                {
                    reporteDolares = reporte.get(0).getFacturacionDetalle();
                }
            } else if (reporte.size() > 1)
            {
                for (int k = 0; k < reporte.size(); k++)
                {
                    if (reporte.get(k).getCodigoMoneda() == 604)
                    {
                        reporteSoles = reporte.get(k).getFacturacionDetalle();
                    } else if (reporte.get(k).getCodigoMoneda() == 840)
                    {
                        reporteDolares = reporte.get(k).getFacturacionDetalle();
                    }
                }
            }
        }

        String[][] filtros = {
                {"Fecha Proceso"    ,criterio.getDescripcionFechaProceso()},
                {"Institución"      ,criterio.getDescripcionInstitucion()},
                {"Empresa"          ,criterio.getDescripcionEmpresa()},
                {"Cliente"          ,criterio.getDescripcionCliente()}
        };
        String[][] cabeceraExportacion = {
            {"Fecha Proceso"            ,"fechaProceso"         ,""                                         ,"formatFecha"      ,"-1"},
            {"Institución"              ,"codigoInstitucion"    ,"descripcionInstitucion"                   ,"formatCadena"     ,"-1"},
            {"Empresa"                  ,"idEmpresa"            ,"descEmpresa"                              ,"formatCadena"     ,"-1"},
            {"Cliente"                  ,"idCliente"            ,"descCliente"                              ,"formatCadena"     ,"-1"},
            {"Rol Transacción"          ,"rolTransaccion"       ,"descRolTransaccion"                       ,"formatCadena"     ,"-1"},
            {"Membresía"                ,"idMembresia"          ,"descMembresia"                            ,"formatCadena"     ,"-1"},
            {"Servicio"                 ,"idClaseServicio"      ,"descClaseServicio"                        ,"formatCadena"     ,"-1"},
            {"Código Facturación"       ,"codigoFacturacion"    ,"descCodigoFacturacion"                    ,"formatCadena"     ,"-1"},
            {"Cta. Cargo"               ,"cuentaCargo"          ,""                                         ,"formatCadena"     ,"-1"},
            {"Cta. Abono"               ,"cuentaAbono"          ,""                                         ,"formatCadena"     ,"-1"},
            {"Cantidad"                 ,"cantidad"             ,""                                         ,"formatCantidad"   ,"-1"},
            {"Comisión Importe"         ,"comisionImporte"      ,""                                         ,"formatComision"   ,"-1"},
            {"Comisión IGV"             ,"comisionIGV"          ,""                                         ,"formatComision"   ,"-1"},
            {"Comisión Total"           ,"comisionTotal"        ,""                                         ,"formatComision"   ,"-1"},
            {"Comisión Promedio"        ,"comisionPromedio"     ,""                                         ,"formatComision"   ,"-1"}
        };
        this.exportReporteFacturacionUbaDetalleService.generarExportacionMonedaNormal("Reporte Facturación UNIBANCA - Detalle Facturación", reporteSoles, reporteDolares, filtros, cabeceraExportacion, true, 3, 85, request, response);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteMoneda> buscarMiscelaneos(CriterioBusquedaReporteFacturacion criterio)
    {
        criterio.setVerbo(Verbo.FACTURCION_UBA_MISCELANEO);
        return reporteFacturacionUBAMapper.buscarMiscelaneos(criterio);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void descargarFacturacionUbaMiscelaneos(List<ReporteMoneda> reporte, CriterioBusquedaReporteFacturacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        List<ReporteFacturacionMiscelaneos> reporteSoles = null;
        List<ReporteFacturacionMiscelaneos> reporteDolares = null;

        if (!reporte.isEmpty())
        {
            if (reporte.size() == 1)
            {
                if (reporte.get(0).getCodigoMoneda() == 604)
                {
                    reporteSoles = reporte.get(0).getFacturacionMiscelaneos();
                } else if (reporte.get(0).getCodigoMoneda() == 840)
                {
                    reporteDolares = reporte.get(0).getFacturacionMiscelaneos();
                }
            } else if (reporte.size() > 1)
            {
                for (int k = 0; k < reporte.size(); k++)
                {
                    if (reporte.get(k).getCodigoMoneda() == 604)
                    {
                        reporteSoles = reporte.get(k).getFacturacionMiscelaneos();
                    } else if (reporte.get(k).getCodigoMoneda() == 840)
                    {
                        reporteDolares = reporte.get(k).getFacturacionMiscelaneos();
                    }
                }
            }
        }

        String[][] filtros = {
                {"Fecha Proceso"    ,criterio.getDescripcionFechaProceso()},
                {"Institución"      ,criterio.getDescripcionInstitucion()},
                {"Empresa"          ,criterio.getDescripcionEmpresa()},
                {"Cliente"          ,criterio.getDescripcionCliente()}
        };
        String[][] cabeceraExportacionFacturacion = {
            {"Fecha Proceso"                    ,"fechaProceso"                         ,""                         ,"formatFecha"      ,"-1"},
            {"Institución"                      ,"codigoInstitucion"                    ,"descripcionInstitucion"   ,"formatCadena"     ,"-1"},
            {"Empresa"                          ,"idEmpresa"                            ,"descEmpresa"              ,"formatCadena"     ,"-1"},
            {"Cliente"                          ,"idCliente"                            ,"descCliente"              ,"formatCadena"     ,"-1"},
            {"Membresía"                        ,"idMembresia"                          ,"descMembresia"            ,"formatCadena"     ,"-1"},
            {"Servicio"                         ,"idClaseServicio"                      ,"descClaseServicio"        ,"formatCadena"     ,"-1"},
            {"Origen"                           ,"idOrigen"                             ,"descOrigen"               ,"formatCadena"     ,"-1"},
            {"Clase Transacción"                ,"idClaseTransaccion"                   ,"descClaseTransaccion"     ,"formatCadena"     ,"-1"},
            {"Código Transacción"               ,"idCodigoTransaccion"                  ,"descCodigoTransaccion"    ,"formatCadena"     ,"-1"},
            {"Secuencia"                        ,"secuenciaTransaccion"                 ,""                         ,"formatCadena"     ,"-1"},
            {"Cta. Cargo"                       ,"cuentaCargo"                          ,""                         ,"formatCadena"     ,"-1"},
            {"Cta. Abono"                       ,"cuentaAbono"                          ,""                         ,"formatCadena"     ,"-1"},
            {"Glosa Regularización"             ,"glosaRegularizacion"                  ,""                         ,"formatCadena"     ,"-1"},
            {"Secuencia Factura"                ,"secuenciaRegistroFacturaMarca"        ,""                         ,"formatCadena"     ,"-1"},
            {"Número Factura"                   ,"numeroFacturaMarca"                   ,""                         ,"formatCadena"     ,"-1"},
            {"Código Evento"                    ,"codigoEventoMarcaInternacional"       ,""                         ,"formatCadena"     ,"-1"},
            {"Distribución cobro"               ,"distribucionCobroMarcaInternacional"  ,""                         ,"formatCadena"     ,"-1"},
            {"Indicador Distribución Cobro"     ,"indicadorDistribucionCobro"           ,""                         ,"formatCadena"     ,"-1"},
            {"Indicador Unidades"               ,"indicadorUnidades"                    ,""                         ,"formatCadena"     ,"-1"},
            {"Total Unidades"                   ,"totalUnidades"                        ,""                         ,"formatCantidad"   ,"-1"},
            {"Tarifa Marca Internacional"       ,"tarifaMarcaInternacional"             ,""                         ,"formatComision"   ,"-1"},
            {"Importe Factura"                  ,"valorFacturaMarcaInternacional"       ,""                         ,"formatComision"   ,"-1"},
            {"Importe Compensación"             ,"valorCompensacion"                    ,""                         ,"formatComision"   ,"-1"}
        };
        this.exportReporteFacturacionUbaMiscelaneosService.generarExportacionMonedaNormal("Reporte Facturación UNIBANCA - Cobros Misceláneos", reporteSoles, reporteDolares, filtros, cabeceraExportacionFacturacion, true, 3, 85, request, response);
    }
}
