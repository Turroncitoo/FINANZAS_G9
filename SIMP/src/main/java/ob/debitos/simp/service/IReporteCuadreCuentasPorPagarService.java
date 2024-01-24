package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.model.criterio.CriterioBusquedaReporteCuadreCuentaPorPagar;
import ob.debitos.simp.model.reporte.ReporteMoneda;

public interface IReporteCuadreCuentasPorPagarService
{

    public List<ReporteMoneda> buscarResumen(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio);

    public List<ReporteMoneda> buscarDetalle(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio);

    public List<ReporteMoneda> buscarAutorizacionesDelDia(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio);

    public List<ReporteMoneda> buscarDetalleAutorizaciones(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterio);
    
    public void exportarResumenCompensacion(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            HttpServletResponse response) throws IOException;

    public void exportarDetalleCompensacion(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            HttpServletResponse response) throws IOException;

    public void exportarResumenAutorizaciones(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            HttpServletResponse response) throws IOException;

    public void exportarDetalleAutorizaciones(
            CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            HttpServletResponse response) throws IOException;

}
