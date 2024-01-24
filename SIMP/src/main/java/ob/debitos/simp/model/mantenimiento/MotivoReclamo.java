package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.IdMotivoReclamo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MotivoReclamo
{
    @IdMotivoReclamo(existe = true, groups = IActualizacion.class)
    @IdMotivoReclamo(existe = false, message = "{Existe.MotivoReclamo.idMotivo}", groups = IRegistro.class)
    private Integer idMotivo;

    @NotNull(message = "{NotNull.MotivoReclamo.descripcion}")
    @NotBlank(message = "{NotBlank.MotivoReclamo.descripcion}")
    @Length(min = 3, max = 60, message = "{Length.MotivoReclamo.descripcion}")
    private String descripcion;
}