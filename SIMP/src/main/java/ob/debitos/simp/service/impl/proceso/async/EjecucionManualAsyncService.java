package ob.debitos.simp.service.impl.proceso.async;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.model.proceso.ResultadoProceso;
import ob.debitos.simp.service.IEjecucionManualAsyncService;
import ob.debitos.simp.service.IEjecucionManualProcesorService;

@Service
public class EjecucionManualAsyncService implements IEjecucionManualAsyncService  {
	private @Autowired IEjecucionManualProcesorService ejecucionManualProcesorService;
	private @Autowired Logger logger;

	@Async(value = "taskExecutor")
	public Future<ResultadoProceso> ejecutarProcedimientoDescargaPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa){
		ResultadoProceso resultadoProceso = null;
		try{
			this.ejecucionManualProcesorService
					.ejecutarProcedimientoDescargaPorInstitucion(controlEjecucionPrograma, programa); 
			resultadoProceso = new ResultadoProceso(1, null);	
		} catch (Exception ex){
			resultadoProceso = new ResultadoProceso(0, ex);
			logger.error("{}",ex);
		}
		return new AsyncResult<>(resultadoProceso);  
	}
}
