package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteConciliacionSaldosDiariosMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaConciliacionSaldos;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenContable;
import ob.debitos.simp.model.reporte.ReporteConciliacionSaldos;
import ob.debitos.simp.service.IReporteConciliacionSaldosDiariosService;

@Service
public class ReporteConciliacionSaldosDiariosService implements IReporteConciliacionSaldosDiariosService {
	
	private @Autowired IReporteConciliacionSaldosDiariosMapper reporteConciliacionSaldosDiariosMapper;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteConciliacionSaldos> buscarResumenDiarioPorCriterios(CriterioBusquedaConciliacionSaldos criterioBusqueda) {
		return this.reporteConciliacionSaldosDiariosMapper.buscarResumenDiarioPorCriterios(criterioBusqueda);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteConciliacionSaldos> buscarResumenMensualPorCriterios(CriterioBusquedaConciliacionSaldos criterioBusqueda) {
		return this.reporteConciliacionSaldosDiariosMapper.buscarResumenMensualPorCriterios(criterioBusqueda);
	}

}
