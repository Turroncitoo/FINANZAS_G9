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
import ob.debitos.simp.model.mantenimiento.CodigoRespuestaVisa;
import ob.debitos.simp.service.ICodigoRptaVisaService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.COD_RPTA_VISA, datos = Dato.COD_RPTA_VISA)
@RequestMapping("/codigoRptaVisa")
public @RestController class CodigoRptaVisaController
{

    private @Autowired ICodigoRptaVisaService codigoRptaVisaService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_RPTAVISA','2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CodigoRespuestaVisa> buscarTodos()
    {
        return codigoRptaVisaService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_RPTAVISA','1')")
    @PostMapping
    public ResponseEntity<?> registrarCodigoRptaVisa(@Validated({ IRegistro.class, Default.class }) @RequestBody CodigoRespuestaVisa codigoRptaVisa, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoRptaVisaService.registrarCodigoRptaVisa(codigoRptaVisa);
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_RPTAVISA','3')")
    @PutMapping
    public ResponseEntity<?> actualizarCodigoRptaVisa(@Validated({ IActualizacion.class, Default.class }) @RequestBody CodigoRespuestaVisa codigoRptaVisa, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoRptaVisaService.actualizarCodigoRptaVisa(codigoRptaVisa);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_RPTAVISA','4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCodigoRptaVisa(@Validated(IActualizacion.class) @RequestBody CodigoRespuestaVisa codigoRptaVisa, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoRptaVisaService.eliminarCodigoRptaVisa(codigoRptaVisa);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}