package ob.debitos.simp.service.impl.reporte;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.model.mantenimiento.ConceptoComision;
import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.IReporteContabilizacionMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaContabilizacion;
import ob.debitos.simp.model.reporte.ReporteContabilizacion;
import ob.debitos.simp.service.IReporteContabilizacionService;

@Service
public class ReporteContabilizacionService implements IReporteContabilizacionService
{

    private @Autowired IReporteContabilizacionMapper reporteContabilizacionMapper;

    private @Autowired IExportacionPOIService<ReporteContabilizacion> exportContabComisService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteContabilizacion> buscarContabilizacionesEmisorMovimientoPorCriterio(CriterioBusquedaContabilizacion criterioBusqueda)
    {
        return reporteContabilizacionMapper.buscarContabilizacionesEmisorMovimientoPorCriterio(criterioBusqueda);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteContabilizacion> buscarContabilizacionesEmisorComisionesPorCriterio(CriterioBusquedaContabilizacion criterioBusqueda)
    {
        return reporteContabilizacionMapper.buscarContabilizacionesEmisorComisionesPorCriterio(criterioBusqueda);
    }

    @Override
    @Truncable(clase = ReporteContabilizacion.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteContabilizacion> buscarContabilizacionesEnCuentaDefault(CriterioBusquedaContabilizacion criterioBusqueda)
    {
        return this.reporteContabilizacionMapper.buscarContabilizacionesEnCuentaDefault(criterioBusqueda);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteContabilizacion> buscarContabilizacionesMiscelaneoPorCriterio(CriterioBusquedaContabilizacion criterioBusqueda)
    {
        return this.reporteContabilizacionMapper.buscarContabilizacionesMiscelaneoPorCriterio(criterioBusqueda);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buscarContabilizacionesEmisorComisionesExportacion(List<ReporteContabilizacion> reporte, CriterioBusquedaContabilizacion criterio, HttpServletRequest request, HttpServletResponse response, List<ConceptoComision> conceptosComision) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso"                ,criterio.getDescripcionFechaProceso()},
                {"Institución"                  ,criterio.getDescripcionInstitucion()},
                {"Empresa"                      ,criterio.getDescripcionEmpresa()},
                {"Cliente"                      ,criterio.getDescripcionCliente()},
                {"Indicador Contabilización"    ,criterio.getDescripcionIndicador()}
        };
        String[][] cabeceraExportacion = {
            {"Fecha Proceso"                ,"fechaProceso"              ,""                                        ,"formatFecha"      ,"-1"},
            {"Institución"                  ,"codigoInstitucion"         ,"descripcionInstitucion"                  ,"formatCadena"     ,"-1"},
            {"Empresa"                      ,"idEmpresa"                 ,"descripcionEmpresa"                      ,"formatCadena"     ,"-1"},
            {"Cliente"                      ,"idCliente"                 ,"descripcionCliente"                      ,"formatCadena"     ,"-1"},
            {"Logo"                         ,"idLogo"                    ,"descLogoBin"                             ,"formatCadena"     ,"-1"},
            {"Producto"                     ,"codigoProducto"            ,"descProducto"                            ,"formatCadena"     ,"-1"},
            {"Membresía"                    ,"codigoMembresia"           ,"descripcionMembresia"                    ,"formatCadena"     ,"-1"},
            {"Servicio"                     ,"codigoClaseServicio"       ,"descripcionClaseServicio"                ,"formatCadena"     ,"-1"},
            {"Origen"                       ,"codigoOrigen"              ,"descripcionOrigen"                       ,"formatCadena"     ,"-1"},
            {"Clase Transacción"            ,"codigoClaseTransaccion"    ,"descripcionClaseTransaccion"             ,"formatCadena"     ,"-1"},
            {"Código Transacción"           ,"codigoTransaccion"         ,"descripcionCodigoTransaccion"            ,"formatCadena"     ,"-1"},
            {"Indicador Contabilización"    ,"indicadorContabilizacion"  ,"descripcionIndicadorContabilizacion"     ,"formatCadena"     ,"-1"},
            {"Cuenta Cargo"                 ,"cuentaCargo"               ,""                                        ,"formatCadena"     ,"-1"},
            {"Cuenta Abono"                 ,"cuentaAbono"               ,""                                        ,"formatCadena"     ,"-1"},
            {"Código Analítico"             ,"codigoAnalitico"           ,""                                        ,"formatCadena"     ,"-1"},
            {"Moneda Compensación"          ,"codigoMonedaCompensacion"  ,"descripcionMonedaCompensacion"           ,"formatCadena"     ,"-1"},
            {"Cantidad"                     ,"cantidad"                  ,""                                        ,"formatCantidad"   ,"-1"}
        };
        this.exportContabComisService.generarExportacionNormalComis("Reporte Contabilización de Comisiones", "comisiones", reporte, filtros, cabeceraExportacion, conceptosComision, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buscarContabilizacionesMovimientosExportacion(List<ReporteContabilizacion> reporte, CriterioBusquedaContabilizacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso"                ,criterio.getDescripcionFechaProceso()},
                {"Institución"                  ,criterio.getDescripcionInstitucion()},
                {"Empresa"                      ,criterio.getDescripcionEmpresa()},
                {"Clientes"                      ,criterio.getDescripcionCliente()},
                {"Indicador Contabilización"    ,criterio.getDescripcionIndicador()},
        };
        String[][] cab = {
                {"Fecha Proceso"                ,"fechaProceso"              ,""                                    ,"formatFecha"      ,"-1"},
                {"Institución"                  ,"codigoInstitucion"         ,"descripcionInstitucion"              ,"formatCadena"     ,"-1"},
                {"Empresa"                      ,"idEmpresa"                 ,"descripcionEmpresa"                  ,"formatCadena"     ,"-1"},
                {"Cliente"                      ,"idCliente"                 ,"descripcionCliente"                  ,"formatCadena"     ,"-1"},
                {"Logo"                         ,"idLogo"                    ,"descLogoBin"                         ,"formatCadena"     ,"-1"},
                {"Producto"                     ,"codigoProducto"            ,"descProducto"                        ,"formatCadena"     ,"-1"},
                {"Membresía"                    ,"codigoMembresia"           ,"descripcionMembresia"                ,"formatCadena"     ,"-1"},
                {"Servicio"                     ,"codigoClaseServicio"       ,"descripcionClaseServicio"            ,"formatCadena"     ,"-1"},
                {"Origen"                       ,"codigoOrigen"              ,"descripcionOrigen"                   ,"formatCadena"     ,"-1"},
                {"Clase Transacción"            ,"codigoClaseTransaccion"    ,"descripcionClaseTransaccion"         ,"formatCadena"     ,"-1"},
                {"Código Transacción"           ,"codigoTransaccion"         ,"descripcionCodigoTransaccion"        ,"formatCadena"     ,"-1"},
                {"Fondo Cargo"                  ,"fondoCargo"                ,"descFondoCargo"                      ,"formatCadena"     ,"-1"},
                {"Fondo Abono"                  ,"fondoAbono"                ,"descFondoAbono"                      ,"formatCadena"     ,"-1"},
                {"Indicador Contabilización"    ,"indicadorContabilizacion"  ,"descripcionIndicadorContabilizacion" ,"formatCadena"     ,"-1"},
                {"Cuenta Cargo"                 ,"cuentaCargo"               ,""                                    ,"formatCadena"     ,"-1"},
                {"Cuenta Abono"                 ,"cuentaAbono"               ,""                                    ,"formatCadena"     ,"-1"},
                {"Código Analítico"             ,"codigoAnalitico"           ,""                                    ,"formatCadena"     ,"-1"},
                {"Moneda Compensación"          ,"codigoMonedaCompensacion"  ,"descripcionMonedaCompensacion"       ,"formatCadena"     ,"-1"},
                {"Cantidad"                     ,"cantidad"                  ,""                                    ,"formatCantidad"   ,"-1"},
                {"Importe Compensación"         ,"monto"                     ,""                                    ,"formatMonto"      ,"-1"}
        };
        this.exportContabComisService.generarExportacionNormal("Reporte Contabilización de Movimientos", reporte, filtros, cab, true, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void descargarReporteCuentaDefault(List<ReporteContabilizacion> reporte, CriterioBusquedaContabilizacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso"  ,criterio.getDescripcionFechaProceso()},
                {"Institución"    ,criterio.getDescripcionInstitucion()},
                {"Empresa"        ,criterio.getDescripcionEmpresa()},
                {"Cliente"        ,criterio.getDescripcionCliente()}
        };
        String[][] cabeceraExportacion = {
            {"Fecha Proceso"            ,"fechaProceso"              ,""                                ,"formatFecha"      ,"-1"},
            {"Institución"              ,"codigoInstitucion"         ,"descripcionInstitucion"          ,"formatCadena"     ,"-1"},
            {"Empresa"                  ,"idEmpresa"                 ,"descripcionEmpresa"              ,"formatCadena"     ,"-1"},
            {"Cliente"                  ,"idCliente"                 ,"descripcionCliente"              ,"formatCadena"     ,"-1"},
            {"Logo"                     ,"idBin"                     ,"descLogoBin"                     ,"formatCadena"     ,"-1"},
            {"Producto"                 ,"codigoProducto"            ,"descProducto"                    ,"formatCadena"     ,"-1"},
            {"Rol Transacción"          ,"idRolTransaccion"          ,"descripcionRolTransaccion"       ,"formatCadena"     ,"-1"},
            {"Membresía"                ,"codigoMembresia"           ,"descripcionMembresia"            ,"formatCadena"     ,"-1"},
            {"Servicio"                 ,"codigoClaseServicio"       ,"descripcionClaseServicio"        ,"formatCadena"     ,"-1"},
            {"Origen"                   ,"codigoOrigen"              ,"descripcionOrigen"               ,"formatCadena"     ,"-1"},
            {"Clase Transacción"        ,"codigoClaseTransaccion"    ,"descripcionClaseTransaccion"     ,"formatCadena"     ,"-1"},
            {"Código Transacción"       ,"codigoTransaccion"         ,"descripcionCodigoTransaccion"    ,"formatCadena"     ,"-1"},
            {"Secuencia"                ,"secuenciaTransaccion"      ,""                                ,"formatCadena"     ,"-1"},
            {"Número Tarjeta"           ,"numeroTarjeta"             ,""                                ,"formatCadena"     ,"-1"},
            {"Número Cuenta"            ,"numeroCuenta"              ,""                                ,"formatCadena"     ,"-1"},
            {"Fondo Cargo"              ,"fondoCargo"                ,"descFondoCargo"                  ,"formatCadena"     ,"-1"},
            {"Fondo Abono"              ,"fondoAbono"                ,"descFondoAbono"                  ,"formatCadena"     ,"-1"},
            {"Tipo Transacción"         ,"tipoTransaccion"           ,"descripcionTransaccion"          ,"formatCadena"     ,"-1"},
            {"Cuenta Cargo"             ,"cuentaCargo"               ,""                                ,"formatCadena"     ,"-1"},
            {"Cuenta Abono"             ,"cuentaAbono"               ,""                                ,"formatCadena"     ,"-1"},
            {"Concepto Comisión"        ,"idConceptoComision"        ,"descripcionConceptoComision"     ,"formatCadena"     ,"-1"},
            {"Registro Contable"        ,"registroContable"          ,"descripcionRegistroContable"     ,"formatCadena"     ,"-1"},
            {"Moneda"                   ,"codigoMoneda"              ,"descripcionMoneda"               ,"formatCadena"     ,"-1"},
            {"Monto / Comisión"         ,"montoOComis"               ,""                                ,"formatComision"   ,"-1"}
        };
        this.exportContabComisService.generarExportacionNormal("Reporte de Contabilización en Cuentas Default", reporte, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void descargarReporteContabilizacionesMiscelaneos(List<ReporteContabilizacion> reporte, CriterioBusquedaContabilizacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso"                ,criterio.getDescripcionFechaProceso()},
                {"Institución"                  ,criterio.getDescripcionInstitucion()},
                {"Empresa"                      ,criterio.getDescripcionEmpresa()},
                {"Cliente"                      ,criterio.getDescripcionCliente()},
                {"Indicador Contabilización"    ,criterio.getDescripcionIndicador()}
        };
        String[][] cabeceraExportacion = {
            {"Fecha Proceso"                ,"fechaProceso"              ,""                                        ,"formatFecha"      ,"-1"},
            {"Institución"                  ,"codigoInstitucion"         ,"descripcionInstitucion"                  ,"formatCadena"     ,"-1"},
            {"Empresa"                      ,"idEmpresa"                 ,"descripcionEmpresa"                      ,"formatCadena"     ,"-1"},
            {"Cliente"                      ,"idCliente"                 ,"descripcionCliente"                      ,"formatCadena"     ,"-1"},
            {"Membresía"                    ,"codigoMembresia"           ,"descripcionMembresia"                    ,"formatCadena"     ,"-1"},
            {"Servicio"                     ,"codigoClaseServicio"       ,"descripcionClaseServicio"                ,"formatCadena"     ,"-1"},
            {"Origen"                       ,"codigoOrigen"              ,"descripcionOrigen"                       ,"formatCadena"     ,"-1"},
            {"Clase Transacción"            ,"codigoClaseTransaccion"    ,"descripcionClaseTransaccion"             ,"formatCadena"     ,"-1"},
            {"Código Transacción"           ,"codigoTransaccion"         ,"descripcionCodigoTransaccion"            ,"formatCadena"     ,"-1"},
            {"Fondo Cargo"                  ,"fondoCargo"                ,"descFondoCargo"                          ,"formatCadena"     ,"-1"},
            {"Fondo Abono"                  ,"fondoAbono"                ,"descFondoAbono"                          ,"formatCadena"     ,"-1"},
            {"Indicador Contabilización"    ,"indicadorContabilizacion"  ,"descripcionIndicadorContabilizacion"     ,"formatCadena"     ,"-1"},
            {"Cuenta Cargo"                 ,"cuentaCargo"               ,""                                        ,"formatCadena"     ,"-1"},
            {"Cuenta Abono"                 ,"cuentaAbono"               ,""                                        ,"formatCadena"     ,"-1"},
            {"Código Analítico"             ,"codigoAnalitico"           ,""                                        ,"formatCadena"     ,"-1"},
            {"Moneda Compensación"          ,"codigoMonedaCompensacion"  ,"descripcionMonedaCompensacion"           ,"formatCadena"     ,"-1"},
            {"Cantidad"                     ,"cantidad"                  ,""                                        ,"formatCantidad"   ,"-1"},
            {"Importe Compensación"         ,"monto"                     ,""                                        ,"formatMonto"      ,"-1"}
        };
        this.exportContabComisService.generarExportacionNormal("Reporte de Contabilización Misceláneos", reporte, filtros, cabeceraExportacion, true, 3, 85, request, response);
    }
}
