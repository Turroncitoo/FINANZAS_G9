package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IInstitucionMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class InstitucionService extends MantenibleService<Institucion> implements IInstitucionService
{

    @SuppressWarnings("unused")
    private IInstitucionMapper institucionMapper;
    private @Autowired IParametroGeneralService parametroGeneralService;

    public InstitucionService(@Qualifier("IInstitucionMapper") IMantenibleMapper<Institucion> mapper)
    {
        super(mapper);
        this.institucionMapper = (IInstitucionMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Institucion> buscarTodos()
    {
        return this.buscar(new Institucion(), Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Institucion> buscarPorCodigoInstitucion(int codigoInstitucion)
    {
        Institucion institucion = Institucion.builder().codigoInstitucion(codigoInstitucion).build();
        return this.buscar(institucion, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Institucion> buscarPorCodigoInstitucionActual()
    {
        int codigoInstitucion = parametroGeneralService.buscarCodigoInstitucion();
        return this.buscarPorCodigoInstitucion(codigoInstitucion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Institucion> buscarPorInstitucionEmpresa()
    {
        return this.buscar(new Institucion(), Verbo.GETS_INS_EMP);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeInstitucion(int codigoInstitucion)
    {
        return !this.buscarPorCodigoInstitucion(codigoInstitucion).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarInstitucion(Institucion institucion)
    {
        this.registrar(institucion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarInstitucion(Institucion institucion)
    {
        this.actualizar(institucion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarInstitucion(Institucion institucion)
    {
        this.eliminar(institucion);
    }

}