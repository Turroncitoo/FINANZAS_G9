package ob.debitos.simp.service.impl.reporte;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.mantenimiento.ConceptoComision;
import ob.debitos.simp.model.reporte.*;
import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteResumenMovimientoMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableEmisor;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableReceptor;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenAutorizacionSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransaccionAprobadaAgencia;
import ob.debitos.simp.model.reporte.ReporteResumenSwDmpLog;
import ob.debitos.simp.service.IReporteResumenMovimientoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ReporteResumenMovimientoService implements IReporteResumenMovimientoService
{

    private @Autowired IReporteResumenMovimientoMapper reporteResumenMovimientoMapper;

    private @Autowired IExportacionPOIService<ReporteResumenLogContableEmisor> exportLogContabEmiService;
    private @Autowired IExportacionPOIService<ReporteResumenSwDmpLog> exportReporteResumenSwDmpLogService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteResumenAutorizacion> buscarResumenAutorizacion(CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda)
    {
        return reporteResumenMovimientoMapper.buscarResumenAutorizaciones(criterioBusqueda);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteResumenSwDmpLog> buscarResumenSwDmpLog(CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda)
    {
        return this.reporteResumenMovimientoMapper.buscarResumenSwDmpLogs(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteResumenTransaccionAprobadaAgencia> buscarResumenTransaccionAprobadaAgencia(CriterioBusquedaResumenTransaccionAprobadaAgencia criterioBusqueda)
    {
        return reporteResumenMovimientoMapper.buscarResumenTransaccionesAprobadasAgencia(criterioBusqueda);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteResumenLogContableEmisor> buscarResumenLogContableEmisor(CriterioBusquedaResumenLogContableEmisor criterioBusqueda)
    {
        return reporteResumenMovimientoMapper.buscarResumenLogContableEmisor(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteResumenLogContableReceptor> buscarResumenLogContableReceptor(CriterioBusquedaResumenLogContableReceptor criterioBusqueda)
    {
        return reporteResumenMovimientoMapper.buscarResumenLogContableReceptor(criterioBusqueda);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buscarResumenLogContableEmisorExportacion(List<ReporteResumenLogContableEmisor> reporte, CriterioBusquedaResumenLogContableEmisor criterioBusqueda, HttpServletRequest request, HttpServletResponse response, List<ConceptoComision> conceptosComision) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso"        ,criterioBusqueda.getDescripcionFechas()},
                {"Institución"          ,criterioBusqueda.getDescripcionInstitucion()},
                {"Empresa"              ,criterioBusqueda.getDescripcionEmpresa()},
                {"Clientes"              ,criterioBusqueda.getDescripcionCliente()},
                {"Rol Transacción"      ,criterioBusqueda.getDescripcionRolTransaccion()},
                {"Membresía"            ,criterioBusqueda.getDescripcionMembresia()},
                {"Servicios"             ,criterioBusqueda.getDescripcionClaseServicio()},
                {"Origenes"               ,criterioBusqueda.getDescripcionOrigen()},
                {"Clase Transacción"    ,criterioBusqueda.getDescripcionClaseTransaccion()},
                {"Código Transacciónes"   ,criterioBusqueda.getDescripcionCodigoTransaccion()},
                {"Canales"                ,criterioBusqueda.getDescripcionCanal()}
        };
        String[][] cabeceraExportacion = {
            {"Fecha Proceso"            ,"fechaProceso"              ,""                                    ,"formatFecha"      ,"-1"},
            {"Empresa"                  ,"idEmpresa"                 ,"descEmpresa"                         ,"formatCadena"     ,"-1"},
            {"Cliente"                  ,"idCliente"                 ,"descCliente"                         ,"formatCadena"     ,"-1"},
            {"Logo"                     ,"idLogo"                    ,"descLogoBin"                     ,"formatCadena"     ,"-1"},
            {"Producto"                 ,"codigoProducto"            ,"descProducto"                        ,"formatCadena"     ,"-1"},
            {"Rol Transacción"          ,"rolTransaccion"            ,"descripcionRolTransaccion"           ,"formatCadena"     ,"-1"},
            {"Membresía"                ,"codigoMembresia"           ,"descripcionMembresia"                ,"formatCadena"     ,"-1"},
            {"Servicios"                 ,"codigoClaseServicio"       ,"descripcionClaseServicio"            ,"formatCadena"     ,"-1"},
            {"Origen"                   ,"codigoOrigen"              ,"descripcionOrigen"                   ,"formatCadena"     ,"-1"},
            {"Clase Transacción"        ,"claseTransaccion"          ,"descripcionClaseTransaccion"         ,"formatCadena"     ,"-1"},
            {"Código Transacción"       ,"codigoTransaccion"         ,"descripcionCodigoTransaccion"        ,"formatCadena"     ,"-1"},
            {"Código Respuesta"         ,"codigoRespuesta"           ,"descripcionCodigoRespuesta"          ,"formatCadena"     ,"-1"},
            {"Canal"                    ,"idCanal"                   ,"descripcionCanal"                    ,"formatCadena"     ,"-1"},
            {"Giro Negocio"             ,"codigoGiroNegocio"         ,"descripcionGiroNegocio"              ,"formatCadena"     ,"-1"},
            {"Inst. Receptora"          ,"codigoInstitucionReceptor" ,"descripcionInstitucionReceptor"      ,"formatCadena"     ,"-1"},
            {"Moneda"                   ,"codigoMoneda"              ,"descripcionMoneda"                   ,"formatCadena"     ,"-1"},
            {"Cantidad"                 ,"cantidad"                  ,""                                    ,"formatCantidad"   ,"-1"},
            {"Importe"                  ,"monto"                     ,""                                    ,"formatMonto"      ,"-1"}
        };
        this.exportLogContabEmiService.generarExportacionNormalComis("Reporte Resumen Log Contable Emisor", "comisiones", reporte, filtros, cabeceraExportacion, conceptosComision, 3, 85, request, response);
    }

    private static final String[][] cabeceraExportacionResumenSwDmpLog = {
        {"Fecha Proceso"    ,"fechaProceso"         ,""                             ,"formatFecha"      ,"-1"},
        {"Institución"      ,"idInstitucion"        ,"descripcionInstitucion"       ,"formatCadena"     ,"-1"},
        {"Empresa"          ,"idEmpresa"            ,"descEmpresa"                  ,"formatCadena"     ,"-1"},
        {"Cliente"          ,"idCliente"            ,"descCliente"                  ,"formatCadena"     ,"-1"},
        {"Rol Transacción"  ,"rolTransaccion"       ,"descripcionRolTransaccion"    ,"formatCadena"     ,"-1"},
        {"Código Proceso"   ,"codigoProceso"        ,"descripcionCodigoProceso"     ,"formatCadena"     ,"-1"},
        {"Código Respuesta" ,"codigoRespuesta"      ,"descripcionCodigoRespuesta"   ,"formatCadena"     ,"-1"},
        {"Canal"            ,"idCanal"              ,"descripcionCanal"             ,"formatCadena"     ,"-1"},
        {"Giro Negocio"     ,"codigoGiroNegocio"    ,"descripcionGiroNegocio"       ,"formatCadena"     ,"-1"},
        {"Tipo Mensaje"     ,"tipoMensaje"          ,""                             ,"formatCadena"     ,"-1"},
        {"Moneda"           ,"codigoMoneda"         ,"descripcionMoneda"            ,"formatCadena"     ,"-1"},
        {"Cantidad"         ,"cantidadTransaccion"  ,""                             ,"formatCantidad"   ,"-1"},
        {"Importe"          ,"montoTransaccion"     ,""                             ,"formatMonto"      ,"-1"}
    };

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void descargarReporteResumenSwDmpLog(List<ReporteResumenSwDmpLog> reporte, CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso"        ,criterioBusqueda.getDescripcionRangoFechas()},
                {"Institución"          ,criterioBusqueda.getDescripcionInstitucion()},
                {"Empresa"              ,criterioBusqueda.getDescripcionEmpresa()},
                {"Cliente"              ,criterioBusqueda.getDescripcionCliente()},
                {"Rol Transacción"      ,criterioBusqueda.getDescripcionRolTransaccion()},
                {"Código Proceso"       ,criterioBusqueda.getDescripcionCodigoProceso()},
                {"Canal"                ,criterioBusqueda.getDescripcionIdCanal()}
        };
        this.exportReporteResumenSwDmpLogService.generarExportacionNormal("Reporte Resumen SwDmpLog", reporte, filtros, cabeceraExportacionResumenSwDmpLog, true, 3, 85, request, response);
    }
}