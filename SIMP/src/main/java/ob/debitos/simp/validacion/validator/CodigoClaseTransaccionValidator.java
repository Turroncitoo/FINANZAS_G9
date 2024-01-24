package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IClaseTransaccionService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoClaseTransaccion;

public class CodigoClaseTransaccionValidator
        implements ConstraintValidator<CodigoClaseTransaccion, Integer>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired IClaseTransaccionService claseTransaccionService;

    @Override
    public void initialize(CodigoClaseTransaccion anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer codigoClaseTransaccion, ConstraintValidatorContext context)
    {
        if (codigoClaseTransaccion == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.ClaseTransaccion.codigoClaseTransaccion}", context);
            return false;
        }
        if (codigoClaseTransaccion < min || codigoClaseTransaccion > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Range.ClaseTransaccion.codigoClaseTransaccion}", context);
            return false;
        }
        boolean existeClaseTransaccion = claseTransaccionService
                .existeClaseTransaccion(codigoClaseTransaccion);
        return existe ? existeClaseTransaccion : !existeClaseTransaccion;
    }
}