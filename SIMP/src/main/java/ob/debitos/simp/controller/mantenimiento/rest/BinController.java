package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.mantenimiento.Bin;
import ob.debitos.simp.service.IBinService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.BIN, datos = Dato.BIN)
@RequestMapping("/bin")
public @RestController class BinController
{

    private @Autowired IBinService binService;

    @PreAuthorize("hasPermission('MANT_BIN', '2')")
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @GetMapping(params = "accion=buscarTodos")
    public List<Bin> buscarTodos()
    {
        return binService.buscarTodos();
    }

    @PreAuthorize("hasPermission('MANT_BIN', '2')")
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @GetMapping(value = "/{idInstitucion}")
    public List<Bin> buscarPorIdInstitucion(@PathVariable Integer idInstitucion)
    {
        return binService.buscarPorIdInstitucion(idInstitucion);
    }

    @PreAuthorize("hasPermission('MANT_BIN', '2')")
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @GetMapping(params = "accion=buscar")
    public List<Bin> buscarPorMembresiaTPP(@RequestParam(name = "membresia") String codigoMembresia)
    {
        return binService.buscarPorMembresia(codigoMembresia);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_BIN','2')")
    @GetMapping(value = "/membresia/{codigoMembresia}/claseServicio/{codigoClaseServicio}/institucion/{idInstitucion}")
    public List<Bin> buscarPorCodigoMembresiaCodigoClaseServicio(@PathVariable String codigoMembresia, @PathVariable String codigoClaseServicio, @PathVariable Integer idInstitucion)
    {
        return binService.buscarPorCodigoMembresiaCodigoClaseServicio(codigoMembresia, codigoClaseServicio, idInstitucion);
    }

    @PreAuthorize("hasPermission('MANT_BIN', '1')")
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PostMapping
    public ResponseEntity<?> registrarBin(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody Bin bin, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        binService.registrarBin(bin);
        return ResponseEntity.ok(binService.buscarPorCodigoBIN(bin.getCodigoBIN()));
    }

    @PreAuthorize("hasPermission('MANT_BIN', '3')")
    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PutMapping
    public ResponseEntity<?> actualizarBin(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody Bin bin, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        binService.actualizarBin(bin);
        return ResponseEntity.ok(binService.buscarPorCodigoBIN(bin.getCodigoBIN()));
    }

    @PreAuthorize("hasPermission('MANT_BIN', '4')")
    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @DeleteMapping
    public ResponseEntity<?> eliminarBin(@Validated(IActualizacion.class) @RequestBody Bin bin, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        binService.eliminarBin(bin);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}