package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IReporteTarjetasVendidasMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjetasVendidas;
import ob.debitos.simp.model.reporte.ReporteTarjetasVendidas;
import ob.debitos.simp.service.IReporteTarjetasVendidasService;

@Service
public class ReporteTarjetasVendidasService  implements IReporteTarjetasVendidasService{

	
	
	private @Autowired IReporteTarjetasVendidasMapper reporteTarjetasVendidasMapper;

	@Override
	public List<ReporteTarjetasVendidas> buscarResumenMensualTarjetasVendidas(
			CriterioBusquedaTarjetasVendidas criterioBusquedaTarjetasVendidas) {
		return this.reporteTarjetasVendidasMapper.buscarResumenMensualTarjetasVendidas(criterioBusquedaTarjetasVendidas);
	}




	
	

	
}
