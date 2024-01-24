package ob.debitos.simp.service.impl.seguridad;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.mapper.IUsuarioMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.MensajeValidacion;
import ob.debitos.simp.model.seguridad.Autenticacion;
import ob.debitos.simp.model.seguridad.Usuario;
import ob.debitos.simp.service.IUsuarioService;
import ob.debitos.simp.service.excepcion.BadRequestException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.Verbo;

@Service
@PropertySource("classpath:admin.properties")
public class UsuarioService extends MantenibleService<Usuario> implements IUsuarioService
{
    private static final String LOGIN = "LOGIN";

    private IUsuarioMapper usuarioMapper;
    private @Autowired Environment env;
    private @Autowired PasswordEncoder passwordEnconder;

    public UsuarioService(@Qualifier("IUsuarioMapper") IMantenibleMapper<Usuario> mapper)
    {
        super(mapper);
        this.usuarioMapper = (IUsuarioMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Usuario> buscarTodos()
    {
        return this.buscar(new Usuario(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Usuario buscarPorIdUsuarioParaInicioSesion(String idUsuario)
    {
        Usuario usuario = Usuario.builder().idUsuario(idUsuario).build();
        List<Usuario> usuarios = this.buscar(usuario, LOGIN);
        return usuarios.stream().findFirst().orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer obtenerCantidadDiasCaducidadContrasenia(String usuario)
    {
        return this.usuarioMapper.obtenerCantidadDiasCaducidadContrasenia(usuario);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean esContraseniaCorrectaPorIdUsuario(String idUsuario, String contrasenia)
    {
        List<Usuario> usuarios = obtenerPasswordPorIdUsuario(idUsuario);
        Preconditions.checkElementIndex(0, usuarios.size(), ConstantesExcepciones.USUARIO_ACTUAL_NO_ENCONTRADO);
        Usuario usuario = usuarios.get(0);
        Preconditions.checkNotNull(usuario.getContraseniaEncriptada(), String.format(ConstantesExcepciones.CONTRASENIA_NO_ENCONTRADO, idUsuario));
        return this.passwordEnconder.matches(contrasenia, usuario.getContraseniaEncriptada());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Usuario> obtenerPasswordPorIdUsuario(String idUsuario)
    {
        Usuario usuario = Usuario.builder().idUsuario(idUsuario).build();
        return this.buscar(usuario, Verbo.GET_PASS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeUsuario(String idUsuario)
    {
        return !buscarPorIdUsuario(idUsuario).isEmpty();
    }

    @Override
    public boolean esUsuarioAdmin(String idUsuario)
    {
        boolean esUsuarioAdmin = false;
        String idUsuarioAdmin = this.env.getProperty("seguridad.usuario.admin");
        Preconditions.checkNotNull(idUsuarioAdmin, ConstantesExcepciones.USUARIO_ADMIN_NO_ENCONTRADO);
        if (idUsuario.equalsIgnoreCase(idUsuarioAdmin))
        {
            esUsuarioAdmin = true;
        }
        return esUsuarioAdmin;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Usuario> buscarPorIdUsuario(String idUsuario)
    {
        Usuario usuario = Usuario.builder().idUsuario(idUsuario).build();
        return this.buscar(usuario, Verbo.GET);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarUsuario(Usuario usuario)
    {
        String contraseniaEncriptada = this.passwordEnconder.encode(usuario.getContrasenia());
        usuario.setContraseniaEncriptada(contraseniaEncriptada);
        this.registrar(usuario);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarUsuario(Usuario usuario)
    {
        if (usuario.isCambioContrasenia())
        {
            validarIgualdadContrasenias(usuario.getContrasenia(), usuario.getContraseniaConfirmacion());
            String contraseniaEncriptada = this.passwordEnconder.encode(usuario.getContrasenia());
            usuario.setContraseniaEncriptada(contraseniaEncriptada);
        }
        this.actualizar(usuario);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarUsuario(Usuario usuario)
    {
        String idUsuario = usuario.getIdUsuario();
        Validate.isTrue(!esUsuarioAdmin(idUsuario), ConstantesExcepciones.NO_PERMITIDA_ELIMINACION_USUARIO_ADMIN, idUsuario);
        Validate.isTrue(!idUsuario.equals(SecurityContextFacade.obtenerNombreUsuario()), ConstantesExcepciones.AUTOELIMINACION_USUARIO, idUsuario);
        this.eliminar(usuario);
    }

    public void validarIgualdadContrasenias(String contrasenia, String contraseniaConfirmacion)
    {
        if (!contrasenia.equals(contraseniaConfirmacion))
        {
            throw new BadRequestException(Arrays.asList(new MensajeValidacion(ConstantesGenerales.CAMPO_CONTRASENIA_CONFIRMACION, ConstantesGenerales.CONTRASENIAS_NO_COINCIDEN)));
        }
    }

    public boolean autenticar(Autenticacion auth)
    {
        boolean autenticado = false;
        String idUsuario = SecurityContextFacade.obtenerNombreUsuario();
        Usuario usuario = this.buscarPorIdUsuarioParaInicioSesion(idUsuario);
        if (usuario != null && passwordEnconder.matches(auth.getPassword(), usuario.getContrasenia()))
        {
            autenticado = true;
        }
        return autenticado;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean puedeVisualizarPAN(String idUsuario)
    {
        List<Usuario> usuarios = this.buscarPorIdUsuario(idUsuario);
        Validate.notEmpty(usuarios, "No se encontró al usuario %s", idUsuario);
        return usuarios.get(0).isVisualizaPAN();
    }
    
}