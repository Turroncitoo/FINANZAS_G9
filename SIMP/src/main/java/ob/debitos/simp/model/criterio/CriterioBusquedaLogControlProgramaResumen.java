package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CriterioBusquedaLogControlProgramaResumen {
	private String idGrupo;
    private String idPrograma;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    private String descripcionRangoFechas;
    private String descripcionGrupo;
    private String descripcionPrograma;
}
