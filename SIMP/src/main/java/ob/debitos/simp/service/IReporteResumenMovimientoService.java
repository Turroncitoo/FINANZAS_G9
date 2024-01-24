package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableEmisor;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableReceptor;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenAutorizacionSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransaccionAprobadaAgencia;
import ob.debitos.simp.model.mantenimiento.ConceptoComision;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.model.reporte.ReporteResumenAutorizacion;
import ob.debitos.simp.model.reporte.ReporteResumenLogContableReceptor;
import ob.debitos.simp.model.reporte.ReporteResumenSwDmpLog;
import ob.debitos.simp.model.reporte.ReporteResumenLogContableEmisor;
import ob.debitos.simp.model.reporte.ReporteResumenTransaccionAprobadaAgencia;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IReporteResumenMovimientoService
{

    public List<ReporteResumenAutorizacion> buscarResumenAutorizacion(CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda);

    public List<ReporteResumenSwDmpLog> buscarResumenSwDmpLog(CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda);

    public List<ReporteResumenTransaccionAprobadaAgencia> buscarResumenTransaccionAprobadaAgencia(CriterioBusquedaResumenTransaccionAprobadaAgencia criterioBusqueda);

    public List<ReporteResumenLogContableEmisor> buscarResumenLogContableEmisor(CriterioBusquedaResumenLogContableEmisor criterioBusqueda);

    public List<ReporteResumenLogContableReceptor> buscarResumenLogContableReceptor(CriterioBusquedaResumenLogContableReceptor criterioBusqueda);

    public void buscarResumenLogContableEmisorExportacion(List<ReporteResumenLogContableEmisor> reporte, CriterioBusquedaResumenLogContableEmisor criterioBusqueda, HttpServletRequest request, HttpServletResponse response, List<ConceptoComision> conceptosComision) throws IOException;
    
    public void descargarReporteResumenSwDmpLog(List<ReporteResumenSwDmpLog> reporte, CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda, HttpServletRequest request, HttpServletResponse response) throws IOException;

}