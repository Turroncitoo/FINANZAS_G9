package ob.debitos.simp.service.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICategoriaRecursoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.MultiTabDet;
import ob.debitos.simp.model.seguridad.CategoriaRecurso;
import ob.debitos.simp.service.ICategoriaRecursoService;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.MultiTablaUtil;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CategoriaRecursoService extends MantenibleService<CategoriaRecurso>
        implements ICategoriaRecursoService
{
    private ICategoriaRecursoMapper categoriaRecursoMapper;
    private @Autowired IMultiTabDetService multiTabDetService;

    public CategoriaRecursoService(
            @Qualifier("ICategoriaRecursoMapper") IMantenibleMapper<CategoriaRecurso> mapper)
    {
        super(mapper);
        this.categoriaRecursoMapper = (ICategoriaRecursoMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CategoriaRecurso> buscarTodosCategoriaRecurso()
    {
        List<MultiTabDet> accionesRecurso = multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_ACCION_RECURSO);
        List<CategoriaRecurso> categoriasRecurso = categoriaRecursoMapper
                .buscarTodosCategoriaRecurso();
        for (CategoriaRecurso categoriaRecurso : categoriasRecurso)
        {
            categoriaRecurso.setAcciones(MultiTablaUtil.convertirAMultiTabDetAccionesRecurso(
                    categoriaRecurso.getAccionCategoria(), accionesRecurso));
        }
        return categoriasRecurso;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CategoriaRecurso> buscarTodos()
    {
        return this.buscar(new CategoriaRecurso(), Verbo.GETS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCategoriaRecurso(CategoriaRecurso categoriaRecurso)
    {
        this.registrar(categoriaRecurso);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CategoriaRecurso> buscarPorCodigoCategoriaRecurso(String categoria)
    {
        CategoriaRecurso categoriaRecurso = CategoriaRecurso.builder().categoria(categoria).build();
        return this.buscar(categoriaRecurso, Verbo.GET);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCategoriaRecurso(CategoriaRecurso categoriaRecurso)
    {
        this.actualizar(categoriaRecurso);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCategoriaRecurso(CategoriaRecurso categoriaRecurso)
    {
        this.eliminar(categoriaRecurso);
    }
}