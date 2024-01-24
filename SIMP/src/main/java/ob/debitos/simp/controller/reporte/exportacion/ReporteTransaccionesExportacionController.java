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
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransacciones;
import ob.debitos.simp.service.IReporteTransaccionesService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/transacciones/")
public @Controller class ReporteTransaccionesExportacionController {
	
	private @Autowired IReporteTransaccionesService reporteTransaccionesService;
	
	@Audit(tipo = Tipo.RPT_TXNSTARJETA)
    @PreAuthorize("hasPermission('RPT_TXNSTARJETA', '5')")
    @GetMapping(value = "tarjetas", params = "accion=exportar")
    public ModelAndView descargarReporteTransaccionesTarjetas(ModelMap model, CriterioBusquedaResumenTransacciones criterioBusqueda)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("criterioBusqueda", criterioBusqueda);
        params.put("reporte", reporteTransaccionesService.buscarTransaccionesTarjetas(criterioBusqueda));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteTransaccionesTarjetas");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,"Reporte Transacciones Tarjetas");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }
}
