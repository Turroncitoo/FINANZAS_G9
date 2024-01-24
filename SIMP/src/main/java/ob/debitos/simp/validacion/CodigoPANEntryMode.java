package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoPANEntryModeValidator;

/**
 * Define la anotación de validación para el modo de entrada de tarjeta.
 * <p>
 * La implementación de la lógica de validación es realizada por la clase
 * {@link CodigoPANEntryModeValidator}.
 * 
 * @author Fernando Fonseca
 */
@Documented
@Constraint(validatedBy = CodigoPANEntryModeValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
        ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoPANEntryMode.List.class)
public @interface CodigoPANEntryMode {
    String message() default "{NoExiste.PANEntryMode.codigo}";

    boolean existe();

    int min() default 1;

    int max() default 2;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
            ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
    	CodigoPANEntryMode[] value();
    }
    
}
