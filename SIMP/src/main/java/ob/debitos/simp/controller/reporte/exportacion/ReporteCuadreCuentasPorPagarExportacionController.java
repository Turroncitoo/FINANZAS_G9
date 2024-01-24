package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

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
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteCuadreCuentaPorPagar;
import ob.debitos.simp.service.IReporteCuadreCuentasPorPagarService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/cuadreCuentasPorPagar")
public @Controller class ReporteCuadreCuentasPorPagarExportacionController
{

    private @Autowired IReporteCuadreCuentasPorPagarService reporteCuadreCuentasPorPagarService;

    @Audit(tipo = Tipo.RPT_CTAS_PAGAR)
    @PreAuthorize("hasPermission('RPT_CUADREXPAGAR', '5')")
    @GetMapping(value = "/resumen", params = "accion=exportar")
    public void exportarResumen(
            @Validated CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            Errors errors, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        this.reporteCuadreCuentasPorPagarService
                .exportarResumenCompensacion(criterioBusqueda, response);
    }

    @Audit(tipo = Tipo.RPT_CTAS_PAGAR)
    @PreAuthorize("hasPermission('RPT_CUADREXPAGAR', '5')")
    @GetMapping(value = "/detalle", params = "accion=exportar")
    public void exportarDetalle(
            @Validated CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            Errors errors, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        this.reporteCuadreCuentasPorPagarService
                .exportarDetalleCompensacion(criterioBusqueda, response);
    }

    @Audit(tipo = Tipo.RPT_CTAS_PAGAR)
    @PreAuthorize("hasPermission('RPT_CUADREXPAGAR', '5')")
    @GetMapping(value = "/autDia", params = "accion=exportar")
    public void exportarAutorizacionesDelDia(
            @Validated CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            Errors errors, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        this.reporteCuadreCuentasPorPagarService
                .exportarResumenAutorizaciones(criterioBusqueda, response);
    }

    @Audit(tipo = Tipo.RPT_CTAS_PAGAR)
    @PreAuthorize("hasPermission('RPT_CUADREXPAGAR', '5')")
    @GetMapping(value = "/autDetalle", params = "accion=exportar")
    public void exportarAutorizacionesDetalle(
            @Validated CriterioBusquedaReporteCuadreCuentaPorPagar criterioBusqueda,
            Errors errors, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        this.reporteCuadreCuentasPorPagarService
                .exportarDetalleAutorizaciones(criterioBusqueda, response);
    }
}
