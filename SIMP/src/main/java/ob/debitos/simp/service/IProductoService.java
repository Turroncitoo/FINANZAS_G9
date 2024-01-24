package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.Producto;

public interface IProductoService extends IMantenibleService<Producto>
{

    public List<Producto> buscarTodos();

    public List<Producto> buscarCodigoProducto(String codigoProducto);

    public List<Producto> buscarCodigoLogo(String idLogo);

    public List<Producto> buscarPorCliente(String idEmpresa, String idCliente, String idLogo);

    public boolean existeProducto(String codigoProducto);

    public void registrarProducto(Producto producto);

    public void actualizarProducto(Producto producto);

    public void eliminarProducto(Producto producto);

}
