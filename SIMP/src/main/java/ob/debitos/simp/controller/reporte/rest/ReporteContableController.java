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
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleContable;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenContable;
import ob.debitos.simp.model.reporte.ReporteDetalleContable;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.service.IReporteContableService;

/**
 * Recibe las peticiones del usuario relacionado a las consulta del balance
 * contable actual.
 * 
 * @author Carla Ulloa
 */
@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/contable/")
public @RestController class ReporteContableController
{
    private @Autowired IReporteContableService reporteContableService;

    /**
     * Reciba las peticiones de consulta de balance contable en resumen filtrado
     * por un rango de fechas.
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda: fecha de inicio y fecha de
     *            fin
     * @return lista de {@link ReporteMoneda}
     */
    @Audit(tipo = Tipo.RPT_CONTABLE)
    @PreAuthorize("hasPermission('RPT_CONT', '2')")
    @GetMapping(value = "resumen", params = "accion=buscar")
    public List<ReporteMoneda> buscarReporteResumenContable(
            CriterioBusquedaResumenContable criterioBusqueda)
    {
        return this.reporteContableService.buscarResumenContable(criterioBusqueda);
    }

    /**
     * Reciba las peticiones de consulta de balance contable en detalle filtrado
     * cualquier criterio de búsqueda.
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda de la consulta
     * @return lista de {@link ReporteDetalleContable}
     */
    @Audit(tipo = Tipo.RPT_CONTABLE)
    @PreAuthorize("hasPermission('RPT_CONT', '2')")
    @GetMapping(value = "detalle", params = "accion=buscar")
    public List<ReporteDetalleContable> buscarReporteDetalleContable(
            CriterioBusquedaDetalleContable criterioBusqueda)
    {
        return this.reporteContableService.buscarDetalleContableVariosCriterios(criterioBusqueda);
    }
    
    /**
     * Recibe las peticiones de consulta de balance contable por periodo
     * @param criterioBusqueda
     * 			almacena los criterios de busqueda de la consulta
     * @return	lista de {@link ReporteMoneda} 
     */
    @Audit(tipo = Tipo.RPT_CONTABLE)
    @PreAuthorize("hasPermission('RPT_CONT', '2')")
    @GetMapping(value = "resumenPorPeriodo", params = "accion=buscar")
    public List<ReporteMoneda> buscarReporteResumenPorPeriodoContable(
            CriterioBusquedaResumenContable criterioBusqueda)
    {
        return this.reporteContableService.buscarResumenPorPeriodoContable(criterioBusqueda);
    }
}