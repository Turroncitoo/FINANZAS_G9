package ob.debitos.simp.service.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IPerfilRecursoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.seguridad.Perfil;
import ob.debitos.simp.model.seguridad.PerfilRecurso;
import ob.debitos.simp.model.seguridad.SecRecurso;
import ob.debitos.simp.service.IPerfilRecursoService;
import ob.debitos.simp.service.impl.MantenibleService;

@Service
public class PerfilRecursoService extends MantenibleService<PerfilRecurso>
        implements IPerfilRecursoService
{
    private IPerfilRecursoMapper perfilRecursoMapper;

    public PerfilRecursoService(
            @Qualifier("IPerfilRecursoMapper") IMantenibleMapper<PerfilRecurso> mapper)
    {
        super(mapper);
        this.perfilRecursoMapper = (IPerfilRecursoMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void asignarPermisos(Perfil perfil)
    {
        PerfilRecurso perfilRecurso = new PerfilRecurso();
        String idPerfil = perfil.getIdPerfil();
        perfilRecurso.setIdPerfil(idPerfil);
        List<SecRecurso> recursos = perfil.getRecursos();
        this.perfilRecursoMapper.eliminarPermisos(idPerfil);
        for (SecRecurso secRecurso : recursos)
        {
            perfilRecurso.setIdRecurso(secRecurso.getIdRecurso());
            perfilRecurso.setPermiso(secRecurso.getAccionRecurso());
            this.registrar(perfilRecurso);
        }
    }
}