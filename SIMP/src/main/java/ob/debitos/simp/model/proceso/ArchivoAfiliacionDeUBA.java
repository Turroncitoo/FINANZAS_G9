package ob.debitos.simp.model.proceso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoAfiliacionDeUBA {

	private String numeroTrace;
	private String codigoTransaccionAdm;
	private String numeroTarjeta;
	private String codigoSeguimiento;
	private String numeroCuenta;
	private String fechaHora;
	private String codigoRespuestaTarjeta;
	private String codigoRespuestaCuenta;
	private String codigoRespuestaTrjCta;
	
}
