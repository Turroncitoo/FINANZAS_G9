package ob.debitos.simp.model.prepago.wshub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWS;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ConsultaSaldo
{
    private RespuestaWS respuesta;
    
    private String moneda;
    
    private String monto;
    
}
