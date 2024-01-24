package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IMonedaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Moneda;
import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class MonedaService extends MantenibleService<Moneda> implements IMonedaService
{

    @SuppressWarnings("unused")
    private IMonedaMapper monedaMapper;

    public MonedaService(@Qualifier("IMonedaMapper") IMantenibleMapper<Moneda> mapper)
    {
        super(mapper);
        this.monedaMapper = (IMonedaMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Moneda> buscarTodos()
    {
        return this.buscar(new Moneda(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Moneda> buscarPorCodigoMoneda(int codigoMoneda)
    {
        Moneda moneda = Moneda.builder().codigoMoneda(codigoMoneda).build();
        return this.buscar(moneda, Verbo.GET);
    }

    public boolean existeMoneda(int codigoMoneda)
    {
        return !this.buscarPorCodigoMoneda(codigoMoneda).isEmpty();
    }

}