package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.IdCanal;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Canal
{

    @IdCanal(existe = true, groups = IActualizacion.class)
    @IdCanal(existe = false, message = "{Existe.Canal.idCanal}", groups = IRegistro.class)
    private Integer idCanal;

    @NotNull(message = "{NotNull.Canal.descripcion}")
    @NotBlank(message = "{NotBlank.Canal.descripcion}")
    @Length(min = 3, max = 50, message = "{Length.Canal.descripcion}")
    private String descripcion;

}