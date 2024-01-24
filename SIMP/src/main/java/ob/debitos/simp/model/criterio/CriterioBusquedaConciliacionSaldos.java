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
public class CriterioBusquedaConciliacionSaldos {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date fechaTransaccion;
	String mesTransaccion;
	//List<Integer> idATMs; cadena: &idATMs=100&idATMs=200
}
