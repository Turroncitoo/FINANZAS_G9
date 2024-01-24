package ob.debitos.simp.model.prepago.wshub;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogControlWS
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;

    private String fechaTransaccionCadena;

    private String hora;

    private Integer idInstitucion;

    private String descInstitucion;

    private String idEmpresa;

    private String descEmpresa;

    private String idCliente;

    private String descCliente;

    private String secuencia;

    private String transaccion;

    private String codigoSeguimiento;

    private String tipoDocumento;

    private String descripcionTipoDocumento;

    private String numeroDocumento;

    private String nombreCliente;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    private Integer monedaTransaccion;

    private String descripcionMonedaTransaccion;

    private Double montoTransaccion;

    private String idCanal;

    private String usuario;

    private String operacion;

    private String numeroTrace;

    private String direccionIP;

    private String tiempoUTC;

    private String exitoCliente;

    private String codigoRespuestaCliente;

    private String descripcionRespuestaCliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRequestClient;

    private String fechaRequestClientCadena;

    private String horaRequestClient;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaResponseClient;

    private String fechaResponseClientCadena;

    private String horaResponseClient;

    private Integer tiempoSIMPHub;

    private String exitoUBA;

    private String exitoUBATexto;

    private String codigoRespuestaUBA;

    private String descripcionRespuestaUBA;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRequestUBA;

    private String fechaRequestUBACadena;

    private String horaRequestUBA;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaResponseUBA;

    private String fechaResponseUBACadena;

    private String horaResponseUBA;

    private Integer tiempoRespuestaUBA;

    private String exitoSIMP;

    private String exitoSIMPTexto;

    private String codigoRespuestaSP;

    private String descripcionRespuestaSP;

    private String nombreSP;

    private Integer lineaErrorSP;

    private Integer tiempoTotal;

    private String jsonRequestClient;

    private String jsonResquestUBA;

    private String jsonResponseClient;

    private String jsonResponseUBA;

    private String objetoParametroSP;

    private String secHash;

    private String secHashSalt;

}
