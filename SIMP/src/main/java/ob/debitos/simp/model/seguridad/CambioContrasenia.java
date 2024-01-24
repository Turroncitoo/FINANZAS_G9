package ob.debitos.simp.model.seguridad;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.Contrasenia;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CambioContrasenia
{
    @NotNull(message = "{NotNull.CambioContrasenia.contraseniaActual}")
    private String contraseniaActual;

    @Contrasenia(permiteContraseniaDefault = false)
    private String contrasenia;

    @NotNull(message = "{NotNull.CambioContrasenia.contraseniaConfirmacion}")
    private String contraseniaConfirmacion;

    private String idUsuario;
    private String passwordEncriptado;
    private String passwordEncriptadoNuevo;
    private String passwordEncriptadoNuevo2;
}