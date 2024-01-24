package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoParametroFTPDirectorioValidator;

@Documented
@Constraint(validatedBy = CodigoParametroFTPDirectorioValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoParametroFTPDirectorio.List.class)
public @interface CodigoParametroFTPDirectorio {

	String message() default "{NoExiste.ParametrosSFTPDirectorio.codigoProceso}";

    boolean existe();

    String campoCodigoProceso() default "codigoProceso";

    String campoCodigoTipoOperacion() default "tipoOperacion";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
	
	@Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
		CodigoParametroFTPDirectorio[] value();
    }
}
