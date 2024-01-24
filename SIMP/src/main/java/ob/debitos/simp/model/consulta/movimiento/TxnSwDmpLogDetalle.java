package ob.debitos.simp.model.consulta.movimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnSwDmpLogDetalle
{
    private String descripcionMembresia;
    private String descripcionClaseServicios;
    private String descripcionRol;
    private String descripcionCanal;
    private String descripcionOrigen;
    private String descripcionClaseTransacccion;
    private String descripcionTipoMensajeOriginal;
    private String descripcionNumeroTraceOriginal;
    private String descripcionHoraTransaccionOriginal;
    private String descripcionFechaTransaccionOriginal;
    private String descripcionOriginalInstitucionAdquirente;
    private String descripcionOriginalInstitucionReenvio;
    private String descripcionTipoMensaje;
    private String descripcionStoreForward;
    @NoIdentificable(campo = "descripcionNumeroTarjeta")
    private String descripcionNumeroTarjeta;
    private String descripcionTipoTransaccion;
    private String descripcionMonedaTransacccion;
    private String descripcionMontoTransacccion;
    private String descripcionCuentaFrom;
    private String descripcionCuentaTo;
    private String descripcionFechaTransaccion;
    private String descripcionHoraTransaccion;
    private String descripcionFechaProcesado;
    private String descripcionHoraProceso;
    private String descripcionFechaExpiracion;
    private String descripcionFechaCapturaSwitch;
    private String descripcionTerminalAdquirente;
    private String descripcionAdquirente;
    private String descripcionIdentificacionAdquirente;
    private String descripcionAutorizacion;
    private String descripcionTrace;
    private String descripcionGiroNegocio;
    private String descripcionPosEntryMode;
    private String descripcionPosConditionCode;
    private String descripcionRespuesta;
    private String descripcionInstitucionSolicitante;
    private String descripcionInstitucionReenvio;
    private String descripcionInstitucionAdquirente;
    private String descripcionIdCuenta1;
    private String descripcionIdCuenta2;
    private String descripcionCuentaCargo;
    private String descripcionCuentaAbono;
    private String descripcionCodigoAnalitico;
    private String descripcionConciliacionAutorizacion;
    private String descripcionConciliacionLogContable;
    private String descripcionFechaConciliaLog;
    private String descripcionNumeroDocumentoSwDmpLog;
    // Para Datos del Cliente
    private String descripcionTipoDocumento;
    private String descripcionNumeroDocumento;
    private String descripcionNombreCliente;
    private String descripcionApellidoCliente;
}
