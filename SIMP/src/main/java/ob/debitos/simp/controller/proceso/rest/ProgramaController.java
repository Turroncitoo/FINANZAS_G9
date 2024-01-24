package ob.debitos.simp.controller.proceso.rest;

import java.util.List;

import javax.validation.groups.Default;

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
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.service.IProgramaService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.PROG, datos = Dato.PROGRAMA)
@RequestMapping("/proceso/mantenimiento/programa")
public @RestController class ProgramaController
{

    private @Autowired IProgramaService programaService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PROG', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<Programa> buscarTodos()
    {
        return programaService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PROG', '2')")
    @GetMapping(value = "/grupo/{idGrupo}")
    public List<Programa> buscarPorGrupo(@PathVariable("idGrupo") String codigoGrupo)
    {
        return programaService.buscarPorGrupo(codigoGrupo);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PROG', '1')")
    @PostMapping
    public ResponseEntity<?> registrarPrograma(@Validated({ Default.class, IRegistro.class }) @RequestBody Programa programa, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        programaService.registrarPrograma(programa);
        return ResponseEntity.ok(programaService.buscarPorCodigo(programa.getCodigoGrupo(), programa.getCodigoPrograma(), programa.getCodigoSubModulo()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PROG', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarPrograma(@Validated({ Default.class, IActualizacion.class }) @RequestBody Programa programa, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        programaService.actualizarPrograma(programa);
        return ResponseEntity.ok(programaService.buscarPorCodigo(programa.getCodigoGrupo(), programa.getCodigoPrograma(), programa.getCodigoSubModulo()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PROG', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarPrograma(@Validated(IActualizacion.class) @RequestBody Programa programa, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        programaService.eliminarPrograma(programa);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}