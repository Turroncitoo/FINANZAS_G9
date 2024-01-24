package ob.debitos.simp.model.reporte;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteConciliacionResumen {
	
	private List<ReporteConciliacionResumenSaldos> reporteResumen;
	
	private Double valorLiberadas;
	
	private Double valorNoCompensadas;
	
}
