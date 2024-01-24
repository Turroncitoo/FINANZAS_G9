package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoPINEntryCapabilityValidator;

/**
 * Define la anotación de validación para las capacidades de ingreso de las 
 * transacciones de tipo PIN.
 * <p>
 * La implementación de la lógica de validación es realizada por la clase
 * {@link CodigoPINEntryCapabilityValidator}.
 * 
 * @author Fernando Fonseca
 */
@Documented
@Constraint(validatedBy = CodigoPINEntryCapabilityValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
        ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoPINEntryCapability.List.class)
public @interface CodigoPINEntryCapability {
    String message() default "{NoExiste.PINEntryCapability.codigo}";

    boolean existe();

    int max() default 1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
            ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
    	CodigoPINEntryCapability[] value();
    }
}
