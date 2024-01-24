package ob.debitos.simp.controller.proceso.rest;

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
import ob.debitos.simp.model.proceso.ProcesoAutomatico;
import ob.debitos.simp.service.IProcesoAutomaticoService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.PROC_AUTO, datos = Dato.PROCESO_AUTOM)
@RequestMapping("/proceso/mantenimiento/procesoAutomatico")
public @RestController class ProcesoAutomaticoController
{

    private @Autowired IProcesoAutomaticoService procesoAutomaticoService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PROCAUTO', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<ProcesoAutomatico> buscarTodos()
    {
        return procesoAutomaticoService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PROCAUTO', '1')")
    @PostMapping
    public ResponseEntity<?> registrarProcesoAutomatico(@Validated({ Default.class, IRegistro.class }) @RequestBody ProcesoAutomatico procesoAutomatico, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        procesoAutomaticoService.registrarProcesoAutomatico(procesoAutomatico);
        return ResponseEntity.ok(procesoAutomaticoService.buscarPorCodigoGrupo(procesoAutomatico.getCodigoGrupo()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PROCAUTO', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarProcesoAutomatico(@Validated({ Default.class, IActualizacion.class }) @RequestBody ProcesoAutomatico procesoAutomatico, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        procesoAutomaticoService.actualizarProcesoAutomatico(procesoAutomatico);
        return ResponseEntity.ok(procesoAutomaticoService.buscarPorCodigoGrupo(procesoAutomatico.getCodigoGrupo()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PROCAUTO', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarProcesoAutomatico(@Validated(IActualizacion.class) @RequestBody ProcesoAutomatico procesoAutomatico, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        procesoAutomaticoService.eliminarProcesoAutomatico(procesoAutomatico);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}