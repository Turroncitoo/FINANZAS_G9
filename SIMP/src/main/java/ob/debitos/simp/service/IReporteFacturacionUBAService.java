package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaReporteFacturacion;
import ob.debitos.simp.model.reporte.ReporteMoneda;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public interface IReporteFacturacionUBAService
{
    public List<ReporteMoneda> buscarResumen(CriterioBusquedaReporteFacturacion criterio);

    public List<ReporteMoneda> buscarDetalle(CriterioBusquedaReporteFacturacion criterio);

    public List<ReporteMoneda> buscarMiscelaneos(CriterioBusquedaReporteFacturacion criterio);

    public void descargarFacturacionUbaResumen(List<ReporteMoneda> reporte, CriterioBusquedaReporteFacturacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException;
    
    public void descargarFacturacionUbaDetalle(List<ReporteMoneda> reporte, CriterioBusquedaReporteFacturacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException;
    
    public void descargarFacturacionUbaMiscelaneos(List<ReporteMoneda> reporte, CriterioBusquedaReporteFacturacion criterio, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
