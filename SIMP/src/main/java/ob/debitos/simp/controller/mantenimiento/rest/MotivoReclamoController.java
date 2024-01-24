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
import ob.debitos.simp.model.mantenimiento.MotivoReclamo;
import ob.debitos.simp.service.IMotivoReclamoService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.MOT_RECLAMO, datos = Dato.MOTIVO_RECLAMO)
@RequestMapping("/motivoReclamo")
public @RestController class MotivoReclamoController
{

    private @Autowired IMotivoReclamoService motivoReclamoService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_MOTRECLAMO', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<MotivoReclamo> buscarTodos()
    {
        return motivoReclamoService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_MOTRECLAMO', '1')")
    @PostMapping
    public ResponseEntity<?> registrarMotivoReclamo(@Validated({ Default.class, IRegistro.class }) @RequestBody MotivoReclamo motivoReclamo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        motivoReclamoService.registrarMotivoReclamo(motivoReclamo);
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_MOTRECLAMO', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarMotivoReclamo(@Validated({ Default.class, IActualizacion.class }) @RequestBody MotivoReclamo motivoReclamo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        motivoReclamoService.actualizarMotivoReclamo(motivoReclamo);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_MOTRECLAMO', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarMotivoReclamo(@Validated(IActualizacion.class) @RequestBody MotivoReclamo motivoReclamo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        motivoReclamoService.eliminarMotivoReclamo(motivoReclamo);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}