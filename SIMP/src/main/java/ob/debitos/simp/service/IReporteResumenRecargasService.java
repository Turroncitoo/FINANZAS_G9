package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.RecargasTemp;
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteResumenRecargas;
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.model.reporte.ReporteResumenRecargas;

public interface IReporteResumenRecargasService {
	public List<ReporteResumenRecargas> obtenerReporteResumenRecargar(CriterioBusquedaReporteResumenRecargas criterioBusquedaReporteResumenRecargas);
	public List<RecargasTemp> obtenerReporteRecargasFormateada(CriterioBusquedaReporteResumenRecargas criterioBusquedaReporteResumenRecargas, List<Institucion> instituciones);
	
}
