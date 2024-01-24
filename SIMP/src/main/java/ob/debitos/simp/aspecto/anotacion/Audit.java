package ob.debitos.simp.aspecto.anotacion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Audit
{
    Accion accion() default Accion.Ninguna;

    Comentario comentario() default Comentario.Ninguno;

    Tipo tipo() default Tipo.NINGUNO;

    Dato datos() default Dato.NINGUNO;
}
