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
public class TxnsConsolidada 
{
	@NoIdentificable(campo="numeroTarjeta")
	private String numeroTarjeta;
	private String numeroCuenta;
	private String origenArchivo;
	private String canal;
	private String tipoTransaccion;
	private String fechaTransaccion;
	private String horaTransaccion;
	private String fechaCompensacion;
	private String numeroTrace;
	private String autorizacion;
	private String adquirente;
	private String monedaTransaccion;
	private String valorTransaccion;
	private String monedaCompensacion;
	private String valorCompensacion;
	private String codigoRespuesta;
	private String idOrigenArchivo;
	private String tipoMensaje;
	private String estadoTarjeta;
	private Integer codigoInstitucion;
	private String descripcionInstitucion;
	private Integer idSecuencia;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaProceso;
}
