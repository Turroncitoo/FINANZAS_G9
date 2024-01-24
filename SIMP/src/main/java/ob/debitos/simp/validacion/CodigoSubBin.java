package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoSubBinValidator;

@Documented
@Constraint(validatedBy = CodigoSubBinValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoSubBin.List.class)
public @interface CodigoSubBin
{

    String message() default "{Existe.SubBin.codigoSubBIN}";

    boolean existe();

    int tamanio() default 2;

    String campoCodigoBin() default "codigoBIN";

    String campoCodigoSubBin() default "codigoSubBIN";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoSubBin[] value();
    }

}
