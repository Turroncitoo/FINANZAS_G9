package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.Membresia;

public interface IMembresiaService extends IMantenibleService<Membresia>
{

    public List<Membresia> buscarTodos();

    public List<Membresia> buscarPorCodigoMembresia(String codigoMembresia);

    public boolean existeMembresia(String codigoMembresia);

    public void registrarMembresia(Membresia membresia);

    public void actualizarMembresia(Membresia membresia);

    public void eliminarMembresia(Membresia membresia);
}
