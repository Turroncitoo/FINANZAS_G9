package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IOrigenMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Origen;
import ob.debitos.simp.service.IOrigenService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class OrigenService extends MantenibleService<Origen> implements IOrigenService
{
    @SuppressWarnings("unused")
    private IOrigenMapper origenMapper;

    public OrigenService(@Qualifier("IOrigenMapper") IMantenibleMapper<Origen> mapper)
    {
        super(mapper);
        this.origenMapper = (IOrigenMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Origen> buscarTodos()
    {
        return this.buscar(new Origen(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Origen> buscarPorCodigoOrigen(int codigoOrigen)
    {
        Origen origen = Origen.builder().codigoOrigen(codigoOrigen).build();
        return this.buscar(origen, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeOrigen(int codigoOrigen)
    {
        return !this.buscarPorCodigoOrigen(codigoOrigen).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarOrigen(Origen origen)
    {
        this.registrar(origen);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarOrigen(Origen origen)
    {
        this.actualizar(origen);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarOrigen(Origen origen)
    {
        this.eliminar(origen);
    }
}