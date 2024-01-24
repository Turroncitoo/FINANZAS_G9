package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import ob.debitos.simp.validacion.grupo.IBasico;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.CodigoInstitucion;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Institucion
{
    @CodigoInstitucion(existe = true, groups = IActualizacion.class)
    @CodigoInstitucion(existe = false, message = "{ExisteInstitucion.Institucion.codigoInstitucion}", groups = IRegistro.class)
    private Integer codigoInstitucion;

    @NotNull(message = "{NotNull.Institucion.descripcion}")
    @NotBlank(message = "{NotBlank.Institucion.descripcion}")
    @Length(min = 3, max = 80, message = "{Length.Institucion.descripcion}")
    private String descripcion;

    @NotNull(message = "{NotNull.Institucion.cuenta_compensacion}")
    @Pattern(regexp = Regex.SOLO_1_o_0, message = "{Pattern.Institucion.cuentaCompensacion}")
    private String cuentaCompensacion;

    @NotBlank(message = "{NotBlank.Institucion.descripcionCorta}")
    @NotNull(message = "{NotNull.Institucion.descripcionCorta}")
    @Length(min = 3, max = 30, message = "{Length.Institucion.descripcionCorta}")
    private String descripcionCorta;
    private String bancoAdministrador;
    private Integer operadorInstitucion;
    @Range(min = 0, max = 1, message = "{Range.Institucion.institucionEmpresa}", groups = IBasico.class)
    private Integer institucionEmpresa;
    @Length(min = 0, max = 5, message = "{Length.Institucion.codigoVisanet}")
    private String codigoVisanet;
}