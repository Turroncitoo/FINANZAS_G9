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
import ob.debitos.simp.model.mantenimiento.CuentaContableEmisor;
import ob.debitos.simp.service.ICuentaContableEmisorService;
import ob.debitos.simp.service.excepcion.BadRequestException;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;
import ob.debitos.simp.validacion.grupo.secuencia.contabilidad.ISecuenciaValidacionEliminacionContabComision;

@Audit(tipo = Tipo.CONTAB_EMI, datos = Dato.CTA_CONTABLE_EMI)
@RequestMapping("/cuentaContableEmisor")
public @RestController class CuentaContableEmisorController
{
    private @Autowired ICuentaContableEmisorService cuentaContableEmisorService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CONTABEMI', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CuentaContableEmisor> buscarTodos()
    {
        return cuentaContableEmisorService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_CONTABEMI', '2')")
    @GetMapping(params = "accion=buscarPorEscenario")
    public List<CuentaContableEmisor> buscarConceptosCuentasPorEscenario(
            CuentaContableEmisor cuentaContableEmisor)
    {
        return cuentaContableEmisorService
                .buscarConceptosCuentasPorEscenario(cuentaContableEmisor);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CONTABEMI', '1')")
    @PostMapping
    public ResponseEntity<?> registrarCuentaContable(
            @Validated(ISecuenciaValidacionRegistro.class) @RequestBody CuentaContableEmisor cuentaContableEmisor,
            Errors error)
    {
        if (error.hasErrors())
        {
            throw new BadRequestException(
                    ValidatorUtil.obtenerMensajeValidacionError(error));
        }
        return ResponseEntity.ok(cuentaContableEmisorService
                .registrarCuentaContable(cuentaContableEmisor));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CONTABEMI', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarCuentaContable(
            @Validated(ISecuenciaValidacionActualizacion.class) @RequestBody CuentaContableEmisor cuentaContableEmisor,
            Errors error)
    {
        if (error.hasErrors())
        {
            throw new BadRequestException(
                    ValidatorUtil.obtenerMensajeValidacionError(error));
        }
        return ResponseEntity.ok(cuentaContableEmisorService
                .actualizarCuentaContable(cuentaContableEmisor));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CONTABEMI', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCuentaContable(
            @Validated(ISecuenciaValidacionEliminacionContabComision.class) @RequestBody CuentaContableEmisor cuentaContableEmisor,
            Errors error)
    {
        if (error.hasErrors())
        {
            throw new BadRequestException(
                    ValidatorUtil.obtenerMensajeValidacionError(error));
        }
        cuentaContableEmisorService
                .eliminarCuentaContable(cuentaContableEmisor);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }
}