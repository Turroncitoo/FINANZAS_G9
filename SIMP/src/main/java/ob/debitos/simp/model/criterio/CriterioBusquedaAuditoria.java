package ob.debitos.simp.model.criterio;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaAuditoria 
{
	@NotNull(message = "{NotNull.Auditoria.idUsuario}")
    @NotBlank(message = "{NotBlank.Auditoria.idUsuario}")
    @Length(min = 3, max = 20, message = "{Length.Auditoria.idUsuario}")
    private String idUsuario;
    
	@NotNull(message = "{NotNull.Auditoria.idRecurso}")
    @Range(min=1, max=20,message = "{Length.Auditoria.idRecurso}")
    private String idRecurso;
    
	@NotNull(message = "{NotNull.Auditoria.idAccion}")
    @NotBlank(message = "{NotBlank.Auditoria.idAccion}")
    @Length(min = 1, max = 4, message = "{Length.Auditoria.idAccion}")
    private String idAccion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    private String descripcionRecurso;
    private String descripcionAccion;
    private String descripcionRangoFechas;
    private String descripcionIdUsuario;
}