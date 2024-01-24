package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoCuentaAjuste;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IEliminacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

/**
 * Representa la información de las cuentas contables utilizadas para las
 * transacciones con diferencias en los montos	 por tipo de cambio.
 * 
 * @author Hanz Llanto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoCuentaAjuste(existe = true, groups = IActualizacion.class)
@CodigoCuentaAjuste(existe = true, groups = IEliminacion.class)
@CodigoCuentaAjuste(existe = false, message = "{Existe.CuentaAjuste.rolTransaccion}", groups = IRegistro.class)
public class CuentaAjuste
{
    @NotNull(message = "{NotNull.CuentaAjuste.tipoMovimiento}")
    private Integer tipoMovimiento;

    @NotNull(message = "{NotNull.CuentaAjuste.registroContable}")
    private String registroContable;

    @NotNull(message = "{NotNull.CuentaAjuste.monedaCompensacion}")
    private Integer monedaCompensacion;

    @NotNull(message = "{NotNull.CuentaAjuste.rolTransaccion}")
    private Integer rolTransaccion;

    @NotNull(message = "{NotNull.CuentaAjuste.cuentaCargo}")
    @Length(max = 20, message = "{Length.CuentaAjuste.cuentaCargo}", groups = IBasico.class)
    private String cuentaCargo;

    @NotNull(message = "{NotNull.CuentaAjuste.cuentaAbono}")
    @Length(max = 20, message = "{Length.CuentaAjuste.cuentaAbono}", groups = IBasico.class)
    private String cuentaAbono;

    @Length(max = 10, message = "{Length.CuentaAjuste.codigoAnalitico}", groups = IBasico.class)
    private String codigoAnalitico;

    private Integer nuevoTipoMovimiento;

    private String nuevoRegistroContable;

    private Integer nuevoMonedaCompensacion;

    private Integer nuevoRolTransaccion;

    private String descripcionRolTransaccion;
    private String descripcionTipoMovimiento;
    private String descripcionRegistroContable;
    private String descripcionMonedaCompensacion;
}