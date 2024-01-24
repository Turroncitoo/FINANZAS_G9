package ob.debitos.simp.model.tarifario;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoMoneda;
import ob.debitos.simp.validacion.IdTarifarioEmisor;
import ob.debitos.simp.validacion.IdTipoTarifa;
import ob.debitos.simp.validacion.MultitabDet;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdTarifarioEmisor(existe = true, groups = IActualizacion.class)
@IdTarifarioEmisor(existe = false, groups = IRegistro.class)
public class TarifarioEmisor
{
    private Integer codigoInstitucion;

    @NotNull(message = "{NotNull.TarifarioAdq.nivel}", groups = IBasico.class)
    @Digits(integer = 2, fraction = 0, message = "{Digits.TarifarioAdq.nivel}", groups = IBasico.class)
    private Integer nivel;

    @IdTipoTarifa(existe = true, groups = ICampo.class)
    private Integer codigoTipoTarifa;

    @MultitabDet(existe = true, idTabla = 7, campoIdItem = "codigoGrupoBin", groups = ICampo.class)
    private String codigoGrupoBin;

    @NotNull(message = "{NotNull.TarifarioAdq.transaccion}", groups = IBasico.class)
    @Digits(integer = 4, fraction = 0, message = "{Digits.TarifarioEmisor.transaccion}", groups = IBasico.class)
    private Integer transaccion;

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

    @NotNull(message = "{NotNull.TarifarioEmisor.tarifaFija}")
    @Range(min = 0, max = 1, message = "{Range.TarifarioEmisor.tarifaFija}")
    private Integer tarifaFija;

    @NotNull(message = "{NotNull.TarifarioEmisor.aplicaIgv}")
    @Range(min = 0, max = 1, message = "{Pattern.TarifarioEmisor.aplicaIgv}")
    private String aplicaIgv;

    private Integer aplicaTransaccion;
    private String descripcionInstitucion;
    private String descripcionTipoTarifa;
    private String descripcionGrupoBin;
    private String descripcionCodigoTransaccion;
    private String descripcionMoneda;
}