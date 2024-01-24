package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IClaseTransaccionMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ClaseTransaccion;
import ob.debitos.simp.service.IClaseTransaccionService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ClaseTransaccionService extends MantenibleService<ClaseTransaccion>
        implements IClaseTransaccionService
{
    @SuppressWarnings("unused")
    private IClaseTransaccionMapper claseTransaccionMapper;

    public ClaseTransaccionService(
            @Qualifier("IClaseTransaccionMapper") IMantenibleMapper<ClaseTransaccion> mapper)
    {
        super(mapper);
        this.claseTransaccionMapper = (IClaseTransaccionMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClaseTransaccion> buscarTodos()
    {
        return this.buscar(new ClaseTransaccion(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ClaseTransaccion> buscarPorCodigoClaseTransaccion(int codigoClaseTransaccion)
    {
        ClaseTransaccion claseTransaccion = ClaseTransaccion.builder()
                .codigoClaseTransaccion(codigoClaseTransaccion).build();
        return this.buscar(claseTransaccion, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeClaseTransaccion(int codigoClaseTransaccion)
    {
        return !this.buscarPorCodigoClaseTransaccion(codigoClaseTransaccion).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarClaseTransaccion(ClaseTransaccion claseTransaccion)
    {
        this.registrar(claseTransaccion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarClaseTransaccion(ClaseTransaccion claseTransaccion)
    {
        this.actualizar(claseTransaccion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarClaseTransaccion(ClaseTransaccion claseTransaccion)
    {
        this.eliminar(claseTransaccion);
    }
}