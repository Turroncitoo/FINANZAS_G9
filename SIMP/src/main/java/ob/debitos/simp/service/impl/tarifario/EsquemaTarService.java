package ob.debitos.simp.service.impl.tarifario;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IEsquemaTarMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.tarifario.EsquemaTar;
import ob.debitos.simp.service.IEsquemaTarService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class EsquemaTarService extends MantenibleService<EsquemaTar>
        implements IEsquemaTarService
{
    @SuppressWarnings("unused")
    private IEsquemaTarMapper esquemaTarMapper;

    public EsquemaTarService(
            @Qualifier("IEsquemaTarMapper") IMantenibleMapper<EsquemaTar> mapper)
    {
        super(mapper);
        this.esquemaTarMapper = (IEsquemaTarMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<EsquemaTar> buscarTodos()
    {
        return this.buscar(new EsquemaTar(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<EsquemaTar> buscarPorIdEsquema(int codigoEsquema)
    {
        EsquemaTar esquema = EsquemaTar.builder().codigoEsquema(codigoEsquema).build();
        return this.buscar(esquema, Verbo.GET);
    }
}