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
import ob.debitos.simp.model.mantenimiento.ClaseTransaccion;
import ob.debitos.simp.service.IClaseTransaccionService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.CLS_TXN, datos = Dato.CLASE_TRANSACCION)
@RequestMapping("/claseTransaccion")
public @RestController class ClaseTransaccionController
{

    private @Autowired IClaseTransaccionService claseTransaccionService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CLSTXN', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<ClaseTransaccion> buscarTodos()
    {
        return claseTransaccionService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CLSTXN', '1')")
    @PostMapping
    public ResponseEntity<?> registrarClaseTransaccion(@Validated({ Default.class, IRegistro.class }) @RequestBody ClaseTransaccion claseTransaccion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        claseTransaccionService.registrarClaseTransaccion(claseTransaccion);
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CLSTXN', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarClaseTransaccion(@Validated({ Default.class, IActualizacion.class }) @RequestBody ClaseTransaccion claseTransaccion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        claseTransaccionService.actualizarClaseTransaccion(claseTransaccion);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CLSTXN', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarClaseTransaccion(@Validated(IActualizacion.class) @RequestBody ClaseTransaccion claseTransaccion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        claseTransaccionService.eliminarClaseTransaccion(claseTransaccion);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}