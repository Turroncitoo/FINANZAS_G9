package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.OrdenEjecucionValidator;

@Documented
@Constraint(validatedBy = OrdenEjecucionValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface OrdenEjecucion
{
    String message() default "{Existe.ProcesoAutomatico.ordenEjecucion}";

    int min() default 1;

    int max() default 99;

    String campoCodigoGrupo() default "codigoGrupo";

    String campoOrdenEjecucion() default "ordenEjecucion";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}