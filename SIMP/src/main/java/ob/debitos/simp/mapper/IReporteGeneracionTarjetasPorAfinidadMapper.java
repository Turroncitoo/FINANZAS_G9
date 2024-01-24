package ob.debitos.simp.mapper;

import ob.debitos.simp.model.criterio.CriterioBusquedaGeneracionTarjetas;
import ob.debitos.simp.model.reporte.ReporteGeneracionTarjetasPorAfinidad;

import java.util.List;

public interface IReporteGeneracionTarjetasPorAfinidadMapper {

    List<ReporteGeneracionTarjetasPorAfinidad> generarResumenPorRangoFechas(CriterioBusquedaGeneracionTarjetas criterios);

}
