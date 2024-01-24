package ob.debitos.simp.model.seguridad;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.model.mantenimiento.MultiTabDet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecRecurso
{
    private String idRecurso;
    private String descripcion;
    private int idCategoria;
    private String accionRecurso;
    private String descripcionCategoria;
    private String permiso;
    private List<MultiTabDet> acciones;
}