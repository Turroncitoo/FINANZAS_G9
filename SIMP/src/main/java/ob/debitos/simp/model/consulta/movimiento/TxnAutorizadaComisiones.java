package ob.debitos.simp.model.consulta.movimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnAutorizadaComisiones 
{
	private String numeroTarjeta;
	private String fechaTransaccion;
	private String codigoProcesamiento;
	private String numeroTrace;
	private String monedaComision;
	private String valorComision;
	private String tipoPago;
	private String numeroDocumento;
}
