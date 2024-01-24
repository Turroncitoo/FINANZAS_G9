package ob.debitos.simp.service.excepcion;

public class NoDisponibleException extends RuntimeException
{   
    private static final long serialVersionUID = 1L;

    public NoDisponibleException(String mensaje)
    {
        super(mensaje);
    }
}