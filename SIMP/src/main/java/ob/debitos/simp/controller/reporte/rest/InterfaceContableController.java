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
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteInterfaceContable;
import ob.debitos.simp.model.reporte.ReporteInterfaceContable;
import ob.debitos.simp.service.IReporteInterfaceContableService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("reporte")
public @RestController class InterfaceContableController
{
    
    private @Autowired IReporteInterfaceContableService reporteInterfaceContableService;

    @Audit(tipo = Tipo.RPT_INTER_CONTABLE)
    @PreAuthorize("hasPermission('RPT_INTER_CONTABLE', '2')")
    @GetMapping(value = "/interfaceContable", params = "accion=buscar")
    public List<ReporteInterfaceContable> buscarInterfaceContablePorCriterio(CriterioBusquedaReporteInterfaceContable criterioBusquedaReporteInterfaceContable)
    {
        return this.reporteInterfaceContableService.buscarInterfaceContablePorCriterio(criterioBusquedaReporteInterfaceContable);
    }
    
}
