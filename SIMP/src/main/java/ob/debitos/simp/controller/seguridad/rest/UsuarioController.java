package ob.debitos.simp.controller.seguridad.rest;

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
import ob.debitos.simp.model.seguridad.Autenticacion;
import ob.debitos.simp.model.seguridad.Usuario;
import ob.debitos.simp.service.IUsuarioService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.USUARIO, datos = Dato.USUARIO)
@RequestMapping("/seguridad/usuario")
public @RestController class UsuarioController
{
    private @Autowired IUsuarioService usuarioService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_USUARIO', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<Usuario> buscarTodos()
    {
        return this.usuarioService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_USUARIO', '1')")
    @PostMapping
    public List<Usuario> registrarUsuario(
            @Validated({ IRegistro.class, Default.class }) @RequestBody Usuario usuario,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.usuarioService.registrarUsuario(usuario);
        return this.usuarioService.buscarPorIdUsuario(usuario.getIdUsuario());
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_USUARIO', '3')")
    @PutMapping
    public List<Usuario> actualizarUsuario(
            @Validated({ IActualizacion.class, Default.class }) @RequestBody Usuario usuario,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.usuarioService.actualizarUsuario(usuario);
        return this.usuarioService.buscarPorIdUsuario(usuario.getIdUsuario());
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_USUARIO', '4')")
    @DeleteMapping
    public String eliminarUsuario(@Validated(IActualizacion.class) @RequestBody Usuario usuario,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.usuarioService.eliminarUsuario(usuario);
        return ConstantesGenerales.ELIMINACION_EXITOSA;
    }
    
    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PostMapping(value ="/auth")
    public ResponseEntity<?> autenticar(@RequestBody Autenticacion auth){
    	return ResponseEntity.ok(this.usuarioService.autenticar(auth));
    }
}