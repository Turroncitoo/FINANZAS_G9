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
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;
import ob.debitos.simp.validacion.IdCodigoFacturacion;
/**
 * Representa la información de los códigos de facturación.
 * 
 * @author Fernando Fonseca
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodigoFacturacion {
	
	@IdCodigoFacturacion(existe = true, groups = IActualizacion.class)
	@IdCodigoFacturacion(existe = false, message = "{Existe.CodigoFacturacion.idCodigoFacturacion}", groups = IRegistro.class)
	private Integer idCodigoFacturacion;
	@NotNull(message = "{NotNull.CodigoFacturacion.descripcion}")
    @NotBlank(message = "{NotBlank.CodigoFacturacion.descripcion}")
    @Length(min = 3, max = 50, message = "{Length.CodigoFacturacion.descripcion}")
	@Pattern(regexp = Regex.DESCRIPCION, message = "{Pattern.CodigoFacturacion.descripcion}")
	private String descripcion;

}
