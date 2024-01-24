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
import ob.debitos.simp.model.mantenimiento.CodigoRespuestaSwitch;
import ob.debitos.simp.service.ICodigoRptaSwitchService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.RPTA_SW, datos = Dato.COD_RPTA_SWITCH)
@RequestMapping("/codigoRptaSwitch")
public @RestController class CodigoRptaSwitchController
{

    private @Autowired ICodigoRptaSwitchService codigoRptaSwitchService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_RPTASW', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CodigoRespuestaSwitch> buscarTodos()
    {
        return codigoRptaSwitchService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_RPTASW', '1')")
    @PostMapping
    public ResponseEntity<?> registrarCodigoRptaSwitch(@Validated({ IRegistro.class, Default.class }) @RequestBody CodigoRespuestaSwitch codigoRptaSwitch, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoRptaSwitchService.registrarCodigoRptaSwitch(codigoRptaSwitch);
        return ResponseEntity.ok(codigoRptaSwitchService.buscarPorCodigoRespuestaSwitch(codigoRptaSwitch.getCodigoRespuestaSwitch()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_RPTASW', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarCodigoRptaSwitch(@Validated({ IActualizacion.class, Default.class }) @RequestBody CodigoRespuestaSwitch codigoRptaSwitch, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoRptaSwitchService.actualizarCodigoRptaSwitch(codigoRptaSwitch);
        return ResponseEntity.ok(codigoRptaSwitchService.buscarPorCodigoRespuestaSwitch(codigoRptaSwitch.getCodigoRespuestaSwitch()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_RPTASW', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCodigoRptaSwitch(@Validated(IActualizacion.class) @RequestBody CodigoRespuestaSwitch codigoRptaSwitch, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoRptaSwitchService.eliminarCodigoRptaSwitch(codigoRptaSwitch);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}