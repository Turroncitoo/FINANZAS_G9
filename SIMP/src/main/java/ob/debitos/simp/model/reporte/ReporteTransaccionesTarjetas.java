package ob.debitos.simp.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteTransaccionesTarjetas {
	Integer idInstitucion;
	String descripcionInstitucion;
	String idOperacion;
	String descripcionOperacion;
	Integer codigoMoneda;
	String descripcionMoneda;
	Integer numeroTarjetas;
	Integer numeroTransacciones;
	Double montoPromedio;
	Double montoTotal;
}
