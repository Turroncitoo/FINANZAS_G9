package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.TipoTerminalPos;

public interface ITipoTerminalPosService extends IMantenibleService<TipoTerminalPos>
{

    public List<TipoTerminalPos> buscarTodos();

    public List<TipoTerminalPos> buscarPorCodigoTipoTerminalPos(String codigoTipoTerminalPOS);

    public boolean existeTipoTerminarPos(String codigoTipoTerminalPOS);

    public void registrarTipoTerminalPos(TipoTerminalPos tipoTerminalPOS);

    public void actualizarTipoTerminalPos(TipoTerminalPos tipoTerminalPOS);

    public void eliminarTipoTerminalPos(TipoTerminalPos tipoTerminalPOS);
}
