package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoTipoTerminalPosValidator;

@Documented
@Constraint(validatedBy = CodigoTipoTerminalPosValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoTipoTerminalPos.List.class)
public @interface CodigoTipoTerminalPos
{

    String message() default "{NoExiste.TipoTerminalPos.codigoTipoTerminalPOS}";

    boolean existe();

    int max() default 1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoTipoTerminalPos[] value();
    }
}
