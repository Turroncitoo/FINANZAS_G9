package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaIngresosYCostosPorTxN;
import ob.debitos.simp.model.reporte.ReporteIngresosYCostosPorTxN;

public interface IReporteIngresosYCostosPorTxNMapper {
	
	public List<ReporteIngresosYCostosPorTxN> buscarResumenMensualPorCriterios(CriterioBusquedaIngresosYCostosPorTxN criterioBusqueda);

}
