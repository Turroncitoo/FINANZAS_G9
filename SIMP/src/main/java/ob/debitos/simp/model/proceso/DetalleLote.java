package ob.debitos.simp.model.proceso;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleLote {

	private String idTipoRegistro;
	private Integer idTipoTransaccion;
	private Integer bin;
	private Integer idSubBin;//ID
	private Integer numeroSecuencia;//ID
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fecha;
	private Integer numeroTrace;
	private Integer idInstitucionEmisora;
	private Integer idInstitucionReceptora;
	private Integer idRespuesta;
	private Integer idRequerimiento;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaRequerimiento;
	private String apellidoPaternoTitular;
	private String ApellidoMaternoTitular;
	private String nombreTitular;
	private String idTipoDocumento;//DN-RU
	private Integer numeroDocumento;
	private String nombreGrabacion;
	private Integer idDestino;
	private Integer idModeloTarjeta;//0001
	private Integer idTipoEmision;//001

}
