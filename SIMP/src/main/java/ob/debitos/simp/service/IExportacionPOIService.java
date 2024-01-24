package ob.debitos.simp.service;

import ob.debitos.simp.model.mantenimiento.ConceptoComision;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Define los métodos genéricos comunes para las clase de servicio de
 * exportacion de reportes y consultas.
 *
 * @param <V> La clase a la cual se generará la exportación de este servicio
 * @author Anthony Oroche
 */
public interface IExportacionPOIService<V>
{

    /**
     * Genera una exportación normal, en base a los criterios de busqueda.
     *
     * @param nombreArchivo  registra el nombre del archivo a generar.
     * @param lista          lista de objetos en que se generará el archivo.
     * @param filtros        matriz de cadenas con la configuración de los titulos y
     *                       atributos para la inserción de los criterios de busqueda.
     * @param cabeceras      matriz de cadenas con la configuración de los titulos,
     *                       atributos, formato y tamaño de la columnas para la inserción
     *                       de los registros de la consulta o reporte.
     * @param agregarTotales indicador para agregar o no la cabeceras de los valores
     *                       totales de columnas con formato cantidad, monto o comisiones.
     * @param tipoReporte    indica el tipo de reporte usar. 1: solo reporte; 2: reporte
     *                       con filtros; 3: reporte con filtros y titulo.
     * @param zoom           indica el zoom a usar para la creación de las hojas del
     *                       archivo, el min es 10 y el max es 400.
     * @param request        de {@link HttpServletRequest}.
     * @param response       de {@link HttpServletResponse}.
     */
    public void generarExportacionNormal(String nombreArchivo, List<V> lista, String[][] filtros, String[][] cabeceras, boolean agregarTotales, int tipoReporte, int zoom, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * Genera una exportación normal con comisiones de manera dinámica, en base
     * a los criterios de busqueda.
     *
     * @param nombreArchivo         registra el nombre del archivo a generar.
     * @param nombreListaComisiones almacena el nombre del atributo del objeto generico, para el
     *                              mapeo de la lista de {@link ConceptoComision}.
     * @param lista                 lista de objetos en que se generará el archivo.
     * @param filtros               matriz de cadenas con la configuración de los titulos y
     *                              atributos para la inserción de los criterios de busqueda.
     * @param cabeceras             matriz de cadenas con la configuración de los titulos,
     *                              atributos, formato y tamaño de la columnas para la inserción
     *                              de los registros de la consulta o reporte.
     * @param conceptosComision     lista de {@link ConceptoComision} que se usara parael aramado
     *                              de las columnas de comisiones.
     * @param tipoReporte           indica el tipo de reporte usar. 1: solo reporte; 2: reporte
     *                              con filtros; 3: reporte con filtros y titulo.
     * @param zoom                  indica el zoom a usar para la creación de las hojas del
     *                              archivo, el min es 10 y el max es 400.
     * @param request               de {@link HttpServletRequest}.
     * @param response              de {@link HttpServletResponse}.
     */
    public void generarExportacionNormalComis(String nombreArchivo, String nombreListaComisiones, List<V> lista, String[][] filtros, String[][] cabeceras, List<ConceptoComision> conceptosComision, int tipoReporte, int zoom,
                                              HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * Genera una exportación con pestañas de monedas de manera normal, en base
     * a los criterios de busqueda.
     *
     * @param nombreArchivo  registra el nombre del archivo a generar.
     * @param listaSoles     lista de objetos en que se generará la hoja de SOLES del
     *                       archivo.
     * @param listaDolares   lista de objetos en que se generará la hoja de DOLARES del
     *                       archivo.
     * @param filtros        matriz de cadenas con la configuración de los titulos y
     *                       atributos para la inserción de los criterios de busqueda.
     * @param cabeceras      matriz de cadenas con la configuración de los titulos,
     *                       atributos, formato y tamaño de la columnas para la inserción
     *                       de los registros de la consulta o reporte.
     * @param agregarTotales indicador para agregar o no la cabeceras de los valores
     *                       totales de columnas con formato cantidad, monto o comisiones.
     * @param tipoReporte    indica el tipo de reporte usar. 1: solo reporte; 2: reporte
     *                       con filtros; 3: reporte con filtros y titulo.
     * @param zoom           indica el zoom a usar para la creación de las hojas del
     *                       archivo, el min es 10 y el max es 400.
     * @param request        de {@link HttpServletRequest}.
     * @param response       de {@link HttpServletResponse}.
     */
    public void generarExportacionMonedaNormal(String nombreArchivo, List<V> listaSoles, List<V> listaDolares, String[][] filtros, String[][] cabeceras, boolean agregarTotales, int tipoReporte, int zoom, HttpServletRequest request,
                                               HttpServletResponse response) throws IOException;

    /**
     * Genera una exportación con pestañas de monedas de manera normal con
     * comisiones de manera dinámica, en base a los criterios de busqueda.
     *
     * @param nombreArchivo         registra el nombre del archivo a generar.
     * @param nombreListaComisiones almacena el nombre del atributo del objeto generico, para el
     *                              mapeo de la lista de {@link ConceptoComision}.
     * @param listaSoles            lista de objetos en que se generará la hoja de SOLES del
     *                              archivo.
     * @param listaDolares          lista de objetos en que se generará la hoja de DOLARES del
     *                              archivo.
     * @param filtros               matriz de cadenas con la configuración de los titulos y
     *                              atributos para la inserción de los criterios de busqueda.
     * @param cabeceras             matriz de cadenas con la configuración de los titulos,
     *                              atributos, formato y tamaño de la columnas para la inserción
     *                              de los registros de la consulta o reporte.
     * @param conceptosComision     lista de {@link ConceptoComision} que se usara parael aramado
     *                              de las columnas de comisiones.
     * @param tipoReporte           indica el tipo de reporte usar. 1: solo reporte; 2: reporte
     *                              con filtros; 3: reporte con filtros y titulo.
     * @param zoom                  indica el zoom a usar para la creación de las hojas del
     *                              archivo, el min es 10 y el max es 400.
     * @param request               de {@link HttpServletRequest}.
     * @param response              de {@link HttpServletResponse}.
     */
    public void generarExportacionMonedaComis(String nombreArchivo, String nombreListaComisiones, List<V> listaSoles, List<V> listaDolares, String[][] filtros, String[][] cabeceras, List<ConceptoComision> conceptosComision, int tipoReporte, int zoom,
                                              HttpServletRequest request, HttpServletResponse response) throws IOException;

}
