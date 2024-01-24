package ob.debitos.simp.service;

import java.util.List;
import ob.debitos.simp.model.criterio.CriterioBusquedaConciliacionResumenSaldos;
import ob.debitos.simp.model.reporte.ReporteMoneda;


public interface IReporteConciliacionResumenSaldosService {
	
	public List<ReporteMoneda> buscarResumenSaldosInicial(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda) ;
	
	public List<ReporteMoneda> buscarResumenSaldosFinal(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda);
	
	public List<ReporteMoneda> buscarResumenLiberadas(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda);
	
	public List<ReporteMoneda> buscarResumenNoCompensadas(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda);

}

