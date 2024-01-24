package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.IdCodigoTransaccionValidator;

@Documented
@Constraint(validatedBy = IdCodigoTransaccionValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IdCodigoTransaccion.List.class)
public @interface IdCodigoTransaccion
{

    String message() default "{Existe.CodigoTransaccion.codigoTransaccion}";

    boolean existe();

    int min() default 1;

    int max() default 9999;

    String campoCodigoClaseTransaccion() default "codigoClaseTransaccion";

    String campoCodigoTransaccion() default "codigoTransaccion";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdCodigoTransaccion[] value();
    }

}
