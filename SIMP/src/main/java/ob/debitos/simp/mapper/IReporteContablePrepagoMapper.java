package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.model.proceso.ArchivoContableFondosPrepago;
import ob.debitos.simp.model.proceso.ReporteContableLogCont;

public interface IReporteContablePrepagoMapper {

	public List<ReporteContableLogCont> generarReporteContable(
			CriterioBusquedaArchivoContablePrepago ArchivoContableFondosPrepago);
	
	public List<ArchivoContableFondosPrepago> generarArchivoContable(
			CriterioBusquedaArchivoContablePrepago ArchivoContableFondosPrepago);
}
