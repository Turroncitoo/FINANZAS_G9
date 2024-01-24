package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICanalEmisorService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoCanalEmisor;

public class CodigoCanalEmisorValidator implements ConstraintValidator<CodigoCanalEmisor, String>
{
    private int max;
    private int min;
    private boolean existe;

    private @Autowired ICanalEmisorService canalEmisorService;

    @Override
    public void initialize(CodigoCanalEmisor anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String codigoCanalEmisor, ConstraintValidatorContext context)
    {
        if (codigoCanalEmisor == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.CanalEmisor.codigoCanalEmisor}",
                    context);
            return false;
        }
        if (codigoCanalEmisor.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.CanalEmisor.codigoCanalEmisor}",
                    context);
            return false;
        }
        if (!codigoCanalEmisor.matches(Regex.ALFANUMERICO))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.CanalEmisor.codigoCanalEmisor}",
                    context);
            return false;
        }
        if (codigoCanalEmisor.length() > max || codigoCanalEmisor.length() < min)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.CanalEmisor.codigoCanalEmisor}",
                    context);
            return false;
        }
        boolean existeCanalEmisor = canalEmisorService.existeCanalEmisor(codigoCanalEmisor);
        return existe ? existeCanalEmisor : !existeCanalEmisor;
    }
}