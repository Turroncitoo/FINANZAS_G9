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
public class ReporteResumenSwDmpLog
{	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
	private Integer idInstitucion;
	private String descripcionInstitucion;
    private String idEmpresa;
    private String descEmpresa;
    private String idCliente;
    private String descCliente;
	private Integer rolTransaccion;	
	private String descripcionRolTransaccion;
    private String codigoProceso;
    private String descripcionCodigoProceso;
    private Integer codigoRespuesta;
    private String descripcionCodigoRespuesta;
	private Integer idCanal;
    private String descripcionCanal;
    private String codigoGiroNegocio;
    private String descripcionGiroNegocio;
	private String tipoMensaje;
    private Integer codigoMoneda;
    private String descripcionMoneda;
    private Integer cantidadTransaccion;
    private double montoTransaccion;
}
