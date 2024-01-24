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
public class ArchivoLote {

	private Integer idInstitucion;
	private Date fechaProceso;//yo
	private String idBIN;
	private String idSubBIN;
	private String idAfinidad;
	private String idTipoEmision;
	private Integer idEstadoLote;//yo
	private Integer idInstancia;//yo
	private String idCliente;
	private String idEmpresa;
	private Integer cantidadPedido;
	private Integer idLote;
	
	private String categoria;
	
	private HeaderLote headerLote;
	private List<DetalleLote> detallesLote;//registroDetalle
	private TrailerLote trailerLote;
	
}
