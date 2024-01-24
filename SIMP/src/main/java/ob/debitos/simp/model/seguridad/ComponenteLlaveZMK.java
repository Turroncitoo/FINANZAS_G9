package ob.debitos.simp.model.seguridad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponenteLlaveZMK {
	String componente1;
	String componente2;
	String componente3;
}
