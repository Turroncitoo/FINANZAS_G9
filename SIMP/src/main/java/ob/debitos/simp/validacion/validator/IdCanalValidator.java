package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICanalService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdCanal;

public class IdCanalValidator implements ConstraintValidator<IdCanal, Integer>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired ICanalService canalService;

    @Override
    public void initialize(IdCanal anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer idCanal, ConstraintValidatorContext context)
    {
        if (idCanal == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Canal.idCanal}", context);
            return false;
        }
        if (idCanal < min || idCanal > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.Canal.idCanal}", context);
            return false;
        }
        boolean existeCanal = canalService.existeCanal(idCanal);
        return existe ? existeCanal : !existeCanal;
    }
}