package ob.debitos.simp.service.impl.proceso;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.LogControlPrograma;
import ob.debitos.simp.aspecto.anotacion.LogControlProgramaResumen;
import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.model.proceso.ResultadoProceso;
import ob.debitos.simp.service.IArchivoLoteService;
import ob.debitos.simp.service.IArchivoUbaService;
import ob.debitos.simp.service.IEjecucionManualProcesorService;
import ob.debitos.simp.service.IEjecucionManualService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.ILogControlProgramaResumenService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IProgramaService;
import ob.debitos.simp.service.excepcion.ValorEncontradoException;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.Util;

@Service
public class EjecucionManualService implements IEjecucionManualService
{

    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired ILogControlProgramaResumenService logControlProgramaResumenService;
    private @Autowired IProgramaService programaService;
    private @Autowired IArchivoUbaService archivoUbaService;
    private @Autowired IArchivoLoteService archivoLoteService;
    private @Autowired IInstitucionService institucionService;
    private @Autowired IEjecucionManualProcesorService ejecucionManualProcesorService;
    private @Autowired Logger logger;

    @LogControlPrograma(registrarLogResumen = true)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarParaControlProceso(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        Date fechaProceso = parametroGeneralService.buscarFechaProceso();
        if (!logControlProgramaResumenService.existeFechaProcesoCargada(fechaProceso))
        {
            this.ejecucionManualProcesorService.ejecutarProcedimientoBatch(controlEjecucionPrograma);
        } else
        {
            throw new ValorEncontradoException(ConstantesExcepciones.FECHA_PROCESO_ENCONTRADA_EJECUCION);
        }
    }

    @LogControlPrograma(registrarLogResumen = true)
    public List<ResultadoProceso> ejecutarProcedimientoBackup(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        Date fechaProcesoSIMP = parametroGeneralService.buscarFechaProceso();
        LocalDate fechaProcesoSIMPLocal = Util.aLocalDate(fechaProcesoSIMP);
        Programa programa = this.buscarPrograma(controlEjecucionPrograma);
        List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
        int resultado = 1;
        if (validarProcesaSabado(programa, fechaProcesoSIMPLocal))
        {
            resultado = archivoUbaService.realizarBackup(programa);
        }
        resultadosProcesos.add(new ResultadoProceso(resultado, null));
        return resultadosProcesos;
    }

    @LogControlProgramaResumen
    public List<ResultadoProceso> ejecutarProcedimientoDescarga(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        Date fechaProcesoSIMP = parametroGeneralService.buscarFechaProceso();
        LocalDate fechaProcesoSIMPLocal = Util.aLocalDate(fechaProcesoSIMP);
        Programa programa = this.buscarPrograma(controlEjecucionPrograma);
        List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
        if (validarProcesaSabado(programa, fechaProcesoSIMPLocal))
        {
            if (programa.isEjecucionPorInstitucion())
            {
                List<Institucion> instituciones = this.institucionService.buscarPorInstitucionEmpresa();
                instituciones.stream().forEach(institucion -> {
                    controlEjecucionPrograma.setIdInstitucion(institucion.getCodigoInstitucion());
                    try
                    {
                        this.ejecucionManualProcesorService.ejecutarProcedimientoDescargaPorInstitucion(controlEjecucionPrograma, programa);
                        resultadosProcesos.add(new ResultadoProceso(1, null));
                    } catch (Exception e)
                    {
                        logger.error(e.getMessage(), e);
                        resultadosProcesos.add(new ResultadoProceso(0, e));
                    }
                });
            } else
            {
                controlEjecucionPrograma.setIdInstitucion(this.parametroGeneralService.buscarCodigoInstitucion());
                this.ejecucionManualProcesorService.ejecutarProcedimientoDescargaPorInstitucion(controlEjecucionPrograma, programa);
                resultadosProcesos.add(new ResultadoProceso(1, null));
            }
        } else
        {
            resultadosProcesos.add(new ResultadoProceso(1, null));
        }
        logger.info("Resultados {}", resultadosProcesos);
        return resultadosProcesos;
    }

    @LogControlProgramaResumen
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ResultadoProceso> ejecutarProcedimientoCargaArchivoUBA(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        Date fechaProcesoSIMP = parametroGeneralService.buscarFechaProceso();
        Programa programa = this.buscarPrograma(controlEjecucionPrograma);
        LocalDate fechaProcesoSIMPLocal = Util.aLocalDate(fechaProcesoSIMP);
        List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
        if (validarProcesaSabado(programa, fechaProcesoSIMPLocal))
        {
            if (programa.isEjecucionPorInstitucion())
            {
                List<Institucion> instituciones = this.institucionService.buscarPorInstitucionEmpresa();
                Validate.notEmpty(instituciones);
                instituciones.forEach(institucion -> {
                    try
                    {
                        controlEjecucionPrograma.setIdInstitucion(institucion.getCodigoInstitucion());
                        this.ejecucionManualProcesorService.ejecutarProcedimientoCargaArchivoUBAPorInstitucion(controlEjecucionPrograma, programa);
                        resultadosProcesos.add(new ResultadoProceso(1, null));
                    } catch (Exception ex)
                    {
                        resultadosProcesos.add(new ResultadoProceso(0, ex));
                    }
                });
            } else
            {
                controlEjecucionPrograma.setIdInstitucion(this.parametroGeneralService.buscarCodigoInstitucion());
                this.ejecucionManualProcesorService.ejecutarProcedimientoCargaArchivoUBAPorInstitucion(controlEjecucionPrograma, programa);
                resultadosProcesos.add(new ResultadoProceso(1, null));
            }
        } else
        {
            resultadosProcesos.add(new ResultadoProceso(1, null));
        }
        logger.info("Resultados {}", resultadosProcesos);
        return resultadosProcesos;
    }

    @LogControlProgramaResumen
    public List<ResultadoProceso> ejecutarProcedimientoEnvio(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        Date fechaProcesoSIMP = parametroGeneralService.buscarFechaProceso();
        LocalDate fechaProcesoSIMPLocal = Util.aLocalDate(fechaProcesoSIMP);
        Programa programa = this.buscarPrograma(controlEjecucionPrograma);
        List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
        if (validarProcesaSabado(programa, fechaProcesoSIMPLocal))
        {
            if (programa.isEjecucionPorInstitucion())
            {
                List<Institucion> instituciones = this.institucionService.buscarPorInstitucionEmpresa();
                instituciones.stream().forEach(institucion -> {
                    controlEjecucionPrograma.setIdInstitucion(institucion.getCodigoInstitucion());
                    try
                    {
                        this.ejecucionManualProcesorService.ejecutarProcedimientoEnvioPorInstitucion(controlEjecucionPrograma, programa);
                        resultadosProcesos.add(new ResultadoProceso(1, null));
                    } catch (Exception e)
                    {
                        logger.error(e.getMessage(), e);
                        resultadosProcesos.add(new ResultadoProceso(0, e));
                    }
                });
            } else
            {
                controlEjecucionPrograma.setIdInstitucion(this.parametroGeneralService.buscarCodigoInstitucion());
                this.ejecucionManualProcesorService.ejecutarProcedimientoEnvioPorInstitucion(controlEjecucionPrograma, programa);
                resultadosProcesos.add(new ResultadoProceso(1, null));
            }
        } else
        {
            resultadosProcesos.add(new ResultadoProceso(1, null));
        }
        logger.info("Resultados {}", resultadosProcesos);
        return resultadosProcesos;
    }

    @LogControlProgramaResumen
    public List<ResultadoProceso> ejecutarProcedimientoGeneracionArchivosContables(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
        if (controlEjecucionPrograma.getCodigoPrograma().equals("AFON"))
        {
            this.institucionService.buscarPorInstitucionEmpresa().forEach(institucion -> {
                try
                {
                    controlEjecucionPrograma.setIdInstitucion(institucion.getCodigoInstitucion());
                    this.ejecucionManualProcesorService.ejecutarGeneracionArchivoContablePorInstitucion(controlEjecucionPrograma,
                            CriterioBusquedaArchivoContablePrepago.builder().fechaProceso(parametroGeneralService.buscarFechaProceso()).idInstitucion(institucion.getCodigoInstitucion()).tipo("FONDOS").build());
                    resultadosProcesos.add(new ResultadoProceso(1, null));
                } catch (Exception e)
                {
                    logger.error(e.getMessage(), e);
                    resultadosProcesos.add(new ResultadoProceso(0, e));
                }
            });
        } else if (controlEjecucionPrograma.getCodigoPrograma().equals("ACOM"))
        {
            this.institucionService.buscarPorInstitucionEmpresa().forEach(institucion -> {
                try
                {
                    controlEjecucionPrograma.setIdInstitucion(institucion.getCodigoInstitucion());
                    this.ejecucionManualProcesorService.ejecutarGeneracionArchivoContablePorInstitucion(controlEjecucionPrograma,
                            CriterioBusquedaArchivoContablePrepago.builder().fechaProceso(parametroGeneralService.buscarFechaProceso()).idInstitucion(institucion.getCodigoInstitucion()).tipo("COMISIONES").build());
                    resultadosProcesos.add(new ResultadoProceso(1, null));
                } catch (Exception e)
                {
                    logger.error(e.getMessage(), e);
                    resultadosProcesos.add(new ResultadoProceso(0, e));
                }
            });
        }
        return resultadosProcesos;
    }

    @Override
    @LogControlProgramaResumen
    public List<ResultadoProceso> ejecutarProcedimientoDescargaYcargaPrepago(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        Programa programa = this.buscarPrograma(controlEjecucionPrograma);
        List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
        if (programa.isEjecucionPorInstitucion())
        {
            List<Institucion> instituciones = this.institucionService.buscarPorInstitucionEmpresa();
            this.archivoLoteService.eliminarTmpAfiliacionesDeUba();
            instituciones.stream().forEach(institucion -> {
                controlEjecucionPrograma.setIdInstitucion(institucion.getCodigoInstitucion());
                try
                {
                    int resultado = this.ejecucionManualProcesorService.ejecutarDescargaYcargaArchivoPrepagoPorInstitucion(controlEjecucionPrograma, programa);
                    resultadosProcesos.add(new ResultadoProceso(resultado == 0 ? 1 : 0, null));
                } catch (Exception e)
                {
                    logger.error(e.getMessage(), e);
                    resultadosProcesos.add(new ResultadoProceso(0, e));
                }
            });
        } else
        {
            controlEjecucionPrograma.setIdInstitucion(this.parametroGeneralService.buscarCodigoInstitucion());
            int resultado = this.ejecucionManualProcesorService.ejecutarDescargaYcargaArchivoPrepagoPorInstitucion(controlEjecucionPrograma, programa);
            resultadosProcesos.add(new ResultadoProceso(resultado == 0 ? 1 : 0, null));
        }
        logger.info("Resultados {}", resultadosProcesos);
        return resultadosProcesos;
    }

    @Override
    @LogControlProgramaResumen
    public List<ResultadoProceso> ejecutarProcedimientoGeneracionArchivoPrepago(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        Programa programa = this.buscarPrograma(controlEjecucionPrograma);
        List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
        if (programa.isEjecucionPorInstitucion())
        {
            List<Institucion> instituciones = this.institucionService.buscarPorInstitucionEmpresa();
            instituciones.stream().forEach(institucion -> {
                controlEjecucionPrograma.setIdInstitucion(institucion.getCodigoInstitucion());
                try
                {
                    int resultado = this.ejecucionManualProcesorService.ejecutarGeneracionArchivoPrepagoPorInstitucion(controlEjecucionPrograma, programa);
                    resultadosProcesos.add(new ResultadoProceso(resultado == 0 ? 1 : 0, null));
                } catch (Exception e)
                {
                    logger.error(e.getMessage(), e);
                    resultadosProcesos.add(new ResultadoProceso(0, e));
                }
            });
        } else
        {
            controlEjecucionPrograma.setIdInstitucion(this.parametroGeneralService.buscarCodigoInstitucion());
            int resultado = this.ejecucionManualProcesorService.ejecutarGeneracionArchivoPrepagoPorInstitucion(controlEjecucionPrograma, programa);
            resultadosProcesos.add(new ResultadoProceso(resultado == 0 ? 1 : 0, null));
        }
        logger.info("Resultados {}", resultadosProcesos);
        return resultadosProcesos;
    }

    @LogControlProgramaResumen
    @Override
    public List<ResultadoProceso> ejecutarProcedimientoBatch(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        Programa programa = this.buscarPrograma(controlEjecucionPrograma);
        List<ResultadoProceso> resultadosProcesos = new ArrayList<>();
        if (programa.isEjecucionPorInstitucion())
        {
            List<Institucion> instituciones = this.institucionService.buscarPorInstitucionEmpresa();
            instituciones.stream().forEach(institucion -> {
                try
                {
                    controlEjecucionPrograma.setIdInstitucion(institucion.getCodigoInstitucion());
                    this.ejecucionManualProcesorService.ejecutarProcedimientoBatchPorInstitucion(controlEjecucionPrograma);
                    resultadosProcesos.add(new ResultadoProceso(1, null));
                } catch (Exception ex)
                {
                    resultadosProcesos.add(new ResultadoProceso(0, ex));
                }
            });
        } else
        {
            controlEjecucionPrograma.setIdInstitucion(this.parametroGeneralService.buscarCodigoInstitucion());
            this.ejecucionManualProcesorService.ejecutarProcedimientoBatchPorInstitucion(controlEjecucionPrograma);
            resultadosProcesos.add(new ResultadoProceso(1, null));
        }
        return resultadosProcesos;
    }

    private boolean validarProcesaSabado(Programa programa, LocalDate fechaProcesoSIMPLocal)
    {
        if (fechaProcesoSIMPLocal.getDayOfWeek().equals(DayOfWeek.SATURDAY))
        {
            logger.info("es sabado");
            if (programa.getProcesaSabado() == 0)
            {
                return false;
            }
        }
        return true;
    }

    private Programa buscarPrograma(ControlEjecucionPrograma controlEjecucionPrograma)
    {
        return programaService.buscarPorCodigo(controlEjecucionPrograma.getCodigoGrupo(), controlEjecucionPrograma.getCodigoPrograma(), controlEjecucionPrograma.getCodigoSubModulo()).get(0);
    }

}