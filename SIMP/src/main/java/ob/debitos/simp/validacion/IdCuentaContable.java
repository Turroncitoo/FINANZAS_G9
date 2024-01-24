package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.IdCuentaContableValidator;

/**
 * Define la anotación de validación para el código de cuenta contable.
 * <p>
 * La implementación de la lógica de validación es realizada por la clase
 * {@link IdCuentaContableValidator}.
 * 
 * @author Hanz Llanto
 */
@Documented
@Constraint(validatedBy = IdCuentaContableValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IdCuentaContable.List.class)
public @interface IdCuentaContable
{
    String message() default "{NoExiste.CuentaContable.idCuenta}";

    boolean existe();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdCuentaContable[] value();
    }
}