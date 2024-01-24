package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.PANEntryMode;

/**
 * Define los métodos asociados a las operaciones de mantenimiento de modos
 * de entrada de tarjeta, los cuales deberán ser implementados.
 * <p>
 * Esta clase extiende de {@link IMantenibleService} para poder heredar los
 * métodos comunes de mantenimiento.
 * 
 * @author Fernando Fonseca
 * @see IMantenibleService
 * @see PANEntryMode
 */
public interface IPANEntryModeService extends IMantenibleService<PANEntryMode> 
{	
	
    /**
     * Obtiene todos los modos de entrada de tarjeta registrados.
     * 
     * @return los {@link PANEntryMode} registrados
     */
	public List<PANEntryMode> buscarTodos();
	
    /**
     * Obtiene un modo de entrada de tarjeta por el código de la misma.
     * 
     * @param codigo
     *            el código del modo de entrada de tarjeta a buscar
     * @return lista de {@link PANEntryMode} que satisfacen la búsqueda. Si ningún modo
     *         de entrada tiene como código <b>codigo</b> entonces la lista es vacía
     */
	public List<PANEntryMode> buscarPorCodigo(String codigo);
	
    /**
     * Evalua si el código del modo de entrada de tarjeta existe en las tablas del SIMP.
     * 
     * @param codigo
     *            el código del modo de entrada de tarjeta a verificar su existencia
     * @return {@code true} si existe algún modo de entrada que tenga como código
     *         <b>codigo</b>, sino es el caso retorna {@code false}
     */
	public boolean existePANEntryMode(String codigo);
	
    /**
     * Registra un modo de entrada de tarjeta
     * 
     * @param panEntrMode
     *            el modo de entrada de tarjeta a registrar
     */
	public void registrarPANEntryMode(PANEntryMode panEntrMode);
	
    /**
     * Actualiza un modo de entrada de tarjeta
     * 
     * @param panEntryMode
     *            el modo de entrada de tarjeta a actualizar
     */
	public void actualizarPANEntryMode(PANEntryMode panEntryMode);
	
    /**
     * Elimina un modo de entrada de tarjeta
     * 
     * @param panEntryMode
     *            el modo de entrada de tarjeta a eliminar
     */
	public void eliminarPANEntryMode(PANEntryMode panEntryMode);
}
