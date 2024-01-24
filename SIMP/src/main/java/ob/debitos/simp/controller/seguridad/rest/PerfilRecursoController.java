package ob.debitos.simp.controller.seguridad.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.seguridad.Perfil;
import ob.debitos.simp.service.IPerfilRecursoService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;

@Audit(tipo = Tipo.PERFIL_RECURSO, datos = Dato.PERFIL_RECURSO)
@RequestMapping("/seguridad/perfilRecurso")
public @RestController class PerfilRecursoController
{
    private @Autowired IPerfilRecursoService perfilRecursoService;

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PostMapping(params = "accion=asignarPermiso")
    public String asignarPermisos(@Validated(IActualizacion.class) @RequestBody Perfil perfil,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.perfilRecursoService.asignarPermisos(perfil);
        return ConstantesGenerales.ASIGNACION_PERMISO_EXITOSO;
    } 
}