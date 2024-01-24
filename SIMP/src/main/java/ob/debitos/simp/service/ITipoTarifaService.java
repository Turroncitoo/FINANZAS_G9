package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.tarifario.TipoTarifa;

public interface ITipoTarifaService extends IMantenibleService<TipoTarifa>
{
    public List<TipoTarifa> buscarTodos();

    public List<TipoTarifa> buscarPorTipoTarifa(TipoTarifa tipoTarifa);

    public List<TipoTarifa> registrarTipoTarifa(TipoTarifa tipoTarifa);

    public List<TipoTarifa> actualizarTipoTarifa(TipoTarifa tipoTarifa);

    public void eliminarTipoTarifa(TipoTarifa tipoTarifa);
}