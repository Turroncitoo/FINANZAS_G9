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
import ob.debitos.simp.model.mantenimiento.RespuestaConcilUba;
import ob.debitos.simp.service.IRptaConcilUbaService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.RPTA_CONCIL_UBA, datos = Dato.RPTA_CONCIL_UBA)
@RequestMapping("/rptaConcilUba")
public @RestController class RptaConcilUbaController
{

    private @Autowired IRptaConcilUbaService rptaConcilUbaService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_RPTACONCILUBA', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<RespuestaConcilUba> buscarTodos()
    {
        return rptaConcilUbaService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_RPTACONCILUBA', '1')")
    @PostMapping
    public ResponseEntity<?> registrarRptaConcilUba(@Validated({ Default.class, IRegistro.class }) @RequestBody RespuestaConcilUba rptaConcilUba, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        rptaConcilUbaService.registrarRptaConcilUba(rptaConcilUba);
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_RPTACONCILUBA', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarRptaConcilUba(@Validated({ Default.class, IActualizacion.class }) @RequestBody RespuestaConcilUba rptaConcilUba, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        rptaConcilUbaService.actualizarRptaConcilUba(rptaConcilUba);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_RPTACONCILUBA', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarRptaConcilUba(@Validated(IActualizacion.class) @RequestBody RespuestaConcilUba rptaConcilUba, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        rptaConcilUbaService.eliminarRptaConcilUba(rptaConcilUba);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}