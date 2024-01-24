package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import ob.debitos.simp.utilitario.Regex;

@Data
public class CriterioBusquedaTransaccionObservada
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaInicioProceso;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaFinProceso;

    private Integer codigoInstitucion;

    private String idEmpresa;

    private List<String> clientes;

    @Pattern(regexp = Regex.VACIO_O_NO_SOLO_ESPACIOS_EN_BLANCO, message = "{Pattern.CriterioBusquedaTransaccionObservada.numeroTarjeta}")
    @Length(max = 20, message = "{Length.CriterioBusquedaTransaccionObservada.numeroTarjeta}")
    private String numeroTarjeta;

    @Pattern(regexp = Regex.VACIO_O_NO_SOLO_ESPACIOS_EN_BLANCO, message = "{Pattern.CriterioBusquedaTransaccionObservada.numeroTrace}")
    private String numeroTrace;

    private List<Integer> indicadoresDevolucion;

    private List<Integer> indicadoresExtorno;

    private List<Integer> indicadoresConciliacion;

    @Range(min = -1, max = 99, message = "{Range.CriterioBusquedaTransaccionObservada.idIndicadorDevolucion}")
    private Integer idIndicadorDevolucion;

    @Range(min = -1, max = 99, message = "{Range.CriterioBusquedaTransaccionObservada.idIndicadorExtorno}")
    private Integer idIndicadorExtorno;

    @Range(min = -1, max = 99, message = "{Range.CriterioBusquedaTransaccionObservada.idOrigenArchivo}")
    private Integer idOrigenArchivo;

    private String idCliente;

    private String descripcionEmpresa;

    private String descripcionCliente;

    private String descripcionOrigenArchivo;

    private String descripcionRangoFechas;

    private String descripcionIndicadorExtorno;

    private String descripcionIndicadorDevolucion;

    private String idIndicadorConciliacion;

    private String descripcionRangoFechasProceso;

    private String descripcionInstitucion;

    private String descripcionIndicadorConciliacion;

}