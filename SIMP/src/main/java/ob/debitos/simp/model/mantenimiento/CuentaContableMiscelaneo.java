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
import ob.debitos.simp.utilitario.MultiTablaUtil;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.CodigoClaseServicio;
import ob.debitos.simp.validacion.CodigoClaseTransaccion;
import ob.debitos.simp.validacion.CodigoInstitucion;
import ob.debitos.simp.validacion.CodigoMembresia;
import ob.debitos.simp.validacion.CodigoMoneda;
import ob.debitos.simp.validacion.CodigoOrigen;
import ob.debitos.simp.validacion.IdCliente;
import ob.debitos.simp.validacion.IdCodigoTransaccion;
import ob.debitos.simp.validacion.IdEmpresa;
import ob.debitos.simp.validacion.MultitabDet;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.IClase;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdCliente(existe = true, groups = IClase.class)
@IdCodigoTransaccion(existe = true, groups = IClase.class)
@CodigoClaseServicio(existe = true, groups = IClase.class)
public class CuentaContableMiscelaneo
{
	@CodigoInstitucion(existe = true, groups = ICampo.class)
    private Integer codigoInstitucion;

    @IdEmpresa(existe = true, groups = ICampo.class)
    private String idEmpresa;
    private String idCliente;

    @CodigoMoneda(existe = true, groups = ICampo.class)
    private Integer codigoMoneda;

    @CodigoMembresia(existe = true, groups = ICampo.class)
    private String codigoMembresia;
    private String codigoClaseServicio;

    @CodigoOrigen(existe = true, groups = ICampo.class)
    private Integer codigoOrigen;

    @MultitabDet(existe = true, min = 1, max = 1, campoIdItem = "idRolTransaccion", idTabla = MultiTablaUtil.TABLA_ROL_TRANSACCION, groups = ICampo.class)
    private Integer idRolTransaccion;

    @CodigoClaseTransaccion(existe = true, groups = ICampo.class)
    private Integer codigoClaseTransaccion;
    private Integer codigoTransaccion;
    
    @NotNull(message = "{NotNull.CuentaContableMiscelaneo.cuentaCargo}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.CuentaContableMiscelaneo.cuentaCargo}", groups = IBasico.class)
    @Pattern(regexp = Regex.ALFANUMERICO, message = "{Pattern.CuentaContableMiscelaneo.cuentaCargo}", groups = IBasico.class)
    @Length(max = 20, message = "{Length.CuentaContableMiscelaneo.cuentaCargo}", groups = IBasico.class)
    private String cuentaCargo;
    
    @NotNull(message = "{NotNull.CuentaContableMiscelaneo.cuentaAbono}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.CuentaContableMiscelaneo.cuentaAbono}", groups = IBasico.class)
    @Pattern(regexp = Regex.ALFANUMERICO, message = "{Pattern.CuentaContableMiscelaneo.cuentaAbono}", groups = IBasico.class)
    @Length(max = 20, message = "{Length.CuentaContableMiscelaneo.cuentaAbono}", groups = IBasico.class)
    private String cuentaAbono;
    
    @Pattern(regexp = Regex.VACIO_O_ALFANUMERICO, message = "{Pattern.CuentaContableMiscelaneo.codigoAnalitico}", groups = IBasico.class)
    @Length(max = 10, message = "{Length.CuentaContableMiscelaneo.codigoAnalitico}", groups = IBasico.class)
    private String codigoAnalitico;
    
    @Range(min = 0, max = 1, message = "{Range.CuentaContableMiscelaneo.cuentaHibrida}")
	private Integer cuentaHibrida;

	@Pattern(regexp = Regex.VACIO_O_ALFANUMERICO, message = "{Pattern.CuentaContableMiscelaneo.cuentaCargoH}", groups = IBasico.class)
	@Length(max = 20, message = "{Length.CuentaContableMiscelaneo.cuentaCargoH}", groups = IBasico.class)
	private String cuentaCargoH;

	@Pattern(regexp = Regex.VACIO_O_ALFANUMERICO, message = "{Pattern.CuentaContableMiscelaneo.cuentaAbonoH}", groups = IBasico.class)
	@Length(max = 20, message = "{Length.CuentaContableMiscelaneo.cuentaAbonoH}", groups = IBasico.class)
	private String cuentaAbonoH;

	@Pattern(regexp = Regex.VACIO_O_ALFANUMERICO, message = "{Pattern.CuentaContableMiscelaneo.codigoAnaliticoH}", groups = IBasico.class)
	@Length(max = 20, message = "{Length.CuentaContableMiscelaneo.codigoAnaliticoH}", groups = IBasico.class)
	private String codigoAnaliticoH;

    private String descripcionEmpresa;
    private String descripcionCliente;
    private String descripcionMoneda;
    private String descripcionMembresia;
    private String descripcionClaseServicio;
    private String descripcionOrigen;
    private String descripcionClaseTransaccion;
    private String descripcionCodigoTransaccion;
    private String descripcionRolTransaccion;
    private String descripcionInstitucion;
}