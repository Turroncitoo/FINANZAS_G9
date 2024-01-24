package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoTarjeta;
import ob.debitos.simp.model.reporte.ReporteEstadoTarjeta;

public interface IReporteEstadoTarjetaService {
	
	public List<ReporteEstadoTarjeta> buscarResumenMensualPorCriterios(CriterioBusquedaEstadoTarjeta criterioBusqueda);

}
