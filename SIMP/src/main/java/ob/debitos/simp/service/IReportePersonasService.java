package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaReportePersonas;
import ob.debitos.simp.model.reporte.ReporteMoneda;


public interface IReportePersonasService {
	public List<ReporteMoneda> obtenerReportePersonas(CriterioBusquedaReportePersonas criterioBusquedaReportePersonas);
}
