package ob.debitos.simp.service;

import java.util.concurrent.Future;

import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.model.proceso.ResultadoProceso;

public interface IEjecucionManualAsyncService {
	
	public Future<ResultadoProceso> ejecutarProcedimientoDescargaPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa);
}
