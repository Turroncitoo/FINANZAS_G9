package ob.debitos.simp.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteConciliacionResumenPorPeriodo {
	//Diario
	private Integer idInstitucion;
	private String descripcionInstitucion;
	private String idCategoria;
	private String descripcionCategoria;
	private Integer tipoTransaccion;
	private String descripcionTipoTransaccion;
	private Double cantidad;
	private Double valorCargo;
	private Double valorAbono;
	private Double valorComision;
	private Double valorTotal;
	//Para reporte excel
	private Double liberadas;
	private Double pendientes;
}
