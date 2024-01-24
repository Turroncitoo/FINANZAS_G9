package ob.debitos.simp.model.seguridad;

import lombok.Data;

@Data
public class PerfilRecurso
{
    private String idPerfil;
    private String idRecurso;
    private String descripcion;
    private String permiso;
}