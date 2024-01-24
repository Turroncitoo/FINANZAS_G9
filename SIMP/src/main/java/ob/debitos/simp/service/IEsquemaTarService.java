package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.tarifario.EsquemaTar;

public interface IEsquemaTarService extends IMantenibleService<EsquemaTar>
{
    public List<EsquemaTar> buscarTodos();

    public List<EsquemaTar> buscarPorIdEsquema(int codigoEsquema);
}
