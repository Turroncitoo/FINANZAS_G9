package ob.debitos.simp.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaAutorizacion;
import ob.debitos.simp.model.reporte.ReporteAutorizacion;
import ob.debitos.simp.service.IReporteAutorizacionService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/autorizacion")
public @RestController class ReporteAutorizacionController
{
    private @Autowired IReporteAutorizacionService reporteAutorizacionService;

    @Audit(tipo = Tipo.RPT_AUT_CANAL)
    @PreAuthorize("hasPermission('RPT_AUTCANAL', '2')")
    @GetMapping(value = "/{tipoAutorizacion:canal}", params = "accion=buscar")
    public List<ReporteAutorizacion> buscarAutorizacionesCanal(
            CriterioBusquedaAutorizacion criterioBusquedaAutorizacion,
            @PathVariable String tipoAutorizacion)
    {
        criterioBusquedaAutorizacion.setTipoAutorizacion(tipoAutorizacion);
        return reporteAutorizacionService.buscarAutorizaciones(criterioBusquedaAutorizacion);
    }

    @Audit(tipo = Tipo.RPT_AUT_ID_PROC)
    @PreAuthorize("hasPermission('RPT_AUTIDPROC', '2')")
    @GetMapping(value = "/{tipoAutorizacion:procesoSwitch}", params = "accion=buscar")
    public List<ReporteAutorizacion> buscarAutorizacionesProcesoSwitch(
            CriterioBusquedaAutorizacion criterioBusquedaAutorizacion,
            @PathVariable String tipoAutorizacion)
    {
        criterioBusquedaAutorizacion.setTipoAutorizacion(tipoAutorizacion);
        return reporteAutorizacionService.buscarAutorizaciones(criterioBusquedaAutorizacion);
    }

    @Audit(tipo = Tipo.RPT_AUT_COD_RPTA)
    @PreAuthorize("hasPermission('RPT_AUTCODRPTA', '2')")
    @GetMapping(value = "/{tipoAutorizacion:respuestaSwitch}", params = "accion=buscar")
    public List<ReporteAutorizacion> buscarAutorizacionesRespuestaSwicth(
            CriterioBusquedaAutorizacion criterioBusquedaAutorizacion,
            @PathVariable String tipoAutorizacion)
    {
        criterioBusquedaAutorizacion.setTipoAutorizacion(tipoAutorizacion);
        return reporteAutorizacionService.buscarAutorizaciones(criterioBusquedaAutorizacion);
    }

}