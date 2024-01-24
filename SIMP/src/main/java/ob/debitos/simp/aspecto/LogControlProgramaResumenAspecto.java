package ob.debitos.simp.aspecto;

import static ob.debitos.simp.utilitario.ConstantesGenerales.ESTADO_EN_EJECUCION;
import static ob.debitos.simp.utilitario.ConstantesGenerales.ESTADO_ERROR;
import static ob.debitos.simp.utilitario.ConstantesGenerales.ESTADO_EXITO;
import static ob.debitos.simp.utilitario.ConstantesGenerales.MENSAJE_EXITO;
import static ob.debitos.simp.utilitario.ConstantesGenerales.MENSAJE_NO_PROCESA_SABADO;
import static ob.debitos.simp.utilitario.ConstantesGenerales.MENSAJE_EJECUCION_POR_INSTITUCION;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ob.debitos.simp.aspecto.anotacion.LogControlProgramaResumen;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.ResultadoProceso;
import ob.debitos.simp.service.ILogControlProgramaResumenService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IProgramaService;
import ob.debitos.simp.service.excepcion.EjecucionManualException;
import ob.debitos.simp.service.excepcion.HeaderTrailerExcepcion;
import ob.debitos.simp.utilitario.DatesUtils;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Aspect
@Component
public class LogControlProgramaResumenAspecto {
	private @Autowired Logger logger;
	private @Autowired IParametroGeneralService parametroGeneralService;
	private @Autowired IProgramaService programaService;
	private @Autowired ILogControlProgramaResumenService logControlProgramaResumenService;
	private static final String TAB_CTRL_PROC_RES = "TAB_CTRL_PROC_RES";
    private static final String CTRE = "CTRE";

	@SuppressWarnings("unchecked")
	@Around("@annotation(logControlProgramaResumen)")
	public Object registrarLogControlProgramaResumenService(ProceedingJoinPoint proceedingJoinPoint,
			LogControlProgramaResumen logControlProgramaResumen) throws Throwable {
		logger.info("LogControlProgramaResumenAspecto");
		int estado = ESTADO_EXITO;
		String mensaje = MENSAJE_EXITO;
		long tiempoFinal = 0;
		long tiempoInicial = System.currentTimeMillis();
		int registros = 0;
		List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
		Date fechaProceso = this.parametroGeneralService.buscarFechaProceso();
		Object[] argumentos = proceedingJoinPoint.getArgs();
		ControlEjecucionPrograma controlEjecucionPrograma = (ControlEjecucionPrograma) argumentos[0];
		controlEjecucionPrograma.setFechaProceso(fechaProceso);
		this.logControlProgramaResumenService.existeProgramaAnteriorNoEjecutadoPorGrupo(
				controlEjecucionPrograma.getCodigoGrupo(), controlEjecucionPrograma.getCodigoPrograma());
		try {
			Object oRegistros = null;
			if (!DatesUtils.esSabado(fechaProceso)) {
				this.actualizarAestadoEnEjecucion(controlEjecucionPrograma);
				oRegistros = proceedingJoinPoint.proceed();
			} else {
				boolean procesaSabado = this.programaService.procesaSabado(controlEjecucionPrograma.getCodigoGrupo(),
						controlEjecucionPrograma.getCodigoPrograma(), controlEjecucionPrograma.getCodigoSubModulo());
				if (procesaSabado) {
					this.actualizarAestadoEnEjecucion(controlEjecucionPrograma);
					oRegistros = proceedingJoinPoint.proceed();
				} else {
					mensaje = MENSAJE_NO_PROCESA_SABADO;
				}
			}
			logger.info("oRegistros {}", oRegistros);
			if (oRegistros instanceof List<?>) {
				resultadosProcesos = (List<ResultadoProceso>) oRegistros;
				int total = resultadosProcesos.size();
				int cantidadExitos = (int) resultadosProcesos.stream().filter(i -> i.getExito().equals(1)).count();
				if(cantidadExitos == 0){
					throw new EjecucionManualException(String.format(MENSAJE_EJECUCION_POR_INSTITUCION, cantidadExitos, total));
				}
				return oRegistros;
			} else {
				registros = (oRegistros instanceof Integer) ? (int) oRegistros : 0;
				List<ResultadoProceso> resultado = new ArrayList<>();
				resultado.add(new ResultadoProceso(registros == 0 ? 1: registros, null));
				return resultado;
			}
		} catch (HeaderTrailerExcepcion ex) {
			logger.info("LogControlProgramaResumenAspecto header trailer exception");
			estado = ESTADO_ERROR;
			mensaje = ExcepcionUtil.obtenerMensajeExcepcionRaiz(ex);
			this.logger.error(ex.getMessage(), ex);
			throw ex;
		} catch (Exception ex) {
			logger.info("Exception");
			estado = ESTADO_ERROR;
			mensaje = ExcepcionUtil.obtenerMensajeExcepcionRaiz(ex);
			this.logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			tiempoFinal = System.currentTimeMillis();
			controlEjecucionPrograma.setEstado(estado);
			controlEjecucionPrograma.setMensaje(mensaje);
			controlEjecucionPrograma.setRegistros(registros);
			controlEjecucionPrograma.setTiempoEjecucionInicial(tiempoInicial);
			controlEjecucionPrograma.setTiempoEjecucionFinal(tiempoFinal);
			logger.info("LogControlProgramaResumenAspecto Finally Actualiza log resumen {}", controlEjecucionPrograma);
			this.logControlProgramaResumenService.actualizarLogControlProgramaResumen(controlEjecucionPrograma);
			logger.info("Fin actualizacion logcontrolprogramaresumen");
		}
	}
	
	private void actualizarAestadoEnEjecucion(ControlEjecucionPrograma controlEjecucionPrograma){
		if (!controlEjecucionPrograma.getCodigoGrupo().equals(TAB_CTRL_PROC_RES)
				&& !controlEjecucionPrograma.getCodigoPrograma().equals(CTRE)) {
			this.logControlProgramaResumenService.actualizarEstado(controlEjecucionPrograma,
					ESTADO_EN_EJECUCION);
		}
	}
}
