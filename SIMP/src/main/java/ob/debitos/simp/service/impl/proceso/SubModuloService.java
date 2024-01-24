package ob.debitos.simp.service.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ISubModuloMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.proceso.SubModulo;
import ob.debitos.simp.service.ISubModuloService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class SubModuloService extends MantenibleService<SubModulo>
        implements ISubModuloService
{
    @SuppressWarnings("unused")
    private ISubModuloMapper subModuloMapper;

    public SubModuloService(
            @Qualifier("ISubModuloMapper") IMantenibleMapper<SubModulo> mapper)
    {
        super(mapper);
        this.subModuloMapper = (ISubModuloMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<SubModulo> buscarTodos()
    {
        return this.buscar(new SubModulo(), Verbo.GETS);
    }

    public List<SubModulo> buscarPorCodigo(SubModulo subModulo)
    {
        return this.buscar(subModulo, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<SubModulo> registrarSubModulo(SubModulo subModulo)
    {
        this.registrar(subModulo);
        return this.buscarPorCodigo(subModulo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<SubModulo> actualizarSubModulo(SubModulo subModulo)
    {
        this.actualizar(subModulo);
        return this.buscarPorCodigo(subModulo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarSubModulo(SubModulo subModulo)
    {
        this.eliminar(subModulo);
    }
}