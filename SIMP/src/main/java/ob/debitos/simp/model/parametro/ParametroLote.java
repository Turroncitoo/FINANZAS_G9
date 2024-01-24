package ob.debitos.simp.model.parametro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametroLote {
	
	private String accion;
	private Integer idLote;
	
}
