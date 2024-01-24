package ob.debitos.simp.model.proceso;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.IdPrograma;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdPrograma(existe = true)
public class ControlEjecucionPrograma
{
    private String codigoGrupo;
    private String codigoPrograma;
    private String codigoSubModulo;
    private int estado;
    private int registros;
    private String mensaje;
    private long tiempoEjecucionInicial;
    private long tiempoEjecucionFinal;
    private String procedimiento;
    private Date fechaProceso;
    private List<LogControlProgramaDetalle> logControlProgramaDetalles;
    private Integer idInstitucion;
}