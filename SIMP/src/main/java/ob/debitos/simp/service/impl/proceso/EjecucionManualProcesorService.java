package ob.debitos.simp.service.impl.proceso;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.LogControlPrograma;
import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPArchivo;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.EntradaProceso;
import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.model.tarjetas.LoteParametro;
import ob.debitos.simp.service.IArchivoLoteService;
import ob.debitos.simp.service.IArchivoUbaService;
import ob.debitos.simp.service.IEjecucionManualProcesorService;
import ob.debitos.simp.service.IErrorService;
import ob.debitos.simp.service.IGestionLotesService;

import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IParametroSFTPArchivoService;
import ob.debitos.simp.service.IProcesoService;
import ob.debitos.simp.service.IReporteContablePrepagoService;
import ob.debitos.simp.service.excepcion.EjecucionManualException;
import ob.debitos.simp.utilitario.ArchivoUtil;

@Service
public class EjecucionManualProcesorService implements IEjecucionManualProcesorService
{

    private @Autowired Logger logger;
    private @Autowired IArchivoUbaService archivoUbaService;
    private @Autowired IProcesoService procesoService;
    private @Autowired IErrorService errorService;
    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired IParametroSFTPArchivoService parametroSFTPArchivoService;
    private @Autowired IArchivoLoteService archivoLoteService;
    private @Autowired IGestionLotesService gestionLotesService;
    private @Autowired IReporteContablePrepagoService reporteContablePrepagoService;

    @LogControlPrograma
    public int ejecutarProcedimientoDescargaPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa)
    {
        return archivoUbaService.obtenerArchivoUba(programa, controlEjecucionPrograma.getIdInstitucion());
    }

    @LogControlPrograma
    public int ejecutarProcedimientoEnvioPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa)
    {
        return archivoUbaService.enviarArchivoUba(programa, controlEjecucionPrograma.getIdInstitucion());
    }

    @LogControlPrograma(detallado = true)
    public List<LogControlProgramaDetalle> ejecutarProcedimientoCargaArchivoUBAPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa)
    {
        List<LogControlProgramaDetalle> logControlProgramaDetalles = this.archivoUbaService.cargarArchivoUBA(programa, controlEjecucionPrograma.getIdInstitucion());
        this.ejecutarProcedimientoBatch(controlEjecucionPrograma);
        return logControlProgramaDetalles;
    }

    @LogControlPrograma
    public int ejecutarProcedimientoBatchPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        return this.ejecutarProcedimientoBatch(controlEjecucionPrograma);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int ejecutarProcedimientoBatch(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        Integer idInstitucion = controlEjecucionPrograma.getIdInstitucion() == null ? this.parametroGeneralService.buscarCodigoInstitucion() : controlEjecucionPrograma.getIdInstitucion();
        EntradaProceso entradaProceso = EntradaProceso.builder().idInstitucion(idInstitucion).idGrupo(controlEjecucionPrograma.getCodigoGrupo()).idPrograma(controlEjecucionPrograma.getCodigoPrograma()).build();
        logger.info("{}", entradaProceso);
        procesoService.ejecutarProceso(entradaProceso);
        logger.info("Proceso completo");
        if (entradaProceso.getResultado() != 0)
        {
            logger.info("Resultado {}", entradaProceso.getResultado());
            throw new EjecucionManualException(errorService.obtenerDescripcionError(entradaProceso.getResultado()));
        }
        logger.info("Sin errores {}", entradaProceso);
        return entradaProceso.getResultado();
    }

    @Override
    @LogControlPrograma
    public int ejecutarDescargaYcargaArchivoPrepagoPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa)
    {
        Integer r = -1;
        logger.info("Descarga archivo prepago {}", controlEjecucionPrograma.getCodigoPrograma());
        int resultado = this.archivoUbaService.obtenerArchivoUba(programa, controlEjecucionPrograma.getIdInstitucion());
        if (resultado == 0)
        {
            switch (controlEjecucionPrograma.getCodigoPrograma())
            {
            case "DWAF":
            //case "DACT":
            case "DRCS":
            case "DRCD":
            case "DDES":
            case "DDED":
            //case "EMBO":
                this.archivoUbaService.cargarArchivoUBA(programa, controlEjecucionPrograma.getIdInstitucion());
                r = this.ejecutarProcedimientoBatch(controlEjecucionPrograma);
                break;
            default:
                r = -1;
            }
        }
        if (resultado == -5)
        {
            r = 0;
        }
        logger.info("Respuesta r: {} resultado descarga {}", r, resultado);
        return r;
    }

    @Override
    @LogControlPrograma
    public int ejecutarGeneracionArchivoPrepagoPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa)
    {
        Integer r = -1;
        ParametrosSFTPArchivo parametroSFTPArchivo = this.parametroSFTPArchivoService.buscarPorIdArchivo(programa.getArchivo()).get(0);
        String path = "";
        logger.info("Generacion archivo prepago {}", controlEjecucionPrograma.getCodigoPrograma());
        File directorioDestino;
        path = this.archivoUbaService.obtenerRutaOrigenArchivo(parametroSFTPArchivo, controlEjecucionPrograma.getFechaProceso(), controlEjecucionPrograma.getIdInstitucion());
        logger.info("Mensaje 2: {}", path);
        directorioDestino = Paths.get(path).toFile().getParentFile();
        ArchivoUtil.crearDirectorio(directorioDestino);
        logger.info("Mensaje 3: {}", directorioDestino);
        switch (controlEjecucionPrograma.getCodigoPrograma())
        {
        case "AFIL":
            r = archivoLoteService.generarArchivoPrepago(path, controlEjecucionPrograma.getIdInstitucion(), parametroSFTPArchivo.getCodigoArchivo());
            break;
        case "ACTI":
            // r = archivoLoteService.generarArchivoActivaciones(path,
            // controlEjecucionPrograma.getIdInstitucion());
            r = 0;
            break;
        case "RECS":
            r = archivoLoteService.generarArchivoPrepago(path, controlEjecucionPrograma.getIdInstitucion(), parametroSFTPArchivo.getCodigoArchivo());
            break;
        case "RECD":
            r = archivoLoteService.generarArchivoPrepago(path, controlEjecucionPrograma.getIdInstitucion(), parametroSFTPArchivo.getCodigoArchivo());
            break;
        case "DEBS":
            r = archivoLoteService.generarArchivoPrepago(path, controlEjecucionPrograma.getIdInstitucion(), parametroSFTPArchivo.getCodigoArchivo());
            break;
        case "DEBD":
            r = archivoLoteService.generarArchivoPrepago(path, controlEjecucionPrograma.getIdInstitucion(), parametroSFTPArchivo.getCodigoArchivo());
            break;
        default:
            r = -1;
        }
        logger.info("Respuesta del proceso de generacion : {}", r);
        if (r == 0)
        {
            Integer subidoSFTP = this.archivoUbaService.enviarArchivoUba(programa, controlEjecucionPrograma.getIdInstitucion());
            logger.info("Respuesta del SFTP ? : {}", subidoSFTP);
            logger.info("Institucion : {}", controlEjecucionPrograma.getIdInstitucion());
            if (subidoSFTP == 0)
            {
                // Se actualiza a enviado a unibanca, si no hay problemas al
                // subir al SFTP
                switch (controlEjecucionPrograma.getCodigoPrograma())
                {
                case "AFIL":
                    this.gestionLotesService.actualizarEstadoLoteAfiliacionesAProcesada(LoteParametro.builder().idInstitucion(controlEjecucionPrograma.getIdInstitucion()).build());
                    break;
                case "ACTI":
                    // archivoLoteService.generarArchivoActivaciones(path,
                    // controlEjecucionPrograma.getIdInstitucion());
                    break;
                case "RECS":
                    this.gestionLotesService.actualizarEstadoLoteRecargaSolesAProcesada(LoteParametro.builder().idInstitucion(controlEjecucionPrograma.getIdInstitucion()).build());
                    break;
                case "RECD":
                    this.gestionLotesService.actualizarEstadoLoteRecargaDolaresAProcesada(LoteParametro.builder().idInstitucion(controlEjecucionPrograma.getIdInstitucion()).build());
                    break;
                case "DEBS":
                    this.gestionLotesService.actualizarEstadoLoteDebitoSolesAProcesada(LoteParametro.builder().idInstitucion(controlEjecucionPrograma.getIdInstitucion()).build());
                    break;
                case "DEBD":
                    this.gestionLotesService.actualizarEstadoLoteDebitoDolaresAProcesada(LoteParametro.builder().idInstitucion(controlEjecucionPrograma.getIdInstitucion()).build());
                    break;
                default:
                    r = -1;
                }
            }
        }
        r = r == -2 ? 0 : r;
        logger.info("Respuesta final r: {}", r);
        return r;
    }

    @Override
    @LogControlPrograma
    public int ejecutarGeneracionArchivoContablePorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, CriterioBusquedaArchivoContablePrepago criterioBusqueda)
    {
        try
        {
            reporteContablePrepagoService.generarArchivoContable(criterioBusqueda);
        } catch (IOException e)
        {
            logger.error(e.getMessage(), e);
            throw new EjecucionManualException(e.getMessage());
        }
        return 0;
    }

}
