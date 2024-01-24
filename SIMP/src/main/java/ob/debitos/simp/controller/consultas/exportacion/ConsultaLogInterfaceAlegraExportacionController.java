package ob.debitos.simp.controller.consultas.exportacion;

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
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaLogInterfaceAlegra;
import ob.debitos.simp.service.IConsultaLogInterfaceAlegraService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

/**
 * 
 * @author Carlos Mirano
 *
 */
@Vista
@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/consulta/integracion")
public @Controller class ConsultaLogInterfaceAlegraExportacionController
{
    
    private @Autowired IConsultaLogInterfaceAlegraService consultaLogInterfaceAlegraService;

    @Audit(tipo = Tipo.CON_LOG_INTER_ALEGRA)
    @PreAuthorize("hasPermission('CON_LOG_INTER_ALEGRA', '5')")
    @GetMapping(value = "/alegra", params = "accion=exportar")
    public ModelAndView descargarInterfaceAgentesPorCriterios(ModelMap modelMap, CriterioBusquedaConsultaLogInterfaceAlegra criterioBusquedaConsultaLogInterfaceAlegra)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("reporte", this.consultaLogInterfaceAlegraService.buscarInterfaceAlegra(criterioBusquedaConsultaLogInterfaceAlegra));
        map.put("criterioBusqueda", criterioBusquedaConsultaLogInterfaceAlegra);
        modelMap.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteLogInterfaceAlegra");
        modelMap.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte de Log Interface Alegra");
        modelMap.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, map);
        return new ModelAndView("jxlsView", modelMap);
    }
    
}
