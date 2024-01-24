package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoClaseServicio;
import ob.debitos.simp.validacion.CodigoMembresia;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoClaseServicio(existe = true, groups = IActualizacion.class)
@CodigoClaseServicio(existe = false, groups = IRegistro.class)
public class ClaseServicio
{
    private String codigoClaseServicio;

    @CodigoMembresia(existe = true, groups = ICampo.class)
    private String codigoMembresia;

    @NotNull(message = "{NotNull.ClaseServicio.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.ClaseServicio.descripcion}", groups = IBasico.class)
    @Length(min = 3, max = 30, message = "{Length.ClaseServicio.descripcion}", groups = IBasico.class)
    private String descripcion;
    private String descripcionMembresia;
}