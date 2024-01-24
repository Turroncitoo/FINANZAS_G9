package ob.debitos.simp.model.mantenimiento;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaContable 
{
	private RespuestaAlegra respuesta;
	private List<CuentaContable> respuestaContable;

}
