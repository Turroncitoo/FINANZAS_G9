package ob.debitos.simp.model.prepago.wshub;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWS;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
public class ConsultaMovimientos
{
    
    RespuestaWS respuesta;
    private List<MovimientoPP> movimientos;
    
}

