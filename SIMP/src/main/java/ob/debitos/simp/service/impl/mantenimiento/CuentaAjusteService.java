package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICuentaAjusteMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CuentaAjuste;
import ob.debitos.simp.service.ICuentaAjusteService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

/**
 * Ejecuta la lógica de negocio asociada a las operaciones de mantenimiento de
 * cuentas de ajuste.
 * <p>
 * Esta clase al ser un servicio de mantenimieno extiende de
 * {@link MantenibleService} e implementa la interface
 * {@link ICuentaAjusteService} y delega la función de acceso a la base de datos
 * a la interface {@link ICuentaAjusteMapper} solo para operaciones de
 * mantenimiento.
 * 
 * @author Carla Ulloa
 */
@Service
public class CuentaAjusteService extends MantenibleService<CuentaAjuste> implements ICuentaAjusteService
{
    @SuppressWarnings("unused")
    private ICuentaAjusteMapper cuentaAjusteMapper;

    /**
     * Instancia la interface {@link ICuentaAjusteMapper} a partir de la
     * interface genérica {@link IMantenibleMapper}.
     * 
     * @param mapper
     *            interface genérica para las interfaces <b>mapper</b> de
     *            mantenimiento
     */
    public CuentaAjusteService(@Qualifier("ICuentaAjusteMapper") IMantenibleMapper<CuentaAjuste> mapper)
    {
        super(mapper);
        this.cuentaAjusteMapper = (ICuentaAjusteMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaAjuste> buscarTodos()
    {
        return this.buscar(new CuentaAjuste(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CuentaAjuste> buscarPorCodigo(Integer rolTransaccion, Integer monedaCompensacion, Integer tipoMovimiento, String registroContable)
    {
        CuentaAjuste cuentaAjuste = CuentaAjuste.builder().rolTransaccion(rolTransaccion).monedaCompensacion(monedaCompensacion).tipoMovimiento(tipoMovimiento).registroContable(registroContable).build();
        return this.buscar(cuentaAjuste, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCuentaAjuste(CuentaAjuste cuentaAjuste)
    {
        this.registrar(cuentaAjuste);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCuentaAjuste(CuentaAjuste cuentaAjuste)
    {
        this.actualizar(cuentaAjuste);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCuentaAjuste(CuentaAjuste cuentaAjuste)
    {
        this.eliminar(cuentaAjuste);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCuentaAjuste(Integer rolTransaccion, Integer monedaCompensacion, Integer tipoMovimiento, String registroContable)
    {
        return !this.buscarPorCodigo(rolTransaccion, monedaCompensacion, tipoMovimiento, registroContable).isEmpty();
    }
}