package ob.debitos.simp.model.mantenimiento;



import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CanalSeguroWS;
import ob.debitos.simp.validacion.IdCliente;
import ob.debitos.simp.validacion.IdEmpresa;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.IClase;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdCliente(existe = true, groups = IClase.class)
public class CanalesSegurosWS 
{
    @CanalSeguroWS(existe = true, groups = IActualizacion.class)
    @CanalSeguroWS(existe = false, message = "{Existe.CanalesSegurosWS.idCanal}", groups = IRegistro.class)
	private String idCanal;
    
    @NotNull(message = "{NotNull.CanalesSegurosWS.contrasenia}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.CanalesSegurosWS.contrasenia}", groups = IBasico.class)
    @Length(min = 4, max = 60, message = "{Length.CanalesSegurosWS.contrasenia}", groups = IBasico.class)
	private String contrasenia;
    
    @NotNull(message = "{NotNull.CanalesSegurosWS.activo}", groups = IBasico.class)
    @Range(min = 0, max = 1, message = "{Range.CanalesSegurosWS.activo}", groups = IBasico.class)
	private Integer activo;
    
    @IdEmpresa(existe = true, groups = IBasico.class)
	private String idEmpresa;
    
	private String descripcionEmpresa;
	
	private String idCliente;
	
	private String descripcionCliente;
}
