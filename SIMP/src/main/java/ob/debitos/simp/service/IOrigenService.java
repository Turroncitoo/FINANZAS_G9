package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.Origen;

public interface IOrigenService extends IMantenibleService<Origen>
{

    public List<Origen> buscarTodos();

    public List<Origen> buscarPorCodigoOrigen(int codigoOrigen);

    public boolean existeOrigen(int codigoOrigen);

    public void registrarOrigen(Origen origen);

    public void actualizarOrigen(Origen origen);

    public void eliminarOrigen(Origen origen);

}
