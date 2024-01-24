package ob.debitos.simp.controller.consultas.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionNoConciliada;
import ob.debitos.simp.service.ILogAutorizacionesService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.CON_MOV_AUT, accion = Accion.Consulta, comentario = Comentario.ConsultaNoConciliada)
@RequestMapping("/logautorizaciones")
public @RestController class LogAutorizacionesController
{

    private @Autowired ILogAutorizacionesService logAutorizacionesService;

    @Audit
    @PreAuthorize("hasPermission('CON_MOVNOCONCIL','ANY')")
    @GetMapping(params = "accion=buscarPorCriterios")
    public ResponseEntity<?> buscarAutorizacionesNoConciliadasPorCriterios(@Validated CriterioBusquedaTransaccionNoConciliada criterioBusquedaTransaccionNoConciliada, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(logAutorizacionesService.buscarAutorizacionesNoConciliadasPorCriterios(criterioBusquedaTransaccionNoConciliada));
    }

    @Audit
    @PreAuthorize("hasPermission('CON_MOVNOCONCIL','ANY')")
    @GetMapping(params = "accion=buscarPorDia")
    public ResponseEntity<?> buscarAutorizacionesNoConciliadasPorDia(@Validated CriterioBusquedaTransaccionNoConciliada criterioBusquedaTransaccionNoConciliada, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(logAutorizacionesService.buscarAutorizacionesNoConciliadasPorDia(criterioBusquedaTransaccionNoConciliada));
    }

}