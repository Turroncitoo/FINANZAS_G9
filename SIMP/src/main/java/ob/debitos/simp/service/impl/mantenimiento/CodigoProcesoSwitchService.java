package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICodigoProcesoSwitchMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoProcSwitch;
import ob.debitos.simp.service.ICodigoProcesoSwitchService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CodigoProcesoSwitchService extends MantenibleService<CodigoProcSwitch>
        implements ICodigoProcesoSwitchService
{
    @SuppressWarnings("unused")
    private ICodigoProcesoSwitchMapper codigoProcesoSwitchMapper;

    public CodigoProcesoSwitchService(
            @Qualifier("ICodigoProcesoSwitchMapper") IMantenibleMapper<CodigoProcSwitch> mapper)
    {
        super(mapper);
        this.codigoProcesoSwitchMapper = (ICodigoProcesoSwitchMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CodigoProcSwitch> buscarTodos()
    {
        return this.buscar(new CodigoProcSwitch(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CodigoProcSwitch> buscarPorCodigoProcesoSwitch(String codProcesoSwitch)
    {
        CodigoProcSwitch codigoProcesoSwitch = CodigoProcSwitch.builder()
                .codigoProcesoSwitch(codProcesoSwitch).build();
        return this.buscar(codigoProcesoSwitch, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCodigoProcesoSwitch(String codigoProcesoSwitch)
    {
        return !this.buscarPorCodigoProcesoSwitch(codigoProcesoSwitch).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCodigoProcesoSwitch(CodigoProcSwitch codigoProcesoSwitch)
    {
        this.registrar(codigoProcesoSwitch);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCodigoProcesoSwitch(CodigoProcSwitch codigoProcesoSwitch)
    {
        this.actualizar(codigoProcesoSwitch);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCodigoProcesoSwitch(CodigoProcSwitch codigoProcesoSwitch)
    {
        this.eliminar(codigoProcesoSwitch);
    }
}