package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IProductoClienteMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ProductoCliente;
import ob.debitos.simp.service.IProductoClienteService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ProductoClienteService extends MantenibleService<ProductoCliente> implements IProductoClienteService
{

    @SuppressWarnings("unused")
    private IProductoClienteMapper productoClienteMapper;

    public ProductoClienteService(@Qualifier("IProductoClienteMapper") IMantenibleMapper<ProductoCliente> mapper)
    {
        super(mapper);
        this.productoClienteMapper = (IProductoClienteMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProductoCliente> buscarAsociacionProductoCliente(ProductoCliente productoCliente)
    {
        return this.buscar(productoCliente, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProductoCliente> buscarPorProducto(String codigoProducto)
    {
        ProductoCliente productoCliente = ProductoCliente.builder().codigoProducto(codigoProducto).build();
        return this.buscar(productoCliente, Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void asociarProductoCliente(ProductoCliente productoCliente)
    {
        try
        {
            this.registrar(productoCliente);
        } catch (Exception e)
        {
            throw new SimpException(e.getCause().getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminarAsociacionProductoCliente(ProductoCliente productoCliente)
    {
        this.eliminar(productoCliente, Verbo.REMOVE);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeAsociacionProductoCliente(String codProducto, String codCliente, String codEmpresa)
    {
        ProductoCliente productoCliente = ProductoCliente.builder().codigoProducto(codProducto).idCliente(codCliente).idEmpresa(codEmpresa).build();
        return !this.buscarAsociacionProductoCliente(productoCliente).isEmpty();
    }

}
