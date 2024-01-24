package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICuentaContableService;
import ob.debitos.simp.validacion.IdCuentaContable;

/**
 * Evalúa las restricciones de validación y existencia del código de la cuenta
 * contable.
 * 
 * @author Hanz Llanto
 */
public class IdCuentaContableValidator implements ConstraintValidator<IdCuentaContable, Integer>
{
    private boolean existe;

    private @Autowired ICuentaContableService cuentaContableService;

    @Override
    public void initialize(IdCuentaContable anotacion)
    {
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer idCuenta, ConstraintValidatorContext context)
    {
        boolean existeCuentaContable = this.cuentaContableService.existeCuentaContable(idCuenta);
        return (existe ? existeCuentaContable : !existeCuentaContable);
    }
}