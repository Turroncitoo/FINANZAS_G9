package ob.debitos.simp.model.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Carlos Mirano
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message
{
	private Double porcentaje;
	private Boolean terminado;
	private Boolean cancelado;
}
