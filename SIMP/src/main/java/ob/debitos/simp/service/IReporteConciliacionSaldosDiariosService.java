package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaConciliacionSaldos;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenContable;
import ob.debitos.simp.model.reporte.ReporteConciliacionSaldos;

public interface IReporteConciliacionSaldosDiariosService {
	
	public List<ReporteConciliacionSaldos> buscarResumenDiarioPorCriterios(CriterioBusquedaConciliacionSaldos criterioBusqueda);
	
	public List<ReporteConciliacionSaldos> buscarResumenMensualPorCriterios(CriterioBusquedaConciliacionSaldos criterioBusqueda);
}

