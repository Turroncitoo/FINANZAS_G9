package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Representa cada item contable del reporte detallado de contabilización por
 * rol de transacción (emisor o misceláneo) y tipo de movimiento (fondos o
 * comisiones).
 * 
 * @author Carla Ulloa
 */
@Data
public class ReporteItemDetalleContable
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    private String cuentaContable;
    private String descripcionCuenta;
    private String descripcionMembresia;
    private String descripcionServicio;
    private String descripcionComision;
    private String descripcionOrigen;
    private double montoDebito;
    private double montoCredito;
    private String codigoEventoMarcaInternacional;
    private Integer distribucionCobroMarcaInternacional;
    private String numeroFacturaMarca;
    private double tarifaMarcaInternacional;
    private Integer totalUnidades;
    private String indicadorDistribucionCobro;
    private Integer indicadorUnidades;
    private double valorFacturaMarcaInternacional;
    private String secuenciaRegistroFacturaMarca;
    private Integer idClaseTransaccion;
    private String descripcionClaseTransaccion;
    private Integer idCodigoTransaccion;
    private String descripcionCodigoTransaccion;
}