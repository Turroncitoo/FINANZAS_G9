package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoIndCorreoTelefono;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndCorreoTelefono
{
    @CodigoIndCorreoTelefono(existe = true, groups = IActualizacion.class)
    @CodigoIndCorreoTelefono(existe = false, message = "{Existe.IndCorreoTelefono.codigoIndCorreoTelefono}", groups = IRegistro.class)
    private String codigoIndCorreoTelefono;

    @NotNull(message = "{NotNull.IndCorreoTelefono.descripcion}")
    @NotBlank(message = "{NotBlank.IndCorreoTelefono.descripcion}")
    @Length(min = 3, max = 80, message = "{Length.IndCorreoTelefono.descripcion}")
    private String descripcion;
}