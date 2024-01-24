package ob.debitos.simp.controller.reporte.rest;

import static ob.debitos.simp.utilitario.ConstantesGenerales.ROL_TRANSACCION_RECEPTOR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacion;
import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.reporte.ReporteCompensacionCanal;
import ob.debitos.simp.model.reporte.ReporteCompensacionInstitucion;
import ob.debitos.simp.model.reporte.ReporteCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.reporte.ReporteCompensacionReceptor;
import ob.debitos.simp.service.IReporteCompensacionService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/compensacion")
public @RestController class ReporteCompensacionController
{

    private @Autowired IReporteCompensacionService reporteCompensacionService;

    @Audit(tipo = Tipo.RPT_COMP_EMI_CANAL)
    @PreAuthorize("hasPermission('RPT_COMPEMICANAL','2')")
    @GetMapping(value = "/emisor/{tipoCompensacion:canal}", params = "accion=buscar")
    public @ResponseBody List<ReporteCompensacionCanal> buscarCompensacionEmisorCanal(CriterioBusquedaCompensacion criterioBusquedaCompensacion, @PathVariable String tipoCompensacion)
    {
        criterioBusquedaCompensacion.setTipoCompensacion(tipoCompensacion);
        return reporteCompensacionService.buscarCompensacionCanal(criterioBusquedaCompensacion);
    }

    @Audit(tipo = Tipo.RPT_COMP_EMI_INST)
    @PreAuthorize("hasPermission('RPT_COMPEMIINST','2')")
    @GetMapping(value = "/emisor/{tipoCompensacion:institucion}", params = "accion=buscar")
    public @ResponseBody List<ReporteCompensacionInstitucion> buscarCompensacionEmisorInstitucion(CriterioBusquedaCompensacion criterioBusquedaCompensacion, @PathVariable String tipoCompensacion)
    {
        criterioBusquedaCompensacion.setTipoCompensacion(tipoCompensacion);
        return reporteCompensacionService.buscarCompensacionInstitucion(criterioBusquedaCompensacion);
    }

    @Audit(tipo = Tipo.RPT_COMP_REC)
    @PreAuthorize("hasPermission('RPT_COMPREC','2')")
    @GetMapping(value = "/{tipoCompensacion:receptor}", params = "accion=buscar")
    public @ResponseBody List<ReporteCompensacionReceptor> buscarCompensacions(CriterioBusquedaCompensacion criterioBusquedaCompensacion, @PathVariable String tipoCompensacion)
    {
        criterioBusquedaCompensacion.setTipoCompensacion(tipoCompensacion);
        criterioBusquedaCompensacion.setRolTransaccion(ROL_TRANSACCION_RECEPTOR);
        return reporteCompensacionService.buscarCompensaciones(criterioBusquedaCompensacion);
    }

    @Audit(tipo = Tipo.RPT_COMP_EMI_GIRO_NEG)
    @PreAuthorize("hasPermission('RPT_COMPEMIGIRNEG', '2')")
    @GetMapping(value = "/emisor/giroDeNegocio", params = "accion=buscar")
    public List<ReporteCompensacionPorGiroDeNegocio> buscarCompensacionEmisorPorPorGiroDeNegocio(CriterioBusquedaCompensacionPorGiroDeNegocio criterioBusqueda)
    {
        return this.reporteCompensacionService.buscarCompensacionEmisorPorPorGiroDeNegocio(criterioBusqueda);
    }
    
}