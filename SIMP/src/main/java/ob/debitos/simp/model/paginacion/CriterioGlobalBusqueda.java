package ob.debitos.simp.model.paginacion;

import lombok.Data;

@Data
public class CriterioGlobalBusqueda {
	private String value;
	private boolean regex; // not used
}
