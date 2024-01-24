package ob.debitos.simp.model.tarifario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsquemaTar
{
    private int codigoEsquema;
    private String descripcion;
}
