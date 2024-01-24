package ob.debitos.simp.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ob.debitos.simp.validacion.validator.CodigoClienteAfinidadValidator;

@Documented
@Constraint(validatedBy = CodigoClienteAfinidadValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoClienteAfinidad.List.class)
public @interface CodigoClienteAfinidad {
	String message() default "{NoExiste.ClienteAfinidad.idAfinidad}";

    boolean existe();

    String campoIdAfinidad() default "idAfinidad";

    String campoIdCliente() default "idCliente";
    
    String campoIdEmpresa() default "idEmpresa";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
	
	@Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
		CodigoClienteAfinidad[] value();
    }
}
