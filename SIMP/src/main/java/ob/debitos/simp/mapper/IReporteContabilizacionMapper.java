package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaContabilizacion;
import ob.debitos.simp.model.reporte.ReporteContabilizacion;

public interface IReporteContabilizacionMapper
{

    public List<ReporteContabilizacion> buscarContabilizacionesEmisorMovimientoPorCriterio(
            CriterioBusquedaContabilizacion criterioBusqueda);

    public List<ReporteContabilizacion> buscarContabilizacionesEmisorComisionesPorCriterio(
            CriterioBusquedaContabilizacion criterioBusqueda);

    public List<ReporteContabilizacion> buscarContabilizacionesEnCuentaDefault(
            CriterioBusquedaContabilizacion criterioBusqueda);

    public List<ReporteContabilizacion> buscarContabilizacionesMiscelaneoPorCriterio(
            CriterioBusquedaContabilizacion criterioBusqueda);

}
