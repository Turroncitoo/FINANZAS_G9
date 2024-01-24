package ob.debitos.simp.model.proceso;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParametroValidaArchivo {

	private String tipoArchivo;
	private String cabecera;
	private Date fechaArchivo ;
}
