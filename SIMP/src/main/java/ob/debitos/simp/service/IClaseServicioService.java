package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ClaseServicio;

public interface IClaseServicioService extends IMantenibleService<ClaseServicio>
{

    public List<ClaseServicio> buscarPorCodigoMembresia(String codigoMembresia);

    public List<ClaseServicio> buscarPorCodigoClaseServicioCodigoMembresia(
            String codigoClaseServicio, String codigoMembresia);

    public List<ClaseServicio> buscarTodosConMembresia();

    public boolean existeClaseServicio(String codigoClaseServicio, String codigoMembresia);

    public void registrarClaseServicio(ClaseServicio claseServicio);

    public void actualizarClaseServicio(ClaseServicio claseServicio);

    public void eliminarClaseServicio(ClaseServicio claseServicio);

}
