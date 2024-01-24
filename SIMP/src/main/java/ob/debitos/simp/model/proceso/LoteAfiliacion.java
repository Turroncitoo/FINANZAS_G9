package ob.debitos.simp.model.proceso;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoteAfiliacion {

	private Integer idLote;
	private Integer idLoteOriginal;
	private Integer estadoLote;
	private String descripcionEstado;
	private Date fechaRegistro;
	private Integer idInstitucion;
	private String idEmpresa;
	private String idCliente;
	
	private List<LoteAfiliacionDetalle> afiliacionDetalle;
	
}
