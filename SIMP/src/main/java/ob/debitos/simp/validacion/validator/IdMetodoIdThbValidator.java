package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IMetodoIdThbService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdMetodo;

public class IdMetodoIdThbValidator implements ConstraintValidator<IdMetodo, String>
{
    private boolean existe;
    private int max;

    private @Autowired IMetodoIdThbService metodoIdThbService;

    @Override
    public void initialize(IdMetodo anotacion)
    {
        this.existe = anotacion.existe();
        this.max = anotacion.max();
    }

    @Override
    public boolean isValid(String idMetodo, ConstraintValidatorContext context)
    {
        if (idMetodo == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.MetodoIdThb.idMetodo}", context);
            return false;
        }
        if (idMetodo.length() > 0 && idMetodo.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.MetodoIdThb.idMetodo}", context);
            return false;
        }
        if (!idMetodo.matches(Regex.SOLO_DIGITOS_O_VACIO))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.MetodoIdThb.idMetodo}", context);
            return false;
        }
        if (idMetodo.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.MetodoIdThb.idMetodo}", context);
            return false;
        }
        boolean existeMetodoIdThn = metodoIdThbService.existeMetodoIdThb(idMetodo);
        return (existe) ? existeMetodoIdThn : !existeMetodoIdThn;
    }
}