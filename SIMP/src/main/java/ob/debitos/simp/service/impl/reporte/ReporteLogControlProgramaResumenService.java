package ob.debitos.simp.service.impl.reporte;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteLogControlProgramaResumenMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlProgramaResumen;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlProgramaResumenDetalle;
import ob.debitos.simp.model.reporte.ReporteLogControlProgramaResumen;
import ob.debitos.simp.model.reporte.ReporteLogControlProgramaResumenDetalle;
import ob.debitos.simp.service.IReporteLogControlProgramaResumenService;

@Service
public class ReporteLogControlProgramaResumenService implements IReporteLogControlProgramaResumenService {

	private @Autowired IReporteLogControlProgramaResumenMapper reporteLogControlProgramaResumenMapper;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ReporteLogControlProgramaResumen> buscarTodos(){
        return this.reporteLogControlProgramaResumenMapper.buscarTodos();
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteLogControlProgramaResumen> filtrar(CriterioBusquedaLogControlProgramaResumen criterio) {
		return this.reporteLogControlProgramaResumenMapper.filtrar(criterio);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteLogControlProgramaResumenDetalle> filtrarDetalle(
			CriterioBusquedaLogControlProgramaResumenDetalle criterio){
		return this.reporteLogControlProgramaResumenMapper.filtrarDetalle(criterio);
	}; 
	
}
