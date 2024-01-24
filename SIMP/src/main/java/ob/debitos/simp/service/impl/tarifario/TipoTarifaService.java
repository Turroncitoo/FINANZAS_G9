package ob.debitos.simp.service.impl.tarifario;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ITipoTarifaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.tarifario.TipoTarifa;
import ob.debitos.simp.service.ITipoTarifaService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class TipoTarifaService extends MantenibleService<TipoTarifa>
        implements ITipoTarifaService
{
    @SuppressWarnings("unused")
    private ITipoTarifaMapper tipoTarifaMapper;

    public TipoTarifaService(
            @Qualifier("ITipoTarifaMapper") IMantenibleMapper<TipoTarifa> mapper)
    {
        super(mapper);
        this.tipoTarifaMapper = (ITipoTarifaMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TipoTarifa> buscarTodos()
    {
        return this.buscar(new TipoTarifa(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TipoTarifa> buscarPorTipoTarifa(TipoTarifa tipoTarifa)
    {
        return this.buscar(tipoTarifa, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TipoTarifa> registrarTipoTarifa(TipoTarifa tipoTarifa)
    {
        this.registrar(tipoTarifa);
        return this.buscarPorTipoTarifa(tipoTarifa);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TipoTarifa> actualizarTipoTarifa(TipoTarifa tipoTarifa)
    {
        this.actualizar(tipoTarifa);
        return this.buscarPorTipoTarifa(tipoTarifa);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarTipoTarifa(TipoTarifa tipoTarifa)
    {
        this.eliminar(tipoTarifa);
    }
}