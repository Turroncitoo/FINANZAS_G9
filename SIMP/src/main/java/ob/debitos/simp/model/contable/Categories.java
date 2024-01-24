package ob.debitos.simp.model.contable;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categories {
	
	private Integer id;
	private Integer quantity;
	private Integer cantidad;
	private Double price;
	private String observations;
	
	
	

}
