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
public class CriterioBusquedaConsultaServicioWeb
{

    //Actualizacion

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccionInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccionFin;

    private Integer idInstitucion;

    private String idEmpresa;

    private List<String> clientes;

    private List<String> operaciones;

    private String numeroTarjeta;

    private String codigoSeguimiento;

    private String numeroDocumento;

    private String direccionIP;

    private String exitoCliente;

    private String exitoUBA;

    private String exitoSIMP;

    private String descripcionRangoFechas;

    private String descripcionInstitucion;

    private String descripcionEmpresa;

    private String descripcionCliente;

    private String descripcionOperacion;

    private String descripcionExitoCliente;

    private String descripcionExitoUBA;

    private String descripcionExitoSIMP;

    //--//

    private Integer idEvento;

    private String nombreServicio;

    private Integer secuencia;

    private String fechaInicioIN;

    private String fechaFinIN;

    private String fechaInicioOUT;

    private String fechaFinOUT;

    private Integer UBAErrorCode;

    private Integer localErrorCode;

    private Integer extornada;

    //Nuevo
    private Integer numeroSecuencia;

    private Integer modo;

    private String ip;

    private String usuario;

    private String numeroTrace;

    private String descripcionRangosFechas;

    private String nombreCliente;

    private String transaccion;

}