package ob.debitos.simp.service.excepcion;

public class ValorNoEncontradoException extends SimpException
{
    private static final long serialVersionUID = 1L;

    public ValorNoEncontradoException(String mensaje)
    {
        super(mensaje);
    }
}