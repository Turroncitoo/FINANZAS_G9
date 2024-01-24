package ob.debitos.simp.controller.proceso.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlPrograma;
import ob.debitos.simp.model.proceso.LogControlPrograma;
import ob.debitos.simp.service.ILogControlProgramaService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.CON_LOG_PROC_AUTOM)
public @RestController class LogControlProgramaController
{

    private @Autowired ILogControlProgramaService logControlProgramaService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_LOGPROCAUTOM', '2')")
    @GetMapping(value = "/proceso/logControlPrograma", params = "accion=buscar")
    public List<LogControlPrograma> buscarPorCriterioBusqueda(@Validated CriterioBusquedaLogControlPrograma criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return logControlProgramaService.buscarPorCriterioBusqueda(criterioBusqueda);
    }

}