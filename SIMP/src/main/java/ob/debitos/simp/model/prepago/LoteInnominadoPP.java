package ob.debitos.simp.model.prepago;

import java.util.Date;

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
public class LoteInnominadoPP {
	private Integer nIdLote;
	private Integer nIdInstitucion;
	private Date dFechaProceso;
	private String codigoBIN;
	private String codigoSubBIN;
	private String ivAfinidad;
	private String tipoEmision;
	private Integer nEstadoLote;
	private Integer nInstancia;
	private String nIdEmpresa;
	private String nIdClienteLote;
	private Integer cantidadLote;
	private Integer nTipoAfiliacion;
}
