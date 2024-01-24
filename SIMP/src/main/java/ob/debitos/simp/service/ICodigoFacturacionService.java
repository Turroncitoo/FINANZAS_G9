package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CodigoFacturacion;



public interface ICodigoFacturacionService extends IMantenibleService<CodigoFacturacion>{
	/**
     * Obtiene todos los códigos de facturación registrados.
     * 
     * @return los {@link CodigoFacturacion} registrados
     */
	public List<CodigoFacturacion> buscarTodos();
	
    /**
     * Obtiene un código de facturación por su código del mismo.
     * 
     * @param idCodigoFacturacion
     *            el código del código de facturación a buscar
     * @return lista de {@link CodigoFacturacion} que satisfacen la búsqueda. Si ningún 
     *         código de facturación tiene como código <b>idCodigoFacturacion</b> entonces 
     *         la lista es vacía.
     */
	public List<CodigoFacturacion> buscarPorCodigo(Integer idCodigoFacturacion);
	
    /**
     * Registra un código de facturación
     * 
     * @param codigoFacturacion
     *            el código de facturación a registrar
     */
	public void registrarCodigoFacturacion(CodigoFacturacion codigoFacturacion);
	
    /**
     * Actualiza un código de facturación
     * 
     * @param codigoFacturacion
     *            el código de facturación a actualizar
     */
	public void actualizarCodigoFacturacion(CodigoFacturacion codigoFacturacion);
	
    /**
     * Elimina un código de facturación
     * 
     * @param codigoFacturacion
     *            el código de facturación a eliminar
     */
	public void eliminarCodigoFacturacion(CodigoFacturacion codigoFacturacion);
	
    /**
     * Evalua si el código del código de facturación existe en las tablas del SIMP.
     * 
     * @param idCodigoFacturacion
     *            el código del código de facturación a verificar su existencia
     * @return {@code true} si existe algún código de facturación que tenga como código
     *         <b>idCodigoFacturacion</b>, sino es el caso retorna {@code false}
     */
	public boolean existeCodigoFacturacion(Integer idCodigoFacturacion);

}
