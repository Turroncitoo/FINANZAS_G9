package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.IdClienteValidator;

@Documented
@Constraint(validatedBy = IdClienteValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IdCliente.List.class)
public @interface IdCliente
{

    String message() default "{NoExiste.Cliente.idCliente}";

    boolean existe();

    int min() default 1;

    int max() default 4;

    String campoIdEmpresa() default "idEmpresa";

    String campoIdCliente() default "idCliente";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdCliente[] value();
    }

}
