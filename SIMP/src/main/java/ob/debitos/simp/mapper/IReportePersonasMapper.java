package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaReportePersonas;
import ob.debitos.simp.model.reporte.ReporteMoneda;


public interface IReportePersonasMapper {
	public List<ReporteMoneda> obtenerReportePersonas(CriterioBusquedaReportePersonas criterioBusquedaReportePersonas);
}
