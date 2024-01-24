package ob.debitos.simp.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteCuadreCuentaPorPagar;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.service.IReporteCuadreCuentasPorPagarService;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/cuadreCuentasPorPagar")
public @RestController class ReporteCuadreCuentasPorPagarController
{
    private @Autowired IReporteCuadreCuentasPorPagarService reporteCuadreCuentasPorPagarService;

    @Audit(tipo = Tipo.RPT_CTAS_PAGAR)
    @PreAuthorize("hasPermission('RPT_CUADREXPAGAR', '2')")
    @GetMapping(value = "/resumen", params = "accion=buscar")
    public List<ReporteMoneda> buscarResumen(
            @Validated CriterioBusquedaReporteCuadreCuentaPorPagar criterio,
            Errors error)
    {
        return this.reporteCuadreCuentasPorPagarService.buscarResumen(criterio);
    }

    @Audit(tipo = Tipo.RPT_CTAS_PAGAR)
    @PreAuthorize("hasPermission('RPT_CUADREXPAGAR', '2')")
    @GetMapping(value = "/detalle", params = "accion=buscar")
    public List<ReporteMoneda> buscarDetalle(
            @Validated CriterioBusquedaReporteCuadreCuentaPorPagar criterio,
            Errors error)
    {
        return this.reporteCuadreCuentasPorPagarService.buscarDetalle(criterio);
    }

    @Audit(tipo = Tipo.RPT_CTAS_PAGAR)
    @PreAuthorize("hasPermission('RPT_CUADREXPAGAR', '2')")
    @GetMapping(value = "/autDia", params = "accion=buscar")
    public List<ReporteMoneda> buscarAutorizacionesDelDia(
            @Validated CriterioBusquedaReporteCuadreCuentaPorPagar criterio,
            Errors error)
    {
        return this.reporteCuadreCuentasPorPagarService
                .buscarAutorizacionesDelDia(criterio);
    }

    @Audit(tipo = Tipo.RPT_CTAS_PAGAR)
    @PreAuthorize("hasPermission('RPT_CUADREXPAGAR', '2')")
    @GetMapping(value = "/autDetalle", params = "accion=buscar")
    public List<ReporteMoneda> buscarDetalleAutorizaciones(
            @Validated CriterioBusquedaReporteCuadreCuentaPorPagar criterio,
            Errors error)
    {
        return this.reporteCuadreCuentasPorPagarService
                .buscarDetalleAutorizaciones(criterio);
    }

}
