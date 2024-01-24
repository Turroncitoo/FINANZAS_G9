package ob.debitos.simp.controller.mantenimiento.rest;

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
import ob.debitos.simp.model.mantenimiento.CodigoProcSwitch;
import ob.debitos.simp.service.ICodigoProcesoSwitchService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.COD_PROC_SW, datos = Dato.COD_PROC_SWITCH)
@RequestMapping("/codigoProcesoSwitch")
public @RestController class CodigoProcesoSwitchController
{

    private @Autowired ICodigoProcesoSwitchService codigoProcesoSwitchService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PROCSW', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CodigoProcSwitch> buscarTodos()
    {
        return codigoProcesoSwitchService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PROCSW', '1')")
    @PostMapping
    public ResponseEntity<?> registrarCodigoProcesoSwitch(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody CodigoProcSwitch codigoProcesoSwitch, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoProcesoSwitchService.registrarCodigoProcesoSwitch(codigoProcesoSwitch);
        return ResponseEntity.ok(codigoProcesoSwitchService.buscarPorCodigoProcesoSwitch(codigoProcesoSwitch.getCodigoProcesoSwitch()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PROCSW', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarCodigoProcesoSwitch(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody CodigoProcSwitch codigoProcesoSwitch, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoProcesoSwitchService.actualizarCodigoProcesoSwitch(codigoProcesoSwitch);
        return ResponseEntity.ok(codigoProcesoSwitchService.buscarPorCodigoProcesoSwitch(codigoProcesoSwitch.getCodigoProcesoSwitch()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PROCSW', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCodigoProcesoSwitch(@Validated(ISecuenciaValidacionEliminacion.class) @RequestBody CodigoProcSwitch codigoProcesoSwitch, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoProcesoSwitchService.eliminarCodigoProcesoSwitch(codigoProcesoSwitch);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}