package ob.debitos.simp.model.proceso;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoAfiliacion {

	private ArchivoLote lote;
	private List<TmpRequerimiento> registros;
	
}
