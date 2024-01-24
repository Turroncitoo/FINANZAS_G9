package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IConceptoComisionService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdConceptoComision;

public class IdConceptoComisionValidator implements ConstraintValidator<IdConceptoComision, Integer>
{
    private int min;
    private int max;
    private boolean existe;
    private boolean incluirCero;

    private @Autowired IConceptoComisionService conceptoComisionService;

    @Override
    public void initialize(IdConceptoComision anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
        this.incluirCero = anotacion.incluirCero();
    }

    @Override
    public boolean isValid(Integer idConceptoComision, ConstraintValidatorContext context)
    {
        if (idConceptoComision == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.ConceptoComision.idConceptoComision}", context);
            return false;
        }
        if (incluirCero && idConceptoComision == 0)
        {
            return true;
        }
        if (idConceptoComision < min || idConceptoComision > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Range.ConceptoComision.idConceptoComision}", context);
            return false;
        }
        boolean existeConceptoComision = conceptoComisionService
                .existeConceptoComision(idConceptoComision);
        return (existe) ? existeConceptoComision : !existeConceptoComision;
    }
}