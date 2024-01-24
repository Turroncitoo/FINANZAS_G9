package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoClienteAfinidad;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoClienteAfinidad(existe = false, groups = IRegistro.class)
public class ClienteAfinidad 
{
	@NotNull(message = "{NotNull.ClienteAfinidad.idAfinidad}", groups = IBasico.class)
	private Integer idAfinidad; 
	
	private String codigoAfinidad;
	
	private String descripcionAfinidad;
	
	private String idLogoAfinidad;
	private String descripcionLogoAfinidad;
	private String idBin;
	
	@NotNull(message = "{NotNull.ClienteAfinidad.idLogo}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.ClienteAfinidad.idLogo}", groups = IBasico.class)
	private String idLogo;
	
	@NotNull(message = "{NotNull.ClienteAfinidad.idInstitucion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.ClienteAfinidad.idInstitucion}", groups = IBasico.class)
	private String idInstitucion;
	
	@NotNull(message = "{NotNull.ClienteAfinidad.idCliente}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.ClienteAfinidad.idCliente}", groups = IBasico.class)
	private String idCliente; 
	
	private String descripcionCliente;
	
	@NotNull(message = "{NotNull.ClienteAfinidad.idEmpresa}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.ClienteAfinidad.idEmpresa}", groups = IBasico.class)
	private String idEmpresa; 
	
	private String descripcionEmpresa;
	
	private Integer idAfinidadAntiguo;
	private String idClienteAntiguo;
	private String idEmpresaAntiguo;
	
	private Integer codigoInstitucion;
	private String descripcionInstitucion;

	
}
