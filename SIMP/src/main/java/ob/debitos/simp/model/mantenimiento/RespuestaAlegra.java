package ob.debitos.simp.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaAlegra 

{
	private String codigo;
	private String descripcion;
	private String idCuentaAlegra;

}
