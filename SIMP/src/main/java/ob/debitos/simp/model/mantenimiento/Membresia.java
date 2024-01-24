package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoMembresia;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Membresia
{
    @CodigoMembresia(existe = true, groups = IActualizacion.class)
    @CodigoMembresia(existe = false, message = "{Existe.Membresia.codigoMembresia}", groups = IRegistro.class)
    private String codigoMembresia;

    @NotNull(message = "{NotNull.Membresia.descripcion}")
    @NotBlank(message = "{NotBlank.Membresia.descripcion}")
    @Length(min = 3, max = 20, message = "{Length.Membresia.descripcion}")
    private String descripcion;
}