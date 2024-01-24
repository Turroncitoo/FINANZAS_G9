package ob.debitos.simp.model.contable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
	
	
	private String code;
	private String exchangeRate;

}
