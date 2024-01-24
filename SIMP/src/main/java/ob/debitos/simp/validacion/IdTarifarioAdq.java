package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.IdTarifarioAdq.List;
import ob.debitos.simp.validacion.validator.IdTarifarioAdqValidator;

@Documented
@Constraint(validatedBy = IdTarifarioAdqValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
        ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface IdTarifarioAdq
{
    String message() default "{Existe.TarifarioAdq.codigo}";

    boolean existe();

    String campoTipoTarifa() default "codigoTipoTarifa";

    String campoNivel() default "nivel";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER,
            ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdTarifarioAdq[] value();
    }
}
