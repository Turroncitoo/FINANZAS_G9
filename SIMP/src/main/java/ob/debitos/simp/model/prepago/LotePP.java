package ob.debitos.simp.model.prepago;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ob.debitos.simp.model.mantenimiento.Cliente;
import ob.debitos.simp.model.mantenimiento.SubBin;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LotePP {
	private Integer idLote;
	private Integer idLoteOriginal;
	private Integer estadoLote;
	private String fechaRegistro;
	private String fechaModificacion;
	private Integer activo;
	private Integer instancia;
	private Integer idInstitucion;
	private String  descripcionInstitucion;
	private Integer idCliente;
	private String descripcionCliente;
	private String nombreArchivo;
	private Integer secuencia;
	private String idBin;
	private String descripcionBin;
	private String idSubBin;
	private String descripcionSubBin;
	private Integer indicadorActivado;
	private Integer indicadorRecargado;
	private List<ControlLotePP> controlesLote;
	private Integer puedeActivar;
	private Integer puedeRecargar;
	private String descripcionEstadoLote;
	
	//atributos del proyecto SIMP_PRE
	private Integer nIdLote;
	private Integer nEstadoLote;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date dFechaRegistro;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date dFechaModificacion;
	private Integer nInstancia;
	private Integer nIdInstitucion;
	private SubBin clSubBin;
	private Cliente clCliente;
	private String sNombreArchivo;
	private Integer nSecuencia;
	private List<ControlLotePP> lstControlLote;
	private Integer nTipoAfiliacion;
}
