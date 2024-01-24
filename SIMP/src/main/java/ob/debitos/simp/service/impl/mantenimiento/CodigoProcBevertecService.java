package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICodigoProcBevertecMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoProcesoBevertec;
import ob.debitos.simp.service.ICodigoProcBevertecService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CodigoProcBevertecService extends MantenibleService<CodigoProcesoBevertec>
        implements ICodigoProcBevertecService
{
    @SuppressWarnings("unused")
    private ICodigoProcBevertecMapper codigoProcBevertecMapper;

    public CodigoProcBevertecService(
            @Qualifier("ICodigoProcBevertecMapper") IMantenibleMapper<CodigoProcesoBevertec> mapper)
    {
        super(mapper);
        this.codigoProcBevertecMapper = (ICodigoProcBevertecMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CodigoProcesoBevertec> buscarTodos()
    {
        return this.buscar(new CodigoProcesoBevertec(), Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CodigoProcesoBevertec> buscarPorCodigoCanalEmisor(String codigoCanalEmisor)
    {
        CodigoProcesoBevertec codigoProcBevertec = CodigoProcesoBevertec.builder()
                .codigoCanalEmisor(codigoCanalEmisor).build();
        return this.buscar(codigoProcBevertec, Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CodigoProcesoBevertec> buscarPorCodigoCanalEmisorTipoTransaccion(
            String codigoCanalEmisor, String tipoTransaccion)
    {
        CodigoProcesoBevertec codigoProcBevertec = CodigoProcesoBevertec.builder()
                .codigoCanalEmisor(codigoCanalEmisor).tipoTransaccion(tipoTransaccion).build();
        return this.buscar(codigoProcBevertec, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeProcBevertec(String codigoCanalEmisor, String tipoTransaccion)
    {
        return !this.buscarPorCodigoCanalEmisorTipoTransaccion(codigoCanalEmisor, tipoTransaccion)
                .isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCodigoProcBevertec(CodigoProcesoBevertec codigoProcBevertec)
    {
        this.registrar(codigoProcBevertec);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCodigoProcBevertec(CodigoProcesoBevertec codigoProcBevertec)
    {
        this.actualizar(codigoProcBevertec);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCodigoProcBevertec(CodigoProcesoBevertec codigoProcBevertec)
    {
        this.eliminar(codigoProcBevertec);
    }
}