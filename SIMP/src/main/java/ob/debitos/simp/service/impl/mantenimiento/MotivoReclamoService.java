package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IMotivoReclamoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.MotivoReclamo;
import ob.debitos.simp.service.IMotivoReclamoService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class MotivoReclamoService extends MantenibleService<MotivoReclamo>
        implements IMotivoReclamoService
{
    @SuppressWarnings("unused")
    private IMotivoReclamoMapper motivoReclamoMapper;

    public MotivoReclamoService(
            @Qualifier("IMotivoReclamoMapper") IMantenibleMapper<MotivoReclamo> mapper)
    {
        super(mapper);
        this.motivoReclamoMapper = (IMotivoReclamoMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<MotivoReclamo> buscarTodos()
    {
        return this.buscar(new MotivoReclamo(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<MotivoReclamo> buscarPorIdMotivoReclamo(int idMotivo)
    {
        MotivoReclamo motivoReclamo = MotivoReclamo.builder().idMotivo(idMotivo).build();
        return this.buscar(motivoReclamo, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeMotivoReclamo(int idMotivo)
    {
        return !this.buscarPorIdMotivoReclamo(idMotivo).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarMotivoReclamo(MotivoReclamo motivoReclamo)
    {
        this.registrar(motivoReclamo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarMotivoReclamo(MotivoReclamo motivoReclamo)
    {
        this.actualizar(motivoReclamo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarMotivoReclamo(MotivoReclamo motivoReclamo)
    {
        this.eliminar(motivoReclamo);
    }
}