package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaReporteCuadreCuentaPorPagar;
import ob.debitos.simp.model.reporte.ReporteMoneda;

public interface IReporteCuadreCuentasPorPagarMapper
{

    public List<ReporteMoneda> buscarResumen(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio);

    public List<ReporteMoneda> buscarDetalle(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio);

    public List<ReporteMoneda> buscarAutorizacionesDelDia(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio);

    public List<ReporteMoneda> buscarDetalleAutorizaciones(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio);

}
