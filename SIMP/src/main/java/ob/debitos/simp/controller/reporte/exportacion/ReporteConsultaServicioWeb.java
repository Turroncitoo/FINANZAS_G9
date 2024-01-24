package ob.debitos.simp.controller.reporte.exportacion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaServicioWeb;
import ob.debitos.simp.service.IConsultaServicioWebService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@RequestMapping("/prepago/consulta/exportacion")
public @Controller class ReporteConsultaServicioWeb
{

    private @Autowired IConsultaServicioWebService consultaServicioWebService;

    @Audit(comentario = Comentario.ReporteCriterioBusqueda)
    @PreAuthorize("hasPermission('WS_CON_SERVICIO_WEB','2')")
    @GetMapping(params = "accion=exportarPorCriterios")
    public ModelAndView descargarReporteConsultaServicioWebPorCriterios(ModelMap model, @Validated CriterioBusquedaConsultaServicioWeb criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        Map<String, Object> params = new HashMap<>();
        params.put("reporte", this.consultaServicioWebService.buscarPorCriterios(criterioBusqueda));
        params.put("criterioBusqueda", criterioBusqueda);
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteConsultaServicioWeb");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte Consulta Servicio Web");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }

}
