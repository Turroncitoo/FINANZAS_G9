package ob.debitos.simp.controller.excepcion;

import lombok.Getter;
import ob.debitos.simp.service.excepcion.SimpException;

@Getter
public class SftpException extends SimpException
{
    private static final long serialVersionUID = 1L;

    public SftpException(String mensaje)
    {
        super(mensaje);
    }
}
