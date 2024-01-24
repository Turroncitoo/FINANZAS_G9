package ob.debitos.simp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.configuracion.security.CustomGrantedAuthority;
import ob.debitos.simp.configuracion.security.CustomUser;
import ob.debitos.simp.model.seguridad.SecRecurso;
import ob.debitos.simp.model.seguridad.Usuario;
import ob.debitos.simp.service.ISecRecursoService;
import ob.debitos.simp.service.IUsuarioService;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private @Autowired IUsuarioService usuarioService;
    private @Autowired ISecRecursoService recursoService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDetails loadUserByUsername(String login)
    {
        Usuario usuario = this.usuarioService.buscarPorIdUsuarioParaInicioSesion(login);
        CustomUser user = null;
        if (usuario != null)
        {
            user = new CustomUser(usuario.getIdUsuario(), usuario.getContrasenia(),
                    usuario.isActivo(), true, true, true,
                    buscarPorIdUsuario(this.recursoService.buscarPorIdUsuario(login)));
        }
        return user;
    }

    private List<GrantedAuthority> buscarPorIdUsuario(List<SecRecurso> recursosAsigandos)
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (SecRecurso recurso : recursosAsigandos)
        {
            authorities.add(
                    new CustomGrantedAuthority(recurso.getIdRecurso(), recurso.getAccionRecurso()));
        }
        return authorities;
    }
}