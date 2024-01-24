package ob.debitos.simp.aspecto.anotacion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface LogControlPrograma
{
	boolean detallado() default false;
	boolean registrarLogResumen() default false;
}
