package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaConciliacionSaldos;
import ob.debitos.simp.model.reporte.ReporteConciliacionSaldos;

public interface IReporteConciliacionSaldosDiariosMapper {
	
	public List<ReporteConciliacionSaldos> buscarResumenDiarioPorCriterios(CriterioBusquedaConciliacionSaldos criterioBusqueda);
	
	public List<ReporteConciliacionSaldos> buscarResumenMensualPorCriterios(CriterioBusquedaConciliacionSaldos criterioBusqueda);
}
