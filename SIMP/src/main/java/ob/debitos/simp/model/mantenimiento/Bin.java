package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoBin;
import ob.debitos.simp.validacion.CodigoClaseServicio;
import ob.debitos.simp.validacion.CodigoMembresia;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.IClase;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoClaseServicio(existe = true, groups = IClase.class)
public class Bin
{
    @CodigoBin(existe = true, groups = IActualizacion.class)
    @CodigoBin(existe = false, message = "{Existe.Bin.codigoBIN}", groups = IRegistro.class)
    private String codigoBIN;

    @CodigoMembresia(existe = true, groups = ICampo.class)
    private String codigoMembresia;
    private String codigoClaseServicio;

    @NotNull(message = "{NotNull.Bin.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.Bin.descripcion}", groups = IBasico.class)
    @Length(min = 3, max = 50, message = "{Length.Bin.descripcion}", groups = IBasico.class)
    private String descripcion;

    private Integer codigoInstitucion;
    private String descripcionCortaInstitucion;
    private String descripcionMembresia;
    private String descripcionClaseServicio;
}