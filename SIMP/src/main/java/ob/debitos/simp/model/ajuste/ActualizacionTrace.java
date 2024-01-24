package ob.debitos.simp.model.ajuste;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizacionTrace
{
    private String trace;
    private String idSecuencia;
    private Integer idInstitucion;
}