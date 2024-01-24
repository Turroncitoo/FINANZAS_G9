package ob.debitos.simp.model.reporte;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReporteTransaccion
{
    private Integer claseTransaccion;
    private String descripcionClaseTransaccion;
    private Integer codigoTransaccion;
    private String descripcionCodigoTransaccion;
    private String codigoMembresia;
    private String descripcionMembresia;
    private String codigoClaseServicio;
    private String descripcionClaseServicio;
    private int codigoOrigen;
    private String descripcionOrigen;
    private int cantidadTransaccion;
    private Date fechaTransaccion;
    private Integer codigoRespuestaTransaccion;
    private String diaMesAnioTransaccion;
    private double monto;
    private String idEmpresa;
    private String idCliente;
    private String descripcionEmpresa;
    private String descripcionCliente;
    private List<ReporteComision> comisiones;
    private double subTotal;
}