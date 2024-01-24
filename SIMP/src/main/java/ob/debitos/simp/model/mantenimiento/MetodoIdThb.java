package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.IdMetodo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetodoIdThb
{
    @IdMetodo(existe = true, groups = IActualizacion.class)
    @IdMetodo(existe = false, message = "{Existe.MetodoIdThb.idMetodo}", groups = IRegistro.class)
    private String idMetodo;

    @NotNull(message = "{NotNull.MetodoIdThb.descripcion}")
    @NotBlank(message = "{NotBlank.MetodoIdThb.descripcion}")
    @Length(min = 3, max = 50, message = "{Length.MetodoIdThb.descripcion}")
    private String descripcion;
}