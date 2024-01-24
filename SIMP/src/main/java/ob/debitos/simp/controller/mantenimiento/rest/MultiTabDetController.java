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
import ob.debitos.simp.model.mantenimiento.MultiTabDet;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.IdTabla;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.MUL_TAB_DET, datos = Dato.MULTI_TAB_DET)
@RequestMapping("/multiTabDet")
public @RestController class MultiTabDetController
{

    private @Autowired IMultiTabDetService multiTabDetService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_MULTABDET', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<MultiTabDet> buscarTodos()
    {
        return multiTabDetService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_MULTABDET', '2')")
    @GetMapping("/multiTabCab/{idTabla}")
    public List<MultiTabDet> buscarPorIdTabla(@IdTabla(existe = true) @PathVariable int idTabla)
    {
        return multiTabDetService.buscarPorIdTabla(idTabla);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_MULTABDET', '1')")
    @PostMapping
    public ResponseEntity<?> registrarMultiTabDet(@Validated({ Default.class, IRegistro.class }) @RequestBody MultiTabDet multiTabDet, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        multiTabDetService.registrarMultiTabDet(multiTabDet);
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_MULTABDET', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarMultiTabDet(@Validated({ Default.class, IActualizacion.class }) @RequestBody MultiTabDet multiTabDet, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        multiTabDetService.actualizarMultiTabDet(multiTabDet);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_MULTABDET', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarMultiTabDet(@Validated(IActualizacion.class) @RequestBody MultiTabDet multiTabDet, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        multiTabDetService.eliminarMultiTabDet(multiTabDet);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}