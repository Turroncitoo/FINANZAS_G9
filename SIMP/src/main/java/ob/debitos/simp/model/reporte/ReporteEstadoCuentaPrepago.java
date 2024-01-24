package ob.debitos.simp.model.reporte;

import java.util.List;

import lombok.Data;

@Data
public class ReporteEstadoCuentaPrepago
{
    private String nombreCompleto;
    private String numeroTarjeta;
    private String saldoDisponible;
    private String descripcionEstadoTarjeta;
    private String fechaActivacionFmt;
    private String fechaBloqueoFmt;
    private String periodo;
    
        
    private List<ReporteEstadoCuentaPrepagoMovimiento> movimientos;
}
