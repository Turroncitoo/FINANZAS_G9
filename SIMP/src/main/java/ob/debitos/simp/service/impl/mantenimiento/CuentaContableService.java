package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICuentaContableMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CuentaContable;
import ob.debitos.simp.service.ICuentaContableService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

/**
 * Ejecuta la lógica de negocio asociada a las operaciones de mantenimiento de
 * cuentas contables.
 * <p>
 * Esta clase al ser un servicio de mantenimieno extiende de
 * {@link MantenibleService} e implementa la interface
 * {@link ICuentaContableService} y delega la función de acceso a la base de
 * datos a la interface {@link ICuentaContableMapper} solo para operaciones de
 * mantenimiento.
 *
 * @author Carla Ulloa
 */
@Service
public class CuentaContableService extends MantenibleService<CuentaContable>
        implements ICuentaContableService
{
    @SuppressWarnings("unused")
    private ICuentaContableMapper cuentaContableMapper;

    /**
     * Instancia la interface {@link ICuentaContableMapper} a partir de la
     * interface genérica {@link IMantenibleMapper}.
     *
     * @param mapper
     *         interface genérica para las interfaces <b>mapper</b> de
     *         mantenimiento
     */
    public CuentaContableService(
            @Qualifier("ICuentaContableMapper") IMantenibleMapper<CuentaContable> mapper)
    {
        super(mapper);
        this.cuentaContableMapper = (ICuentaContableMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContable> buscarTodos()
    {
        return this.buscar(new CuentaContable(), Verbo.GETS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CuentaContable> buscarPorCodigo(Integer idCuenta)
    {
        CuentaContable cuentaContable = CuentaContable.builder()
                .idCuenta(idCuenta).build();
        return this.buscar(cuentaContable, Verbo.GET);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCuentaContable(CuentaContable cuentaContable)
    {
        this.registrar(cuentaContable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCuentaContable(CuentaContable cuentaContable)
    {
        this.actualizar(cuentaContable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCuentaContable(CuentaContable cuentaContable)
    {
        this.eliminar(cuentaContable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCuentaContable(Integer idCuenta)
    {
        return !this.buscarPorCodigo(idCuenta).isEmpty();
    }

    @Override
    public boolean existeNumeroCuentaContable(String numeroCuentaContable)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<CuentaContable> buscarPorCuentaContable(String numeroCuenta)
    {
        // TODO Auto-generated method stub
        return null;
    }
}