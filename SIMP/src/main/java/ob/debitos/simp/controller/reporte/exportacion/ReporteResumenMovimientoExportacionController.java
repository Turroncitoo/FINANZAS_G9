package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ob.debitos.simp.service.IConceptoComisionService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
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
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenAutorizacionSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableEmisor;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransaccionAprobadaAgencia;
import ob.debitos.simp.model.reporte.ReporteResumenSwDmpLog;
import ob.debitos.simp.model.reporte.ReporteResumenLogContableEmisor;
import ob.debitos.simp.service.IReporteResumenMovimientoService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/resumen/movimiento/")
public @Controller class ReporteResumenMovimientoExportacionController
{
    private @Autowired IReporteResumenMovimientoService reporteService;
    private @Autowired IConceptoComisionService conceptoComisionService;

    @Audit(tipo = Tipo.RPT_MOV_AUT)
    @PreAuthorize("hasPermission('RPT_MOVAUT','ANY')")
    @GetMapping(value = "autorizacion", params = "accion=exportar")
    public ModelAndView descargarResumenAutorizacion(ModelMap model, ModelAndView modelAndView,
                                                     CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("param1", reporteService.buscarResumenAutorizacion(criterioBusqueda));
        params.put("param2", criterioBusqueda);
        model.addAttribute("rb_titulo", ReporteUtilYarg.buildReportBand("Titulo"));
        model.addAttribute("rb_criterioBusqueda", ReporteUtilYarg.buildReportBand(
                "CriteriosBusqueda", "CriterioBusqueda", "parameter=param2 $", "json"));
        model.addAttribute("rb_encabezado", ReporteUtilYarg.buildReportBand("Encabezado"));
        model.addAttribute("rb_datos",
                ReporteUtilYarg.buildReportBand("Datos", "Datos", "parameter=param1 $", "json"));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteResumenMovimientoAutorizacion");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte Resumen Movimiento SwDmpLog");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        modelAndView = new ModelAndView("yargView", model);
        return modelAndView;
    }

    @Audit(tipo = Tipo.RPT_MOV_SWDMPLOG)
    @PreAuthorize("hasPermission('RPT_MOVSWDMPLOG','5')")
    @GetMapping(value = "swDmpLog", params = "accion=exportar")
    public void descargarResumenSwDmpLog(@Validated CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda, Errors errors, 
                                        HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteResumenSwDmpLog> reporte = this.reporteService.buscarResumenSwDmpLog(criterioBusqueda);
        this.reporteService.descargarReporteResumenSwDmpLog(reporte, criterioBusqueda, request, response);
    }

    @Audit(tipo = Tipo.RPT_MOV_TRANS_AG)
    @PreAuthorize("hasPermission('RPT_MOVTRANSAG','ANY')")
    @GetMapping(value = "transaccionAprobadaAgencia", params = "accion=exportar")
    public ModelAndView descargarResumenTransaccionAprobadaPorAgencia(ModelMap model,
                                                                      ModelAndView modelAndView,
                                                                      CriterioBusquedaResumenTransaccionAprobadaAgencia criterioBusqueda)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("param1",
                reporteService.buscarResumenTransaccionAprobadaAgencia(criterioBusqueda));
        params.put("param2", criterioBusqueda);
        model.addAttribute("rb_titulo", ReporteUtilYarg.buildReportBand("Titulo"));
        model.addAttribute("rb_criterioBusqueda", ReporteUtilYarg.buildReportBand(
                "CriteriosBusqueda", "CriterioBusqueda", "parameter=param2 $", "json"));
        model.addAttribute("rb_encabezado", ReporteUtilYarg.buildReportBand("Encabezado"));
        model.addAttribute("rb_datos",
                ReporteUtilYarg.buildReportBand("Datos", "Datos", "parameter=param1 $", "json"));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteResumenMovimientoTransaccionAprobadaAgencia");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte Resumen Movimiento de Transacciones Observadas por Agencia");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        modelAndView = new ModelAndView("yargView", model);
        return modelAndView;
    }

    @Audit(tipo = Tipo.RPT_MOV_LG_CNT_EMI)
    @PreAuthorize("hasPermission('RPT_MOVLGCNTEMI', '5')")
    @GetMapping(value = "logContableEmisor", params = "accion=exportar")
    public void descargarResumenLogContableEmisor(@Validated CriterioBusquedaResumenLogContableEmisor criterio, Errors errors, 
                                                HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteResumenLogContableEmisor> reporte = this.reporteService.buscarResumenLogContableEmisor(criterio);
        this.reporteService.buscarResumenLogContableEmisorExportacion(reporte, criterio, request, response,
                                        this.conceptoComisionService.buscarTodosRolEmisor());
    }
}