package ob.debitos.simp.model.consulta.movimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnsLiberadas
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    private Integer codigoInstitucion;

    private String descripcionInstitucion;

    private String idEmpresa;

    private String descEmpresa;

    private String idCliente;

    private String descCliente;

    private Integer rolTransaccion;

    private String descRolTransaccion;

    private Integer origen;

    private String descripcionOrigen;

    private Integer idCanal;

    private String descCanal;

    private String proceso;

    private String descripcionProceso;

    private String codigoRespuesta;

    private String descCodigoRespuesta;

    private String tipoMensaje;

    private String motivoLiberacion;

    private String descMotivoLiberacion;

    private Integer tipoDocumento;

    private String descTipoDocumento;

    private String numeroDocumento;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    private String estadoTarjeta;

    private String descEstadoTarjeta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;

    private String fechaTransaccionCadena;

    private String horaTransaccion;

    private Integer monedaAutorizacion;

    private String descMonedaAutorizacion;

    private Double valorAutorizacion;

    private String modoEntradaPos;

    private String descModoEntradaPos;

    private String codigoProcesamiento;

    private String trace;

    private String numeroReferencia;

    private String nombreAfiliado;

    private String cuentaFrom;

    private String cuentaTo;

    private Integer conciliacionAutorizacion;

    private String descConciliacionAutorizacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaCaptura;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaCapturaSwitch;

    private String numeroDocumentoLiberada;

    private String codigoAutorizacion;

}