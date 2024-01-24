package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.Regex;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaTransaccionAjuste
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
    @Length(max = 15, message = "{Length.CriterioBusquedaTransaccionObservada.numeroTrace}")
    private String numeroVoucher;

    private String descripcionInstitucion;

    private String descripcionEmpresa;

    private String descripcionRangoFechasProceso;

    private String descripcionCliente;

}
