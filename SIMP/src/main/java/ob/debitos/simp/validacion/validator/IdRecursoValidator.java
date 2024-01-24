package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ISecRecursoService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdRecurso;

public class IdRecursoValidator implements ConstraintValidator<IdRecurso, String>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired ISecRecursoService secRecursoService;

    @Override
    public void initialize(IdRecurso anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String IdRecurso, ConstraintValidatorContext context)
    {
        if (IdRecurso == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Recurso.idRecurso}", context);
            return false;
        }
        if (IdRecurso.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.Recurso.idRecurso}", context);
            return false;
        }
        if (!IdRecurso.matches(Regex.LETRAS_GUION_BAJO))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.Recurso.idRecurso}", context);
            return false;
        }
        if (IdRecurso.length() < min || IdRecurso.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.Recurso.idRecurso}", context);
            return false;
        }
        boolean existeRecurso = secRecursoService.existeRecurso(IdRecurso);
        return (existe) ? existeRecurso : !existeRecurso;
    }
}