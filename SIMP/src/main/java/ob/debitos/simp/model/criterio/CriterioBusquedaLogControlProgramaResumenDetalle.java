package ob.debitos.simp.model.criterio;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class CriterioBusquedaLogControlProgramaResumenDetalle {
	private String idGrupo;
    private String idPrograma;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaProceso;
    private String descripcionFechaProceso;
}
