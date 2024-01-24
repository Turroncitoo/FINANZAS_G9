package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICodigoProcesoSwitchService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoProcesoSwitch;

public class CodigoProcesoSwitchValidator
        implements ConstraintValidator<CodigoProcesoSwitch, String>
{
    private int tamanio;
    private boolean existe;

    private @Autowired ICodigoProcesoSwitchService codigoProcesoSwitchService;

    @Override
    public void initialize(CodigoProcesoSwitch anotacion)
    {
        this.existe = anotacion.existe();
        this.tamanio = anotacion.tamanio();
    }

    @Override
    public boolean isValid(String codigoProcesoSwitch, ConstraintValidatorContext context)
    {
        if (codigoProcesoSwitch == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.CodigoProcesoSwitch.codigoProcesoSwitch}", context);
            return false;
        }
        if (codigoProcesoSwitch.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotBlank.CodigoProcesoSwitch.codigoProcesoSwitch}", context);
            return false;
        }
        if (codigoProcesoSwitch.length() != tamanio)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Length.CodigoProcesoSwitch.codigoProcesoSwitch}", context);
            return false;
        }
        if (!codigoProcesoSwitch.matches(Regex.SOLO_DIGITOS))
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Pattern.CodigoProcesoSwitch.codigoProcesoSwitch}", context);
            return false;
        }
        boolean existeCodigoProcesoSwitch = codigoProcesoSwitchService
                .existeCodigoProcesoSwitch(codigoProcesoSwitch);
        return existe ? existeCodigoProcesoSwitch : !existeCodigoProcesoSwitch;
    }
}