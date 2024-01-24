package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacion;
import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.reporte.ReporteCompensacionCanal;
import ob.debitos.simp.model.reporte.ReporteCompensacionExportacion;
import ob.debitos.simp.model.reporte.ReporteCompensacionInstitucion;
import ob.debitos.simp.model.reporte.ReporteCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.reporte.ReporteCompensacionReceptor;

public interface IReporteCompensacionService
{

    public List<ReporteCompensacionCanal> buscarCompensacionCanal(CriterioBusquedaCompensacion criterio);

    public List<ReporteCompensacionInstitucion> buscarCompensacionInstitucion(CriterioBusquedaCompensacion criterioBusquedaCompensacion);

    public void exportarDetalleCompensacionInstitucion(List<ReporteCompensacionInstitucion> list, CriterioBusquedaCompensacion criterioBusqueda, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public List<ReporteCompensacionReceptor> buscarCompensaciones(CriterioBusquedaCompensacion criterioBusqueda);

    public List<ReporteCompensacionExportacion> buscarCompensacionesReporte(CriterioBusquedaCompensacion criterioBusqueda);

    public JasperReportBuilder reporteDinamico(CriterioBusquedaCompensacion criterioBusqueda);

    public List<ReporteCompensacionPorGiroDeNegocio> buscarCompensacionEmisorPorPorGiroDeNegocio(CriterioBusquedaCompensacionPorGiroDeNegocio criterioBusqueda);

    public void exportarReporteCompensacionCanal(List<ReporteCompensacionCanal> list, CriterioBusquedaCompensacion criterioBusquedaTransaccionCompensacion, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void exportarReporteCompensacionEmisorPorPorGiroDeNegocio(List<ReporteCompensacionPorGiroDeNegocio> list, CriterioBusquedaCompensacionPorGiroDeNegocio criterioBusqueda, HttpServletRequest request, HttpServletResponse response)
            throws IOException;

}