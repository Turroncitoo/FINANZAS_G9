package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaTarjetasVendidas;
import ob.debitos.simp.model.reporte.ReporteTarjetasVendidas;

public interface IReporteTarjetasVendidasMapper {
	
	public List<ReporteTarjetasVendidas> buscarResumenMensualTarjetasVendidas(CriterioBusquedaTarjetasVendidas criterioBusquedaTarjetasVendidas);

}
