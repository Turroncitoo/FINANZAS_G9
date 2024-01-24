package ob.debitos.simp.utilitario;

public class IntegerUtil
{
    public static Integer aEntero(String cadena){
        Integer numero = null;
        try
        {
            numero = Integer.parseInt(cadena);
        } catch (NumberFormatException ex)
        {
           return null;
        }
        return numero; 
    }
}
