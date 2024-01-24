package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICodigoRptaSwitchMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoRespuestaSwitch;
import ob.debitos.simp.service.ICodigoRptaSwitchService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CodigoRptaSwitchService extends MantenibleService<CodigoRespuestaSwitch>
        implements ICodigoRptaSwitchService
{
    @SuppressWarnings("unused")
    private ICodigoRptaSwitchMapper codigoRptaSwitchMapper;

    public CodigoRptaSwitchService(
            @Qualifier("ICodigoRptaSwitchMapper") IMantenibleMapper<CodigoRespuestaSwitch> mapper)
    {
        super(mapper);
        this.codigoRptaSwitchMapper = (ICodigoRptaSwitchMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CodigoRespuestaSwitch> buscarTodos()
    {
        return this.buscar(new CodigoRespuestaSwitch(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CodigoRespuestaSwitch> buscarPorCodigoRespuestaSwitch(String codigoRespuestaSwitch)
    {
        CodigoRespuestaSwitch codigoRptaSwitch = CodigoRespuestaSwitch.builder()
                .codigoRespuestaSwitch(codigoRespuestaSwitch).build();
        return this.buscar(codigoRptaSwitch, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCodigoRespuestaSwitch(String codigoRespuestaSwitch)
    {
        return !this.buscarPorCodigoRespuestaSwitch(codigoRespuestaSwitch).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCodigoRptaSwitch(CodigoRespuestaSwitch codigoRptaSwitch)
    {
        this.registrar(codigoRptaSwitch);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCodigoRptaSwitch(CodigoRespuestaSwitch codigoRptaSwitch)
    {
        this.actualizar(codigoRptaSwitch);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCodigoRptaSwitch(CodigoRespuestaSwitch codigoRptaSwitch)
    {
        this.eliminar(codigoRptaSwitch);
    }
}