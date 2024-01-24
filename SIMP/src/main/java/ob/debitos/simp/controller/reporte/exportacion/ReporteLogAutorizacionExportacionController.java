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
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionNoConciliada;
import ob.debitos.simp.service.ILogAutorizacionesService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/logautorizacion")
public @Controller class ReporteLogAutorizacionExportacionController
{

    private @Autowired ILogAutorizacionesService logAutorizacionService;

    @Audit
    @PreAuthorize("hasPermission('RPT_LOGAUT','ANY')")
    @GetMapping(value = "/noConciliadas", params = "accion=buscarPorCriterios")
    public ModelAndView descargarReporteAutorizacionNoConciliadasPorCriterio(ModelMap model, ModelAndView modelAndView, @Validated CriterioBusquedaTransaccionNoConciliada criterioBusquedaTransaccionNoConciliada, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        Map<String, Object> params = new HashMap<>();
        params.put("param1", this.logAutorizacionService.buscarAutorizacionesNoConciliadasPorCriterios(criterioBusquedaTransaccionNoConciliada));
        params.put("param2", criterioBusquedaTransaccionNoConciliada);
        model.addAttribute("rb_titulo", ReporteUtilYarg.buildReportBand("Titulo"));
        model.addAttribute("rb_criterioBusqueda", ReporteUtilYarg.buildReportBand("CriteriosBusqueda", "CriterioBusqueda", "parameter=param2 $", "json"));
        model.addAttribute("rb_encabezado", ReporteUtilYarg.buildReportBand("Encabezado"));
        model.addAttribute("rb_datos", ReporteUtilYarg.buildReportBand("Datos", "Datos", "parameter=param1 $", "json"));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteLogAutorizacionNoConciliadas");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte de Autorizaciones no conciliadas");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        modelAndView = new ModelAndView("yargView", model);
        return modelAndView;
    }

}
