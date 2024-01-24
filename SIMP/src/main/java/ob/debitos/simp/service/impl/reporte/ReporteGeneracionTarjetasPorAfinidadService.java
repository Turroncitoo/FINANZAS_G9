package ob.debitos.simp.service.impl.reporte;

import ob.debitos.simp.mapper.IReporteGeneracionTarjetasPorAfinidadMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaGeneracionTarjetas;
import ob.debitos.simp.model.reporte.ReporteGeneracionTarjetasPorAfinidad;
import ob.debitos.simp.service.IReporteGeneracionTarjetasPorAfinidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReporteGeneracionTarjetasPorAfinidadService implements IReporteGeneracionTarjetasPorAfinidadService {

    private @Autowired IReporteGeneracionTarjetasPorAfinidadMapper reporteGeneracionTarjetasPorAfinidadMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteGeneracionTarjetasPorAfinidad> generarReportePorRangoFechas(CriterioBusquedaGeneracionTarjetas criterios) {
        return reporteGeneracionTarjetasPorAfinidadMapper.generarResumenPorRangoFechas(criterios);
    }
}
