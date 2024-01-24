package ob.debitos.simp.controller.reporte.exportacion.seguridad;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaAuditoria;
import ob.debitos.simp.service.IAuditoriaService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Vista
@RequestMapping("/seguridad/auditoria")
public @Controller class ReporteConsultaAuditoriaExportacionController
{
    private @Autowired IAuditoriaService auditoriaService;

    @PreAuthorize("hasPermission('CON_AUDIT', '5')")
    @GetMapping(params = "accion=exportar")
    public ModelAndView descargarReporteTarifarioResumenEmisor(ModelMap model,
            CriterioBusquedaAuditoria criterioBusquedaAuditoria)
    {
    	Map<String, Object> params = new HashMap<>();
		params.put("criterioBusqueda", criterioBusquedaAuditoria);
		params.put("reporte", this.auditoriaService.buscarPistasAuditoriaPorCriterio(criterioBusquedaAuditoria));
		model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteAuditoria");
		model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte de auditoría");
		model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
		return new ModelAndView("jxlsView", model);
    }
}