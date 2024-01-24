package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaTarjetasVendidas;
import ob.debitos.simp.model.reporte.ReporteTarjetasVendidas;

public interface IReporteTarjetasVendidasService {

	public List<ReporteTarjetasVendidas> buscarResumenMensualTarjetasVendidas(CriterioBusquedaTarjetasVendidas criterioBusquedaTarjetasVendidas);

}
