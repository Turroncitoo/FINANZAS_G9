package ob.debitos.simp.utilitario;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CollectionUtil
{

    public static <T> Optional<T> obtenerUnicoElemento(List<T> lista) 
    {
        T valor = null;
        try
        {
            valor = lista.stream().findFirst().get();
        } catch (NoSuchElementException ex)
        {
             return null;
        }
        return Optional.ofNullable(valor);
    }

}
