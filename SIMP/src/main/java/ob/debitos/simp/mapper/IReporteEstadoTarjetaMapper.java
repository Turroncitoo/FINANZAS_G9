package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoTarjeta;
import ob.debitos.simp.model.reporte.ReporteEstadoTarjeta;

public interface IReporteEstadoTarjetaMapper {
	
	public List<ReporteEstadoTarjeta> buscarResumenMensualPorCriterios(CriterioBusquedaEstadoTarjeta criterioBusqueda);

}
