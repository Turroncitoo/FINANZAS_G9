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
public class TransaccionObservada
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    private Integer codigoInstitucion;

    private String descripcionInstitucion;

    private String idEmpresa;

    private String descEmpresa;

    private String idCliente;

    private String descCliente;

    private Integer idRolTransaccion;

    private String descripcionRolTransaccion;

    private Integer idOrigenArchivo;

    private String descripcionOrigenArchivo;

    private String codigoProcesoSwitch;

    private String descripcionProcesoSwitch;

    private String codigoRespuestaSwitch;

    private String descripcionCodigoRespuestaSwitch;

    private Integer idCanal;

    private String descripcionCanal;

    private Integer codigoTipoDocumento;

    private String descripcionTipoDocumento;

    private String numeroDocumento;

    private Integer idPersona;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String numeroCuenta;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    private String estado;

    private String descEstado;

    private Integer monedaCargoThb;

    private String descMonedaCargoThb;

    private Double montoCargoThb;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;

    private String fechaTransaccionCadena;

    private String horaTransaccion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaSwitch;

    private String numeroTrace;

    private Integer sinAutorizacion;

    private String autorizacion;

    private String nombreAdquirente;

    private Integer codigoMonedaTransaccion;

    private String descripcionMonedaTransaccion;

    private Double valorTransaccion;

    private Integer pendienteCompensar;

    private Integer diasPendiente;

    private Integer txnCurrencyComp;

    private String descTxnCurrencyComp;

    private Double txnAmountComp;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaCompensacion;

    private Integer idIndicadorDevolucion;

    private String descripcionIndicadorDevolucion;

    private Integer idIndicadorExtorno;

    private String descripcionIndicadorExtorno;

    private Integer idIndicadorConciliacion;

    private String descripcionIndicadorConciliacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRegistro;

    private String fechaRegistroCadena;

    private String horaRegistro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRegul;

    private String fechaRegulCadena;

    private String horaRegul;

    private String idSecuencia;

    private Integer responseCodeComp;

    private String responseCodeCompDescripcion;

    private String autorizacionAut;

    private Integer codigoRespuestaAut;

    private String descripcionCodigoRespuestaSwitchAut;

    private Integer codigoMonedaTransaccionAut;

    private String descripcionMonedaTransaccionAut;

    private Double valorTransaccionAut;

    private String descripcionEmpresa;

    private String descripcionCliente;

    private String numeroDocumentoTransaccion;

    private String nombreCompleto;

    private Integer codigoOrigen;

    private String descripcionOrigen;

    private Integer codigoTransaccion;

    private String descripcionCodigoTransaccion;

    private Double valorDiferencia;

    private Integer indicadorExtornoDevolucion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransmisionAutorizacion;

    private Integer codigoRespuestaSwitchAutorizacion;

    private String descripcionCodigoRespuestaSwitchAutorizacion;

    private Integer codigoMonedaAutorizacion;

    private String descripcionMonedaAutorizacion;

    private Double montoAutorizacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRegulacion;

    private String horaRegulacion;

    private Integer numeroDias;

    private String estadoTarjeta;

    private Integer txnCurrencyCompDescripcion;

    private String pendienteCompensarEnTexto;

    private String sinAutorizacionEnTexto;

}