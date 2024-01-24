package ob.debitos.simp.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteAtm
{
    private Integer idAtm;
    private String descripcionAtm;
    private int cantidad;
    private double monto;
    private double comisionInt;
    private double comisionGas;
    private double comisionOpe;
    private double comisionSur;
    private double totalComision;
}