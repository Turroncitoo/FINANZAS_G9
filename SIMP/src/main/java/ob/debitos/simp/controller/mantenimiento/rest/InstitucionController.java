package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import javax.validation.groups.Default;

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
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.INST, datos = Dato.INSTITUCION)
@RequestMapping("/institucion")
public @RestController class InstitucionController
{

    private @Autowired IInstitucionService institucionService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_INST', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<Institucion> buscarTodos()
    {
        return institucionService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_INST', '2')")
    @GetMapping(params = "accion=buscarPorInstitucionEmpresa")
    public List<Institucion> buscarPorInstitucionEmpresa()
    {
        return institucionService.buscarPorInstitucionEmpresa();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_INST', '1')")
    @PostMapping
    public ResponseEntity<?> registrarInstitucion(@Validated({ IRegistro.class, Default.class }) @RequestBody Institucion institucion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        institucionService.registrarInstitucion(institucion);
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_INST', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarInstitucion(@Validated({ IActualizacion.class, Default.class }) @RequestBody Institucion institucion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        institucionService.actualizarInstitucion(institucion);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_INST', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarInstitucion(@Validated(IActualizacion.class) @RequestBody Institucion institucion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        institucionService.eliminarInstitucion(institucion);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}