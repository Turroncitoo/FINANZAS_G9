package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IEsquemaTarService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdEsquemaTar;

public class IdEsquemaTarValidator implements ConstraintValidator<IdEsquemaTar, Integer>
{
    private int min;
    private int max;
    private boolean existe;
    private @Autowired IEsquemaTarService esquemaTarService;

    @Override
    public void initialize(IdEsquemaTar anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer codigoEsquema, ConstraintValidatorContext context)
    {
        if (codigoEsquema == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.EsquemaTar.codigoEsquema}", context);
            return false;
        }
        if (codigoEsquema < min || codigoEsquema > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.EsquemaTar.codigoEsquema}", context);
            return false;
        }
        return (existe) ? !esquemaTarService.buscarPorIdEsquema(codigoEsquema).isEmpty() : esquemaTarService.buscarPorIdEsquema(codigoEsquema).isEmpty();
    }
}