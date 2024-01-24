package ob.debitos.simp.service.excepcion;

import java.util.List;

import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;

public class HeaderTrailerExcepcion extends SimpException
{
    private static final long serialVersionUID = 1L;

    private final List<LogControlProgramaDetalle> logControlProgramaDetalles;

    public HeaderTrailerExcepcion(String mensaje,
            List<LogControlProgramaDetalle> logControlProgramaDetalles)
    {
        super(mensaje);
        this.logControlProgramaDetalles = logControlProgramaDetalles;
    }

    public List<LogControlProgramaDetalle> getLogControlProgramaDetalles()
    {
        return this.logControlProgramaDetalles;
    }
}