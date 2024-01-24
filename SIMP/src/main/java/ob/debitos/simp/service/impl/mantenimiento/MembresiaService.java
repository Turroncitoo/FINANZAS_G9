package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IMembresiaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Membresia;
import ob.debitos.simp.service.IMembresiaService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class MembresiaService extends MantenibleService<Membresia> implements IMembresiaService
{
    @SuppressWarnings("unused")
    private IMembresiaMapper membresiaMapper;

    public MembresiaService(@Qualifier("IMembresiaMapper") IMantenibleMapper<Membresia> mapper)
    {
        super(mapper);
        this.membresiaMapper = (IMembresiaMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Membresia> buscarTodos()
    {
        return this.buscar(new Membresia(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Membresia> buscarPorCodigoMembresia(String codigoMembresia)
    {
        Membresia membresia = Membresia.builder().codigoMembresia(codigoMembresia).build();
        return this.buscar(membresia, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeMembresia(String codigoMembresia)
    {
        return !this.buscarPorCodigoMembresia(codigoMembresia).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarMembresia(Membresia membresia)
    {
        this.registrar(membresia);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarMembresia(Membresia membresia)
    {
        this.actualizar(membresia);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarMembresia(Membresia membresia)
    {
        this.eliminar(membresia);
    }
}