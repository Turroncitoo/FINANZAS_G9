package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoInstitucion;

public class CodigoInstitucionValidator implements ConstraintValidator<CodigoInstitucion, Integer>
{
    private int min;
    private int max;
    private boolean existe;
    private @Autowired IInstitucionService institucionService;

    @Override
    public void initialize(CodigoInstitucion anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer codigoInstitucion, ConstraintValidatorContext context)
    {
        if (codigoInstitucion == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Institucion.codigoInstitucion}",
                    context);
            return false;
        }
        if (codigoInstitucion < min || codigoInstitucion > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.Institucion.codigoInstitucion}",
                    context);
            return false;
        }
        boolean existeInstitucion = institucionService.existeInstitucion(codigoInstitucion);
        return (existe) ? existeInstitucion : !existeInstitucion;
    }
}