package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.model.proceso.ReporteContableLogCont;

public interface IReporteContablePrepagoService {

	public Integer generarArchivoContable(CriterioBusquedaArchivoContablePrepago 
			criterioBusquedaArchivoContablePrepago) throws IOException;
	
	public Integer generarReporteContable(CriterioBusquedaArchivoContablePrepago 
			criterioBusquedaArchivoContablePrepago) throws IOException;
	
	public List<ReporteContableLogCont> buscarReporteContable(CriterioBusquedaArchivoContablePrepago 
			criterioBusquedaArchivoContablePrepago);
}
