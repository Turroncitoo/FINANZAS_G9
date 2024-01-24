package ob.debitos.simp.service.impl.tarifario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IEscenarioMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.tarifario.Escenario;
import ob.debitos.simp.service.IEscenarioService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class EscenarioService extends MantenibleService<Escenario> implements IEscenarioService
{
    @SuppressWarnings("unused")
    private IEscenarioMapper escenarioMapper;
    
    private @Autowired IParametroGeneralService parametroGeneralService;

    public EscenarioService(@Qualifier("IEscenarioMapper") IMantenibleMapper<Escenario> mapper)
    {
        super(mapper);
        this.escenarioMapper = (IEscenarioMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Escenario> buscarTodos()
    {
        return this.buscar(new Escenario(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Escenario> buscarPorCodigo(Escenario escenario)
    {
        escenario.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        return this.buscar(escenario, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeEscenario(Escenario escenario)
    {
        return !this.buscarPorCodigo(escenario).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Escenario> registrarEscenario(Escenario escenario)
    {
        escenario.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.registrar(escenario);
        return this.buscarPorCodigo(escenario);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Escenario> actualizarEscenario(Escenario escenario)
    {
        escenario.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.actualizar(escenario);
        return this.buscarPorCodigo(escenario);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarEscenario(Escenario escenario)
    {
        escenario.setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        this.eliminar(escenario);
    }
}