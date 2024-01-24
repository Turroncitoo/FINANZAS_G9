package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacion;
import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.reporte.ReporteCompensacionCanal;
import ob.debitos.simp.model.reporte.ReporteCompensacionExportacion;
import ob.debitos.simp.model.reporte.ReporteCompensacionInstitucion;
import ob.debitos.simp.model.reporte.ReporteCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.reporte.ReporteCompensacionReceptor;

public interface IReporteCompensacionMapper
{
    
    public List<ReporteCompensacionCanal> reporteCanal(CriterioBusquedaCompensacion criterioBusqueda);

    public List<ReporteCompensacionInstitucion> reporteInstitucion(CriterioBusquedaCompensacion criterioBusqueda);

    public List<ReporteCompensacionReceptor> buscarCompensaciones(CriterioBusquedaCompensacion criterioBusqueda);

    public List<ReporteCompensacionExportacion> buscarCompensacionesReporte(CriterioBusquedaCompensacion criterioBusqueda);

    public List<ReporteCompensacionPorGiroDeNegocio> buscarCompensacionEmisorPorPorGiroDeNegocio(CriterioBusquedaCompensacionPorGiroDeNegocio criterioBusqueda);

}
