package ob.debitos.simp.model.tarifario;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoMoneda;
import ob.debitos.simp.validacion.IdSurcharge;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdSurcharge(existe = true, groups = IActualizacion.class)
@IdSurcharge(existe = false, groups = IRegistro.class)
public class Surcharge
{
    private Integer codigoInstitucion;

    @NotNull(message = "{NotNull.Surcharge.nivel}", groups = IBasico.class)
    @Digits(integer = 2, fraction = 0, message = "{Digits.TarifarioAdq.nivel}", groups = IBasico.class)
    private Integer nivel;

    @NotNull(message = "{NotNull.Surcharge.rangoInicial}", groups = IBasico.class)
    @Digits(integer = 8, fraction = 0, message = "{Digits.Surcharge.rangoInicial}", groups = IBasico.class)
    private Integer rangoInicial;

    @NotNull(message = "{NotNull.Surcharge.rangoFinal}", groups = IBasico.class)
    @Digits(integer = 8, fraction = 0, message = "{Digits.Surcharge.rangoFinal}", groups = IBasico.class)
    private Integer rangoFinal;

    @CodigoMoneda(existe = true, groups = ICampo.class)
    private Integer codigoMoneda;

    @NotNull(message = "{NotNull.Surcharge.surchargeFlat}", groups = IBasico.class)
    @Digits(integer = 8, fraction = 4, message = "{Digits.Surcharge.surchargeFlat}", groups = IBasico.class)
    private Double surchargeFlat;

    @NotNull(message = "{NotNull.Surcharge.surchargePorc}", groups = IBasico.class)
    @Digits(integer = 8, fraction = 4, message = "{Digits.Surcharge.surchargePorc}", groups = IBasico.class)
    private Double surchargePorc;

    @NotNull(message = "{NotNull.Surcharge.tarifaFija}")
    @Range(min = 0, max = 1, message = "{Range.Surcharge.tarifaFija}")
    private Integer tarifaFija;

    @NotNull(message = "{NotNull.Surcharge.aplicaIgv}")
    @Range(min = 0, max = 1, message = "{Pattern.Surcharge.aplicaIgv}")
    private String aplicaIgv;

    private String descripcionInstitucion;
    private String descripcionMoneda;
}