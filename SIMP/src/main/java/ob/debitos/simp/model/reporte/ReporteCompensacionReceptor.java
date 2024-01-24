package ob.debitos.simp.model.reporte;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteCompensacionReceptor
{
    private String idMembresia;
    private String descripcionMembresia;
    private List<ReporteAtm> atms;
}