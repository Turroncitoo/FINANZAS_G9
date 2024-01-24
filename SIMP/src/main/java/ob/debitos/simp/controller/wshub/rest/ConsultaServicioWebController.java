package ob.debitos.simp.controller.wshub.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaServicioWeb;
import ob.debitos.simp.model.prepago.wshub.ConsultaServicioWeb;
import ob.debitos.simp.model.prepago.wshub.LogControlWS;
import ob.debitos.simp.service.IConsultaServicioWebService;

@Audit(tipo = Tipo.CON_WS_SER_WEB)
@RequestMapping("/prepago/consulta")
public @RestController class ConsultaServicioWebController
{

    private @Autowired IConsultaServicioWebService consultaServicioWebService;

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('WS_CON_SERVICIO_WEB','2')")
    @GetMapping(value = "/resumen", params = "accion=buscarTodos")
    public List<ConsultaServicioWeb> buscarTodosResumen()
    {
        return this.consultaServicioWebService.buscarTodos();
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('WS_CON_SERVICIO_WEB','2')")
    @GetMapping(value = "/resumen", params = "accion=buscarPorCriterios")
    public List<ConsultaServicioWeb> buscarPorCriteriosResumen(CriterioBusquedaConsultaServicioWeb criterioBusqueda)
    {
        return this.consultaServicioWebService.buscarPorCriterios(criterioBusqueda);
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('WS_CON_SERVICIO_WEB','2')")
    @GetMapping(value = "/detalle", params = "accion=buscarPorEvento")
    public List<ConsultaServicioWeb> buscarPorCriteriosResumenDetalle(CriterioBusquedaConsultaServicioWeb criterio)
    {
        return this.consultaServicioWebService.buscarPorEvento(criterio);
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('WS_LOG_CONTROL','2')")
    @GetMapping(value = "/logControl", params = "accion=buscarEnLinea")
    public List<LogControlWS> buscarLogControlEnLinea(CriterioBusquedaConsultaServicioWeb criterio)
    {
        return this.consultaServicioWebService.buscarLogControlEnLinea(criterio);
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('WS_LOG_CONTROL','2')")
    @GetMapping(value = "/logControl", params = "accion=buscarHistorial")
    public List<LogControlWS> buscarLogControlHistorico(CriterioBusquedaConsultaServicioWeb criterio)
    {
        return this.consultaServicioWebService.buscarLogControlHistorico(criterio);
    }

}
