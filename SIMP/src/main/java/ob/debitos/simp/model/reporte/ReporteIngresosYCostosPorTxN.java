package ob.debitos.simp.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteIngresosYCostosPorTxN {
	
	private Integer idInstitucion;
	private String descripcionInstitucion;
	private String periodo;
	private String concepto;
	private double montoTotal;
	private Integer codigoMoneda;
	private String descripcionMoneda;

}
