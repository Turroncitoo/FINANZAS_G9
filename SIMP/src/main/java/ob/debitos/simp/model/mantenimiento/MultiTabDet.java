package ob.debitos.simp.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiTabDet
{

    private int idTabla;

    private String idItem;

    private String descripcionItem;

    private String abreviatura;

}