package ob.debitos.simp.model.criterio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaTarjetasVendidas {

	private Integer anioInicio;
	private Integer mesInicio;
	private Integer anioFin;
	private Integer mesFin;

}
