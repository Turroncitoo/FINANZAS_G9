package ob.debitos.simp.controller.reporte.exportacion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlProgramaResumen;
import ob.debitos.simp.service.IReporteLogControlProgramaResumenService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Audit(tipo = Tipo.CON_LOGCON_PROGRESUMEN)
@RequestMapping("/reporte/logControlPrograma")
public @Controller class ReporteLogControlProgramaResumenExportacion {

	private @Autowired IReporteLogControlProgramaResumenService reporteLogControlProgramaResumen;
	
	@Audit(tipo = Tipo.CON_LOGCON_PROGRESUMEN)
    @PreAuthorize("hasPermission('RPT_LOGCONPROCRES','ANY')")
    @GetMapping(value = "/resumen", params = "accion=exportarTodos")
    public ModelAndView exportarTodosLogControlPrograma(ModelMap model)
    {
    	Map<String, Object> params = new HashMap<>();
        params.put("reporte", this.reporteLogControlProgramaResumen.buscarTodos());
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE,
                "reporteResumenLogContableProgramaResumen");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte Resumen Log Control Programa");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }
	
	@Audit(tipo = Tipo.CON_LOGCON_PROGRESUMEN)
    @PreAuthorize("hasPermission('RPT_LOGCONPROCRES','ANY')")
    @GetMapping(value = "/resumen", params = "accion=exportarPorCriterios")
    public ModelAndView exportarPorCriteriosLogControlPrograma(ModelMap model,
    		CriterioBusquedaLogControlProgramaResumen criterio)
    {
    	Map<String, Object> params = new HashMap<>();
    	params.put("criterioBusqueda", criterio);
        params.put("reporte", this.reporteLogControlProgramaResumen.filtrar(criterio));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE,
                "reporteResumenLogContableProgramaResumen");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte Resumen Log Control Programa");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }
}