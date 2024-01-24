package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ProductoCliente;

public interface IProductoClienteService extends IMantenibleService<ProductoCliente>
{

    public List<ProductoCliente> buscarPorProducto(String codigoProducto);

    public List<ProductoCliente> buscarAsociacionProductoCliente(ProductoCliente productoCliente);

    public boolean existeAsociacionProductoCliente(String codProducto, String codCliente, String idEmpresa);

    public void asociarProductoCliente(ProductoCliente productoCliente);

    public void eliminarAsociacionProductoCliente(ProductoCliente productoCliente);

}
