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
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteResumenRecargas;
import ob.debitos.simp.model.reporte.ReporteResumenRecargas;
import ob.debitos.simp.service.IReporteResumenRecargasService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/resumen/recargas/")
public @RestController class ReporteResumenRecargasController {
	private @Autowired IReporteResumenRecargasService reporteResumenRecargasService;
	
	@Audit(tipo = Tipo.RPT_RECARGAS)
	@PreAuthorize("hasPermission('RPT_RECARGAS', '2')")
    @GetMapping(value = "resumen", params = "accion=buscar")
	public List<ReporteResumenRecargas> obtenerReporteResumenRecargar(CriterioBusquedaReporteResumenRecargas criterioBusquedaReporteResumenRecargas){
		return this.reporteResumenRecargasService.obtenerReporteResumenRecargar(criterioBusquedaReporteResumenRecargas);
	}
}
