package ob.debitos.simp.model.prepago.wshub.respuesta;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespuestaWSRegistroCliente
{
    private RespuestaWS respuestaWS;
    private String idCliente;    
}
