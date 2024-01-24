package ob.debitos.simp.model.proceso;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogControlPrograma
{
    private int idSecuencia;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaEjecucion;
    private Integer estado;
    private String mensaje;
    private int registro; 
    private String codigoGrupo;
    private String descripcionProcesoAutomatico;
    private String codigoPrograma;
    private String descripcionPrograma;
    private String codigoSubModulo;
    private Integer codigoInstitucion;
    private String descripcionInstitucion;
    private String horaInicio;
    private String horaFin;
    private String tiempoProceso;
    private String tipoEjecucion;
    private String descripcionTipoEjecucion;
    private String idUsuario;
    private boolean cargaMultiple;
    private boolean ejecucionDetallada;
}