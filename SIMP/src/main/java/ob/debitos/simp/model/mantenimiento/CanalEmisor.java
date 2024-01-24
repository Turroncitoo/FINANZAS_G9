package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoCanalEmisor;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanalEmisor
{
    @CodigoCanalEmisor(existe = true, groups = IActualizacion.class)
    @CodigoCanalEmisor(existe = false, message = "{Existe.CanalEmisor.codigoCanalEmisor}", groups = IRegistro.class)
    private String codigoCanalEmisor;

    @NotNull(message = "{NotNull.CanalEmisor.descripcion}")
    @NotBlank(message = "{NotBlank.CanalEmisor.descripcion}")
    @Length(min = 3, max = 40, message = "{Length.CanalEmisor.descripcion}")
    private String descripcion;
}