package ob.debitos.simp.model.criterio;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaConciliacionResumenSaldos {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date fechaProceso;
	String mesProceso;
	String anioProceso;
	String verbo;
	String modo;
	String idsInstitucion;
}
