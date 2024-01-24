package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IMultiTabCabMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.MultiTabCab;
import ob.debitos.simp.service.IMultiTabCabService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class MultiTabCabService extends MantenibleService<MultiTabCab>
        implements IMultiTabCabService
{
    @SuppressWarnings("unused")
    private IMultiTabCabMapper multiTabCabMapper;

    public MultiTabCabService(
            @Qualifier("IMultiTabCabMapper") IMantenibleMapper<MultiTabCab> mapper)
    {
        super(mapper);
        this.multiTabCabMapper = (IMultiTabCabMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<MultiTabCab> buscarTodos()
    {
        return this.buscar(new MultiTabCab(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<MultiTabCab> buscarPorIdTabla(int idTabla)
    {
        MultiTabCab multiTabCab = MultiTabCab.builder().idTabla(idTabla).build();
        return this.buscar(multiTabCab, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeIdTabla(Integer idTabla)
    {
        return !this.buscarPorIdTabla(idTabla).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarMultiTabCab(MultiTabCab multiTabCab)
    {
        this.registrar(multiTabCab);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarMultiTabCab(MultiTabCab multiTabCab)
    {
        this.actualizar(multiTabCab);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarMultiTabCab(MultiTabCab multiTabCab)
    {
        this.eliminar(multiTabCab);
    }
}