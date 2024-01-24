package ob.debitos.simp.model.reporte;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteCodigoProcesoSwitch
{
    private String codigo_switch;
    private String descripcion_proceso_switch;
    private List<ReporteTransaccion> transacciones;
}