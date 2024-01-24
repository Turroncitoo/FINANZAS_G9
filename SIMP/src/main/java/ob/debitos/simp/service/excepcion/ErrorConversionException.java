package ob.debitos.simp.service.excepcion;

public class ErrorConversionException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ErrorConversionException(String mensaje)
    {
        super(mensaje);
    }
}