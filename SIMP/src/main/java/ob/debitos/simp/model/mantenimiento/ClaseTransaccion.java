
package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoClaseTransaccion;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaseTransaccion
{
    @CodigoClaseTransaccion(existe = true, groups = IActualizacion.class)
    @CodigoClaseTransaccion(existe = false, message = "{Existe.ClaseTransaccion.codigoClaseTransaccion}", groups = IRegistro.class)
    private Integer codigoClaseTransaccion;

    @NotNull(message = "{NotNull.ClaseTransaccion.descripcion}")
    @NotBlank(message = "{NotBlank.ClaseTransaccion.descripcion}")
    @Length(min = 3, max = 80, message = "{Length.ClaseTransaccion.descripcion}")
    private String descripcion;
}