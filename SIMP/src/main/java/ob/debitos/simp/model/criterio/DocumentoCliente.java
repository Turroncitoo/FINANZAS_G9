package ob.debitos.simp.model.criterio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoCliente {
	Integer idx;
	String tipoDocumento;
	String numeroDocumento;
	String existeCliente;
}
