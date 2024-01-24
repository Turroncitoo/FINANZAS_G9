package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteTransaccionesMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransacciones;
import ob.debitos.simp.model.reporte.ReporteTransaccionesTarjetas;
import ob.debitos.simp.service.IReporteTransaccionesService;

@Service
public class ReporteTransaccionesService implements IReporteTransaccionesService{
	
	private @Autowired IReporteTransaccionesMapper reporteTransaccionesMapper;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteTransaccionesTarjetas> buscarTransaccionesTarjetas(CriterioBusquedaResumenTransacciones criterioBusqueda){
		return this.reporteTransaccionesMapper.buscarTransaccionesTarjetas(criterioBusqueda);
	}
}
