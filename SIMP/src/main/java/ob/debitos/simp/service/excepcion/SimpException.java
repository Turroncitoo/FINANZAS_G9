package ob.debitos.simp.service.excepcion;

public class SimpException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public SimpException(String mensaje, Object... args)
    {
        super((args != null && args.length > 0) ? String.format(mensaje, args) : mensaje);
    }
}