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
import ob.debitos.simp.model.criterio.CriterioBusquedaReportePersonas;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.service.IReportePersonasService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/resumen/personas/")
public @RestController class ReportePersonasController {
	private @Autowired IReportePersonasService reportePersonasService;
	
	@Audit(tipo = Tipo.RPT_PERSONAS)
	@PreAuthorize("hasPermission('RPT_PERSONAS', '2')")
    @GetMapping(value = "resumen", params = "accion=buscar")
	public List<ReporteMoneda> obtenerReportePersonas(CriterioBusquedaReportePersonas criterioBusquedaReportePersonas){
		return this.reportePersonasService.obtenerReportePersonas(criterioBusquedaReportePersonas);
	}
}
