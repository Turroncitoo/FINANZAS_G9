package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaReporteFacturacion;
import ob.debitos.simp.model.reporte.ReporteMoneda;

public interface IReporteFacturacionUBAMapper
{

    public List<ReporteMoneda> buscarResumen(CriterioBusquedaReporteFacturacion criterio);

    public List<ReporteMoneda> buscarDetalle(CriterioBusquedaReporteFacturacion criterio);

    public List<ReporteMoneda> buscarMiscelaneos(CriterioBusquedaReporteFacturacion criterio);

}
