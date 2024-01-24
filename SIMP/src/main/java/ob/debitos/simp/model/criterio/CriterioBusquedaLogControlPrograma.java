package ob.debitos.simp.model.criterio;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CriterioBusquedaLogControlPrograma
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{NotNull.CriterioBusquedaLogControlPrograma.fechaProceso}")
    private Date fechaProceso;

    @Length(max = 20, message = "{Length.CriterioBusquedaLogControlPrograma.idUsuario}")
    private String idUsuario;

    @Length(min = 0, max = 1, message = "{Length.CriterioBusquedaLogControlPrograma.tipoEjecucion}")
    private String tipoEjecucion;
    
    private String descripcionTipoEjecucion;
    private String descripcionFechaProceso;
    private String descripcionUsuario;
}