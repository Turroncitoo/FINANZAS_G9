package ob.debitos.simp.model.paginacion;

import lombok.Data;

@Data
public class CriterioOrdenamiento {
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	private int column;
	private String dir;
}
