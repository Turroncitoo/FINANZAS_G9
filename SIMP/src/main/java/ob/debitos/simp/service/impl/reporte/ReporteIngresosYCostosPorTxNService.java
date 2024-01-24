package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteIngresosYCostosPorTxNMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaIngresosYCostosPorTxN;
import ob.debitos.simp.model.reporte.ReporteIngresosYCostosPorTxN;
import ob.debitos.simp.service.IReporteIngresosYCostosPorTxNService;

@Service
public class ReporteIngresosYCostosPorTxNService implements IReporteIngresosYCostosPorTxNService{
	
	private @Autowired IReporteIngresosYCostosPorTxNMapper reporteIngresosYCostosPorTxNMapper;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteIngresosYCostosPorTxN> buscarResumenMensualPorCriterios(CriterioBusquedaIngresosYCostosPorTxN criterioBusqueda) {
		return this.reporteIngresosYCostosPorTxNMapper.buscarResumenMensualPorCriterios(criterioBusqueda);
	}

}
