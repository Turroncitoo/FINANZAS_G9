package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CuentaContable;

/**
 * Define los métodos asociados a las operaciones de mantenimiento de cuentas
 * contables, los cuales deberán ser implementados.
 * <p>
 * Esta clase extiende de {@link IMantenibleService} para poder heredar los
 * métodos comunes de mantenimiento.
 * 
 * @author Carla Ulloa
 * @see IMantenibleService
 * @see CuentaContable
 */
public interface ICuentaContableService extends IMantenibleService<CuentaContable>
{
    /**
     * Obtiene todas las cuentas contables registradas.
     * 
     * @return lista de {@link CuentaContable} registradas
     */
    public List<CuentaContable> buscarTodos();

    /**
     * Obtiene una cuenta contable por su identificador de cuenta.
     * 
     * @param idCuenta
     *            el identificador de la cuenta contable
     * @return lista de {@link CuentaContable} que satisfacen la búsqueda, sino
     *         fuera el caso retorna una lista vacía
     */
    public List<CuentaContable> buscarPorCodigo(Integer idCuenta);
    

    /**
     * Obtiene una cuenta contable por su identificador de cuenta.
     * 
     * @param idCuenta
     *            el identificador de la cuenta contable
     * @return lista de {@link CuentaContable} que satisfacen la búsqueda, sino
     *         fuera el caso retorna una lista vacía
     */
    public boolean existeNumeroCuentaContable(String numeroCuentaContable) ;
    
    /**
     * Obtiene una cuenta contable por su identificador de cuenta.
     * 
     * @param idCuenta
     *            el identificador de la cuenta contable
     * @return lista de {@link CuentaContable} que satisfacen la búsqueda, sino
     *         fuera el caso retorna una lista vacía
     */
    public List<CuentaContable> buscarPorCuentaContable(String numeroCuenta);

    /**
     * Registra una cuenta contable.
     * 
     * @param cuentaContable
     *            la cuenta contable a registrar
     */
    public void registrarCuentaContable(CuentaContable cuentaContable);

    /**
     * Actualiza una cuenta contable.
     * 
     * @param cuentaContable
     *            la cuenta contable a actualizar
     */
    public void actualizarCuentaContable(CuentaContable cuentaContable);

    /**
     * Elimina una cuenta contable.
     * 
     * @param cuentaContable
     *            la cuenta contable a eliminar
     */
    public void eliminarCuentaContable(CuentaContable cuentaContable);

    /**
     * Evalua si existe alguna cuenta contable con el identificador
     * <b>idCuenta</b> en las tablas del SIMP.
     * 
     * @param idCuenta
     *            el identificador de la cuenta contable a buscar
     * @return {@code true} si existe alguna cuenta contable con el
     *         identificador <b>idCuenta</b>, sino retorna {@code false}
     */
    public boolean existeCuentaContable(Integer idCuenta);
}