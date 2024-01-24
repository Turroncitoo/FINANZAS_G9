package ob.debitos.simp.model.tarifario;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoMoneda;
import ob.debitos.simp.validacion.IdTarifarioAdq;
import ob.debitos.simp.validacion.IdTipoTarifa;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdTarifarioAdq(existe = true, groups = IActualizacion.class)
@IdTarifarioAdq(existe = false, groups = IRegistro.class)
public class TarifarioAdq
{
    private Integer codigoInstitucion;

    @NotNull(message = "{NotNull.TarifarioAdq.nivel}", groups = IBasico.class)
    @Digits(integer = 2, fraction = 0, message = "{Digits.TarifarioAdq.nivel}", groups = IBasico.class)
    private Integer nivel;

    @IdTipoTarifa(existe = true, groups = ICampo.class)
    private Integer codigoTipoTarifa;

    @NotNull(message = "{NotNull.TarifarioAdq.rangoInicial}", groups = IBasico.class)
    @Digits(integer = 8, fraction = 0, message = "{Digits.TarifarioAdq.rangoInicial}", groups = IBasico.class)
    private Integer rangoInicial;

    @NotNull(message = "{NotNull.TarifarioAdq.rangoFinal}", groups = IBasico.class)
    @Digits(integer = 8, fraction = 0, message = "{Digits.TarifarioAdq.rangoFinal}", groups = IBasico.class)
    private Integer rangoFinal;

    @CodigoMoneda(existe = true, groups = ICampo.class)
    private Integer codigoMoneda;

    @NotNull(message = "{NotNull.TarifarioAdq.tarifaFlat}", groups = IBasico.class)
    @Digits(integer = 8, fraction = 4, message = "{Digits.TarifarioAdq.tarifaFlat}", groups = IBasico.class)
    private Double tarifaFlat;

    @NotNull(message = "{NotNull.TarifarioAdq.tarifaPorc}", groups = IBasico.class)
    @Digits(integer = 8, fraction = 4, message = "{Digits.TarifarioAdq.tarifaPorc}", groups = IBasico.class)
    private Double tarifaPorc;

    @NotNull(message = "{NotNull.TarifarioAdq.tarifaFija}", groups = IBasico.class)
    @Range(min = 0, max = 1, message = "{Range.TarifarioAdq.tarifaFija}", groups = IBasico.class)
    private Integer tarifaFija;

    @NotNull(message = "{NotNull.TarifarioAdq.aplicaIgv}", groups = IBasico.class)
    @Range(min = 0, max = 1, message = "{Range.TarifarioAdq.aplicaIgv}", groups = IBasico.class)
    private String aplicaIgv;

    private String descripcionInstitucion;
    private String descripcionTipoTarifa;
    private String descripcionMoneda;
}