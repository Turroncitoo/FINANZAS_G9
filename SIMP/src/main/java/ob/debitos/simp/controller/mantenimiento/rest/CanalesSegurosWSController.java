package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.mantenimiento.CanalesSegurosWS;
import ob.debitos.simp.service.ICanalesSegurosWSService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.MANT_CANALSEGUROWS, datos = Dato.CANALSEGUROWS)
@RequestMapping("/canalesSegurosWS")

public @RestController class CanalesSegurosWSController
{

    private @Autowired ICanalesSegurosWSService canalesSegurosWSService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CANALSEGUROWS', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CanalesSegurosWS> buscarTodos()
    {
        return canalesSegurosWSService.buscarTodos();
    }

    @GetMapping(value = "/{idCanal}")
    public List<CanalesSegurosWS> buscarPorIdCanal(@PathVariable String idCanal)
    {
        return canalesSegurosWSService.buscarPorIdCanal(idCanal);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CANALSEGUROWS', '1')")
    @PostMapping
    public ResponseEntity<?> registrarCanalSeguroWS(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody CanalesSegurosWS canalesSegurosWS, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        canalesSegurosWSService.registrarCanalSeguroWS(canalesSegurosWS);
        return ResponseEntity.ok(canalesSegurosWSService.buscarPorIdCanal(canalesSegurosWS.getIdCanal()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CANALSEGUROWS', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarCanalSeguroWS(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody CanalesSegurosWS canalesSegurosWS, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        canalesSegurosWSService.actualizarCanalSeguroWS(canalesSegurosWS);
        return ResponseEntity.ok(canalesSegurosWSService.buscarPorIdCanal(canalesSegurosWS.getIdCanal()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CANALSEGUROWS', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCanalSeguroWS(@Validated(ISecuenciaValidacionEliminacion.class) @RequestBody CanalesSegurosWS canalesSegurosWS, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        canalesSegurosWSService.eliminarCanalSeguroWS(canalesSegurosWS);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}
