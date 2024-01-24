package ob.debitos.simp.model.prepago.wshub.solicitud;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SolicitudWSRegistroCliente
{
    @JsonProperty("AP_MATERNO")
    private String apellidoMaterno;
    
    @JsonProperty("AP_PATERNO")
    private String apellidoPaterno; 
    
    @JsonProperty("BIRTHDATE")
    private String fechaCumpleanios;
    
    @JsonProperty("NOMBRE1")
    private String nombre1;
    
    @JsonProperty("NOMBRE2")
    private String nombre2;
    
    @JsonProperty("TIPO_DOC")
    private String tipoDocumento;
    
    @JsonProperty("NRO_DOC")
    private String numeroDocumento;
    
    @JsonProperty("OP")
    private String operacion;
    
    @JsonProperty("SEC_HASH")
    private String secHash;
    
   
}
