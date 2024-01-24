package ob.debitos.simp.service.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IPerfilMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.seguridad.Perfil;
import ob.debitos.simp.service.IPerfilService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class PerfilService extends MantenibleService<Perfil> implements IPerfilService
{
    private IPerfilMapper perfilMapper;

    public PerfilService(@Qualifier("IPerfilMapper") IMantenibleMapper<Perfil> mapper)
    {
        super(mapper);
        this.perfilMapper = (IPerfilMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Perfil> buscarTodos()
    {
        return this.buscar(new Perfil(), Verbo.GETS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Perfil> buscarPorCodigoPerfil(String idPerfil)
    {
        Perfil perfil = Perfil.builder().idPerfil(idPerfil).build();
        return this.buscar(perfil, Verbo.GET);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Perfil> buscarRecursosPorIdPerfil(String idPerfil)
    {
        return perfilMapper.buscarRecursosPorIdPerfil(idPerfil);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarPerfil(Perfil perfil)
    {
        this.registrar(perfil);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarPerfil(Perfil perfil)
    {
        this.actualizar(perfil);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarPerfil(Perfil perfil)
    {
        this.eliminar(perfil);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existePerfil(String idPerfil)
    {
        return !this.buscarPorCodigoPerfil(idPerfil).isEmpty();
    }
}