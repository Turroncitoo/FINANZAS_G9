package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.IdEsquemaTarValidator;

@Documented
@Constraint(validatedBy = IdEsquemaTarValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
        ElementType.TYPE_USE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IdEsquemaTar.List.class)
public @interface IdEsquemaTar
{

    String message() default "{NoExisteEsquemaTar.EsquemaTar.codigoEsquema}";

    boolean existe();

    int min() default 0;

    int max() default 99;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
            ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdEsquemaTar[] value();
    }
}
