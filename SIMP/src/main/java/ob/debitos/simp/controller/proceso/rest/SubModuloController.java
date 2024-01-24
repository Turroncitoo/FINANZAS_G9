package ob.debitos.simp.controller.proceso.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import ob.debitos.simp.model.proceso.SubModulo;
import ob.debitos.simp.service.ISubModuloService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;
import ob.debitos.simp.validacion.grupo.secuencia.contabilidad.ISecuenciaValidacionEliminacionContabComision;

@Audit(tipo = Tipo.SUB_MOD, datos = Dato.SUB_MODULO)
@RequestMapping("/proceso/mantenimiento/subModulo")
public @RestController class SubModuloController
{

    private @Autowired ISubModuloService subModuloService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_SUBMOD', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<SubModulo> buscarTodos()
    {
        return subModuloService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_SUBMOD', '1')")
    @PostMapping
    public ResponseEntity<?> registrarSubModulo(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody SubModulo subModulo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(subModuloService.registrarSubModulo(subModulo));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_SUBMOD', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarSubModulo(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody SubModulo subModulo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(subModuloService.actualizarSubModulo(subModulo));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_SUBMOD', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarSubModulo(@Validated(ISecuenciaValidacionEliminacionContabComision.class) @RequestBody SubModulo subModulo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        subModuloService.eliminarSubModulo(subModulo);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}