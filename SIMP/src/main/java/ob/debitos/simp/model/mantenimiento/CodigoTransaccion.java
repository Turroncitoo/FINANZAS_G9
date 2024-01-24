package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.CodigoClaseTransaccion;
import ob.debitos.simp.validacion.IdCodigoTransaccion;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdCodigoTransaccion(existe = true, groups = IActualizacion.class)
@IdCodigoTransaccion(existe = false, groups = IRegistro.class)
public class CodigoTransaccion
{
    
    private Integer codigoTransaccion;

    @NotNull(message = "{NotNull.CodigoTransaccion.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.CodigoTransaccion.descripcion}", groups = IBasico.class)
    @Length(min = 3, max = 100, message = "{Length.CodigoTransaccion.descripcion}", groups = IBasico.class)
    private String descripcion;

    @NotNull(message = "{NotNull.CodigoTransaccion.transaccionMiscelanea}", groups = IBasico.class)
    @Pattern(regexp = Regex.SOLO_1_o_0, message = "{Pattern.CodigoTransaccion.transaccionMiscelanea}", groups = IBasico.class)
    private String transaccionMiscelanea;

    @NotNull(message = "{NotNull.CodigoTransaccion.compensaFondos}", groups = IBasico.class)
    @Range(min = 0, max = 1, message = "{Range.CodigoTransaccion.compensaFondos}", groups = IBasico.class)
    private Integer compensaFondos;

    @NotNull(message = "{NotNull.CodigoTransaccion.compensaComisiones}", groups = IBasico.class)
    @Range(min = 0, max = 1, message = "{Range.CodigoTransaccion.compensaComisiones}", groups = IBasico.class)
    private Integer compensaComisiones;

    @CodigoClaseTransaccion(existe = true, groups = ICampo.class)
    private Integer codigoClaseTransaccion;

    private String descripcionClaseTransaccion;

    @Pattern(regexp = "[A-C]", message = "{Pattern.CodigoTransaccion.registroContable}", groups = IBasico.class)
    private String registroContable;

    private String descripcionRegistroContable;
}