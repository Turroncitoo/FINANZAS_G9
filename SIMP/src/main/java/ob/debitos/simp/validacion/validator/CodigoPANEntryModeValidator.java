package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IPANEntryModeService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoPANEntryMode;

/**
 * Evalúa las restricciones de validación y existencia del el modo de entrada de tarjeta.
 * 
 * @author Fernando Fonseca
 */
public class CodigoPANEntryModeValidator implements ConstraintValidator<CodigoPANEntryMode, String> {
    private int min;
    private int max;
    private boolean existe;

    private @Autowired IPANEntryModeService panEntryModeService;

    @Override
    public void initialize(CodigoPANEntryMode anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String codigoPANEntryMode, ConstraintValidatorContext context)
    {
        if (codigoPANEntryMode == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.PANEntryMode.codigo}", context);
            return false;
        }
        if (codigoPANEntryMode.length() < min || codigoPANEntryMode.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.PANEntryMode.codigo}", context);
            return false;
        }
        boolean existePANEntryMode = panEntryModeService.existePANEntryMode(codigoPANEntryMode);
        return existe ? existePANEntryMode : !existePANEntryMode;
    }
	
}
