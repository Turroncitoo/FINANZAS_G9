package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.PINEntryCapability;

/**
 * Define los métodos asociados a las operaciones de mantenimiento de capacidad de
 * ingreso de txns de tipo PIN, los cuales deberán ser implementados.
 * <p>
 * Esta clase extiende de {@link IMantenibleService} para poder heredar los
 * métodos comunes de mantenimiento.
 * 
 * @author Fernando Fonseca
 * @see IMantenibleService
 * @see PINEntryCapability
 */
public interface IPINEntryCapabilityService extends IMantenibleService<PINEntryCapability> 
{
	
    /**
     * Obtiene todas las capacidades de ingreso de txns de tipo PIN registradas.
     * 
     * @return las {@link PINEntryCapability} registradas
     */
	public List<PINEntryCapability> buscarTodos();
	
    /**
     * Obtiene una capacidad de ingreso de txn tipo PIN por el código de la misma.
     * 
     * @param codigo
     *            el código de la capacidad de ingreso a buscar
     * @return lista de {@link PINEntryCapability} que satisfacen la búsqueda. Si ninguna
     *         capacidad de ingreso tiene como código <b>codigo</b> entonces la lista es vacía
     */
	public List<PINEntryCapability> buscarPorCodigo(String codigo);
	
    /**
     * Evalua si el código de capacidad de ingreso existe en las tablas del SIMP.
     * 
     * @param codigo
     *            el código de la capacidad de ingreso a verificar su existencia
     * @return {@code true} si existe alguna capacidad de ingreso que tenga como código
     *         <b>codigo</b>, sino es el caso retorna {@code false}
     */
	public boolean existePINEntryCapability(String codigo);
	
    /**
     * Registra una capacidad de ingreso de una txn de tipo PIN
     * 
     * @param pinEntryCapability
     *            la capacidad de ingreso de una txn de tipo PIN a registrar
     */
	public void registrarPINEntryCapability(PINEntryCapability pinEntryCapability);
	
    /**
     * Actualiza una capacidad de ingreso de una txn de tipo PIN
     * 
     * @param pinEntryCapability
     *            la capacidad de ingreso de una txn de tipo PIN a actualizar
     */
	public void actualizarPINEntryCapability(PINEntryCapability pinEntryCapability);
	
    /**
     * Elimina un capacidad de ingreso de una txn de tipo PIN
     * 
     * @param pinEntryCapability
     *            la capacidad de ingreso de una txn de tipo PIN a eliminar
     */
	public void eliminarPINEntryCapability(PINEntryCapability pinEntryCapability);
}
