package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.ConceptoComisionValidator;

@Documented
@Constraint(validatedBy = ConceptoComisionValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
        ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ConceptoComision.List.class)
public @interface ConceptoComision
{
    
    String message() default "{NoExiste.ConceptoComision.idConceptoComision}";
    
    int min() default 0;

    int max() default 999;

    boolean existe();
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
            ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        ConceptoComision[] value();
    }
    
}
