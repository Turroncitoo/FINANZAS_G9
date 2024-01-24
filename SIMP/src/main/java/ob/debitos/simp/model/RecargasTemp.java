package ob.debitos.simp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecargasTemp {
		private String institucion;
		private String montoCantidad;
		private String recargable;
		private String noRecargable;
		private String totales;
	
}
