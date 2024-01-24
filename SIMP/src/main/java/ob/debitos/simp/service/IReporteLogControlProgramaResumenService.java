package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlProgramaResumen;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlProgramaResumenDetalle;
import ob.debitos.simp.model.reporte.ReporteLogControlProgramaResumen;
import ob.debitos.simp.model.reporte.ReporteLogControlProgramaResumenDetalle;

public interface IReporteLogControlProgramaResumenService {

	public List<ReporteLogControlProgramaResumen> buscarTodos();
	public List<ReporteLogControlProgramaResumen> filtrar(CriterioBusquedaLogControlProgramaResumen criterio);
	public List<ReporteLogControlProgramaResumenDetalle> filtrarDetalle(
			CriterioBusquedaLogControlProgramaResumenDetalle criterio); 
}
