package ob.debitos.simp.model.tarjetas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuxLotes
{

    private String verbo;
    private Integer valor;
    private Boolean afiliaciones;
    private Boolean recargaDebito;
    private Boolean bloqueos;

}
