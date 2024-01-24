package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IIndCorreoTelefonoService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoIndCorreoTelefono;

public class CodigoIndCorreoTelefonoValidator implements ConstraintValidator<CodigoIndCorreoTelefono, String>
{
    private boolean existe;
    private int max;

    private @Autowired IIndCorreoTelefonoService indCorreoTelefonoService;

    @Override
    public void initialize(CodigoIndCorreoTelefono anotacion)
    {
        this.existe = anotacion.existe();
        this.max = anotacion.max();
    }

    @Override
    public boolean isValid(String codigoIndCorreoTelefono, ConstraintValidatorContext context)
    {
        if (codigoIndCorreoTelefono == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.IndCorreoTelefono.codigoIndCorreoTelefono}", context);
            return false;
        }
        if (codigoIndCorreoTelefono.length() > 0 && codigoIndCorreoTelefono.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotBlank.IndCorreoTelefono.codigoIndCorreoTelefono}", context);
            return false;
        }
        if (!codigoIndCorreoTelefono.matches(Regex.SOLO_DIGITOS_O_VACIO))
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Pattern.IndCorreoTelefono.codigoIndCorreoTelefono}", context);
            return false;
        }
        if (codigoIndCorreoTelefono.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Length.IndCorreoTelefono.codigoIndCorreoTelefono}", context);
            return false;
        }
        boolean existeIndCorreoTelefono = indCorreoTelefonoService
                .existeIndCorreoTelefono(codigoIndCorreoTelefono);
        return (existe) ? existeIndCorreoTelefono : !existeIndCorreoTelefono;
    }
}