package ob.debitos.simp.model.contable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceContableAlegraRequest {
	private String date;
	private Integer bankAccount;
	private List<Categories> categories;
	private String type;
}
