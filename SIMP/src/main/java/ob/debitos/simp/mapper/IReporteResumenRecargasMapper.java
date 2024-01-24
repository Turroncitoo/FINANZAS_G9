package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaReporteResumenRecargas;
import ob.debitos.simp.model.reporte.ReporteResumenRecargas;

public interface IReporteResumenRecargasMapper {
	public List<ReporteResumenRecargas> obtenerReporteResumenRecargas(CriterioBusquedaReporteResumenRecargas criterioBusquedaReporteResumenRecargas);
}
