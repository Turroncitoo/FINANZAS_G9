package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaTransaccionLiberada
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaInicioProceso;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaFinProceso;

    private Integer codigoInstitucion;

    private String idEmpresa;

    private List<String> clientes;

    private List<String> procesos;

    private List<Integer> canales;

    private String numeroTarjeta;

    private String trace;

    private String descripcionInstitucion;

    private String descripcionEmpresa;

    private String descripcionRangoFechasProceso;

    private String descripcionCliente;

    private String descripcionCodigoProceso;

    private String descripcionCanal;

}
