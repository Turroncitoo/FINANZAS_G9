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
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoTarjeta;
import ob.debitos.simp.service.IReporteEstadoTarjetaService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Vista
@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/estadoTarjeta/")
public @Controller class ReporteEstadoTarjetaExportacionController {
	
	private @Autowired IReporteEstadoTarjetaService reporteEstadoTarjetaService;
	
	@Audit(tipo = Tipo.RPT_ESTADOTARJETAS)
    @PreAuthorize("hasPermission('RPT_ESTADOTARJETAS','5')")
    @GetMapping(value = "resumenMensual", params = "accion=exportar")
    public ModelAndView descargarResumenMensualEstadoTarjeta(ModelMap model, CriterioBusquedaEstadoTarjeta criterioBusqueda)
    {
        Map<String, Object> params = new HashMap<>();
        
        params.put("reporte", reporteEstadoTarjetaService.buscarResumenMensualPorCriterios(criterioBusqueda));
        params.put("criterioBusqueda", criterioBusqueda);
    
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE,"reporteEstadoTarjeta");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,"Reporte Estado Tarjeta");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        
        return new ModelAndView("jxlsView", model);
    
    }

}
