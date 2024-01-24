package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.Canal;

public interface ICanalService extends IMantenibleService<Canal>
{

    public List<Canal> buscarTodos();

    public List<Canal> buscarPorIdCanal(int idCanal);

    public boolean existeCanal(int idCanal);

    public void registrarCanal(Canal canal);

    public void actualizarCanal(Canal canal);

    public void eliminarCanal(Canal canal);
}
