package ob.debitos.simp.model.consulta.movimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnsAutorizacionDetalle
{
	private String descripcionTipoMensajeOriginal;
	private String descripcionNumeroTraceOriginal;		
	private String descripcionFechaTransaccionOriginal;
	private String descripcionOriginalInstitucionAdquirente;
	private String descripcionOriginalInstitucionReenvio;
	private String descripcionOrigenArchivo;
    private String descripcionRol;
    private String descripcionCanal;
    private String descripcionTipoMensaje;
    private String descripcionNumeroTarjeta;
    private String descripcionTipoTransaccion;
    private String descripcionMonedaTransaccion;
    private String descripcionMontoTransaccion;
    private String descripcionTrace;
    private String descripcionFechaTransaccion;
    private String descripcionHoraTransaccion;
    private String descripcionFechaProcesado;
    private String descripcionHoraProceso;
    private String descripcionFechaExpiracion;
    private String descripcionFechaCapturaSwitch;
    private String descripcionGiroNegocio;
    private String descripcionPosEntryMode;
    private String descripcionPosConditionCode;
    private String descripcionRespuesta;
    private String descripcionIdCuenta1;
    private String descripcionIdCuenta2;
    private String descripcionTerminalAdquirente;
    private String descripcionAdquirente;
    private String descripcionIdentificacionAdquirente;
    private String descripcionAutorizacion;
    private String descripcionInstitucionSolicitante;
    private String descripcionInstitucionAdquirente;
    private String descripcionConciliacionSwDmpLog;
    private String descripcionConciliacionLiberada;
    private String descripcionConciliacionLogContable;
    private String descripcionConciliacionPendientes;
    private String descripcionFechaConciliaLog;
    private String descripcionFechaConciliaSwt;
    private String descripcionFechaConciliaLib;
    private String descripcionFechaConciliaPen;
    
    private String descripcionTipoDocumento;
    private String descripcionNumeroDocumento;
    private String descripcionNombreCliente;
    private String descripcionApellidoCliente;
}
