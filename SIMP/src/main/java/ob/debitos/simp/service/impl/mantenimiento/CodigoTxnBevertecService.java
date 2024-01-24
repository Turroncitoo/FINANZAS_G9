package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICodigoTxnBevertecMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoTransaccionBevertec;
import ob.debitos.simp.service.ICodigoTxnBevertecService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CodigoTxnBevertecService extends MantenibleService<CodigoTransaccionBevertec>
        implements ICodigoTxnBevertecService
{
    @SuppressWarnings("unused")
    private ICodigoTxnBevertecMapper codigoTxnBevertecMapper;

    public CodigoTxnBevertecService(
            @Qualifier("ICodigoTxnBevertecMapper") IMantenibleMapper<CodigoTransaccionBevertec> mapper)
    {
        super(mapper);
        this.codigoTxnBevertecMapper = (ICodigoTxnBevertecMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CodigoTransaccionBevertec> buscarTodos()
    {
        return this.buscar(new CodigoTransaccionBevertec(), Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CodigoTransaccionBevertec> buscarPorCodigoCanalEmisor(String codigoCanalEmisor)
    {
        CodigoTransaccionBevertec codigoTxnBevertec = CodigoTransaccionBevertec.builder()
                .codigoCanalEmisor(codigoCanalEmisor).build();
        return this.buscar(codigoTxnBevertec, Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CodigoTransaccionBevertec> buscarPorCodigoCanalEmisorTipoTransaccionCodTransaccion(
            String codigoCanalEmisor, String tipoTransaccion, String codTransaccion)
    {
        CodigoTransaccionBevertec codigoTxnBevertec = CodigoTransaccionBevertec.builder()
                .codigoCanalEmisor(codigoCanalEmisor).tipoTransaccion(tipoTransaccion)
                .codTransaccion(codTransaccion).build();
        return this.buscar(codigoTxnBevertec, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCodigoTxnBevertec(String codigoCanalEmisor, String tipoTransaccion,
            String codTransaccion)
    {
        return !this.buscarPorCodigoCanalEmisorTipoTransaccionCodTransaccion(codigoCanalEmisor,
                tipoTransaccion, codTransaccion).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCodigoTxnBevertec(CodigoTransaccionBevertec codigoTxnBevertec)
    {
        this.registrar(codigoTxnBevertec);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCodigoTxnBevertec(CodigoTransaccionBevertec codigoTxnBevertec)
    {
        this.actualizar(codigoTxnBevertec);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCodigoTxnBevertec(CodigoTransaccionBevertec codigoTxnBevertec)
    {
        this.eliminar(codigoTxnBevertec);
    }
}