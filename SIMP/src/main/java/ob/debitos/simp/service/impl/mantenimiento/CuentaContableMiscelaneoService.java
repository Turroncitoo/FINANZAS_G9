package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICuentaContableMiscelaneoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CuentaContableMiscelaneo;
import ob.debitos.simp.service.ICuentaContableMiscelaneoService;
import ob.debitos.simp.service.excepcion.EscenarioException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CuentaContableMiscelaneoService extends MantenibleService<CuentaContableMiscelaneo>
        implements ICuentaContableMiscelaneoService
{
    @SuppressWarnings("unused")
    private ICuentaContableMiscelaneoMapper cuentaContableMiscelaneoMapper;

    public CuentaContableMiscelaneoService(
            @Qualifier("ICuentaContableMiscelaneoMapper") IMantenibleMapper<CuentaContableMiscelaneo> mapper)
    {
        super(mapper);
        this.cuentaContableMiscelaneoMapper = (ICuentaContableMiscelaneoMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableMiscelaneo> buscarTodos()
    {
        return this.buscar(new CuentaContableMiscelaneo(), Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableMiscelaneo> buscarPorEscenario(
            CuentaContableMiscelaneo cuentaContableMiscelaneo)
    {
        return this.buscar(cuentaContableMiscelaneo, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableMiscelaneo> registrarCuentaContable(
            CuentaContableMiscelaneo cuentaContableMiscelaneo)
    {
        if (this.existeEscenario(cuentaContableMiscelaneo))
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_YA_EXISTE);
        }
        this.registrar(cuentaContableMiscelaneo);
        return this.buscarPorEscenario(cuentaContableMiscelaneo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableMiscelaneo> actualizarCuentaContable(
            CuentaContableMiscelaneo cuentaContableMiscelaneo)
    {
        if (!this.existeEscenario(cuentaContableMiscelaneo))
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_YA_EXISTE);
        }
        this.actualizar(cuentaContableMiscelaneo);
        return this.buscarPorEscenario(cuentaContableMiscelaneo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCuentaContable(CuentaContableMiscelaneo cuentaContableMiscelaneo)
    {
        if (!this.existeEscenario(cuentaContableMiscelaneo))
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_YA_EXISTE);
        }
        this.eliminar(cuentaContableMiscelaneo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existeEscenario(CuentaContableMiscelaneo cuentaContableMiscelaneo)
    {
        return this.existe(cuentaContableMiscelaneo);
    }
}