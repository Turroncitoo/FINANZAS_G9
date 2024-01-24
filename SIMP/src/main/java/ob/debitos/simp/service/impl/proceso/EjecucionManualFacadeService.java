package ob.debitos.simp.service.impl.proceso;

import static ob.debitos.simp.utilitario.ConstantesExcepciones.ERROR_EJECUCION_HILOS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.model.proceso.ResultadoProceso;
import ob.debitos.simp.service.IEjecucionManualAsyncService;
import ob.debitos.simp.service.IEjecucionManualFacadeService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.excepcion.EjecucionManualException;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Service
public class EjecucionManualFacadeService implements IEjecucionManualFacadeService {
	private @Autowired IInstitucionService institucionService;
	private @Autowired IEjecucionManualAsyncService ejecucionManualAsyncService;
	private @Autowired Logger logger;
	
	// Falta corregir
	public List<ResultadoProceso> ejecutarProcedimientoDescarga(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa){
		List<Future<ResultadoProceso>> resultadosFuture = new ArrayList<>();
		List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
		List<Institucion> instituciones = this.institucionService.buscarPorInstitucionEmpresa();
		Validate.notEmpty(instituciones);
		instituciones.forEach(institucion -> {
			logger.info("Institucion {}", institucion.getCodigoInstitucion());
			ControlEjecucionPrograma controlEjecucionParametro = ControlEjecucionPrograma.builder()
					.codigoGrupo(controlEjecucionPrograma.getCodigoGrupo())
					.codigoPrograma(controlEjecucionPrograma.getCodigoPrograma())
					.codigoSubModulo(controlEjecucionPrograma.getCodigoSubModulo())
					.idInstitucion(institucion.getCodigoInstitucion())
					.build();
			resultadosFuture.add(this.ejecucionManualAsyncService.ejecutarProcedimientoDescargaPorInstitucion(controlEjecucionParametro, programa));
		});
		for(Future<ResultadoProceso> future: resultadosFuture){
			ResultadoProceso resultadoProceso;
			try {
				resultadoProceso = future.get();
				resultadosProcesos.add(resultadoProceso);
				logger.info("Completo {}", resultadoProceso);
			} catch (InterruptedException | ExecutionException e) {
				logger.error(e.getMessage(), e);
				resultadosProcesos.add(ResultadoProceso.builder().exito(0).ex(e).build());
				throw new EjecucionManualException(String.format(ERROR_EJECUCION_HILOS,
                        ExcepcionUtil.obtenerMensajeExcepcionRaiz(e)));
			}
			
		}
		return resultadosProcesos;
	}
}
