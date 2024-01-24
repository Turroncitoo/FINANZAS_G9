package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.IdConceptoComisionValidator;

@Documented
@Constraint(validatedBy = IdConceptoComisionValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IdConceptoComision.List.class)
public @interface IdConceptoComision
{

    String message() default "{NoExiste.ConceptoComision.idConceptoComision}";

    boolean existe();
    
    boolean incluirCero();

    int min() default 1;

    int max() default 9;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdConceptoComision[] value();
    }

}
