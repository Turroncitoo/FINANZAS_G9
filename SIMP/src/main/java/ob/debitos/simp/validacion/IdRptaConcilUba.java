package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.IdRptaConcilUbaValidator;

@Documented
@Constraint(validatedBy = IdRptaConcilUbaValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IdRptaConcilUba.List.class)
public @interface IdRptaConcilUba
{

    String message() default "{NoExiste.RptaConcilUba.idRespuestaConcilUba}";

    boolean existe();

    int min() default 1;
    int max() default 3;
    String pattern() default "^[A-Z0-9]+$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdRptaConcilUba[] value();
    }
}