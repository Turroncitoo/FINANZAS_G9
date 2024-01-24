package ob.debitos.simp.model.criterio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaAuthWSPendiente {
	String modo;
	String operacion;
	String tipoOperacion;
	String codigoSeguimiento;
	Integer monedaTransaccion;
	Double montoTransaccion;
	String pathOperacion;
	String jsonOperacion;
	String usuario;
	Integer exito;
	String idTransaccion;
	String trace;
	String codigoRespuesta;
	String descripcionRespuesta;
	String fechaEnvioTxn;
	String fechaRecepcionTxn;
	String transaccion;
	Integer idGenerado;
}