package ob.debitos.simp.model.reporte;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteTarjetasVendidas {

	
	private Integer idInstitucion;
	private String descripcionInstitucion;
	private Integer tarjetasRecargadas;
	private Integer tarjetasActivadas;
	private Integer tarjetasEnUso;
	private Double recargaPromedio;
	private Double porcentajeTarjetasRecargadas;
	private Double porcentajeTarjetasEnUso;
	private Double transaccionesPromedio;
	
	
}
