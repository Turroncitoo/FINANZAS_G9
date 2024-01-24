package ob.debitos.simp.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosInterfaceContable {

	private String usuarioAlegra;

	private String tokenAlegra;

	private String cuentaContableURI;
	
	private String pagoURI;

	private Integer activo;

}
