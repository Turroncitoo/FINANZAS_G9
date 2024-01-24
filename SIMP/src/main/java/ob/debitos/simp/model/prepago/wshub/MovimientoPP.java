package ob.debitos.simp.model.prepago.wshub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonIgnoreProperties
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
//@JsonRootName(value = "movimiento")
public class MovimientoPP
{
    //@JsonProperty("SECUENCIA")
    private String secuencia;
    
    //@JsonProperty("TIPO")
    String tipo;
    
    //@JsonFormat(shape=Shape.STRING)
    //@JsonProperty("MONTO")
    String monto; 
    
    //@JsonFormat(shape=Shape.STRING)
    //@JsonProperty("COSTO")
    String costo;
    
    //@JsonProperty("HORA")
    //@JsonDeserialize(using = CustomTimeDeserializer.class)
    String hora; 
    
    //@JsonProperty("FECHA")
  //  @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    String fecha;
    
    //@JsonProperty("COMERCIO")
    String comercio;
    
    //@JsonProperty("CODIGO DE OPERACION")
    String codigoOperacion;
    
    //@JsonProperty("PAN_TRUNC")
    String tarjetaTruncada;
        
}
