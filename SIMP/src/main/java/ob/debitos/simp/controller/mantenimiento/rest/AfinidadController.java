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
import ob.debitos.simp.model.mantenimiento.Afinidad;
import ob.debitos.simp.service.IAfinidadService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.AFIN, datos = Dato.AFINIDAD)
@RequestMapping("/afinidad")
public @RestController class AfinidadController
{

    private @Autowired IAfinidadService afinidadService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_AFINIDAD','2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<Afinidad> buscarTodos()
    {
        return afinidadService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_AFINIDAD','2')")
    @GetMapping("/{idLogo}")
    public List<Afinidad> buscarAfinidadPorIdLogo(@PathVariable String idLogo)
    {
        return afinidadService.buscarAfinidadPorLogo(idLogo);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_AFINIDAD','2')")
    @GetMapping("/{codigo}/logo/{idLogo}")
    public List<Afinidad> buscarAfinidadPorCodigoIdLogo(@PathVariable String codigo, @PathVariable String idLogo)
    {
        return afinidadService.buscarAfinidadPorCodigoIdLogo(codigo, idLogo);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_AFINIDAD','1')")
    @PostMapping
    public ResponseEntity<?> registrarAfinidad(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody Afinidad afinidad, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        afinidadService.registrarAfinidad(afinidad);
        return ResponseEntity.ok(afinidadService.buscarAfinidadPorCodigoIdLogo(afinidad.getCodigo(), afinidad.getIdLogo()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_AFINIDAD','3')")
    @PutMapping
    public ResponseEntity<?> actualizarAfinidad(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody Afinidad afinidad, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        afinidadService.actualizarAfinidad(afinidad);
        return ResponseEntity.ok(afinidadService.buscarAfinidadPorCodigoIdLogo(afinidad.getCodigo(), afinidad.getIdLogo()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_AFINIDAD','4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarAfinidad(@Validated(ISecuenciaValidacionEliminacion.class) @RequestBody Afinidad afinidad, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        afinidadService.eliminarAfinidad(afinidad);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}
