package ob.debitos.simp.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaContabilizacion;
import ob.debitos.simp.model.reporte.ReporteContabilizacion;
import ob.debitos.simp.service.IReporteContabilizacionService;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/contabilizacion")
public @RestController class ReporteContabilizacionController
{
    private @Autowired IReporteContabilizacionService reporteContabilizacionService;

    @Audit(tipo = Tipo.RPT_CONT_MOV)
    @PreAuthorize("hasPermission('RPT_CONTMOV','2')")
    @GetMapping(value = "/movimiento", params = "accion=buscar")
    public List<ReporteContabilizacion> buscarContabilizacionesMovimiento(
            CriterioBusquedaContabilizacion criterioBusqueda)
    {
        return reporteContabilizacionService
                .buscarContabilizacionesEmisorMovimientoPorCriterio(
                        criterioBusqueda);
    }

    @Audit(tipo = Tipo.RPT_CONT_COMIS)
    @PreAuthorize("hasPermission('RPT_CONTCOMIS','2')")
    @GetMapping(value = "/comisiones", params = "accion=buscar")
    public List<ReporteContabilizacion> buscarContabilizacionesComisiones(
            CriterioBusquedaContabilizacion criterioBusqueda)
    {
        return reporteContabilizacionService
                .buscarContabilizacionesEmisorComisionesPorCriterio(
                        criterioBusqueda);
    }

    @Audit(tipo = Tipo.RPT_CONT_DEFAULT)
    @PreAuthorize("hasPermission('RPT_CONTDEFAULT', '2')")
    @GetMapping(value = "/cuentaDefault", params = "accion=buscar")
    public List<ReporteContabilizacion> buscarContabilizacionesEnCuentaDefault(
            CriterioBusquedaContabilizacion criterioBusqueda)
    {
        return this.reporteContabilizacionService
                .buscarContabilizacionesEnCuentaDefault(criterioBusqueda);
    }

    @Audit(tipo = Tipo.RPT_CONT_MISC)
    @PreAuthorize("hasPermission('RPT_CONTMIS', '2')")
    @GetMapping(value = "/miscelaneos", params = "accion=buscar")
    public List<ReporteContabilizacion> buscarContabilizacionesMiscelaneo(
            CriterioBusquedaContabilizacion criterioBusqueda)
    {
        return this.reporteContabilizacionService
                .buscarContabilizacionesMiscelaneoPorCriterio(criterioBusqueda);
    }
}