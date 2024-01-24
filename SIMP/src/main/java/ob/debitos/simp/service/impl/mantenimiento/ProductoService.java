package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IProductoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Producto;
import ob.debitos.simp.service.IProductoService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ProductoService extends MantenibleService<Producto> implements IProductoService
{

    @SuppressWarnings("unused")
    private IProductoMapper productoMapper;

    public ProductoService(@Qualifier("IProductoMapper") IMantenibleMapper<Producto> mapper)
    {
        super(mapper);
        this.productoMapper = (IProductoMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Producto> buscarTodos()
    {
        return this.buscar(new Producto(), Verbo.GETS_T);
    }

    @Override
    public List<Producto> buscarCodigoProducto(String codigoProducto)
    {
        Producto producto = Producto.builder().codigoProducto(codigoProducto).build();
        return this.buscar(producto, Verbo.GET);
    }

    @Override
    public List<Producto> buscarCodigoLogo(String idLogo)
    {
        Producto producto = Producto.builder().idLogo(idLogo).build();
        return this.buscar(producto, Verbo.GET_LOGO);
    }

    @Override
    public List<Producto> buscarPorCliente(String idEmpresa, String idCliente, String idLogo)
    {
        Producto producto = Producto.builder().idEmpresa(idEmpresa).idCliente(idCliente).idLogo(idLogo).build();
        return this.buscar(producto, Verbo.GET_CLIENTE);
    }

    @Override
    public boolean existeProducto(String codigoProducto)
    {
        return !this.buscarCodigoProducto(codigoProducto).isEmpty();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarProducto(Producto producto)
    {
        try
        {
            this.registrar(producto);
        } catch (Exception e)
        {
            throw new SimpException(e.getCause().getMessage());
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarProducto(Producto producto)
    {
        try
        {
            this.actualizar(producto);
        } catch (Exception e)
        {
            throw new SimpException(e.getCause().getMessage());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarProducto(Producto producto)
    {
        this.eliminar(producto);
    }

}