package ob.debitos.simp.controller.ajuste.rest;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.ajuste.ActualizacionExtornoDevolucion;
import ob.debitos.simp.model.ajuste.ActualizacionTrace;
import ob.debitos.simp.service.ITxnsObservadasService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.IDevolucion;
import ob.debitos.simp.validacion.grupo.IExtorno;

@Audit(tipo = Tipo.TRACE, datos = Dato.TRACE)
@RequestMapping("/txnsObservadas")
public @RestController class AjusteTransaccionObservadaController
{
    private @Autowired ITxnsObservadasService txnsObservadasService;

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PutMapping(params = "accion=actualizarTrace")
    public ResponseEntity<?> actualizarTrace(@RequestBody ActualizacionTrace actualizacionTrace,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        txnsObservadasService.actualizarTrace(actualizacionTrace);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PutMapping(params = "accion=actualizarIndicadorExtorno")
    public ResponseEntity<?> actualizarIndicadorExtorno(@Validated({ Default.class,
            IExtorno.class }) @RequestBody ActualizacionExtornoDevolucion actualizacionExtornoDevolucion,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        txnsObservadasService.actualizarExtornoDevolucion(actualizacionExtornoDevolucion);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXTORNO_EXITOSA);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PutMapping(params = "accion=actualizarIndicadorDevolucion")
    public ResponseEntity<?> actualizarIndicadorDevolucion(@Validated({ Default.class,
            IDevolucion.class }) @RequestBody ActualizacionExtornoDevolucion actualizacionExtornoDevolucion,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        txnsObservadasService.actualizarExtornoDevolucion(actualizacionExtornoDevolucion);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_DEVOLUCION_EXITOSA);
    }
}