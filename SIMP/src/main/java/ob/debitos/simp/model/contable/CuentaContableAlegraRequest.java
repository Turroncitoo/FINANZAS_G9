package ob.debitos.simp.model.contable;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data   
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaContableAlegraRequest { 
	
	//@JsonInclude(JsonInclude.Include.NON_NULL)
	private String id;
	private Integer idParent;
	private String name;
	private String code;
	private String description;
	
	
	
		
}
