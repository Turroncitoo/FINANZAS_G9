package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoAfinidad;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoAfinidad(existe = true, groups = IActualizacion.class)
@CodigoAfinidad(existe = false, groups = IRegistro.class)
public class Afinidad 
{

	private Integer idAfinidad;
	
	@NotNull(message = "{NotNull.Afinidad.codigoAfinidad}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.Afinidad.codigoAfinidad}", groups = IBasico.class)
    @Length(min = 1, max = 4, message = "{Length.Afinidad.codigoAfinidad}", groups = IBasico.class)
	private String codigo;
	
	@NotNull(message = "{NotNull.Afinidad.descripcionAfinidad}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.Afinidad.descripcionAfinidad}", groups = IBasico.class)
    @Length(min = 3, max = 100, message = "{Length.Afinidad.descripcionAfinidad}", groups = IBasico.class)
	private String descripcionAfinidad;
	
	@NotNull(message = "{NotNull.Logo.idLogo}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.Logo.idLogo}", groups = IBasico.class)
	private String idLogo;
	
	private String descripcionLogo;
	
	private Integer idGenerado;
	
}
