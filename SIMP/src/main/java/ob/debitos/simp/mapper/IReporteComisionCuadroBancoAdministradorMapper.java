package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaResumen;
import ob.debitos.simp.model.reporte.ReporteMoneda;

public interface IReporteComisionCuadroBancoAdministradorMapper
{
    public List<ReporteMoneda> buscarComisionesCuadroBancoAdministrador(
            CriterioBusquedaResumen parametro);

    /**
     * Obtiene el cuadre de fondos, comisiones y cobros miscelaneos,
     * especificando montos deudores y acreedores, por cada clase de servicio de
     * las membresias, filtradas por fecha de proceso. La consulta se realiza a
     * la vista {@code VIE_REP_SUMARIO_COMPENSACION} en el fichero xml.
     * 
     * @param parametro
     *            almacena la fecha de proceso de la consulta
     * @return lista de {@link ReporteMoneda}
     */
    public List<ReporteMoneda> buscarSumarioCompensacion(
            CriterioBusquedaResumen parametro);
}