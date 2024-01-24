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
import ob.debitos.simp.model.mantenimiento.MultiTabCab;
import ob.debitos.simp.service.IMultiTabCabService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.MUL_TAB_CAB, datos = Dato.MULTI_TAB_CAB)
@RequestMapping("/multiTabCab")
public @RestController class MultiTabCabController
{

    private @Autowired IMultiTabCabService multiTabCabService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_MULTABCAB', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<MultiTabCab> buscarTodos()
    {
        return multiTabCabService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_MULTABCAB', '1')")
    @PostMapping
    public ResponseEntity<?> registrarMultiTabCab(@Validated({ Default.class, IRegistro.class }) @RequestBody MultiTabCab multiTabCab, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        multiTabCabService.registrarMultiTabCab(multiTabCab);
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_MULTABCAB', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarMultiTabCab(@Validated({ Default.class, IActualizacion.class }) @RequestBody MultiTabCab multiTabCab, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        multiTabCabService.actualizarMultiTabCab(multiTabCab);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_MULTABCAB', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarMultiTabCab(@Validated(IActualizacion.class) @RequestBody MultiTabCab multiTabCab, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        multiTabCabService.eliminarMultiTabCab(multiTabCab);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}