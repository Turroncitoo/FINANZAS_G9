package ob.debitos.simp.model.consulta;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Carlos Mirano
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaLogInterfaceAlegra
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaProceso;
	private String descripcion;
	private String idEnvio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss", timezone = "EST")
	private Date fechaRequest;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss", timezone = "EST")
	private Date fechaResponse;
	private String uriRequest;
	private Integer exito;
	private String httpStatus;
	private String traceError;
	private String usuario;
	private String jsonRequest;
	private String jsonResponse;
}
