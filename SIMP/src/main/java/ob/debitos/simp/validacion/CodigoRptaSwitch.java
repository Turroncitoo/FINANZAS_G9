package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoRptaSwitchValidator;

@Documented
@Constraint(validatedBy = CodigoRptaSwitchValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
        ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoRptaSwitch.List.class)
public @interface CodigoRptaSwitch
{

    String message() default "{NoExiste.CodigoRptaSwitch.codigoRespuestaSwitch}";

    boolean existe();

    int min() default 1;

    int max() default 3;
    
    String pattern() default "^[A-Z0-9]+$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
            ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoRptaSwitch[] value();
    }

}
