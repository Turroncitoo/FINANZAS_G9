package ob.debitos.simp.model.proceso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoteAfiliacionDetalle {

	private Integer idControlLote;
	private Integer idTarjeta;
	private Integer idSecuencial;
	private String fecHora;
	private String datos;
	private Integer idPersona;
	private Integer respCode;
	
	//recargas
	private double montoEnviado;
	private Integer moneda;
	
}
