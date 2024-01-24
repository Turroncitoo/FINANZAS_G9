package ob.debitos.simp.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEstadoTarjeta {
	
	private String idMembresia;
	private String descripcionMembresia;
	private String idBin;
	private String descripcionBin;
	private String idEstadoTarjeta;
	private String descripcionEstadoTarjeta;
	private String periodo;
	private String mes;
	private Integer cantidadTarjetas;

}
