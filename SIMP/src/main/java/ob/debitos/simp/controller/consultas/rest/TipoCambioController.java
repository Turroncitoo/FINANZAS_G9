package ob.debitos.simp.controller.consultas.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoCambio;
import ob.debitos.simp.service.ITipoCambioService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/consulta/tipoCambio")
public @RestController class TipoCambioController
{

    private @Autowired ITipoCambioService tipoCambioService;

    @Audit(tipo = Tipo.CON_CAMB_VISA)
    @PreAuthorize("hasPermission('CON_CAMBVISA','ANY')")
    @GetMapping(value = "/{tipo:visa}", params = "accion=buscar")
    public @ResponseBody ResponseEntity<?> buscarTiposCambioVisa(@PathVariable String tipo, @Validated CriterioBusquedaTipoCambio criterioBusqueda, Errors error)
    {
        criterioBusqueda.setTipo(tipo);
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(tipoCambioService.buscarTiposCambio(criterioBusqueda));
    }

    @Audit(tipo = Tipo.CON_CAMB_SBS)
    @PreAuthorize("hasPermission('CON_CAMBSBS','ANY')")
    @GetMapping(value = "/{tipo:sbs}", params = "accion=buscar")
    public @ResponseBody ResponseEntity<?> buscarTiposCambioSBS(@PathVariable String tipo, @Validated CriterioBusquedaTipoCambio criterioBusqueda, Errors error)
    {
        criterioBusqueda.setTipo(tipo);
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(tipoCambioService.buscarTiposCambio(criterioBusqueda));
    }

}