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
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlProgramaResumen;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlProgramaResumenDetalle;
import ob.debitos.simp.model.reporte.ReporteLogControlProgramaResumen;
import ob.debitos.simp.model.reporte.ReporteLogControlProgramaResumenDetalle;
import ob.debitos.simp.service.IReporteLogControlProgramaResumenService;

@Audit(tipo = Tipo.CON_LOGCON_PROGRESUMEN)
@RequestMapping("/reporte/logControlPrograma")
public @RestController class ReporteLogControlPrograma {
	
	private @Autowired IReporteLogControlProgramaResumenService reporteLogControlProgramaResumen;
	
	@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('RPT_LOGCONPROCRES', '2')")
    @GetMapping(value = "/resumen", params = "accion=buscarTodos")
    public List<ReporteLogControlProgramaResumen> buscarLogControlProgramaResumen()
    {
        return this.reporteLogControlProgramaResumen.buscarTodos();
    }
	
	@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('RPT_LOGCONPROCRES', '2')")
    @GetMapping(value = "/resumen", params = "accion=buscarPorCriterios")
    public List<ReporteLogControlProgramaResumen> filtrarLogControlProgramaResumen(CriterioBusquedaLogControlProgramaResumen criterio)
    {
        return this.reporteLogControlProgramaResumen.filtrar(criterio);
    }
	
	@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('RPT_LOGCONPROCRES', '2')")
    @GetMapping(value = "/detalle", params = "accion=buscarPorCriterios")
    public List<ReporteLogControlProgramaResumenDetalle> filtrarLogControlProgramaResumenDetalle(
    		CriterioBusquedaLogControlProgramaResumenDetalle criterio)
    {
        return this.reporteLogControlProgramaResumen.filtrarDetalle(criterio);
    }
}