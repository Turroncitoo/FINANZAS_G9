package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReporteResumenAutorizacion
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaTransaccion;
	private String tipoMensaje;
	private Integer rolTransaccion;	
	private String descripcionRolTransaccion;
	private Integer idCanal;
	private String descripcionCanal;
	private String codigoProceso;
    private String codigoGiroNegocio;
    private Integer codigoRespuesta;
    private Integer codigoMoneda;
    private Integer cantidadTransaccion;
    private double montoTransaccion;
    private String descripcionCodigoProceso;
    private String descripcionGiroNegocio;
    private String descripcionCodigoRespuesta;
    private String descripcionMoneda;
}