package ob.debitos.simp.model.prepago.wshub.respuesta;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import ob.debitos.simp.model.prepago.TarjetaPP;

@Data
@Builder
public class RespuestaWSConsultaCliente {

	private String idUsuario;
	private String email;
	private String celular;
	private String idCliente;
	private String apellidoPaterno;
	private String nombre1;
	private String nombre2;
	private String apellidoMaterno;
	private String numeroDocumento;
	private String tipoDocumento;
	private List<TarjetaPP> tarjetas;
	
}
