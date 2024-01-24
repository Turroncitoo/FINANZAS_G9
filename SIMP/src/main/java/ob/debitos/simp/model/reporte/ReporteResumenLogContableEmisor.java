package ob.debitos.simp.model.reporte;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReporteResumenLogContableEmisor
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    private String idEmpresa;
    private String descEmpresa;
    private String idCliente;
    private String descCliente;
    private String codigoBIN;
    private String descripcionBIN;
    private String idLogo;
    private String descLogoBin;
    private String descripcionLogo;
    private Integer idInstitucion;
    private String codigoProducto;
    private String descProducto;
    private Integer rolTransaccion;
    private String descripcionRolTransaccion;
    private String codigoMembresia;
    private String codigoMembresiaCadena;
    private String descripcionMembresia;
    private String codigoClaseServicio;
    private String descripcionClaseServicio;
    private Integer codigoOrigen;
    private String descripcionOrigen;
    private Integer claseTransaccion;
    private String descripcionClaseTransaccion;
    private Integer codigoTransaccion;
    private String descripcionCodigoTransaccion;
    private String codigoRespuesta;
    private String descripcionCodigoRespuesta;
    private Integer idCanal;
    private String descripcionCanal;
    private String codigoGiroNegocio;
    private String descripcionGiroNegocio;
    private Integer codigoInstitucionReceptor;
    private String descripcionInstitucionReceptor;
    private Integer codigoMoneda;
    private String descripcionMoneda;
    private Integer cantidad;
    private double monto;

    // para exportacion
    private double comision1;
    private double comision2;
    private double comision3;
    private double comision4;
    private double comision5;
    private double comision6;
    private double comision10;
    private double comision11;
    private double comision12;
    private double comision14;
    
    private List<ReporteComision> comisiones;
    private double subTotal;
}
