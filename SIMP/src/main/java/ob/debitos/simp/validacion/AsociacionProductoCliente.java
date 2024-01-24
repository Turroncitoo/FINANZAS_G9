package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.AsociacionProductoClienteValidator;

@Documented
@Constraint(validatedBy = AsociacionProductoClienteValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(AsociacionProductoCliente.List.class)
public @interface AsociacionProductoCliente
{

    String message() default "{Existe.ProductoCliente.codProducto}";

    boolean existe();

    String campoCodigoProducto() default "codigoProducto";

    String campoIdCliente() default "idCliente";

    String campoIdEmpresa() default "idEmpresa";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        AsociacionProductoCliente[] value();
    }

}