package ob.debitos.simp.controller.consultas.rest;

import ob.debitos.simp.model.consulta.movimiento.TransaccionObservada;
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
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionObservada;
import ob.debitos.simp.service.ITxnsObservadasService;
import ob.debitos.simp.service.excepcion.BadRequestException;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.utilitario.ValidatorUtil;

import java.util.List;

@Audit(tipo = Tipo.CON_MOV_OBS, accion = Accion.Consulta)
@RequestMapping("/txnsObservadas")
public @RestController class TransaccionObservadaController
{

    private @Autowired ITxnsObservadasService txnsObservadasService;

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_MOVOBS','ANY')")
    @GetMapping(params = "accion=buscarPorCriterios")
    public List<TransaccionObservada> buscarTransaccionesObservadasPorCriterios(@Validated CriterioBusquedaTransaccionObservada criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return txnsObservadasService.buscarTransaccionesObservadasPorCriterios(criterioBusqueda);
    }

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @PreAuthorize("hasPermission('CON_MOVOBS','ANY')")
    @GetMapping(params = "accion=buscarPorTipoDocumento")
    public List<TransaccionObservada> buscarTransaccionesObservadasPorTipoDocumento(@Validated CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return txnsObservadasService.buscarTransaccionesObservadasPorTipoDocumento(criterioBusquedaTipoDocumento);
    }

}