package ob.debitos.simp.model.seguridad;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.IdPerfil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Perfil
{
    @IdPerfil(existe = true, groups = IActualizacion.class)
    @IdPerfil(existe = false, message = "{Existe.Perfil.idPerfil}", groups = IRegistro.class)
    private String idPerfil;

    @NotNull(message = "{NotNull.Perfil.descripcion}")
    @NotBlank(message = "{NotBlank.Perfil.descripcion}")
    @Length(min = 3, max = 50, message = "{Length.Perfil.descripcion}")
    private String descripcion;

    private boolean visualizaPAN;

    private List<SecRecurso> recursos;
}