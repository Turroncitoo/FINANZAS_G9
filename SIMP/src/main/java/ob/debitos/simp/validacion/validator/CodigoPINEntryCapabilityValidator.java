package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IPINEntryCapabilityService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoPINEntryCapability;

/**
 * Evalúa las restricciones de validación y existencia de las capacidades de ingreso de las 
 * transacciones de tipo PIN.
 * 
 * @author Hanz Llanto
 */
public class CodigoPINEntryCapabilityValidator implements ConstraintValidator<CodigoPINEntryCapability, String> {
    private int max;
    private boolean existe;

    private @Autowired IPINEntryCapabilityService pinEntryCapabilityService;

    @Override
    public void initialize(CodigoPINEntryCapability anotacion)
    {
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String codigoPINEntryCapability, ConstraintValidatorContext context)
    {
        if (codigoPINEntryCapability == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.PINEntryCapability.codigo}", context);
            return false;
        }
        if (codigoPINEntryCapability.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.PINEntryCapability.codigo}", context);
            return false;
        }
        boolean existePINEntryCapability = pinEntryCapabilityService.existePINEntryCapability(codigoPINEntryCapability);
        return existe ? existePINEntryCapability : !existePINEntryCapability;
    }
}
