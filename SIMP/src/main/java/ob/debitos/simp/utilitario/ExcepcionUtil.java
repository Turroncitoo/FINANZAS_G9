package ob.debitos.simp.utilitario;

import org.springframework.validation.Errors;

import ob.debitos.simp.service.excepcion.BadRequestException;

public class ExcepcionUtil
{

    private ExcepcionUtil()
    {
    }

    public static String traducirMensajeDesdeMensajeErrorLdap(String mensajeErrorLdap, String idUsuario)
    {
        String mensajeError = ConstantesExcepciones.ERROR_DESCONOCIDO;
        if (mensajeErrorLdap.contains(ConstantesExcepciones.CODIGO_AD_CONTRASENIA_INCORRECTA))
        {
            mensajeError = ConstantesExcepciones.CONTRASENIA_INCORRECTA;
        } else if (mensajeErrorLdap.contains(ConstantesExcepciones.CODIGO_AD_CONTRASENIA_EXPIRADA))
        {
            mensajeError = ConstantesExcepciones.CONTRASENIA_EXPIRADA;
        } else if (mensajeErrorLdap.contains(ConstantesExcepciones.CODIGO_AD_USUARIO_NO_ACTIVO))
        {
            mensajeError = String.format(ConstantesExcepciones.USUARIO_NO_ACTIVO, idUsuario);
        } else if (mensajeErrorLdap.contains(ConstantesExcepciones.CODIGO_AD_USUARIO_EXPIRADO))
        {
            mensajeError = String.format(ConstantesExcepciones.USUARIO_EXPIRADO, idUsuario);
        } else if (mensajeErrorLdap.contains(ConstantesExcepciones.CODIGO_AD_CONTRASENIA_DEBE_CAMBIAR))
        {
            mensajeError = ConstantesExcepciones.CONTRASENIA_DEBE_CAMBIAR;
        } else if (mensajeErrorLdap.contains(ConstantesExcepciones.CODIGO_AD_DIRECTORIO_NO_ENCONTRADO))
        {
            mensajeError = ConstantesExcepciones.DIRECTORIO_NO_ENCONTRADO;
        }
        return StringsUtils.concatenarCadena(ConstantesGenerales.ACTIVE_DIRECTORY, mensajeError);
    }

    public static void lanzarExcepcionSiHayErrores(Errors error)
    {
        if (error.hasErrors())
        {
            throw new BadRequestException(ValidatorUtil.obtenerMensajeValidacionError(error));
        }
    }

    public static String obtenerMensajeExcepcionRaiz(Exception exception)
    {
        return (exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage());
    }

}