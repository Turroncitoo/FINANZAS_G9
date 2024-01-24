package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IMetodoIdThbMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.MetodoIdThb;
import ob.debitos.simp.service.IMetodoIdThbService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class MetodoIdThbService extends MantenibleService<MetodoIdThb>
        implements IMetodoIdThbService
{
    @SuppressWarnings("unused")
    private IMetodoIdThbMapper metodoIdThbMapper;

    public MetodoIdThbService(
            @Qualifier("IMetodoIdThbMapper") IMantenibleMapper<MetodoIdThb> mapper)
    {
        super(mapper);
        this.metodoIdThbMapper = (IMetodoIdThbMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<MetodoIdThb> buscarTodos()
    {
        return this.buscar(new MetodoIdThb(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<MetodoIdThb> buscarPorIdMetodoIdThb(String idMetodo)
    {
        MetodoIdThb metodoIdThb = MetodoIdThb.builder().idMetodo(idMetodo).build();
        return this.buscar(metodoIdThb, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeMetodoIdThb(String idMetodo)
    {
        return !this.buscarPorIdMetodoIdThb(idMetodo).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarMetodoIdThb(MetodoIdThb metodoIdThb)
    {
        this.registrar(metodoIdThb);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarMetodoIdThb(MetodoIdThb metodoIdThb)
    {
        this.actualizar(metodoIdThb);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarMetodoIdThb(MetodoIdThb metodoIdThb)
    {
        this.eliminar(metodoIdThb);
    }
}