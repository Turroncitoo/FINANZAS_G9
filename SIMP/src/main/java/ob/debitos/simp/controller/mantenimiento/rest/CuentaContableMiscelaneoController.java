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
import ob.debitos.simp.model.mantenimiento.CuentaContableMiscelaneo;
import ob.debitos.simp.service.ICuentaContableMiscelaneoService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;
import ob.debitos.simp.validacion.grupo.secuencia.contabilidad.ISecuenciaValidacionEliminacionContabComision;

@Audit(tipo = Tipo.CONTAB_MIS, datos = Dato.CTA_CONTABLE_MISC)
@RequestMapping("/cuentaContableMiscelaneo")
public @RestController class CuentaContableMiscelaneoController
{

    private @Autowired ICuentaContableMiscelaneoService cuentaContableMiscelaneoService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CONTABMIS', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CuentaContableMiscelaneo> buscarTodos()
    {
        return cuentaContableMiscelaneoService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CONTABMIS', '1')")
    @PostMapping
    public ResponseEntity<?> registrarCuentaContable(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody CuentaContableMiscelaneo cuentaContableMiscelaneo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(cuentaContableMiscelaneoService.registrarCuentaContable(cuentaContableMiscelaneo));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CONTABMIS', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarCuentaContable(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody CuentaContableMiscelaneo cuentaContableMiscelaneo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return ResponseEntity.ok(cuentaContableMiscelaneoService.actualizarCuentaContable(cuentaContableMiscelaneo));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CONTABMIS', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCuentaContable(@Validated(ISecuenciaValidacionEliminacionContabComision.class) @RequestBody CuentaContableMiscelaneo cuentaContableMiscelaneo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        cuentaContableMiscelaneoService.eliminarCuentaContable(cuentaContableMiscelaneo);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}