package ob.debitos.simp.service.impl.tarifario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ITarifarioEmisorMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.tarifario.TarifarioEmisor;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.ITarifarioEmisorService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class TarifarioEmisorService extends MantenibleService<TarifarioEmisor>
        implements ITarifarioEmisorService
{
    @SuppressWarnings("unused")
    private ITarifarioEmisorMapper emisorMapper;
    
    private @Autowired IParametroGeneralService parametroGeneralService;

    public TarifarioEmisorService(
            @Qualifier("ITarifarioEmisorMapper") IMantenibleMapper<TarifarioEmisor> mapper)
    {
        super(mapper);
        this.emisorMapper = (ITarifarioEmisorMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TarifarioEmisor> buscarTodos()
    {
        return this.buscar(new TarifarioEmisor(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TarifarioEmisor> buscarPorCodigo(TarifarioEmisor emisor)
    {
        emisor.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        return this.buscar(emisor, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TarifarioEmisor> registrarTarEmisor(TarifarioEmisor emisor)
    {
        emisor.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.registrar(emisor);
        return this.buscarPorCodigo(emisor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TarifarioEmisor> actualizarTarEmisor(TarifarioEmisor emisor)
    {
        emisor.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.actualizar(emisor);
        return this.buscarPorCodigo(emisor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarTarEmisor(TarifarioEmisor emisor)
    {
        emisor.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.eliminar(emisor);
    }
}