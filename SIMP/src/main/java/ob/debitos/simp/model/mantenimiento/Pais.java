package ob.debitos.simp.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pais
{
    Integer idPais;
    String descripcion;
    String codigoPaisIso3Alfa3;
    String codigoCelular;
    String codigoPaisIso3Alfa2;
    String codigoMoneda;
}
