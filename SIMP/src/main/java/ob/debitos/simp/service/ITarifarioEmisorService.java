package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.tarifario.TarifarioEmisor;

public interface ITarifarioEmisorService extends IMantenibleService<TarifarioEmisor>
{
    public List<TarifarioEmisor> buscarTodos();

    public List<TarifarioEmisor> buscarPorCodigo(TarifarioEmisor emisor);

    public List<TarifarioEmisor> registrarTarEmisor(TarifarioEmisor emisor);

    public List<TarifarioEmisor> actualizarTarEmisor(TarifarioEmisor emisor);

    public void eliminarTarEmisor(TarifarioEmisor emisor);
}