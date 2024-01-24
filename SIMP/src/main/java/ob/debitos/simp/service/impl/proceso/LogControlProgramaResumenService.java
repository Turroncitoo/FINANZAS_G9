package ob.debitos.simp.service.impl.proceso;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ILogControlProgramaResumenMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.LogControlProgramaResumen;
import ob.debitos.simp.service.ILogControlProgramaResumenService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.excepcion.OrdenEjecucionException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.DatesUtils;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class LogControlProgramaResumenService extends MantenibleService<LogControlProgramaResumen> implements ILogControlProgramaResumenService
{

    @SuppressWarnings("unused")
    private ILogControlProgramaResumenMapper logControlProgramaResumenMapper;

    private @Autowired IParametroGeneralService parametroGeneralService;

    private static final String GET_NOT_EXECUTED_GRUPO_PROG = "GET_NOT_EXECUTED_GRUPO_PROG";
    private static final String UPDATE_ESTADO = "UPDATE_ESTADO";

    public LogControlProgramaResumenService(@Qualifier("ILogControlProgramaResumenMapper") IMantenibleMapper<LogControlProgramaResumen> mapper)
    {
        super(mapper);
        this.logControlProgramaResumenMapper = (ILogControlProgramaResumenMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<LogControlProgramaResumen> buscarPorFechaProceso(Date fechaProceso)
    {
        LogControlProgramaResumen controlPrograma = LogControlProgramaResumen.builder().fechaProceso(fechaProceso).build();
        return this.buscar(controlPrograma, Verbo.GET_DATE);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeFechaProcesoCargada(Date fechaProceso)
    {
        return !this.buscarPorFechaProceso(fechaProceso).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void actualizarLogControlProgramaResumen(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        LogControlProgramaResumen logControlProgramaResumen = LogControlProgramaResumen.builder().fechaProceso(controlEjecucionPrograma.getFechaProceso()).codigoGrupo(controlEjecucionPrograma.getCodigoGrupo()).codigoPrograma(controlEjecucionPrograma.getCodigoPrograma())
                .codigoSubModulo(controlEjecucionPrograma.getCodigoSubModulo()).estado(controlEjecucionPrograma.getEstado()).fechaInicio(DatesUtils.obtenerFechaDeMilisegundos(controlEjecucionPrograma.getTiempoEjecucionInicial()))
                .horaInicio(DatesUtils.obtenerFechaDeMilisegundosEnFormato(controlEjecucionPrograma.getTiempoEjecucionInicial(), DatesUtils.FORMATO_HHMMSS)).fechaFin(DatesUtils.obtenerFechaDeMilisegundos(controlEjecucionPrograma.getTiempoEjecucionFinal()))
                .horaFin(DatesUtils.obtenerFechaDeMilisegundosEnFormato(controlEjecucionPrograma.getTiempoEjecucionFinal(), DatesUtils.FORMATO_HHMMSS)).build();
        this.actualizar(logControlProgramaResumen);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarEstado(ControlEjecucionPrograma controlEjecucionPrograma, int estado)
    {
        LogControlProgramaResumen logControlProgramaResumen = LogControlProgramaResumen.builder().fechaProceso(controlEjecucionPrograma.getFechaProceso()).codigoGrupo(controlEjecucionPrograma.getCodigoGrupo()).codigoPrograma(controlEjecucionPrograma.getCodigoPrograma())
                .codigoSubModulo(controlEjecucionPrograma.getCodigoSubModulo()).estado(estado).build();
        this.actualizar(logControlProgramaResumen, UPDATE_ESTADO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void existeProgramaAnteriorNoEjecutadoPorGrupo(String codigoGrupo, String codigoPrograma)
    {
        LogControlProgramaResumen logControlProgramaResumen = LogControlProgramaResumen.builder().codigoGrupo(codigoGrupo).codigoPrograma(codigoPrograma).fechaProceso(parametroGeneralService.buscarFechaProceso()).build();
        List<LogControlProgramaResumen> logsControlProgramaResumen = this.buscar(logControlProgramaResumen, GET_NOT_EXECUTED_GRUPO_PROG);
        int cantidadProgramasAnterioresNoEjecutados = 0;
        if (!logsControlProgramaResumen.isEmpty())
        {
            cantidadProgramasAnterioresNoEjecutados = logsControlProgramaResumen.get(0).getCantidadProgramasAnterioresNoEjecutados();
        }
        if (cantidadProgramasAnterioresNoEjecutados > 0)
        {
            throw new OrdenEjecucionException(String.format(ConstantesExcepciones.ORDEN_EJECUCION, cantidadProgramasAnterioresNoEjecutados));
        }
    }

}