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
import ob.debitos.simp.model.mantenimiento.CodigoTransaccion;
import ob.debitos.simp.service.ICodigoTransaccionService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.CodigoClaseTransaccion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.COD_TXN, datos = Dato.COD_TRANSACCION)
@RequestMapping("/codigoTransaccion")
public @RestController class CodigoTransaccionController
{

    private @Autowired ICodigoTransaccionService codigoTransaccionService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CODTXN', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CodigoTransaccion> buscarTodos()
    {
        return codigoTransaccionService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_CODTXN', '2')")
    @GetMapping("/claseTransaccion/{codigoClaseTransaccion}")
    public List<CodigoTransaccion> buscarPorCodigoClaseTransaccion(@CodigoClaseTransaccion(existe = true) @PathVariable int codigoClaseTransaccion)
    {
        return codigoTransaccionService.buscarPorCodigoClaseTransaccion(codigoClaseTransaccion);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CODTXN', '1')")
    @PostMapping
    public ResponseEntity<?> registrarCodigoTransaccion(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody CodigoTransaccion codigoTransaccion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoTransaccionService.registrarCodigoTransaccion(codigoTransaccion);
        return ResponseEntity.ok(codigoTransaccionService.buscarPorCodigoTransaccionCodigoClaseTransaccion(codigoTransaccion.getCodigoTransaccion(), codigoTransaccion.getCodigoClaseTransaccion()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CODTXN', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarCodigoTransaccion(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody CodigoTransaccion codigoTransaccion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoTransaccionService.actualizarCodigoTransaccion(codigoTransaccion);
        return ResponseEntity.ok(codigoTransaccionService.buscarPorCodigoTransaccionCodigoClaseTransaccion(codigoTransaccion.getCodigoTransaccion(), codigoTransaccion.getCodigoClaseTransaccion()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CODTXN', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCodigoTransaccion(@Validated(ISecuenciaValidacionEliminacion.class) @RequestBody CodigoTransaccion codigoTransaccion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoTransaccionService.eliminarCodigoTransaccion(codigoTransaccion);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}