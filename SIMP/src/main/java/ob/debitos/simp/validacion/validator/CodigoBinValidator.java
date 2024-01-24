package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ILogoService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoBin;

public class CodigoBinValidator implements ConstraintValidator<CodigoBin, String>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired ILogoService binService;

    @Override
    public void initialize(CodigoBin anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String codigoBIN, ConstraintValidatorContext context)
    {
        if (codigoBIN == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Bin.codigoBIN}", context);
            return false;
        }
        if (codigoBIN.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.Bin.codigoBIN}", context);
            return false;
        }
        if (!codigoBIN.matches(Regex.SOLO_DIGITOS))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.Bin.codigoBIN}", context);
            return false;
        }
        if (codigoBIN.length() < min || codigoBIN.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.Bin.codigoBIN}", context);
            return false;
        }
        boolean existeBIN = binService.existeBin(codigoBIN);
        return (existe) ? existeBIN : !existeBIN;
    }
}
