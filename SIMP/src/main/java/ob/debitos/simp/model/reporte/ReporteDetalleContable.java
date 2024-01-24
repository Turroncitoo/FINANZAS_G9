package ob.debitos.simp.model.reporte;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa el reporte detallado de contabilización por rol de transacción
 * (emisor o misceláneo) y tipo de movimiento (fondos o comisiones).
 * 
 * @author Carla Ulloa
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDetalleContable
{
    private String criterio;
    private List<ReporteMoneda> reporteMoneda;
}