package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransacciones;
import ob.debitos.simp.model.reporte.ReporteTransaccionesTarjetas;

public interface IReporteTransaccionesMapper {
	
	public List<ReporteTransaccionesTarjetas> buscarTransaccionesTarjetas(CriterioBusquedaResumenTransacciones criterioBusqueda);
}
