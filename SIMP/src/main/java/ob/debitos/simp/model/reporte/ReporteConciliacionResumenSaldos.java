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
public class ReporteConciliacionResumenSaldos {
	List<ReporteMoneda> saldoFinal;
	List<ReporteMoneda> saldoLiberadas;
	List<ReporteMoneda> saldoInicial;
	List<ReporteMoneda> saldoNoCompensado;	
}
