package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CuentaAjuste;

/**
 * Define los métodos asociados a las operaciones de mantenimiento de cuentas de
 * ajuste, los cuales deberán ser implementados.
 * <p>
 * Esta clase extiende de {@link IMantenibleService} para poder heredar los
 * métodos comunes de mantenimiento.
 * 
 * @author Carla Ulloa
 * @see IMantenibleService
 * @see CuentaAjuste
 */
public interface ICuentaAjusteService extends IMantenibleService<CuentaAjuste>
{
    /**
     * Obtiene todas las cuentas de ajuste registradas.
     * 
     * @return lista de {@link CuentaAjuste} registrados
     */
    public List<CuentaAjuste> buscarTodos();

    /**
     * Obtiene una cuenta ajuste por rol de transacción, código de moneda de
     * compensación, tipo de movimiento y registro contable.
     * 
     * @param rolTransaccion
     *            el rol de transacción de la cuenta de ajuste a buscar
     * @param monedaCompensacion
     *            el código de moneda de compensación de la cuenta de ajuste a
     *            buscar
     * @param tipoMovimiento
     *            el tipo de movimiento de la cuenta de ajuste a buscar
     * @param registroContable
     *            el tipo de registro contable de la cuenta de ajuste a buscar
     *            (C = Cargo, A = Abono)
     * @return lista de {@link CuentaAjuste} que satisfacen la búsqueda, sino
     *         fuera el caso retorna una lista vacía
     */
    public List<CuentaAjuste> buscarPorCodigo(Integer rolTransaccion, Integer monedaCompensacion,
            Integer tipoMovimiento, String registroContable);

    /**
     * Evalua si existe un rol de transacción, código de moneda de compensación,
     * tipo de movimiento y registro contable asociado a una cuenta de ajuste.
     * 
     * @param rolTransaccion
     *            el rol de transacción de la cuenta de ajuste a buscar
     * @param monedaCompensacion
     *            el código de moneda de compensación de la cuenta de ajuste a
     *            buscar
     * @param tipoMovimiento
     *            el tipo de movimiento de la cuenta de ajuste a buscar
     * @param registroContable
     *            el tipo de registro contable de la cuenta de ajuste a buscar
     *            (C = Cargo, A = Abono)
     * @return {@code true} si existe una cuenta de ajuste que satisface la
     *         búsqueda, sino fuera el caso retorna {@code false}
     */
    public boolean existeCuentaAjuste(Integer rolTransaccion, Integer monedaCompensacion,
            Integer tipoMovimiento, String registroContable);

    /**
     * Registra una cuenta de ajuste.
     * 
     * @param cuentaAjuste
     *            la cuenta de ajuste a registrar
     */
    public void registrarCuentaAjuste(CuentaAjuste cuentaAjuste);

    /**
     * Actualiza una cuenta de ajuste.
     * 
     * @param cuentaAjuste
     *            la cuenta de ajuste a actualizar
     */
    public void actualizarCuentaAjuste(CuentaAjuste cuentaAjuste);

    /**
     * Elimina una cuenta de ajuste.
     * 
     * @param cuentaAjuste
     *            la cuenta de ajuste a eliminar
     */
    public void eliminarCuentaAjuste(CuentaAjuste cuentaAjuste);
}