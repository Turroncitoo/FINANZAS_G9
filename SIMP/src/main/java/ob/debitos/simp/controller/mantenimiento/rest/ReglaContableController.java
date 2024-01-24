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
import ob.debitos.simp.model.mantenimiento.ReglaContable;
import ob.debitos.simp.service.IReglaContableService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@RequestMapping("mantenimientos/recaudacionRecargas")
public @RestController class ReglaContableController
{
    private @Autowired IReglaContableService reglaContableService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CONTAC_RECAUDA', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<ReglaContable> buscarTodos()
    {
        return reglaContableService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CONTAC_RECAUDA', '1')")
    @PostMapping
    public List<ReglaContable> registrarReglaContable(@Validated({ Default.class, IRegistro.class }) @RequestBody ReglaContable reglaContable, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.reglaContableService.registrarReglaContable(reglaContable);
        return this.reglaContableService.buscarPorClave(reglaContable.getIdComercio(), reglaContable.getIdCliente());
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CONTAC_RECAUDA', '3')")
    @PutMapping
    public List<ReglaContable> actualizarReglaContable(@Validated({ Default.class, IActualizacion.class }) @RequestBody ReglaContable reglaContable, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.reglaContableService.actualizarReglaContable(reglaContable);
        return this.reglaContableService.buscarPorClave(reglaContable.getIdComercio(), reglaContable.getIdCliente());
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CONTAC_RECAUDA', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarReglaContable(@Validated({ IActualizacion.class }) @RequestBody ReglaContable reglaContable, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.reglaContableService.eliminarReglaContable(reglaContable);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}
