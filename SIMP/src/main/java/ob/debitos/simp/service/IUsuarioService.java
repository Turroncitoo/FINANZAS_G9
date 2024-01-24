package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.seguridad.Autenticacion;
import ob.debitos.simp.model.seguridad.Usuario;

public interface IUsuarioService extends IMantenibleService<Usuario>
{

    public List<Usuario> buscarTodos();

    public boolean existeUsuario(String idUsuario);

    public boolean esUsuarioAdmin(String idUsuario);

    public Usuario buscarPorIdUsuarioParaInicioSesion(String idUsuario);

    public List<Usuario> obtenerPasswordPorIdUsuario(String idUsuario);

    public List<Usuario> buscarPorIdUsuario(String idUsuario);

    public boolean esContraseniaCorrectaPorIdUsuario(String idUsuario, String contrasenia);

    public Integer obtenerCantidadDiasCaducidadContrasenia(String usuario);

    public void registrarUsuario(Usuario usuario);

    public void actualizarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);

    public void validarIgualdadContrasenias(String contrasenia, String contraseniaConfirmacion);

    public boolean autenticar(Autenticacion auth);

    public boolean puedeVisualizarPAN(String idUsuario);

}