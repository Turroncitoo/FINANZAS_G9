package ob.debitos.simp.model.proceso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrailerLote {

	private Integer idTipoLote;
	private Integer numeroReqistros;

}
