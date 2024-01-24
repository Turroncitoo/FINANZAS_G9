package ob.debitos.simp.controller.proceso.rest;

import static ob.debitos.simp.utilitario.Constantes.BCKP;
import static ob.debitos.simp.utilitario.Constantes.LOAD;
import static ob.debitos.simp.utilitario.Constantes.SEND;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.ResultadoEjecucionResponse;
import ob.debitos.simp.model.proceso.ResultadoProceso;
import ob.debitos.simp.service.IEjecucionManualService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(accion = Accion.Ejecucion, comentario = Comentario.Ejecucion, datos = Dato.LOG_CTRL_PROG_RES)
@RequestMapping("/proceso/ejecucion/manual")
public @RestController class EjecucionProcesoManualController
{

    private @Autowired IEjecucionManualService ejecucionManualService;

    @Audit(tipo = Tipo.CTRL_PROC)
    @PreAuthorize("hasPermission('EJEC_CTRLPROC', 'ANY')")
    @PostMapping(params = "paso=prepararControlProceso")
    public ResponseEntity<ResultadoEjecucionResponse> prepararControlProcesoResumen(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        ejecucionManualService.registrarParaControlProceso(controlEjecucionPrograma);
        return ResponseEntity.ok(ResultadoEjecucionResponse.builder().codigo(ConstantesGenerales.RESULTADO_EJECUCION_COMPLETA).mensaje(ConstantesGenerales.MENSAJE_EJECUCION_PROGRAMA).build());
    }

    @Audit(tipo = Tipo.PROCESO_SFTP)
    @PreAuthorize("hasPermission('EJEC_PAIU', 'ANY')")
    @PostMapping(params = "paso=descarga")
    public ResponseEntity<ResultadoEjecucionResponse> ejecutarProcesoSFTP(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        String subModulo = controlEjecucionPrograma.getCodigoSubModulo();
        List<ResultadoProceso> resultados = new ArrayList<>();
        switch (subModulo)
        {
        case BCKP:
            resultados = ejecucionManualService.ejecutarProcedimientoBackup(controlEjecucionPrograma);
            break;
        case SEND:
            resultados = this.ejecucionManualService.ejecutarProcedimientoEnvio(controlEjecucionPrograma);
            break;
        case LOAD:
            resultados = this.ejecucionManualService.ejecutarProcedimientoDescarga(controlEjecucionPrograma);
            break;
        }
        return ResponseEntity.ok(this.obtenerMensajeRespuesta(resultados));
    }

    @Audit(tipo = Tipo.COMPENSACION)
    @PreAuthorize("hasPermission('EJEC_COMPENSACION', 'ANY')")
    @PostMapping(params = "paso=compensacion")
    public ResponseEntity<ResultadoEjecucionResponse> ejecutarCompensacion(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        List<ResultadoProceso> resultados = this.ejecucionManualService.ejecutarProcedimientoCargaArchivoUBA(controlEjecucionPrograma);
        return ResponseEntity.ok(this.obtenerMensajeRespuesta(resultados));
    }

    @Audit(tipo = Tipo.COMIS)
    @PreAuthorize("hasPermission('EJEC_COMPENSACION', 'ANY')")
    @PostMapping(params = "paso=comisiones")
    public ResponseEntity<ResultadoEjecucionResponse> ejecutarCalculoComisiones(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        List<ResultadoProceso> resultados = this.ejecucionManualService.ejecutarProcedimientoBatch(controlEjecucionPrograma);
        return ResponseEntity.ok(this.obtenerMensajeRespuesta(resultados));
    }

    @Audit(tipo = Tipo.CONCILIACION)
    @PreAuthorize("hasPermission('EJEC_CONCILIACION', 'ANY')")
    @PostMapping(params = "paso=conciliacion")
    public ResponseEntity<ResultadoEjecucionResponse> ejecutarConciliacion(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        List<ResultadoProceso> resultados = this.ejecucionManualService.ejecutarProcedimientoBatch(controlEjecucionPrograma);
        return ResponseEntity.ok(this.obtenerMensajeRespuesta(resultados));
    }

    @Audit(tipo = Tipo.CONTABILIZACION)
    @PreAuthorize("hasPermission('EJEC_CONTABILIZACION', 'ANY')")
    @PostMapping(params = "paso=contabilizacion")
    public ResponseEntity<ResultadoEjecucionResponse> ejecutarContabilizacion(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        List<ResultadoProceso> resultados = this.ejecucionManualService.ejecutarProcedimientoBatch(controlEjecucionPrograma);
        return ResponseEntity.ok(this.obtenerMensajeRespuesta(resultados));
    }

    @Audit(tipo = Tipo.CONTABILIZACION_ARCHIVOS)
    @PreAuthorize("hasPermission('EJEC_CONTABILIZACION', 'ANY')")
    @PostMapping(params = "paso=contabilizacion-archivos")
    public ResponseEntity<ResultadoEjecucionResponse> ejecutarCargaConciliacion(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        List<ResultadoProceso> resultados = this.ejecucionManualService.ejecutarProcedimientoGeneracionArchivosContables(controlEjecucionPrograma);
        return ResponseEntity.ok(this.obtenerMensajeRespuesta(resultados));
    }

    @Audit(tipo = Tipo.REQUERIM_PREPAGO)
    @PreAuthorize("hasPermission('EJEC_PREP', 'ANY')")
    @PostMapping(params = "paso=requerimiento-prepago")
    public ResponseEntity<ResultadoEjecucionResponse> ejecutarRequerimientosPrepago(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        String subModulo = controlEjecucionPrograma.getCodigoSubModulo();
        List<ResultadoProceso> resultados = new ArrayList<>();
        switch (subModulo)
        {
        case SEND:
            resultados = this.ejecucionManualService.ejecutarProcedimientoGeneracionArchivoPrepago(controlEjecucionPrograma);
            break;
        case LOAD:
            resultados = this.ejecucionManualService.ejecutarProcedimientoDescargaYcargaPrepago(controlEjecucionPrograma);
            break;
        }
        return ResponseEntity.ok(this.obtenerMensajeRespuesta(resultados));
    }

    /*ESTE CONTROLLER SE ESTA REVISANDO*/
    @Audit(tipo = Tipo.REQUERIM_PREPAGO)
    @PreAuthorize("hasPermission('EJEC_PREP', 'ANY')")
    @PostMapping(params = "paso=requerimiento-prepago-envio")
    public ResponseEntity<ResultadoEjecucionResponse> ejecutarRequerimientosPrepagoEnvio(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        List<ResultadoProceso> resultados = this.ejecucionManualService.ejecutarProcedimientoGeneracionArchivoPrepago(controlEjecucionPrograma);
        return ResponseEntity.ok(this.obtenerMensajeRespuesta(resultados));
    }

    @Audit(tipo = Tipo.AVANCE_FECHA_PROCESO)
    @PreAuthorize("hasPermission('EJEC_AVANCEFECHAPROCESO', 'ANY')")
    @PostMapping(params = "paso=avanzeFechaProceso")
    public ResponseEntity<ResultadoEjecucionResponse> ejecutarAvanceFechaProceso(@Validated @RequestBody ControlEjecucionPrograma controlEjecucionPrograma, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.ejecucionManualService.ejecutarProcedimientoBatch(controlEjecucionPrograma);
        return ResponseEntity.ok(ResultadoEjecucionResponse.builder().codigo(ConstantesGenerales.RESULTADO_EJECUCION_COMPLETA).mensaje(ConstantesGenerales.MENSAJE_EJECUCION_PROGRAMA).build());
    }

    private ResultadoEjecucionResponse obtenerMensajeRespuesta(List<ResultadoProceso> resultados)
    {
        int total = resultados.size();
        int cantidadExitos = (int) resultados.stream().filter(r -> r.getExito().equals(1)).count();
        int codigo = ConstantesGenerales.RESULTADO_EJECUCION_COMPLETA;
        String mensaje = ConstantesGenerales.MENSAJE_EJECUCION_PROGRAMA;
        if (total > 1)
        {
            if (cantidadExitos < total)
            {
                codigo = ConstantesGenerales.RESULTADO_EJECUCION_PARCIAL;
            }
            mensaje = String.format(ConstantesGenerales.MENSAJE_EJECUCION_POR_INSTITUCION, cantidadExitos, total);
        }
        return ResultadoEjecucionResponse.builder().codigo(codigo).mensaje(mensaje).build();
    }

}