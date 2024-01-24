package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableEmisor;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableReceptor;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenAutorizacionSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransaccionAprobadaAgencia;
import ob.debitos.simp.model.reporte.ReporteResumenAutorizacion;
import ob.debitos.simp.model.reporte.ReporteResumenLogContableReceptor;
import ob.debitos.simp.model.reporte.ReporteResumenSwDmpLog;
import ob.debitos.simp.model.reporte.ReporteResumenLogContableEmisor;
import ob.debitos.simp.model.reporte.ReporteResumenTransaccionAprobadaAgencia;

public interface IReporteResumenMovimientoMapper
{

    public List<ReporteResumenAutorizacion> buscarResumenAutorizaciones(CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda);

    public List<ReporteResumenSwDmpLog> buscarResumenSwDmpLogs(CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda);

    public List<ReporteResumenTransaccionAprobadaAgencia> buscarResumenTransaccionesAprobadasAgencia(CriterioBusquedaResumenTransaccionAprobadaAgencia criterioBusqueda);

    public List<ReporteResumenLogContableEmisor> buscarResumenLogContableEmisor(CriterioBusquedaResumenLogContableEmisor criterioBusqueda);

    public List<ReporteResumenLogContableReceptor> buscarResumenLogContableReceptor(CriterioBusquedaResumenLogContableReceptor criterioBusqueda);

}