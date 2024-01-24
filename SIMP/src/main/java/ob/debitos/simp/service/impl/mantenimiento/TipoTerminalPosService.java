package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ITipoTerminalPosMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.TipoTerminalPos;
import ob.debitos.simp.service.ITipoTerminalPosService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class TipoTerminalPosService extends MantenibleService<TipoTerminalPos>
        implements ITipoTerminalPosService
{
    @SuppressWarnings("unused")
    private ITipoTerminalPosMapper tipoTerminalPosMapper;

    public TipoTerminalPosService(
            @Qualifier("ITipoTerminalPosMapper") IMantenibleMapper<TipoTerminalPos> mapper)
    {
        super(mapper);
        this.tipoTerminalPosMapper = (ITipoTerminalPosMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TipoTerminalPos> buscarTodos()
    {
        return this.buscar(new TipoTerminalPos(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TipoTerminalPos> buscarPorCodigoTipoTerminalPos(String codigoTipoTerminalPOS)
    {
        TipoTerminalPos tipoTerminalPos = TipoTerminalPos.builder()
                .codigoTipoTerminalPOS(codigoTipoTerminalPOS).build();
        return this.buscar(tipoTerminalPos, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeTipoTerminarPos(String codigoTipoTerminalPOS)
    {
        return !this.buscarPorCodigoTipoTerminalPos(codigoTipoTerminalPOS).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarTipoTerminalPos(TipoTerminalPos tipoTerminalPos)
    {
        this.registrar(tipoTerminalPos);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarTipoTerminalPos(TipoTerminalPos tipoTerminalPos)
    {
        this.actualizar(tipoTerminalPos);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarTipoTerminalPos(TipoTerminalPos tipoTerminalPos)
    {
        this.eliminar(tipoTerminalPos);
    }
}