package ob.debitos.simp.controller.reporte.rest;

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
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionObservada;
import ob.debitos.simp.service.ITxnsObservadasService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.RPT_CONCIL_OBS)
@RequestMapping("/reporte/conciliacionesObservadas")
public @RestController class ReporteConciliacionObservadaController
{

    private @Autowired ITxnsObservadasService txnsObservadasService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('RPT_CONCILOBS','2')")
    @GetMapping(params = "accion=buscar")
    public ResponseEntity<?> buscarConciliacionObservada(@Validated CriterioBusquedaTransaccionObservada criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(txnsObservadasService.buscarConciliacionObservada(criterioBusqueda));
    }

}