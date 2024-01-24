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
public class SolicitudWSRecarga
{
    @JsonProperty("COD_SEG")
    private String codigoSeguimiento;
    
    @JsonProperty("MONEDA")
    private String moneda;
    
    @JsonProperty("MONTO_2")
    private String monto;
    
    @JsonProperty("TLOCAL")
    private String tlocal;
    
    @JsonProperty("OP")
    private String operacion;
    
    @JsonProperty("SEC_HASH")
    private String secHash;
    
     
}
