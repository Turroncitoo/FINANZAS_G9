package ob.debitos.simp.model.seguridad;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoUsuario;
import ob.debitos.simp.validacion.Contrasenia;
import ob.debitos.simp.validacion.IdPerfil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario
{
    @CodigoUsuario(existe = true, groups = IActualizacion.class)
    @CodigoUsuario(existe = false, message = "{Existe.Usuario.idUsuario}", groups = IRegistro.class)
    private String idUsuario;
    
    @IdPerfil(existe = true)
    private String idPerfil;
    private String descripcionPerfil;

    @Contrasenia
    private String contrasenia;
    private String contraseniaConfirmacion;
    private String contraseniaEncriptada;
    private Date fechaAdicion;
    private boolean activo;
    private boolean cambioContrasenia;
    private boolean visualizaPAN;
    private boolean visualizaPANPerfil;
}