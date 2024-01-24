package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.CodigoParametroFTPProceso;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosSFTPProceso {
	@CodigoParametroFTPProceso(existe = false, groups = IRegistro.class, message = "{Existe.ParametrosSFTPProceso.codigo}")
	@CodigoParametroFTPProceso(existe = true, groups = IActualizacion.class)
	private String codigo;
	
	@NotNull(message = "{NotNull.ParametrosSFTPProceso.descripcion}")
    @NotBlank(message = "{NotBlank.ParametrosSFTPProceso.descripcion}")
    @Length(min = 3, max = 30, message = "{Length.ParametrosSFTPProceso.descripcion}")
    @Pattern(regexp = Regex.DESCRIPCION, message = "{Pattern.ParametrosSFTPProceso.descripcion}")
	private String descripcion;
}
