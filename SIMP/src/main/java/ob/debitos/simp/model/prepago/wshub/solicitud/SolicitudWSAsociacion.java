package ob.debitos.simp.model.prepago.wshub.solicitud;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SolicitudWSAsociacion
{
    @JsonProperty("COD_SEG")
    private String codigoSeguimiento;
    
    @JsonProperty("ID_CLIENTE")
    private String idCliente;   
    
    @JsonProperty("ID_USUARIO")
    private String idUsuario;
    
    @JsonProperty("SEC_HASH")
    private String secHash;
    
    @JsonProperty("OP")
    private String operacion;
}
