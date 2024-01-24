package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransacciones;
import ob.debitos.simp.model.reporte.ReporteTransaccionesTarjetas;

public interface IReporteTransaccionesService {
	
	public List<ReporteTransaccionesTarjetas> buscarTransaccionesTarjetas(CriterioBusquedaResumenTransacciones criterioBusqueda);
}
