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
public class TxnsPendientes
{	
	//Nuevos
	private Integer idInstitucion;
	private String descripcionInstitucion;
	private String tipoMensaje;
	@NoIdentificable(campo = "numeroTarjeta")
	private String numeroTarjeta;
	private String codigoProcesamiento;
	private String fechaTransaccion;
	private String horaTransaccion;
	private String descripcionOrigen;
	private String traceTransaccion;
	private String modoEntradaPos;
	private String fechaCaptura;
	private String numeroReferencia;
	private String codigoRespuesta;
	private String cuentaFrom;
	private String cuentaTo;
	private String monedaAutorizacion;
	private String valorAutorizacion;
	private String fechaProceso;
	private String descripcionProceso; 
	private String conciliacionAutorizacion;
	private String descripcionCanal;
	private String numeroDocumentoLiberada;
	private String nombreAfiliado;	 
	private String estadoTarjeta;
}
