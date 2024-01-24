package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaConciliacionResumenSaldos;
import ob.debitos.simp.model.reporte.ReporteMoneda;

public interface IReporteConciliacionResumenSaldosMapper {
	
	public List<ReporteMoneda> buscarResumenPorCriterios(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda);
	
}
