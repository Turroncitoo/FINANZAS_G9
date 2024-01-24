package ob.debitos.simp.service.excepcion;

public class ErrorConexionException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public ErrorConexionException(String mensaje)
    {
        super(mensaje);
    }
}