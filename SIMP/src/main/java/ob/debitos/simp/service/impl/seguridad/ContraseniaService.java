package ob.debitos.simp.service.impl.seguridad;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.mapper.IContraseniaMapper;
import ob.debitos.simp.model.parametro.MensajeValidacion;
import ob.debitos.simp.model.seguridad.CambioContrasenia;
import ob.debitos.simp.service.IContraseniaService;
import ob.debitos.simp.service.IUsuarioService;
import ob.debitos.simp.service.excepcion.BadRequestException;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.ConstantesGenerales;

@Service
public class ContraseniaService implements IContraseniaService
{
    private @Autowired IUsuarioService usuarioService;
    private @Autowired PasswordEncoder passwordEnconder;
    private @Autowired IContraseniaMapper contraseniaMapper;

    @Override
    public void actualizarContrasenia(CambioContrasenia cambioContrasenia)
    {
        cambioContrasenia.setIdUsuario(SecurityContextFacade.obtenerNombreUsuario());
        if (!this.usuarioService.esContraseniaCorrectaPorIdUsuario(cambioContrasenia.getIdUsuario(),
                cambioContrasenia.getContraseniaActual()))
        {
            throw new BadRequestException(Arrays
                    .asList(new MensajeValidacion(ConstantesGenerales.CAMPO_CONTRASENIA_ACTUAL,
                            ConstantesExcepciones.CONTRASENIA_INCORRECTA)));
        }
        this.usuarioService.validarIgualdadContrasenias(cambioContrasenia.getContrasenia(),
                cambioContrasenia.getContraseniaConfirmacion());
        String hashedPassword = this.passwordEnconder
                .encode(cambioContrasenia.getContraseniaActual());
        String hashedPasswordNuevo = this.passwordEnconder
                .encode(cambioContrasenia.getContrasenia());
        String hashedPasswordNuevo2 = this.passwordEnconder
                .encode(cambioContrasenia.getContraseniaConfirmacion());
        cambioContrasenia.setPasswordEncriptado(hashedPassword);
        cambioContrasenia.setPasswordEncriptadoNuevo(hashedPasswordNuevo);
        cambioContrasenia.setPasswordEncriptadoNuevo2(hashedPasswordNuevo2);
        this.contraseniaMapper.actualizarContrasenia(cambioContrasenia);
    }
}