package ob.debitos.simp.model.prepago.wshub.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginSimpHub {
	String idCanal;
	String password;
	String idUsuario;
}
