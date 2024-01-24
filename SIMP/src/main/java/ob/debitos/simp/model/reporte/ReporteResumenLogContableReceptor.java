package ob.debitos.simp.model.reporte;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReporteResumenLogContableReceptor
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    private String codigoMembresia;
    private String descripcionMembresia;
    private String codigoClaseServicio;
    private String descripcionClaseServicio;
    private Integer rolTransaccion;
    private String descripcionRolTransaccion;
    private Integer idCanal;
    private String descripcionCanal;
    private Integer codigoOrigen;
    private String descripcionOrigen;
    private Integer codigoTransaccion;
    private String descripcionCodigoTransaccion;
    private String codigoGiroNegocio;
    private String descripcionGiroNegocio;
    private Integer codigoInstitucionEmisor;
    private String descripcionInstitucionEmisor;
    private Integer idAtm;
    private String descripcionAtm;
    private Integer codigoRespuesta;
    private String descripcionCodigoRespuesta;
    private Integer codigoMoneda;
    private String descripcionMoneda;
    private Integer cantidad;
    private double monto;
    private List<ReporteComision> comisiones;
}