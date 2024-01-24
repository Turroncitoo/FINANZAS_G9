package ob.debitos.simp.service;

import ob.debitos.simp.model.criterio.CriterioBusquedaGeneracionTarjetas;
import ob.debitos.simp.model.reporte.ReporteGeneracionTarjetasPorAfinidad;

import java.util.List;

public interface IReporteGeneracionTarjetasPorAfinidadService {
    List<ReporteGeneracionTarjetasPorAfinidad> generarReportePorRangoFechas(CriterioBusquedaGeneracionTarjetas criterios);
}
