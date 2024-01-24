package ob.debitos.simp.model.reporte;

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
public class ReporteLogControlProgramaResumenDetalle {
	
	private String idGrupo;
	private String descripcionGrupo;
	private String idPrograma;
	private String descripcionPrograma;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaProceso;
	private Integer secuencia;
	private Integer estado;
	private String descripcionEstado;
	private String mensaje;
	private Integer numeroRegistros;
	private String usuarioEjecucion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaEjecucion;
	private Integer idInstitucion;
	private String descripcionInstitucion;
	private String horaInicio;
	private String horaFinal;
	private String tiempoProceso;
	private String tipoEjecucion;
	private String descripcionTipoEjecucion;

}
