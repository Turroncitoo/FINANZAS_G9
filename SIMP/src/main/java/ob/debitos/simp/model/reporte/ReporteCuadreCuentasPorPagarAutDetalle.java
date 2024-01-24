package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
public class ReporteCuadreCuentasPorPagarAutDetalle
{
    private String tipoMensaje;
    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;
    private String numeroCuenta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaAdquirente;
    private String horaAdquirente;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaLocal;
    private String horaLocal;
    private String numeroTrace;
    private String codigoAutorizacion;
    private String codigoGiroNegocio;
    private String codigoProceso;
    private String descripcionProceso;
    private Integer codigoCanal;
    private String descripcionCanal;
    private double monto;
    private String descripcionAdquirente;
    private String binAdquirente;
    private String descripcionBinAdquirente;
    private Integer codigoRespuesta;
    private String descripcionRespuesta;
    private Integer codigoConciliacion;
    private String descripcionConciliacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaConciliacionLog;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    private Integer codigoInstitucion;
    private String descripcionInstitucion;
}
