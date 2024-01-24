package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoClaseServicio;
import ob.debitos.simp.validacion.CodigoInstitucion;
import ob.debitos.simp.validacion.CodigoLogo;
import ob.debitos.simp.validacion.CodigoMembresia;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Logo
{
    @CodigoInstitucion(existe = true, groups = ICampo.class)
    private Integer idInstitucion;

    @CodigoLogo(existe = true, groups = IActualizacion.class)
    @CodigoLogo(existe = false, message = "{ExisteLogo.Logo.idLogo}", groups = IRegistro.class)
    private String idLogo;

    @NotNull(message = "{NotNull.Logo.descripcion}")
    @NotBlank(message = "{NotBlank.Logo.descripcion}")
    @Length(min = 3, max = 40, message = "{Length.Logo.descripcion}")
    private String descripcion;

    @CodigoMembresia(existe = true, groups = ICampo.class)
    private String idMembresia;

    @CodigoClaseServicio(existe = true, groups = ICampo.class)
    private String idClaseServicio;

    @Range(min = 3, max = 11, message = "{Range.Logo.longitudBin}", groups = IBasico.class)
    private Integer longitudBin;

    @Range(min = 14, max = 19, message = "{Range.Logo.longitudPan}", groups = IBasico.class)
    private Integer longitudPan;

    @NotNull(message = "{NotNull.Logo.idBin}")
    private String idBin;

    @NotNull(message = "{NotNull.Logo.rangoInicialTarjeta}")
    private Long rangoInicialTarjeta;

    @NotNull(message = "{NotNull.Logo.rangoFinalTarjeta}")
    private Long rangoFinalTarjeta;

    // DESCRIPCION
    private String descInstitucion;
    private String descMembresia;
    private String descClaseServicio;
}
