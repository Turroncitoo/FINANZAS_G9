package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javaslang.Tuple;
import javaslang.Tuple2;
import ob.debitos.simp.mapper.ICodigoTransaccionMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoTransaccion;
import ob.debitos.simp.service.ICodigoTransaccionService;
import ob.debitos.simp.service.excepcion.ListaVaciaException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CodigoTransaccionService extends MantenibleService<CodigoTransaccion>
        implements ICodigoTransaccionService
{
    @SuppressWarnings("unused")
    private ICodigoTransaccionMapper codigoTransaccionMapper;

    public CodigoTransaccionService(
            @Qualifier("ICodigoTransaccionMapper") IMantenibleMapper<CodigoTransaccion> mapper)
    {
        super(mapper);
        this.codigoTransaccionMapper = (ICodigoTransaccionMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CodigoTransaccion> buscarTodos()
    {
        return this.buscar(new CodigoTransaccion(), Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CodigoTransaccion> buscarPorCodigoTransaccionCodigoClaseTransaccion(
            int codTransaccion, int codigoClaseTransaccion)
    {
        CodigoTransaccion codigoTransaccion = CodigoTransaccion.builder()
                .codigoTransaccion(codTransaccion).codigoClaseTransaccion(codigoClaseTransaccion)
                .build();
        return this.buscar(codigoTransaccion, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Tuple2<Boolean, Boolean> buscarCompensacionesPorCodigoTransaccionCodigoClaseTransaccion(
            int codTransaccion, int codigoClaseTransaccion)
    {
        List<CodigoTransaccion> codigoTransacciones = this
                .buscarPorCodigoTransaccionCodigoClaseTransaccion(codTransaccion,
                        codigoClaseTransaccion);
        if (codigoTransacciones.isEmpty())
        {
            throw new ListaVaciaException("{ListaVacia.CodigoTransaccion.codigoTransaccion}");
        }
        CodigoTransaccion codigoTransaccion = codigoTransacciones.get(0);
        return Tuple.of(codigoTransaccion.getCompensaComisiones() == 1,
                codigoTransaccion.getCompensaFondos() == 1);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CodigoTransaccion> buscarPorCodigoClaseTransaccion(int codigoClaseTransaccion)
    {
        CodigoTransaccion codigoTransaccion = CodigoTransaccion.builder()
                .codigoClaseTransaccion(codigoClaseTransaccion).build();
        return this.buscar(codigoTransaccion, Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCodigoTransaccion(int codigoTransaccion, int codigoClaseTransaccion)
    {
        return !this.buscarPorCodigoTransaccionCodigoClaseTransaccion(codigoTransaccion,
                codigoClaseTransaccion).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCodigoTransaccion(CodigoTransaccion codigoTransaccion)
    {
        this.registrar(codigoTransaccion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCodigoTransaccion(CodigoTransaccion codigoTransaccion)
    {
        this.actualizar(codigoTransaccion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCodigoTransaccion(CodigoTransaccion codigoTransaccion)
    {
        this.eliminar(codigoTransaccion);
    }
}