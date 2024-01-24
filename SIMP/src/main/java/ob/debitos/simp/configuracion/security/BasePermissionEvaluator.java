package ob.debitos.simp.configuracion.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import ob.debitos.simp.utilitario.StringsUtils;

@Component
public class BasePermissionEvaluator implements PermissionEvaluator
{
    @Override
    @SuppressWarnings("unchecked")
    public boolean hasPermission(Authentication authentication, Object targetDomainObject,
            Object permission)
    {
        boolean autorizado = false;
        List<CustomGrantedAuthority> autorizaciones = (List<CustomGrantedAuthority>) (Object) authentication
                .getAuthorities();
        if (permission.equals("PARENT_MENU"))
        {
            autorizado = this.verificarPermisoPorIdRecurso(autorizaciones, targetDomainObject);
        } else
        {
            autorizado = this.verificarPermisoPorIdRecursoIdAccion(autorizaciones,
                    targetDomainObject, permission);
        }
        return autorizado;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
            String targetType, Object permission)
    {
        return true;
    }

    private boolean verificarPermisoPorIdRecurso(List<CustomGrantedAuthority> autorizaciones,
            Object targetDomainObject)
    {
        int indiceAutorizacion = 0;
        boolean autorizacionEncontrada = false;
        String recursos = targetDomainObject.toString();
        while (!autorizacionEncontrada && indiceAutorizacion < autorizaciones.size())
        {
            CustomGrantedAuthority autorizacion = autorizaciones.get(indiceAutorizacion);
            if (recursos
                    .contains(StringsUtils.concatenarCadena("[", autorizacion.getAuthority(), "]")))
            {
                autorizacionEncontrada = true;
            }
            indiceAutorizacion++;
        }
        return autorizacionEncontrada;
    }

    private boolean verificarPermisoPorIdRecursoIdAccion(
            List<CustomGrantedAuthority> autorizaciones, Object targetDomainObject,
            Object permission)
    {
        int indiceAutorizacion = 0;
        int indiceAccionRecurso = 0;
        boolean autorizacionEncontrada = false;
        while (!autorizacionEncontrada && indiceAutorizacion < autorizaciones.size())
        {
            CustomGrantedAuthority autorizacion = autorizaciones.get(indiceAutorizacion);
            if (autorizacion.getAuthority().equals(targetDomainObject))
            {
                if (permission.equals("ANY"))
                {
                    autorizacionEncontrada = true;
                }
                indiceAccionRecurso = 0;
                String[] accionesRecurso = autorizacion.getAccionRecurso().split(",");
                while (!autorizacionEncontrada && indiceAccionRecurso < accionesRecurso.length)
                {
                    String accion = accionesRecurso[indiceAccionRecurso];
                    if (permission.equals(accion))
                    {
                        autorizacionEncontrada = true;
                    }
                    indiceAccionRecurso++;
                }
            }
            indiceAutorizacion++;
        }
        return autorizacionEncontrada;
    }
}