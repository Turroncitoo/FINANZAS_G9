package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ob.debitos.simp.mapper.IReporteConciliacionResumenSaldosMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaConciliacionResumenSaldos;
import ob.debitos.simp.model.reporte.ReporteConciliacionResumenSaldos;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.service.IReporteConciliacionResumenSaldosService;

@Service
public class ReporteConciliacionResumenSaldosService implements IReporteConciliacionResumenSaldosService {
	
	private static final String SALDOS_INICIAL="SALDOS_INICIAL";
	private static final String SALDOS_FINAL="SALDOS_FINAL";
	private static final String LIBERADAS="LIBERADAS";
	private static final String NO_COMPENSADAS="NO_COMPENSADAS";
	private static final String SEPARADOR="_";
	private @Autowired IReporteConciliacionResumenSaldosMapper reporteConciliacionResumenSaldosMapper;
	

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteMoneda> buscarResumenSaldosInicial(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda) {
		criterioBusqueda.setVerbo(SALDOS_INICIAL+SEPARADOR+criterioBusqueda.getModo());
		return this.reporteConciliacionResumenSaldosMapper.buscarResumenPorCriterios(criterioBusqueda);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteMoneda> buscarResumenSaldosFinal(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda) {
		criterioBusqueda.setVerbo(SALDOS_FINAL+SEPARADOR+criterioBusqueda.getModo());
		return this.reporteConciliacionResumenSaldosMapper.buscarResumenPorCriterios(criterioBusqueda);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteMoneda> buscarResumenLiberadas(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda) {
		criterioBusqueda.setVerbo(LIBERADAS+SEPARADOR+criterioBusqueda.getModo());
		return this.reporteConciliacionResumenSaldosMapper.buscarResumenPorCriterios(criterioBusqueda);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteMoneda> buscarResumenNoCompensadas(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda) {
		criterioBusqueda.setVerbo(NO_COMPENSADAS+SEPARADOR+criterioBusqueda.getModo());
		return this.reporteConciliacionResumenSaldosMapper.buscarResumenPorCriterios(criterioBusqueda);
	}



}
