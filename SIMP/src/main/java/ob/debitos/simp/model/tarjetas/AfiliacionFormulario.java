package ob.debitos.simp.model.tarjetas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AfiliacionFormulario
{

    private Integer idInstitucion;

    private String idEmpresa;
    private String idCliente;
    private String idLogo;
    private String codigoProducto;
    private String tipoAfiliacion;
    private String idAfinidad;
    private String idCategoria;
    private String tipoTarjetas;

    private Integer registros;

}
