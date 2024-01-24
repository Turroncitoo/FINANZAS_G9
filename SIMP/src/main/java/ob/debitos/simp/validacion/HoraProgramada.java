package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.HoraProgramadaValidator;

@Documented
@Constraint(validatedBy = HoraProgramadaValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HoraProgramada
{
    String message() default "{Existe.ProcesoAutomatico.horaProgramada}";
    
    String campoCodigoGrupo() default "codigoGrupo";
    
    String campoHoraProgramada() default "horaProgramada";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}