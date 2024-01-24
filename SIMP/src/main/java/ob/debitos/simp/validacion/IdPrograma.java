package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.IdPrograma.List;
import ob.debitos.simp.validacion.validator.IdProgramaValidator;

@Documented
@Constraint(validatedBy = IdProgramaValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
public @interface IdPrograma
{
    String message() default "{NoExiste.Programa.codigoPrograma}";

    boolean existe();

    String campoCodigoPrograma() default "codigoPrograma";

    String campoCodigoSubModulo() default "codigoSubModulo";

    String campoCodigoGrupo() default "codigoGrupo";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdPrograma[] value();
    }
}
