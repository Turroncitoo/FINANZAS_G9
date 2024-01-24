package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.model.reporte.ReporteContabilizacion;
import ob.debitos.simp.service.IConceptoComisionService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaContabilizacion;
import ob.debitos.simp.service.IReporteContabilizacionService;

@Vista
@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/contabilizacion")
public @Controller class ReporteContabilizacionExportacionController
{
    private @Autowired IReporteContabilizacionService reporteContabilizacionService;
    private @Autowired IConceptoComisionService conceptoComisionService;

    @Audit(tipo = Tipo.RPT_CONT_DEFAULT)
    @PreAuthorize("hasPermission('RPT_CONTDEFAULT', '5')")
    @GetMapping(value = "/cuentaDefault", params = "accion=exportar")
    public void descargarReporteResumenAutorizacion(
            @Validated CriterioBusquedaContabilizacion criterioBusqueda,
            Errors errors, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteContabilizacion> reporte = this.reporteContabilizacionService
                .buscarContabilizacionesEnCuentaDefault(criterioBusqueda);
        this.reporteContabilizacionService.descargarReporteCuentaDefault(
                reporte, criterioBusqueda, request, response);
    }

    @Audit(tipo = Tipo.RPT_CONT_MISC)
    @PreAuthorize("hasPermission('RPT_CONTMIS', '5')")
    @GetMapping(value = "/miscelaneos", params = "accion=exportar")
    public void descargarReporteResumenMiscelaneos(
            @Validated CriterioBusquedaContabilizacion criterioBusqueda,
            Errors errors, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteContabilizacion> reporte = this.reporteContabilizacionService
                .buscarContabilizacionesMiscelaneoPorCriterio(criterioBusqueda);
        this.reporteContabilizacionService
                .descargarReporteContabilizacionesMiscelaneos(reporte,
                        criterioBusqueda, request, response);
    }

    @Audit(tipo = Tipo.RPT_CONT_MOV)
    @PreAuthorize("hasPermission('RPT_CONTMOV', '5')")
    @GetMapping(value = "/movimientos", params = "accion=exportar")
    public void descargarReporteResumenMovimientos(
            @Validated CriterioBusquedaContabilizacion criterio, Errors errors,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteContabilizacion> reporte = this.reporteContabilizacionService
                .buscarContabilizacionesEmisorMovimientoPorCriterio(criterio);
        this.reporteContabilizacionService
                .buscarContabilizacionesMovimientosExportacion(reporte,
                        criterio, request, response);
    }

    @Audit(tipo = Tipo.RPT_CONT_COMIS)
    @PreAuthorize("hasPermission('RPT_CONTCOMIS', '5')")
    @GetMapping(value = "/comisiones", params = "accion=exportar")
    public void descargarReporteResumenComisiones(
            @Validated CriterioBusquedaContabilizacion criterio, Errors errors,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteContabilizacion> reporte = this.reporteContabilizacionService
                .buscarContabilizacionesEmisorComisionesPorCriterio(criterio);
        this.reporteContabilizacionService
                .buscarContabilizacionesEmisorComisionesExportacion(reporte,
                        criterio, request, response,
                        this.conceptoComisionService.buscarTodosRolEmisor());
    }
}
