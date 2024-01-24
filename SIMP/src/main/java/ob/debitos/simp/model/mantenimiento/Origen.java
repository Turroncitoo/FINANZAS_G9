package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoOrigen;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Origen
{
    @CodigoOrigen(existe = true, groups = IActualizacion.class)
    @CodigoOrigen(existe = false, message = "{Existe.Origen.codigoOrigen}", groups = IRegistro.class)
    private Integer codigoOrigen;

    @NotNull(message = "{NotNull.Origen.descripcion}")
    @NotBlank(message = "{NotBlank.Origen.descripcion}")
    @Length(min = 3, max = 40, message = "{Length.Origen.descripcion}")
    private String descripcion;
}