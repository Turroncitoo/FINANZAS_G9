package ob.debitos.simp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleContable;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenContable;
import ob.debitos.simp.model.reporte.ReporteDetalleContable;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.model.reporte.ReporteResumenContable;

/**
 * Define los métodos asociados a las operaciones de consulta de los montos de
 * débito y crédito de cada asiento contable asignado en el proceso de
 * contabilización, los cuales deberán ser implementados.
 * 
 * @author Carla Ulloa
 */
public interface IReporteContableService
{
    /**
     * Obtiene los montos de débito y crédito por cada cuenta contable y
     * agrupados por tipo de moneda (soles o dólares) y fecha de proceso.
     * 
     * @param criterioBusqueda
     *            almacena la fecha de inicio y fecha de fin a utilizarse en la
     *            consulta
     * @return lista de {@link ReporteMoneda}
     */
    public List<ReporteMoneda> buscarResumenContable(
            CriterioBusquedaResumenContable criterioBusqueda);

    /**
     * Obtiene los montos de débito y crédito por cada cuenta contable y
     * transacción agrupados en primer nivel por criterios(*) y en segundo nivel
     * por tipo de moneda (soles o dólares) y fecha de proceso.
     * <p>
     * (*)El atributo <b>criterios</b> de
     * {@link CriterioBusquedaDetalleContable} contiene varios criterios los
     * cuales describen el filtro por tipo de contabilización (fondos [F],
     * comisiones [C] y ajustes [A]) y el rol de transacción (emisor [E] y
     * misceláneo [M]).
     * 
     * @param criterioBusqueda
     *            almacena la fecha de inicio y fecha de fin a utilizarse en la
     *            consulta
     * @return lista de {@link ReporteDetalleContable}
     */
    public List<ReporteDetalleContable> buscarDetalleContableVariosCriterios(
            CriterioBusquedaDetalleContable criterioBusqueda);

    /**
     * Obtiene los montos de débito y crédito por cada cuenta contable y
     * transacción agrupados por tipo de moneda (soles o dólares) y fecha de
     * proceso.
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda de la consulta
     * @param criterio
     *            describe un filtro por tipo de contabilización (fondos [F],
     *            comisiones [C] y ajustes [A]) y el rol de transacción (emisor
     *            [E] y misceláneo [M]).
     * @return lista de {@link ReporteMoneda}
     */
    public List<ReporteMoneda> buscarDetalleContableUnCriterio(
            CriterioBusquedaDetalleContable criterioBusqueda, String criterio);

    /**
     * Obtiene los montos de débito y crédito por cada cuenta contable y
     * transacción agrupados por tipo de moneda (soles o dólares).
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda de la consulta
     * @return lista de {@link ReporteMoneda}
     */
    public List<ReporteMoneda> buscarResumenPorPeriodoContable(
            CriterioBusquedaResumenContable criterioBusqueda);

    /**
     * Obtiene los montos de débito y crédito por cada cuenta contable y
     * transacción agrupados por tipo de moneda (soles o dólares) para exportar.
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda de la consulta
     * @return lista de {@link ReporteResumenContable}
     */
    public List<ReporteResumenContable> buscarResumenPorPeriodoContableParaPDF(
            CriterioBusquedaResumenContable criterio);

    /**
     * Obtiene los montos de débito y crédito por cada cuenta contable y
     * agrupados por tipo de moneda (soles o dólares) y fecha de proceso.
     * 
     * @param criterioBusqueda
     *            almacena la fecha de inicio y fecha de fin a utilizarse en la
     *            consulta, asi mismo el codigo de la insticucion
     * @param response
     *            proporcionar una funcionalidad específica de HTTP al enviar
     *            una respuesta
     */
    public void exportarResumenContable(
            CriterioBusquedaResumenContable criterioBusqueda,
            HttpServletResponse response) throws IOException;

    /**
     * Obtiene los montos de débito y crédito por cada cuenta contable y
     * agrupados por tipo de moneda (soles o dólares) y fecha de proceso.
     * 
     * @param criterioBusqueda
     *            almacena la fecha de inicio y fecha de fin a utilizarse en la
     *            consulta, asi mismo el codigo de la insticucion
     * @param response
     *            proporcionar una funcionalidad específica de HTTP al enviar
     *            una respuesta
     */
    public void exportarDetalleContable(
            CriterioBusquedaDetalleContable criterioBusqueda,
            HttpServletResponse response) throws IOException;

    public void generarPdfReporteContableResumenPorPeriodo(
            CriterioBusquedaResumenContable criterioBusqueda,
            HttpServletResponse response)
            throws IOException, JRException, SQLException;
}