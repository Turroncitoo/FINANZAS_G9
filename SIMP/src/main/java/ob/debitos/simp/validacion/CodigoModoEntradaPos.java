package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoModoEntradaPosValidator;

@Documented
@Constraint(validatedBy = CodigoModoEntradaPosValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoModoEntradaPos.List.class)
public @interface CodigoModoEntradaPos
{

    String message() default "{NoExiste.ModoEntradaPos.codigoModoEntradaPOS}";

    boolean existe();

    int max() default 2;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoModoEntradaPos[] value();
    }
}
