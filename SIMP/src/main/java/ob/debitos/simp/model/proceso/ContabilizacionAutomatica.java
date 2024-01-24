package ob.debitos.simp.model.proceso;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContabilizacionAutomatica
{
    private Integer codigoInstitucion;
    private Date fechaProceso;
    private String idEmpresa;
    private String usuario;
}
