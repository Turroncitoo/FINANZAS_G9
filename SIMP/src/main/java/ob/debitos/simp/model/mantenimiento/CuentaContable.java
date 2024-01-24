package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.CodigoMoneda;
import ob.debitos.simp.validacion.IdCuentaContable;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;

/**
 * Representa la información de las cuentas contables de la institución.
 *
 * @author Hanz Llanto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaContable
{
    @IdCuentaContable(existe = true, groups = IActualizacion.class)
    private Integer idCuenta;

    @NotNull(message = "{NotNull.CuentaContable.numeroCuentaContable}")
    @Pattern(regexp = Regex.SOLO_DIGITOS, message = "{Pattern.CuentaContable.numeroCuentaContable}", groups = IBasico.class)
    @Length(max = 20, message = "{Length.CuentaContable.numeroCuentaContable}", groups = IBasico.class)
    private String numeroCuentaContable;

    @Length(max = 50, message = "{Length.CuentaContable.descripcion}", groups = IBasico.class)
    private String descripcion;

    @CodigoMoneda(existe = true)
    private Integer codigoMoneda;

    @Range(min = 0, max = 1, message = "{Range.CuentaContable.cuentaATM}")
    private Integer cuentaATM;

    @Range(min = 0, max = 1, message = "{Range.CuentaContable.cuentaBase}")
    private Integer cuentaBase;

    @Range(min = 0, max = 1, message = "{Range.CuentaContable.cuentaAjuste}")
    private Integer cuentaAjuste;

    private Integer longitudCuenta;

    private Integer idGenerado;

    private String descripcionMoneda;

    private String tipoCuenta;

    private String nombreCuenta;

    private String idCuentaAlegra;

    private String descripcionTipoCuenta;
}