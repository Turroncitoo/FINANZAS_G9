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
import ob.debitos.simp.validacion.IdConceptoComision;
import ob.debitos.simp.validacion.grupo.IBasico;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContabConceptoCuenta
{
    @IdConceptoComision(existe = true, incluirCero = true, groups = IBasico.class)
    private Integer idConceptoComision;

    private String descripcionConceptoComision;

    @NotNull(message = "{NotNull.ContabConceptoCuenta.cuentaCargo}", groups = IBasico.class)
    //@NotBlank(message = "{NotBlank.ContabConceptoCuenta.cuentaCargo}", groups = IBasico.class)
    //@Pattern(regexp = Regex.ALFANUMERICO, message = "{Pattern.ContabConceptoCuenta.cuentaCargo}", groups = IBasico.class)
    @Length(max = 20, message = "{Length.ContabConceptoCuenta.cuentaCargo}", groups = IBasico.class)
    private String cuentaCargo;

    @NotNull(message = "{NotNull.ContabConceptoCuenta.cuentaAbono}", groups = IBasico.class)
    //@NotBlank(message = "{NotBlank.ContabConceptoCuenta.cuentaAbono}", groups = IBasico.class)
    //@Pattern(regexp = Regex.ALFANUMERICO, message = "{Pattern.ContabConceptoCuenta.cuentaAbono}", groups = IBasico.class)
    @Length(max = 20, message = "{Length.ContabConceptoCuenta.cuentaAbono}", groups = IBasico.class)
    private String cuentaAbono;
    
    private String cuentaAtm;

    //@Pattern(regexp = Regex.VACIO_O_ALFANUMERICO, message = "{Pattern.ContabConceptoCuenta.codigoAnalitico}", groups = IBasico.class)
    @Length(max = 10, message = "{Length.ContabConceptoCuenta.codigoAnalitico}", groups = IBasico.class)
    private String codigoAnalitico;

    @NotNull(message = "{NotNull.ContabConceptoCuenta.tipoCompensacion}", groups = IBasico.class)
    @Pattern(regexp = Regex.TIPO_COMPENSACION, message = "{Pattern.ContabConceptoCuenta.tipoCompensacion}", groups = IBasico.class)
    private String tipoCompensacion;
}