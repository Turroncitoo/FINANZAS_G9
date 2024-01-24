package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ClaseTransaccion;

public interface IClaseTransaccionService extends IMantenibleService<ClaseTransaccion>
{

    public List<ClaseTransaccion> buscarTodos();

    public List<ClaseTransaccion> buscarPorCodigoClaseTransaccion(int codigoClaseTransaccion);

    public boolean existeClaseTransaccion(int codigoClaseTransaccion);

    public void registrarClaseTransaccion(ClaseTransaccion claseTransaccion);

    public void actualizarClaseTransaccion(ClaseTransaccion claseTransaccion);

    public void eliminarClaseTransaccion(ClaseTransaccion claseTransaccion);
}
