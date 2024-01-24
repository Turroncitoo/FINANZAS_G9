package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoUsuarioValidator;

@Documented
@Constraint(validatedBy = CodigoUsuarioValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoUsuario.List.class)
public @interface CodigoUsuario {
	String message() default "{NoExiste.Usuario.idUsuario}";

	boolean existe();

	int min() default 3;

	int max() default 20;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Documented
	@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
	@Retention(RetentionPolicy.RUNTIME)
	@interface List {
		CodigoUsuario[] value();
	}
}
