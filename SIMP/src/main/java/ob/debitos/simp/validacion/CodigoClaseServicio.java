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

import ob.debitos.simp.validacion.validator.CodigoClaseServicioValidator;

@Documented
@Constraint(validatedBy = CodigoClaseServicioValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE,
        ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoClaseServicio.List.class)
public @interface CodigoClaseServicio
{

    String campoCodigoClaseServicio() default "codigoClaseServicio";

    String campoCodigoMembresia() default "codigoMembresia";

    int posClaseServicio() default -1;

    int posMembresia() default -1;

    boolean existe();

    boolean enMetodo() default false;

    int tamanio() default 1;

    String message() default "{NoExiste.ClaseServicio.codigoClaseServicio}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE,
            ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoClaseServicio[] value();
    }

}
