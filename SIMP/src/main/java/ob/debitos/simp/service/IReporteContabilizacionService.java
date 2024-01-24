package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.model.criterio.CriterioBusquedaContabilizacion;
import ob.debitos.simp.model.mantenimiento.ConceptoComision;
import ob.debitos.simp.model.reporte.ReporteContabilizacion;

public interface IReporteContabilizacionService
{

    public List<ReporteContabilizacion> buscarContabilizacionesEmisorMovimientoPorCriterio(CriterioBusquedaContabilizacion criterioBusqueda);

    public List<ReporteContabilizacion> buscarContabilizacionesEmisorComisionesPorCriterio(CriterioBusquedaContabilizacion criterioBusqueda);

    public List<ReporteContabilizacion> buscarContabilizacionesEnCuentaDefault(CriterioBusquedaContabilizacion criterioBusqueda);

    public List<ReporteContabilizacion> buscarContabilizacionesMiscelaneoPorCriterio(CriterioBusquedaContabilizacion criterioBusqueda);

    public void buscarContabilizacionesEmisorComisionesExportacion(List<ReporteContabilizacion> reporte, CriterioBusquedaContabilizacion criterio, HttpServletRequest request, HttpServletResponse response, List<ConceptoComision> conceptosComision) throws IOException;

    public void buscarContabilizacionesMovimientosExportacion(List<ReporteContabilizacion> reporte, CriterioBusquedaContabilizacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException;
    
    public void descargarReporteCuentaDefault(List<ReporteContabilizacion> reporte, CriterioBusquedaContabilizacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException;
    
    public void descargarReporteContabilizacionesMiscelaneos(List<ReporteContabilizacion> reporte, CriterioBusquedaContabilizacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
