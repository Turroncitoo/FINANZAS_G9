package ob.debitos.simp.model.reporte;

import lombok.Data;

@Data
public class ReporteEstadoCuentaPrepagoMovimiento
{
    private String fechaTransaccionFmt;
    private String horaTransaccion;
    private String trace; 
    private String descripcionCodigoTransaccion; 
    private String nombreAfiliado; 
    private String valorTransaccion;
    private Integer monedaTransaccion;
    private double valorTransaccionCargo;
    private double valorTransaccionAbono;
    private Integer monedaSoles;
    private double valorSoles;
    private double tipoCambio;
    
}
