package ob.debitos.simp.model.consulta.movimiento;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnsPreAutorizadas
{

    private Integer idInstitucion;

    private String descInstitucion;

    private String idEmpresa;

    private String descEmpresa;

    private String idCliente;

    private String descCliente;

    private String tipoOperacion;

    private String operacion;

    private String codigoSeguimiento;

    private Integer id;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    private Integer tipoDocumento;

    private String descTipoDocumento;

    private String numeroDocumento;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private Integer monedaTransaccion;

    private String descripcionMonedaTransaccion;

    private Double montoTransaccion;

    private String estado;

    private String descripcionEstado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaSolicitud;

    private String fechaSolicitudCadena;

    private String horaSolicitud;

    private String usuarioSolicitud;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaAprobacion;

    private String fechaAprobacionCadena;

    private String horaAprobacion;

    private String usuarioAprobacion;

    private Integer exito;

    private Integer numeroReintentos;

    private String idTransaccion;

    private String numeroTrace;

    private String codigoRespuesta;

    private String descripcionRespuesta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaEnvioTransaccion;

    private String fechaEnvioTransaccionCadena;

    private String horaEnvioTransaccion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRecepcionTransaccion;

    private String fechaRecepcionTransaccionCadena;

    private String horaRecepcionTransaccion;

    private Integer tiempoTotal;

    private String pathOperacion;

    private String jsonOperacion;

    String nombreCompleto;

}
