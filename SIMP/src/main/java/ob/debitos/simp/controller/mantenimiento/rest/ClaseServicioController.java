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
import ob.debitos.simp.model.mantenimiento.ClaseServicio;
import ob.debitos.simp.service.IClaseServicioService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.CodigoMembresia;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.CLS_SERV, datos = Dato.CLASE_SERVICIO)
@RequestMapping("/claseServicio")
public @RestController class ClaseServicioController
{

    private @Autowired IClaseServicioService claseServicioService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CLSSERV', '2')")
    @GetMapping(params = "accion=buscarTodosMemb")
    public List<ClaseServicio> buscarTodosConMembresia()
    {
        return claseServicioService.buscarTodosConMembresia();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_CLSSERV', '2')")
    @GetMapping("/membresia/{codigoMembresia}")
    public List<ClaseServicio> buscarPorCodigoMembresia(@CodigoMembresia(existe = true) @PathVariable String codigoMembresia)
    {
        return claseServicioService.buscarPorCodigoMembresia(codigoMembresia);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CLSSERV', '1')")
    @PostMapping
    public ResponseEntity<?> registrarClaseServicio(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody ClaseServicio claseServicio, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        claseServicioService.registrarClaseServicio(claseServicio);
        return ResponseEntity.ok(claseServicioService.buscarPorCodigoClaseServicioCodigoMembresia(claseServicio.getCodigoClaseServicio(), claseServicio.getCodigoMembresia()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CLSSERV', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarClaseServicio(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody ClaseServicio claseServicio, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        claseServicioService.actualizarClaseServicio(claseServicio);
        return ResponseEntity.ok(claseServicioService.buscarPorCodigoClaseServicioCodigoMembresia(claseServicio.getCodigoClaseServicio(), claseServicio.getCodigoMembresia()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CLSSERV', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarClaseServicio(@Validated(ISecuenciaValidacionEliminacion.class) @RequestBody ClaseServicio claseServicio, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        claseServicioService.eliminarClaseServicio(claseServicio);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}