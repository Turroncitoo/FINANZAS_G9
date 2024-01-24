package ob.debitos.simp.model.criterio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriterioBusquedaResumenTransacciones {
	
	private String mes;
	private Integer anio;
	private String mesAnioTexto;
}
