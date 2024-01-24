package ob.debitos.simp.model.contable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInterfaceAlegra {
	String descripcion;
	String idEnvio;
	String fechaRequest;
	String fechaResponse;
	String uriResquest;
	String httpStatus;
	String jsonRequest;
	String jsonResponse;
	String traceError;
	String usuario;
	Integer exito;
}
