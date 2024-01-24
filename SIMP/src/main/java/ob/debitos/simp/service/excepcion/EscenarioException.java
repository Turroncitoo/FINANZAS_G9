package ob.debitos.simp.service.excepcion;

public class EscenarioException extends SimpException
{
    private static final long serialVersionUID = 1L;

    public EscenarioException(String mensaje)
    {
        super(mensaje);
    }
}