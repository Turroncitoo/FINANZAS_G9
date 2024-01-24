package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintTarget;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoAfinidadValidator;

@Documented
@Constraint(validatedBy = CodigoAfinidadValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE,
        ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoAfinidad.List.class)
public @interface CodigoAfinidad 
{
    String campoCodigoAfinidad() default "codigo";

    String campoCodigoLogo() default "idLogo";

    boolean existe();
    
    String message() default "{NoExiste.Afinidad.codigo}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE,
            ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
    	CodigoAfinidad[] value();
    }
}
