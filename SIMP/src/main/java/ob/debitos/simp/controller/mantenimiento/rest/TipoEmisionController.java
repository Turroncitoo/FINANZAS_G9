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
import ob.debitos.simp.model.mantenimiento.TipoEmision;
import ob.debitos.simp.service.ITipoEmisionService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.TIPO_EMISION, datos = Dato.TIPO_EMISION)
@RequestMapping("/tipoEmision")
public @RestController class TipoEmisionController
{

    private @Autowired ITipoEmisionService tipoEmisionService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_TIPEMI', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<TipoEmision> buscarTodos()
    {
        return tipoEmisionService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_TIPEMI', '2')")
    @GetMapping("/tipoEmision/{codigo}")
    public List<TipoEmision> buscarTipoEmisionPorCodigo(@PathVariable String codigo)
    {
        return tipoEmisionService.buscarTipoEmisionPorCodigo(codigo);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_TIPEMI', '1')")
    @PostMapping
    public ResponseEntity<?> registrarTipoEmision(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody TipoEmision tipoEmision, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        tipoEmisionService.registrar(tipoEmision);
        return ResponseEntity.ok(tipoEmisionService.buscarTipoEmisionPorCodigo(tipoEmision.getCodigo()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_TIPEMI', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarTipoEmision(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody TipoEmision tipoEmision, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        tipoEmisionService.actualizar(tipoEmision);
        return ResponseEntity.ok(tipoEmisionService.buscarTipoEmisionPorCodigo(tipoEmision.getCodigo()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_TIPEMI', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarTipoEmision(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody TipoEmision tipoEmision, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        tipoEmisionService.eliminar(tipoEmision);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}
