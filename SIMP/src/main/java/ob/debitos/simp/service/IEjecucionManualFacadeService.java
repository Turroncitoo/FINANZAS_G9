package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.model.proceso.ResultadoProceso;

public interface IEjecucionManualFacadeService {
	
	public List<ResultadoProceso> ejecutarProcedimientoDescarga(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa);
}
