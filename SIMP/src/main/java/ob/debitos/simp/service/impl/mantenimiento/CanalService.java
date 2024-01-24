package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICanalMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Canal;
import ob.debitos.simp.service.ICanalService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CanalService extends MantenibleService<Canal> implements ICanalService
{
    @SuppressWarnings("unused")
    private ICanalMapper canalMapper;

    public CanalService(@Qualifier("ICanalMapper") IMantenibleMapper<Canal> mapper)
    {
        super(mapper);
        this.canalMapper = (ICanalMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Canal> buscarTodos()
    {
        return this.buscar(new Canal(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Canal> buscarPorIdCanal(int idCanal)
    {
        Canal canal = Canal.builder().idCanal(idCanal).build();
        return this.buscar(canal, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCanal(int idCanal)
    {
        return !this.buscarPorIdCanal(idCanal).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCanal(Canal canal)
    {
        this.registrar(canal);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCanal(Canal canal)
    {
        this.actualizar(canal);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCanal(Canal canal)
    {
        this.eliminar(canal);
    }
}