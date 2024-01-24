package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoProcBevertecValidator;

@Documented
@Constraint(validatedBy = CodigoProcBevertecValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoProcBevertec.List.class)
public @interface CodigoProcBevertec
{

    String message() default "{Existe.CodigoProcBevertec.tipoTransaccion}";

    boolean existe();

    int min() default 1;

    int max() default 5;

    String campoCodigoCanalEmisor() default "codigoCanalEmisor";

    String campoTipoTransaccion() default "tipoTransaccion";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoProcBevertec[] value();
    }

}
