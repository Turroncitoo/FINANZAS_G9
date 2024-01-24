package ob.debitos.simp.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMembresia
{
    private String idMembresia;
    private String descripcionMembresia;
    private int cantidad;
    private double monto;
    private double comisionInt;
    private double comisionGas;
    private double comisionOpe;
    private double comisionSur;
    private double totalMembresia;       
}