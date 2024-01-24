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
public class SolicitudWSConsultarPorTarjeta {

	@JsonProperty("COD_SEG")
    private String codigoSeguimiento;
        
    @JsonProperty("SEC_HASH")
    private String secHash;
    
}
