package ob.debitos.simp.model.reporte;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.model.proceso.LoteAfiliacion;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMoneda
{
    private Integer codigoMoneda;
    private String descripcionMoneda;
    private List<ReporteTransaccion> transacciones;
    private List<ReporteResumenLogContableEmisor> resumenesLogContableEmisor;
	
 // Reporte sumario compensacion
 	private List<ReporteSumarioCompensacion> sumarioCompensaciones;
 // Reporte contable
    private List<ReporteResumenContable> cuentas;
    private List<ReporteItemDetalleContable> cuentasDetalle;
	// Reporte cuadre cuentas por pagar	
	private List<ReporteCuadreCuentasPorPagarResumen> cuentasPorPagarResumen;
	private List<ReporteCuadreCuentasPorPagarDetalle> cuentasPorPagarDetalle;
	private List<ReporteCuadreCuentasPorPagarAutDetalle> cuentasPorPagarAutDetalle;
    // Reporte facturacion UNIBANCA
    private List<ReporteFacturacionResumen> facturacionResumen;
    private List<ReporteFacturacionDetalle> facturacionDetalle;
    private List<ReporteFacturacionMiscelaneos> facturacionMiscelaneos;
    // recargas
    private List<LoteAfiliacion> recargas;
    //Transacciones x Tarjeta
    private List<ReporteTransaccionesTarjetas> detalleTransaccionesTarjetas;
    //personas
    private List<ReportePersonas> personas;
    
    //Resumen de conciliación
    private List<ReporteConciliacionResumenPorPeriodo> reporteConciliacionResumen;
    
    
    
}