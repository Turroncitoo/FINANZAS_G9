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
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionAjuste;
import ob.debitos.simp.service.IConsultaAjusteService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.CON_MOV_AJUS, accion = Accion.Consulta)
@RequestMapping("/txnsAjustes")
public @RestController class TxnsAjustesController
{

    private @Autowired IConsultaAjusteService txnsAjustesService;

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_MOVAJUS','2')")
    @GetMapping(params = "accion=buscarPorCriterios")
    public ResponseEntity<?> buscarTransaccionesAjustePorCriterios(@Validated CriterioBusquedaTransaccionAjuste criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(txnsAjustesService.buscarTransaccionesAjustesPorCriterios(criterioBusqueda));
    }

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @PreAuthorize("hasPermission('CON_MOVAJUS','2')")
    @GetMapping(params = "accion=buscarPorTipoDocumento")
    public ResponseEntity<?> buscarTransaccionesAjustePorTipoDocumento(@Validated CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(txnsAjustesService.buscarTransaccionesAjustesPorTipoDocumento(criterioBusquedaTipoDocumento));
    }

}