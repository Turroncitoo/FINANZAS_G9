package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaReporteInterfaceContable;
import ob.debitos.simp.model.reporte.ReporteInterfaceContable;

public interface IReporteInterfaceContableMapper
{

    public List<ReporteInterfaceContable> buscarInterfaceContablePorCriterio(CriterioBusquedaReporteInterfaceContable criterioBusquedaReporteInterfaceContable);

}
