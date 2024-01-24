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
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlPrograma;
import ob.debitos.simp.service.ILogControlProgramaService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Vista
@Audit(tipo = Tipo.CON_LOG_PROC_AUTOM)
public @Controller class ReporteLogControlProgramaExportacionController
{
    private @Autowired ILogControlProgramaService logControlProgramaService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.Reporte)
    @PreAuthorize("hasPermission('CON_LOGPROCAUTOM', '5')")
    @GetMapping(value = "/proceso/logControlPrograma", params = "accion=exportar")
    public ModelAndView exportarLogControlPrograma(ModelMap model,
            @Validated CriterioBusquedaLogControlPrograma criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        Map<String, Object> params = new HashMap<>();
    	params.put("criterioBusqueda", criterioBusqueda);
        params.put("reporte", this.logControlProgramaService.buscarPorCriterioBusqueda(criterioBusqueda));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE,
                "reporteLogControlPrograma");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte de Log Control Programa");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }
}