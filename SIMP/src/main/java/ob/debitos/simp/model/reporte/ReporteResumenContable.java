package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Representa el reporte resumen de contabilización por rol de transacción
 * (emisor o misceláneo) y tipo de movimiento (fondos o comisiones).
 * 
 * @author Hanz Llanto
 */
@Data
public class ReporteResumenContable
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    private String cuentaContable;
    private String descripcionCuenta;
    private double montoCredito;
    private double montoDebito;
    
  //para exportar
  	private Integer codigoMoneda;
  	private String descripcionMoneda;
}