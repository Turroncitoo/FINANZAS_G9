package ob.debitos.simp.aspecto;

import static ob.debitos.simp.utilitario.ConstantesGenerales.ESTADO_EN_EJECUCION;
import static ob.debitos.simp.utilitario.ConstantesGenerales.ESTADO_ERROR;
import static ob.debitos.simp.utilitario.ConstantesGenerales.ESTADO_EXITO;
import static ob.debitos.simp.utilitario.ConstantesGenerales.MENSAJE_EXITO;
import static ob.debitos.simp.utilitario.ConstantesGenerales.MENSAJE_NO_PROCESA_SABADO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ob.debitos.simp.aspecto.anotacion.LogControlPrograma;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;
import ob.debitos.simp.service.ILogControlProgramaDetalleService;
import ob.debitos.simp.service.ILogControlProgramaResumenService;
import ob.debitos.simp.service.ILogControlProgramaService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IProgramaService;
import ob.debitos.simp.service.excepcion.HeaderTrailerExcepcion;
import ob.debitos.simp.utilitario.DatesUtils;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Aspect
@Component
public class LogControlProgramaAspecto
{
    private @Autowired Logger logger;
    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired IProgramaService programaService;
    private @Autowired ILogControlProgramaService logControlProgramaService;
    private @Autowired ILogControlProgramaResumenService logControlProgramaResumenService;
    private @Autowired ILogControlProgramaDetalleService logControlProgramaDetalleService;
    private static final String TAB_CTRL_PROC_RES = "TAB_CTRL_PROC_RES";
    private static final String CTRE = "CTRE";

    @SuppressWarnings("unchecked")
    @Around("@annotation(logControlPrograma)")
    public Object registrarLogControlProgramaResumenService(ProceedingJoinPoint proceedingJoinPoint,
            LogControlPrograma logControlPrograma) throws Throwable
    {
    	logger.info("LogControlProgramaAspecto");
    	int estado = ESTADO_EXITO;
        String mensaje = MENSAJE_EXITO;
        long tiempoFinal = 0;
        long tiempoInicial = System.currentTimeMillis();
        int registros = 0;
        int idSecuenciaLogControlPrograma = 0;
        List<LogControlProgramaDetalle> logControlProgramaDetalles = new ArrayList<>();
        Date fechaProceso = this.parametroGeneralService.buscarFechaProceso();
        Object[] argumentos = proceedingJoinPoint.getArgs();
        ControlEjecucionPrograma controlEjecucionPrograma = (ControlEjecucionPrograma) argumentos[0];
        controlEjecucionPrograma.setFechaProceso(fechaProceso);
        this.logControlProgramaResumenService.existeProgramaAnteriorNoEjecutadoPorGrupo(
                controlEjecucionPrograma.getCodigoGrupo(),
                controlEjecucionPrograma.getCodigoPrograma());
        try
        {
            Object oRegistros = null;
            if (!DatesUtils.esSabado(fechaProceso))
            {
            	this.actualizarAestadoEnEjecucion(logControlPrograma, controlEjecucionPrograma);
                oRegistros = proceedingJoinPoint.proceed();
            } else
            {
                boolean procesaSabado = this.programaService.procesaSabado(
                        controlEjecucionPrograma.getCodigoGrupo(),
                        controlEjecucionPrograma.getCodigoPrograma(),
                        controlEjecucionPrograma.getCodigoSubModulo());
                if (procesaSabado)
                {
                	this.actualizarAestadoEnEjecucion(logControlPrograma, controlEjecucionPrograma);
                    oRegistros = proceedingJoinPoint.proceed();
                } else
                {
                    mensaje = MENSAJE_NO_PROCESA_SABADO;
                }
            }
            if (logControlPrograma.detallado() && oRegistros instanceof List<?>)
            {
                logControlProgramaDetalles = (List<LogControlProgramaDetalle>) oRegistros;
                return oRegistros;
            } else
            {
                registros = (oRegistros instanceof Integer) ? (int) oRegistros : 0;
                return oRegistros instanceof List<?> ? oRegistros : registros;
            }
        } catch (HeaderTrailerExcepcion ex)
        {
        	logger.info("LogControlProgramaAspecto header trailer exception");
            estado = ESTADO_ERROR;
            mensaje = ExcepcionUtil.obtenerMensajeExcepcionRaiz(ex);
            logControlProgramaDetalles = ex.getLogControlProgramaDetalles();
            this.logger.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex)
        {
            estado = ESTADO_ERROR;
            mensaje = ExcepcionUtil.obtenerMensajeExcepcionRaiz(ex);
            this.logger.error(ex.getMessage(), ex);
            throw ex;
        } finally
        {
            tiempoFinal = System.currentTimeMillis();
            controlEjecucionPrograma.setEstado(estado);
            controlEjecucionPrograma.setMensaje(mensaje);
            controlEjecucionPrograma.setRegistros(registros);
            controlEjecucionPrograma.setTiempoEjecucionInicial(tiempoInicial);
            controlEjecucionPrograma.setTiempoEjecucionFinal(tiempoFinal);
            controlEjecucionPrograma.setLogControlProgramaDetalles(logControlProgramaDetalles);
            logger.info("LogControlProgramaAspecto Finally Actualiza log y detalle");
            idSecuenciaLogControlPrograma = this.logControlProgramaService
                    .registrarLogControlPrograma(controlEjecucionPrograma);
            this.logControlProgramaDetalleService.registrarLogControlProgramaDetalle(
                    logControlProgramaDetalles, idSecuenciaLogControlPrograma);
            if(logControlPrograma.registrarLogResumen()){
                this.logControlProgramaResumenService
                .actualizarLogControlProgramaResumen(controlEjecucionPrograma);	
            }
        }
    }
    
    private void actualizarAestadoEnEjecucion(LogControlPrograma logControlPrograma, ControlEjecucionPrograma controlEjecucionPrograma){
		if (logControlPrograma.registrarLogResumen() && !controlEjecucionPrograma.getCodigoGrupo().equals(TAB_CTRL_PROC_RES)
				&& !controlEjecucionPrograma.getCodigoPrograma().equals(CTRE)) {
			this.logControlProgramaResumenService.actualizarEstado(controlEjecucionPrograma,
					ESTADO_EN_EJECUCION);
		}
	}
}