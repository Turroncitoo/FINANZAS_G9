package ob.debitos.simp.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResumenRecargas {
	private Integer idInstitucion;
	private String descripcionInstitucion;
	private Integer idTipoTarjeta;
	private String descripcionTipoTarjeta;
	private Integer codigoMoneda;
	private String descripcionMoneda;
	private Double montoRecarga;
	private Integer totalRecarga;
}

