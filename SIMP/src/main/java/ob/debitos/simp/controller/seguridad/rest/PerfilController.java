package ob.debitos.simp.controller.seguridad.rest;

import java.util.List;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
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
import ob.debitos.simp.model.seguridad.Perfil;
import ob.debitos.simp.service.IPerfilService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.PERFIL, datos = Dato.PERFIL)
@RequestMapping("/seguridad/perfil")
public @RestController class PerfilController
{
    private @Autowired IPerfilService perfilService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PERFIL', '2') or hasPermission('MANT_PERFIL_RECURSO', '3')")
    @GetMapping(params = "accion=buscarTodos")
    public List<Perfil> buscarTodos()
    {
        return this.perfilService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @GetMapping(value = "/{idPerfil}", params = "accion=buscarRecursos")
    public List<Perfil> buscarRecursosPorIdPerfil(@PathVariable String idPerfil)
    {
        return this.perfilService.buscarRecursosPorIdPerfil(idPerfil);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PERFIL', '1')")
    @PostMapping
    public List<Perfil> registrarPerfil(
            @Validated({ IRegistro.class, Default.class }) @RequestBody Perfil perfil, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.perfilService.registrarPerfil(perfil);
        return this.perfilService.buscarPorCodigoPerfil(perfil.getIdPerfil());
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PERFIL', '3')")
    @PutMapping
    public List<Perfil> actualizarPerfil(
            @Validated({ Default.class, IActualizacion.class }) @RequestBody Perfil perfil,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.perfilService.actualizarPerfil(perfil);
        return this.perfilService.buscarPorCodigoPerfil(perfil.getIdPerfil());
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PERFIL', '4')")
    @DeleteMapping
    public String eliminarPerfil(@Validated(IActualizacion.class) @RequestBody Perfil perfil,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.perfilService.eliminarPerfil(perfil);
        return ConstantesGenerales.ELIMINACION_EXITOSA;
    }
}