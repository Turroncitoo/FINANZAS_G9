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
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteFacturacion;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.service.IReporteFacturacionUBAService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/facturacionUBA")
public @RestController class ReporteFacturacionUBAController
{
    private @Autowired IReporteFacturacionUBAService reporteFacturacionUBAService;

    @Audit(tipo = Tipo.RPT_FACTURACION_UBA)
    @PreAuthorize("hasPermission('RPT_FACTURACIONUBA', '2')")
    @GetMapping(value = "/resumen", params = "accion=buscar")
    public List<ReporteMoneda> buscarResumen(@Validated CriterioBusquedaReporteFacturacion criterio)
    {
        return this.reporteFacturacionUBAService.buscarResumen(criterio);
    }

    @Audit(tipo = Tipo.RPT_FACTURACION_UBA)
    @PreAuthorize("hasPermission('RPT_FACTURACIONUBA', '2')")
    @GetMapping(value = "/detalle", params = "accion=buscar")
    public List<ReporteMoneda> buscarDetalle(@Validated CriterioBusquedaReporteFacturacion criterio)
    {
        return this.reporteFacturacionUBAService.buscarDetalle(criterio);
    }

    @Audit(tipo = Tipo.RPT_FACTURACION_UBA)
    @PreAuthorize("hasPermission('RPT_FACTURACIONUBA', '2')")
    @GetMapping(value = "/miscelaneos", params = "accion=buscar")
    public List<ReporteMoneda> buscarAutorizacionesDelDia(@Validated CriterioBusquedaReporteFacturacion criterio)
    {
        return this.reporteFacturacionUBAService.buscarMiscelaneos(criterio);
    }
    
}
