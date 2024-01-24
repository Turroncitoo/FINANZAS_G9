package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.CodigoPINEntryCapability;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la información de las capacidades de ingreso de las transacciones de 
 * tipo PIN.
 * 
 * @author Hanz Llanto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PINEntryCapability {
	
	@CodigoPINEntryCapability(existe = true, groups = IActualizacion.class)
	@CodigoPINEntryCapability(existe = false, message = "{Existe.PINEntryCapability.codigo}", groups = IRegistro.class)
	private String codigo;
	
	@NotNull(message = "{NotNull.PINEntryCapability.descripcion}")
    @NotBlank(message = "{NotBlank.PINEntryCapability.descripcion}")
    @Length(min = 3, max = 50, message = "{Length.PINEntryCapability.descripcion}")
	@Pattern(regexp = Regex.DESCRIPCION, message = "{Pattern.PINEntryCapability.descripcion}")
	private String descripcion;
}
