package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoClaseTransaccion;
import ob.debitos.simp.validacion.CodigoProcesoSwitch;
import ob.debitos.simp.validacion.IdCodigoTransaccion;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.IClase;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdCodigoTransaccion(existe = true, groups = IClase.class)
public class CodigoProcSwitch
{
    @CodigoProcesoSwitch(existe = true, groups = IActualizacion.class)
    @CodigoProcesoSwitch(existe = false, message = "{Existe.CodigoProcesoSwitch.codigoProcesoSwitch}", groups = IRegistro.class)
    private String codigoProcesoSwitch;

    @NotNull(message = "{NotNull.CodigoProcesoSwitch.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.CodigoProcesoSwitch.descripcion}", groups = IBasico.class)
    @Length(min = 3, max = 80, message = "{Length.CodigoProcesoSwitch.descripcion}", groups = IBasico.class)
    private String descripcion;

    @NotNull(message = "{NotNull.CodigoProcesoSwitch.transaccionMonetaria}", groups = IBasico.class)
    @Range(min = 0, max = 1, message = "{Range.CodigoProcesoSwitch.transaccionMonetaria}", groups = IBasico.class)
    private Integer transaccionMonetaria;

    @NotNull(message = "{NotNull.CodigoProcesoSwitch.aplicaInteres}", groups = IBasico.class)
    @Range(min = 0, max = 1, message = "{Range.CodigoProcesoSwitch.aplicaInteres}", groups = IBasico.class)
    private Integer aplicaInteres;

    @CodigoClaseTransaccion(existe = true, groups = IBasico.class)
    private Integer codigoClaseTransaccion;

    private Integer codigoTransaccion;
    private String descripcionClaseTransaccion;
    private String descripcionCodigoTransaccion;
}