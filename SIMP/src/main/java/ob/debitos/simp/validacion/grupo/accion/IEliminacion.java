package ob.debitos.simp.validacion.grupo.accion;

/**
 * Define un grupo de validación para las operaciones de eliminación.
 * <p>
 * Esto permite que solo las anotaciones de validación que definan este grupo
 * sean ejecutados. Por ejemplo: <br>
 * {@code @CodigoBin(existe = true, groups = IEliminacion.class)}
 * 
 * @author Hanz Llanto
 */
public interface IEliminacion
{

}