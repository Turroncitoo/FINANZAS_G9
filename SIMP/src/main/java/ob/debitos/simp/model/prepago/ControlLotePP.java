package ob.debitos.simp.model.prepago;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ControlLotePP {

	private Integer idControlLote;
	private Integer idLote;
	private Integer idSecuencial;
	private String fecHora;
	private Integer cardLife;
	private String datos;
	private PersonaPP persona;
	private RecargaPP recarga; //este no esta
	private Integer respCode;
	private Map<String,Integer> respCodeControlLote;
}
