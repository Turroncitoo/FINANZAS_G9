package ob.debitos.simp.model.prepago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecargaPP {
	private Integer idRecarga;
	private Integer idLote;
	private Integer idTarjeta;
	private Integer idSecuencial;
	private String fecHora;
	private String montoEnviado;
	private String montoRecibido;
	private String moneda;
	private String datos;
	private Integer respCode;

	//Atributos de SIMP_PRE
	private TarjetaPP tarjeta;
	private double montoEnviadoo;
}
