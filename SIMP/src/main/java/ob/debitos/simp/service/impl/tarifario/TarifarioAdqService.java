package ob.debitos.simp.service.impl.tarifario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ITarifarioAdqMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.tarifario.TarifarioAdq;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.ITarifarioAdqService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class TarifarioAdqService extends MantenibleService<TarifarioAdq>
        implements ITarifarioAdqService
{
    @SuppressWarnings("unused")
    private ITarifarioAdqMapper adqMapper;
    
    private @Autowired IParametroGeneralService parametroGeneralService;

    public TarifarioAdqService(
            @Qualifier("ITarifarioAdqMapper") IMantenibleMapper<TarifarioAdq> mapper)
    {
        super(mapper);
        this.adqMapper = (ITarifarioAdqMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TarifarioAdq> buscarTodos()
    {
        return this.buscar(new TarifarioAdq(), Verbo.GETS);
    }

    public List<TarifarioAdq> buscarPorCodigo(TarifarioAdq adquirente)
    {
        adquirente.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        return this.buscar(adquirente, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TarifarioAdq> registrarTarAdq(TarifarioAdq adquirente)
    {
        adquirente.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.registrar(adquirente);
        return this.buscarPorCodigo(adquirente);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TarifarioAdq> actualizarTarAdq(TarifarioAdq adquirente)
    {
        adquirente.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.actualizar(adquirente);
        return this.buscarPorCodigo(adquirente);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarTarAdq(TarifarioAdq adquirente)
    {
        adquirente.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.eliminar(adquirente);
    }
}