package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaIngresosYCostosPorTxN;
import ob.debitos.simp.model.reporte.ReporteIngresosYCostosPorTxN;

public interface IReporteIngresosYCostosPorTxNService {
	
	public List<ReporteIngresosYCostosPorTxN> buscarResumenMensualPorCriterios(CriterioBusquedaIngresosYCostosPorTxN criterioBusqueda);


}
