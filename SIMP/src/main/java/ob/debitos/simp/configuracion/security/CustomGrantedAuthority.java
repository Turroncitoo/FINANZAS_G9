package ob.debitos.simp.configuracion.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class CustomGrantedAuthority implements GrantedAuthority
{
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String idRecurso;
    private final String accionRecurso;

    public CustomGrantedAuthority(String idRecurso, String accionRecurso)
    {
        this.idRecurso = idRecurso;
        this.accionRecurso = accionRecurso;
    }

    @Override
    public String getAuthority()
    {
        return this.idRecurso;
    }

    public String getAccionRecurso()
    {
        return this.accionRecurso;
    }

    @Override
    public String toString()
    {
        return "CustomGrantedAuthority [idRecurso=" + idRecurso + ", accionRecurso=" + accionRecurso
                + "]";
    }
}