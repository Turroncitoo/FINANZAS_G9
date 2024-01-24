package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReporteResumenTransaccionAprobadaAgencia
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;
	private Integer idAgencia;	
	private String descripcionAgencia;
	private String idSexo;
	private String descripcionSexo;
	private String idEstadoCivil;
	private String descripcionEstadoCivil;
	private Integer idCanal;
    private String codigoProceso;
    private String codigoGiroNegocio;
    private Integer codigoRespuesta;
    private Integer codigoMoneda;
    private Integer cantidadTransaccion;
    private Double montoTransaccion;
    private String descripcionCanal;
    private String descripcionCodigoProceso;
    private String descripcionGiroNegocio;
    private String descripcionCodigoRespuesta;
    private String descripcionMoneda;
}
