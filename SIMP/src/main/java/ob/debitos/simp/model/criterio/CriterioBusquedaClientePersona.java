package ob.debitos.simp.model.criterio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaClientePersona {

	private Integer idCliente;
	
	private String nombres;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private Integer codigoUBA;
	
	private String nombreCompleto;
	
	private String numeroDocumento;
}
