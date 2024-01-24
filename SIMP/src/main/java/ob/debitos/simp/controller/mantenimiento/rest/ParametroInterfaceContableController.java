package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.mantenimiento.ParametrosInterfaceContable;

import ob.debitos.simp.service.IParametroInterfaceContableService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.PARAM_INT_CONT, datos = Dato.PARAM_INT_CONT)
@RequestMapping("/parametroIntCon")
public @RestController class ParametroInterfaceContableController
{

    private @Autowired IParametroInterfaceContableService parametroInterfaceContableService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PARAMINTCONT', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<ParametrosInterfaceContable> buscarTodos()
    {
        return this.parametroInterfaceContableService.buscarTodos();
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PARAMINTCONT', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarParametroInterfaceContable(@Validated @RequestBody ParametrosInterfaceContable parametrosInterfaceContable, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroInterfaceContableService.actualizarParametroInterfaceContable(parametrosInterfaceContable);
        return ResponseEntity.ok(parametroInterfaceContableService.buscarTodos());
    }

}
