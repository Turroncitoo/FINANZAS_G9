package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IOrigenService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoOrigen;

public class CodigoOrigenValidator implements ConstraintValidator<CodigoOrigen, Integer>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired IOrigenService origenService;

    @Override
    public void initialize(CodigoOrigen anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer codigoOrigen, ConstraintValidatorContext context)
    {
        if (codigoOrigen == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Origen.codigoOrigen}", context);
            return false;
        }
        if (codigoOrigen < min || codigoOrigen > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.Origen.codigoOrigen}", context);
            return false;
        }
        boolean existeOrigen = origenService.existeOrigen(codigoOrigen);
        return existe ? existeOrigen : !existeOrigen;
    }
}