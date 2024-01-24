package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoClaseTransaccionValidator;

@Documented
@Constraint(validatedBy = CodigoClaseTransaccionValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
        ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoClaseTransaccion.List.class)
public @interface CodigoClaseTransaccion
{

    String message() default "{NoExiste.ClaseTransaccion.codigoClaseTransaccion}";

    boolean existe();

    int min() default 1;

    int max() default 9999;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
            ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoClaseTransaccion[] value();
    }
}
