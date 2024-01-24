package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlProgramaResumen;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlProgramaResumenDetalle;
import ob.debitos.simp.model.reporte.ReporteLogControlProgramaResumen;
import ob.debitos.simp.model.reporte.ReporteLogControlProgramaResumenDetalle;

public interface IReporteLogControlProgramaResumenMapper {
	
	public List<ReporteLogControlProgramaResumen> buscarTodos();
	public List<ReporteLogControlProgramaResumen> filtrar(CriterioBusquedaLogControlProgramaResumen criterio);
	public List<ReporteLogControlProgramaResumenDetalle> filtrarDetalle(
			CriterioBusquedaLogControlProgramaResumenDetalle criterio); 

}
