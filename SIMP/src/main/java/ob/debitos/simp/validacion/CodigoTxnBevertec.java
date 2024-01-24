package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoTxnBevertecValidator;

@Documented
@Constraint(validatedBy = CodigoTxnBevertecValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoTxnBevertec.List.class)
public @interface CodigoTxnBevertec
{

    String message() default "{Existe.CodigoTxnBevertec.codTransaccion}";

    boolean existe();

    int tamanio() default 6;

    String campoCodigoCanalEmisor() default "codigoCanalEmisor";

    String campoTipoTransaccion() default "tipoTransaccion";

    String campoCodTransaccion() default "codTransaccion";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoTxnBevertec[] value();
    }

}
