package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoCuentaAjusteValidator;

/**
 * Define la anotación de validación para la cuenta ajuste.
 * <p>
 * La implementación de la lógica de validación es realizada por la clase
 * {@link CodigoCuentaAjusteValidator}.
 * 
 * @author Hanz Llanto
 */
@Documented
@Constraint(validatedBy = CodigoCuentaAjusteValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoCuentaAjuste.List.class)
public @interface CodigoCuentaAjuste
{
    String message() default "";

    boolean existe();

    String campoRolTransaccion() default "rolTransaccion";

    String campoTipoMovimiento() default "tipoMovimiento";

    String campoMonedaCompensacion() default "monedaCompensacion";

    String campoRegistroContable() default "registroContable";

    String campoNuevoRolTransaccion() default "nuevoRolTransaccion";

    String campoNuevoTipoMovimiento() default "nuevoTipoMovimiento";

    String campoNuevoMonedaCompensacion() default "nuevoMonedaCompensacion";

    String campoNuevoRegistroContable() default "nuevoRegistroContable";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoCuentaAjuste[] value();
    }
}