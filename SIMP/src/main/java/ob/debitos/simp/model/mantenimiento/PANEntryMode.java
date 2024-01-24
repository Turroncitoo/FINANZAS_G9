package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.CodigoPANEntryMode;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la información del modo de entrada de tarjeta con la cual se realizó
 * la transacción.
 * 
 * @author Fernando Fonseca
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PANEntryMode {
	
	@CodigoPANEntryMode(existe = true, groups = IActualizacion.class)
	@CodigoPANEntryMode(existe = false, message = "{Existe.PANEntryMode.codigo}", groups = IRegistro.class)
	private String codigo;
	
	@NotNull(message = "{NotNull.PANEntryMode.descripcion}")
    @NotBlank(message = "{NotBlank.PANEntryMode.descripcion}")
    @Length(min = 3, max = 50, message = "{Length.PANEntryMode.descripcion}")
	@Pattern(regexp = Regex.DESCRIPCION, message = "{Pattern.PANEntryMode.descripcion}")
	private String descripcion;
}
