package ob.debitos.simp.aspecto.anotacion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NoIdentificable
{
    String campo() default "numeroTarjetaTruncado";

    int primerosCaracteres() default 6;

    int ultimosCaracteres() default 4;
}