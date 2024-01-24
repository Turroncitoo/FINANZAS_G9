package ob.debitos.simp.model.proceso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntradaProceso
{
	private Integer idInstitucion;
    private String idPrograma;
    private String idGrupo;
    private String idUsuario;
    private Integer resultado;  
}