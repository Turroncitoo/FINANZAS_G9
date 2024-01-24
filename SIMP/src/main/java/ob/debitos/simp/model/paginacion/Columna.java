package ob.debitos.simp.model.paginacion;

import lombok.Data;

@Data
public class Columna {
	private String data;
	private String name;
	private boolean searchable;
	private boolean orderable;
	private CriterioGlobalBusqueda search;
}
