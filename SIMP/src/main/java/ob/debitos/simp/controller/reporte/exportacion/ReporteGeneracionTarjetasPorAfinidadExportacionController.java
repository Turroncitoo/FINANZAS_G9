package ob.debitos.simp.controller.reporte.exportacion;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaGeneracionTarjetas;
import ob.debitos.simp.service.IReporteGeneracionTarjetasPorAfinidadService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/generacionTarjetas")
public @Controller class ReporteGeneracionTarjetasPorAfinidadExportacionController {

    @Autowired
    private IReporteGeneracionTarjetasPorAfinidadService reporteGeneracionTarjetasPorAfinidadService;

    @Audit(tipo = Tipo.RPT_GENERACION_TARJ)
    @PreAuthorize("hasPermission('RPT_GENERACION_TARJ', '5')")
    @GetMapping(value="resumen", params = "accion=exportar")
    public ModelAndView descargarReporteCompensacionEmisorInstitucion(ModelMap model,
        CriterioBusquedaGeneracionTarjetas criterioBusqueda) {

        Map<String, Object> params = new HashMap<>();
        params.put("reporte", reporteGeneracionTarjetasPorAfinidadService.generarReportePorRangoFechas(criterioBusqueda));
        params.put("criterioBusqueda", criterioBusqueda);

        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteGeneracionTarjetasPorAfinidad");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte Generación Tarjetas Por Afinidad");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }
}
