package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IModoEntradaPosMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ModoEntradaPos;
import ob.debitos.simp.service.IModoEntradaPosService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ModoEntradaPosService extends MantenibleService<ModoEntradaPos>
        implements IModoEntradaPosService
{
    @SuppressWarnings("unused")
    private IModoEntradaPosMapper modoEntradaPosMapper;

    public ModoEntradaPosService(
            @Qualifier("IModoEntradaPosMapper") IMantenibleMapper<ModoEntradaPos> mapper)
    {
        super(mapper);
        this.modoEntradaPosMapper = (IModoEntradaPosMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ModoEntradaPos> buscarTodos()
    {
        return this.buscar(new ModoEntradaPos(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ModoEntradaPos> buscarPorCodigoModoEntradaPos(String codigoModoEntradaPOS)
    {
        ModoEntradaPos modoEntradaPos = ModoEntradaPos.builder()
                .codigoModoEntradaPOS(codigoModoEntradaPOS).build();
        return this.buscar(modoEntradaPos, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeModoEntradaPos(String codigoModoEntradaPOS)
    {
        return !this.buscarPorCodigoModoEntradaPos(codigoModoEntradaPOS).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarModoEntradaPos(ModoEntradaPos modoEntradaPos)
    {
        this.registrar(modoEntradaPos);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarModoEntradaPos(ModoEntradaPos modoEntradaPos)
    {
        this.actualizar(modoEntradaPos);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarModoEntradaPos(ModoEntradaPos modoEntradaPos)
    {
        this.eliminar(modoEntradaPos);
    }
}