package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.validacion.ConceptoComision;
import ob.debitos.simp.service.IConceptoComisionService;
import ob.debitos.simp.utilitario.ValidatorUtil;

public class ConceptoComisionValidator implements ConstraintValidator<ConceptoComision, Integer>
{
    private boolean existe;
    private int min;
    private int max;
    
    private @Autowired IConceptoComisionService conceptoComisionService;
    
    @Override
    public void initialize(ConceptoComision anotacion) {
        this.existe = anotacion.existe();
        this.min = anotacion.min();
        this.max = anotacion.max();
    }
    
    @Override
    public boolean isValid(Integer idConceptoComision, ConstraintValidatorContext context) {
        if (idConceptoComision == null) {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.ConceptoComision.idConceptoComision}", context);
            return false;
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
