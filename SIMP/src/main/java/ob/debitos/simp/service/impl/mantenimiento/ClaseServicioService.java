package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IClaseServicioMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ClaseServicio;
import ob.debitos.simp.service.IClaseServicioService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ClaseServicioService extends MantenibleService<ClaseServicio>
        implements IClaseServicioService
{
    @SuppressWarnings("unused")
    private IClaseServicioMapper claseServicioMapper;

    public ClaseServicioService(
            @Qualifier("IClaseServicioMapper") IMantenibleMapper<ClaseServicio> mapper)
    {
        super(mapper);
        this.claseServicioMapper = (IClaseServicioMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClaseServicio> buscarPorCodigoMembresia(String codigoMembresia)
    {
        ClaseServicio claseServicio = ClaseServicio.builder().codigoMembresia(codigoMembresia)
                .build();
        return this.buscar(claseServicio, Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ClaseServicio> buscarPorCodigoClaseServicioCodigoMembresia(
            String codigoClaseServicio, String codigoMembresia)
    {
        ClaseServicio claseServicio = ClaseServicio.builder()
                .codigoClaseServicio(codigoClaseServicio).codigoMembresia(codigoMembresia).build();
        return this.buscar(claseServicio, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClaseServicio> buscarTodosConMembresia()
    {
        return this.buscar(new ClaseServicio(), Verbo.GETS_T_MEM);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeClaseServicio(String codigoClaseServicio, String codigoMembresia)
    {
        return !this
                .buscarPorCodigoClaseServicioCodigoMembresia(codigoClaseServicio, codigoMembresia)
                .isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarClaseServicio(ClaseServicio claseServicio)
    {
        this.registrar(claseServicio);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarClaseServicio(ClaseServicio claseServicio)
    {
        this.actualizar(claseServicio);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarClaseServicio(ClaseServicio claseServicio)
    {
        this.eliminar(claseServicio);
    }
}