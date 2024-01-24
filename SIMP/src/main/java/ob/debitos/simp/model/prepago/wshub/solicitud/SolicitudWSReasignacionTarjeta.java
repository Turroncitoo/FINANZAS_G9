package ob.debitos.simp.model.prepago.wshub.solicitud;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudWSReasignacionTarjeta
{
   
    @JsonProperty("COD_SEG_ANTERIOR")
    private String codigoSeguimientoAnterior;
    
    @JsonProperty("COD_SEG_NUEVO")
    private String codigoSeguimientoNuevo;
    
    @JsonProperty("ID_USUARIO")
    private String idUsuario;
    
    @JsonProperty("SEC_HASH")
    private String secHash;
    
    @JsonProperty("OP")
    private String operacion;
    
    
}
