package ob.debitos.simp.model.prepago.wshub;
import lombok.Data;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class ConsultaServicioWeb {
	private Integer idEvento ;
	private String  nombreContexto;
	private String nombreServicio;
	private Integer secuencia;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy  hh:mm:ss", timezone = "EST")
	private Date fechaHoraIN;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy  hh:mm:ss", timezone = "EST")
	private Date fechaHoraRequest;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy  hh:mm:ss", timezone = "EST")
	private Date fechaHoraResponse;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy  hh:mm:ss", timezone = "EST")	
	private Date fechaHoraTimeout;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy  hh:mm:ss", timezone = "EST")
	private Date fechaHoraOUT;
	private Integer UBAErrorCode;
	private Integer	 localErrorCode;
	private String descripcionUBAErrorCode;
	private String descripcionLocalErrorCode;
	private Integer extornada;//Byte
	private String descripcionExtornada;
	private String requestHash;
	private String responseHash;
	private String requestJSON;
	private String responseJSON;
}
