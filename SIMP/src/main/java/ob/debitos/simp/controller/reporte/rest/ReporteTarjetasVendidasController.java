package ob.debitos.simp.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjetasVendidas;
import ob.debitos.simp.model.reporte.ReporteTarjetasVendidas;
import ob.debitos.simp.service.IReporteTarjetasVendidasService;


@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/tarjetas/vendidas")
public @RestController class ReporteTarjetasVendidasController {

	
	@Autowired
	private IReporteTarjetasVendidasService reporteTarjetasVendidasService;
	

	
	@Audit(tipo = Tipo.RPT_CONTABLE)
	@PreAuthorize("hasPermission('RPT_TARJETAS_VENDIDAS','2')")
	@GetMapping(value = "resumen", params = "accion=buscar")
	public List<ReporteTarjetasVendidas> buscarResumenTarjetasVendidasPorMes(CriterioBusquedaTarjetasVendidas criterioBusquedaTarjetasVendidas){
		
		return this.reporteTarjetasVendidasService.buscarResumenMensualTarjetasVendidas(criterioBusquedaTarjetasVendidas);
	
	}
}
