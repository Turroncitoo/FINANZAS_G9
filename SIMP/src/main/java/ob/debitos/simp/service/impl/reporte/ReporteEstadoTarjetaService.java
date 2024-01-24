package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteEstadoTarjetaMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoTarjeta;
import ob.debitos.simp.model.reporte.ReporteEstadoTarjeta;
import ob.debitos.simp.service.IReporteEstadoTarjetaService;

@Service
public class ReporteEstadoTarjetaService implements IReporteEstadoTarjetaService{
	
	private @Autowired IReporteEstadoTarjetaMapper reporteEstadoTarjetaMapper;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteEstadoTarjeta> buscarResumenMensualPorCriterios(CriterioBusquedaEstadoTarjeta criterioBusqueda) {
		return this.reporteEstadoTarjetaMapper.buscarResumenMensualPorCriterios(criterioBusqueda);
	}

}
