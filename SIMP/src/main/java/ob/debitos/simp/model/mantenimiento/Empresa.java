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
import ob.debitos.simp.validacion.IdEmpresa;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empresa
{
    @IdEmpresa(existe = true, groups = IActualizacion.class)
    @IdEmpresa(existe = false, message = "{Existe.Empresa.idEmpresa}", groups = IRegistro.class)
    private String idEmpresa;

    @NotNull(message = "{NotNull.Empresa.descripcion}")
    @NotBlank(message = "{NotBlank.Empresa.descripcion}")
    @Length(min = 3, max = 70, message = "{Length.CodigoProcesoSwitch.descripcion}", groups = IBasico.class)
    private String descripcion;

    @NotNull(message = "{NotNull.Empresa.ruc}")
    @NotBlank(message = "{NotBlank.Empresa.ruc}")
    @Pattern(regexp = Regex.SOLO_DIGITOS, message = "{Pattern.Empresa.ruc}")
    @Length(min = 11, max = 11, message = "{Length.Empresa.ruc}")
    private String ruc;

    @NotNull(message = "{NotNull.Empresa.direccion}")
    @NotBlank(message = "{NotBlank.Empresa.direccion}")
    private String direccion;
}