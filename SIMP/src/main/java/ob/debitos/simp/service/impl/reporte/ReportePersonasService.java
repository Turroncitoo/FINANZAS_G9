package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReportePersonasMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaReportePersonas;
import ob.debitos.simp.model.reporte.ReporteMoneda;

import ob.debitos.simp.service.IReportePersonasService;

@Service
public class ReportePersonasService implements IReportePersonasService {

	private @Autowired IReportePersonasMapper reportePersonasMapper;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteMoneda> obtenerReportePersonas(
			CriterioBusquedaReportePersonas criterioBusquedaReportePersonas) {
		return this.reportePersonasMapper.obtenerReportePersonas(criterioBusquedaReportePersonas);
	}

}
