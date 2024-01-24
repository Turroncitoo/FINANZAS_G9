package ob.debitos.simp.model.prepago.wshub.respuesta;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespuestaWS
{

    private String id;
    private String descripcion;
    private String secHash;
       
}
