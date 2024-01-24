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
import ob.debitos.simp.validacion.CodigoRptaVisa;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodigoRespuestaVisa
{
    @CodigoRptaVisa(existe = true, groups = IActualizacion.class)
    @CodigoRptaVisa(existe = false, message = "{Existe.CodigoRptaVisa.codigoRespuestaVisa}", groups = IRegistro.class)
    private String codigoRespuestaVisa;

    @NotNull(message = "{NotNull.CodigoRptaVisa.descripcion}")
    @NotBlank(message = "{NotBlank.CodigoRptaVisa.descripcion}")
    @Length(min = 3, max = 80, message = "{Length.CodigoRptaVisa.descripcion}")
    private String descripcion;

    @NotNull(message = "{NotNull.CodigoRptaVisa.atribuible}")
    @Pattern(regexp = Regex.SOLO_1_o_0, message = "{Pattern.CodigoRptaVisa.atribuible}")
    private String atribuible;
}