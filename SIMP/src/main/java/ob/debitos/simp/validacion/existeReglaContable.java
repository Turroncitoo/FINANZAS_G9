package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.ReglaContableValidator;

@Documented
@Constraint(validatedBy = ReglaContableValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(existeReglaContable.List.class)
public @interface existeReglaContable
{
	String message() default "{Existe.ReglaContable.existeReglaContable}";
	
	boolean existe();
	
	String campoIdComercio() default "idComercio";
	
	String campoIdCliente() default "idCliente";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	@Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
	@interface List
	{
		existeReglaContable[] value();
	}
}
