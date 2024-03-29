package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoCanalEmisorValidator;

@Documented
@Constraint(validatedBy = CodigoCanalEmisorValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoCanalEmisor.List.class)
public @interface CodigoCanalEmisor
{

    String message() default "{NoExiste.CanalEmisor.codigoCanalEmisor}";

    boolean existe();

    int min() default 1;

    int max() default 2;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoCanalEmisor[] value();
    }
}
