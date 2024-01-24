package ob.debitos.simp.model.paginacion;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioPaginacion <T, S> {
	private Integer draw;
	private Integer start;
	private Integer length;
	
	private CriterioGlobalBusqueda search;
	private List<CriterioOrdenamiento> order;
	
	private List<Columna> columns;
		
	private T criterioBusqueda;
	
	private String orderExpression;
	
	private S filtrosDatatable;
	
}
