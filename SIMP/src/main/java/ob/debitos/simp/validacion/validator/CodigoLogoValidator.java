package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ILogoService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoLogo;

public class CodigoLogoValidator
        implements ConstraintValidator<CodigoLogo, String>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired ILogoService logoService;

    @Override
    public void initialize(CodigoLogo anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String codigoLogo,
            ConstraintValidatorContext context)
    {
        if (codigoLogo == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Logo.idLogo}",
                    context);
            return false;
        }

        if (codigoLogo.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.Logo.idLogo}",
                    context);
            return false;
        }

        if (!codigoLogo.matches(Regex.ALFANUMERICO))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.Logo.idLogo}",
                    context);
            return false;
        }

        if (codigoLogo.length() < min || codigoLogo.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.Logo.idLogo}",
                    context);
            return false;
        }

        boolean existeLogo = logoService.existeLogo(codigoLogo);
        return existe ? existeLogo : !existeLogo;
    }

}
