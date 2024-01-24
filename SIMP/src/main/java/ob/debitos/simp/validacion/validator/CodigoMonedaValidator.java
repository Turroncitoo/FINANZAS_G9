package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoMoneda;

public class CodigoMonedaValidator implements ConstraintValidator<CodigoMoneda, Integer>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired IMonedaService monedaService;

    @Override
    public void initialize(CodigoMoneda anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer codigoMoneda, ConstraintValidatorContext context)
    {
        if (codigoMoneda == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Moneda.codigoMoneda}", context);
            return false;
        }
        if (codigoMoneda < min || codigoMoneda > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.Moneda.codigoMoneda}", context);
            return false;
        }
        boolean existeMoneda = monedaService.existeMoneda(codigoMoneda);
        return (existe) ? existeMoneda : !existeMoneda;
    }
}