package ob.debitos.simp.configuracion.security;

import java.util.Hashtable;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;

import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.service.IAuditoriaService;
import ob.debitos.simp.service.IPoliticaSeguridadService;
import ob.debitos.simp.service.excepcion.LoginException;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Component
@PropertySource("classpath:ldap.properties")
public class CustomAuthenticationProvider implements AuthenticationProvider
{
    @Qualifier("customUserDetailsService")
    private @Autowired UserDetailsService userDetailsService;
    private @Autowired PasswordEncoder passwordEnconder;
    private @Autowired IAuditoriaService auditoriaService;
    private @Autowired Environment enviroment;
    private @Autowired IPoliticaSeguridadService politicaSeguridadService;

    @Override
    public Authentication authenticate(Authentication authentication)
    {
        String idUsuario = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        CustomUser usuario = null;
        String direccionIp = ((WebAuthenticationDetails) authentication.getDetails())
                .getRemoteAddress();
        try
        {
            usuario = (CustomUser) userDetailsService.loadUserByUsername(idUsuario);
        } catch (CannotCreateTransactionException cannotCreateTransactionException)
        {
            throw new LoginException(ConstantesExcepciones.ERROR_CONEXION_BASE_DATOS);
        }
        if (usuario == null)
        {
            auditoriaService.registrarAuditoria(Tipo.LOGIN, Comentario.UsuarioNoEncontrado,
                    Accion.Acceso, 0, idUsuario, direccionIp);
            throw new LoginException(
                    String.format(ConstantesExcepciones.USUARIO_NO_ENCONTRADO, idUsuario));
        }
        boolean autenticacionActiveDirectory = politicaSeguridadService
                .buscarAutenticacionActiveDirectory();
        if (autenticacionActiveDirectory)
        {
            try
            {
                Hashtable<String, String> env = new Hashtable<>();
                env.put(Context.INITIAL_CONTEXT_FACTORY,
                        enviroment.getProperty("ldap.contextFactory"));
                env.put(Context.PROVIDER_URL, enviroment.getProperty("ldap.url"));
                env.put(Context.SECURITY_AUTHENTICATION, enviroment.getProperty("ldap.simple"));
                env.put(Context.SECURITY_PRINCIPAL,
                        new String(enviroment.getProperty("ldap.dominio") + "\\" + idUsuario));
                env.put(Context.SECURITY_CREDENTIALS, new String(password));
                new InitialDirContext(env);
            } catch (CommunicationException communicationException)
            {
                auditoriaService.registrarAuditoria(Tipo.LOGIN, Comentario.ErrorActiveDirectory,
                        Accion.Acceso, 0, idUsuario, direccionIp);
                throw new LoginException(ConstantesExcepciones.ERROR_CONEXION_ACTIVE_DIRECTORY);
            } catch (NamingException namingException)
            {
                auditoriaService.registrarAuditoria(Tipo.LOGIN, Comentario.CredencialIncorrecta,
                        Accion.Acceso, 0, idUsuario, direccionIp);
                throw new LoginException(ExcepcionUtil.traducirMensajeDesdeMensajeErrorLdap(
                        namingException.getMessage(), idUsuario));
            }
        } else
        {
            if (!usuario.isEnabled())
            {
                auditoriaService.registrarAuditoria(Tipo.LOGIN, Comentario.NoActivo, Accion.Acceso,
                        0, idUsuario, direccionIp);
                throw new LoginException(
                        String.format(ConstantesExcepciones.USUARIO_NO_ACTIVO, idUsuario));
            }
            if (!passwordEnconder.matches(password, usuario.getPassword()))
            {
                auditoriaService.registrarAuditoria(Tipo.LOGIN, Comentario.CredencialIncorrecta,
                        Accion.Acceso, 0, idUsuario, direccionIp);
                throw new LoginException(ConstantesExcepciones.CONTRASENIA_INCORRECTA);
            }
        }
        auditoriaService.registrarAuditoria(Tipo.LOGIN, Comentario.CredencialCorrecta,
                Accion.Acceso, 1, idUsuario, direccionIp);
        return new UsernamePasswordAuthenticationToken(usuario, password, usuario.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}