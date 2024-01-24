package ob.debitos.simp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametroTmp {
	private String campo;
	private String identificador;
	private String verbo;
}
