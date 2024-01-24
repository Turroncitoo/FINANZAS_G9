package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleContable;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenContable;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.model.reporte.ReporteResumenContable;

/**
 * Realiza las operaciones de consulta del balance contable, como resultado del
 * proceso de contabilización, a nivel detalle y resumen.
 * <p>
 * Esta interface <b>mapper</b> tiene asociado el fichero xml
 * <b>IReporteContableMapper.xml</b> donde se especifican las queries sql.
 * 
 * @author Hanz Llanto
 */
public interface IReporteContableMapper
{
    /**
     * Obtienes las cuentas contables y sus correspondientes montos de crédito o
     * débito agrupado por fecha de proceso y moneda (soles o dólares).
     * <p>
     * La vista sql asociada a esta consulta es <b>ReporteResumenContable</b>.
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda de la consulta
     * @return lista de {@link ReporteMoneda} que satisfacen los criterios de
     *         búsqueda
     */
    public List<ReporteMoneda> buscarResumenContable(
            CriterioBusquedaResumenContable criterioBusqueda);

    /**
     * Obtiene los montos de crédito y débito de cada cuenta contable y
     * transacción agrupadas por tipo de moneda.
     * <p>
     * El procedimiento almacenado asociado a esta consulta es
     * <b>REPORT_CONTABLE</b>.
     * 
     * @param criterioBusqueda
     *            almacena la fecha de proceso inicio y fin a utilizar como
     *            criterio de búsqueda
     * @return lista de {@link ReporteMoneda} que satisface los criterios de
     *         búsqueda
     */
    public List<ReporteMoneda> buscarDetalleContable(
            CriterioBusquedaDetalleContable criterioBusqueda);
    
    /**
     * Obtiene los montos de débito y crédito por cada cuenta contable y
     * transacción agrupados por tipo de moneda (soles o dólares). 
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda de la consulta
     * @return lista de {@link ReporteMoneda}
     */
    public List<ReporteMoneda> buscarResumenPorPeriodoContable(CriterioBusquedaResumenContable criterioBusqueda);
	
    /**
     * Obtiene los montos de débito y crédito por cada cuenta contable y
     * transacción agrupados por tipo de moneda (soles o dólares) para 
     * exportar. 
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda de la consulta
     * @return lista de {@link ReporteResumenContable}
     */
	public List<ReporteResumenContable> buscarResumenPorPeriodoContableParaReporte(CriterioBusquedaResumenContable criterio);
}