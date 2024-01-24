package ob.debitos.simp.service.impl.proceso;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ILogControlProgramaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlPrograma;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.LogControlPrograma;
import ob.debitos.simp.service.ILogControlProgramaService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.DatesUtils;

@Service
public class LogControlProgramaService extends MantenibleService<LogControlPrograma>
        implements ILogControlProgramaService
{
    private ILogControlProgramaMapper logControlProgramaMapper;
    private @Autowired IParametroGeneralService parametroGeneralService;

    public LogControlProgramaService(
            @Qualifier("ILogControlProgramaMapper") IMantenibleMapper<LogControlPrograma> mapper)
    {
        super(mapper);
        this.logControlProgramaMapper = (ILogControlProgramaMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public int registrarLogControlPrograma(ControlEjecucionPrograma controlEjecucionPrograma)
    {
    	int idInstitucion = controlEjecucionPrograma.getIdInstitucion() == null ? this.parametroGeneralService.buscarCodigoInstitucion()
    				: controlEjecucionPrograma.getIdInstitucion();
        LogControlPrograma logControlPrograma = LogControlPrograma.builder()
                .fechaProceso(controlEjecucionPrograma.getFechaProceso())
                .codigoInstitucion(idInstitucion)
                .estado(controlEjecucionPrograma.getEstado())
                .mensaje(controlEjecucionPrograma.getMensaje())
                .registro(controlEjecucionPrograma.getRegistros())
                .tipoEjecucion(ConstantesGenerales.EJECUCION_MANUAL)
                .codigoGrupo(controlEjecucionPrograma.getCodigoGrupo())
                .codigoPrograma(controlEjecucionPrograma.getCodigoPrograma())
                .codigoSubModulo(controlEjecucionPrograma.getCodigoSubModulo())
                .horaInicio(DatesUtils.obtenerFechaDeMilisegundosEnFormato(
                        controlEjecucionPrograma.getTiempoEjecucionInicial(),
                        DatesUtils.FORMATO_HHMMSS))
                .horaFin(DatesUtils.obtenerFechaDeMilisegundosEnFormato(
                        controlEjecucionPrograma.getTiempoEjecucionFinal(),
                        DatesUtils.FORMATO_HHMMSS))
                .tiempoProceso(DatesUtils.obtenerDuracionDesdeMilisegundos(
                        controlEjecucionPrograma.getTiempoEjecucionFinal()
                                - controlEjecucionPrograma.getTiempoEjecucionInicial()))
                .build();
        List<LogControlPrograma> logControlProgramas = this
                .registrarAutoIncrementable(logControlPrograma);
        Validate.notEmpty(logControlProgramas,
                "Error en la recuperación de la secuencia del Log de control de programa.");
        int idSecuencia = logControlProgramas.get(0).getIdSecuencia();
        Validate.isTrue(idSecuencia > 0,
                "La secuencia del Log de control de programa debe ser mayor que 0.");
        return idSecuencia;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LogControlPrograma> buscarPorCriterioBusqueda(
            CriterioBusquedaLogControlPrograma criterioBusqueda)
    {
        return logControlProgramaMapper.buscarPorCriterioBusqueda(criterioBusqueda);
    }
}