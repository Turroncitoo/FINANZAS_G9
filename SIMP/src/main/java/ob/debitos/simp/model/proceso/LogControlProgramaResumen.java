package ob.debitos.simp.model.proceso;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogControlProgramaResumen
{
    private String codigoGrupo;
    private String codigoPrograma;
    private String codigoSubModulo;
    private Date fechaProceso;
    private Integer estado;
    private Date fechaInicio;
    private String horaInicio;
    private Date fechaFin;
    private String horaFin;
    private String descripcionGrupo;
    private String descripcionPrograma;
    private String descripcionSubModulo;
    private String procedimiento;
    private int cantidadProgramasAnterioresNoEjecutados;
}

