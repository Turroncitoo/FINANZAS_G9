package ob.debitos.simp.model.proceso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogControlProgramaDetalle
{
    private int idItem;
    private String descripcionItem;
    private String mensaje;
    private int estado;

    /**
     * Representa el Log Control Programa al cual pertenece
     */
    private int idSecuenciaLogControlPrograma;
}