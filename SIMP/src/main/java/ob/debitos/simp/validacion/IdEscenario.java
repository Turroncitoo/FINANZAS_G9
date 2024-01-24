package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.IdEscenario.List;
import ob.debitos.simp.validacion.validator.IdEscenarioValidator;

@Documented
@Constraint(validatedBy = IdEscenarioValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
        ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface IdEscenario
{
    String message() default "{Existe.Escenario.codigo}";

    boolean existe();

    String campoCodigoMembresia() default "codigoMembresia";

    String campoCodigoClaseServicio() default "codigoClaseServicio";

    String campoCodigoClaseTransaccion() default "codigoClaseTransaccion";

    String campoCodigoTransaccion() default "codigoTransaccion";

    String campoCodigoOrigen() default "codigoOrigen";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
            ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdEscenario[] value();
    }
}
