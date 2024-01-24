package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ob.debitos.simp.model.prepago.Saldo;
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

import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.model.criterio.CriterioBusquedaSaldo;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.service.IReporteContablePrepagoService;
import ob.debitos.simp.service.ISaldoService;
import ob.debitos.simp.service.ITarjetaPPService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/reporte/prepago/contabilidad/")
public @Controller class ReporteContablePrepagoExportacionController
{

    private @Autowired IReporteContablePrepagoService reporteService;
    private @Autowired ISaldoService saldoService;
    private @Autowired ITarjetaPPService tarjetaService;

    @PreAuthorize("hasPermission('REPORT_CONT_ANEXO_01', '2')")
    @GetMapping(value = "anexo1", params = "accion=exportar")
    public ModelAndView descargarReporteAnexo1(ModelMap model, ModelAndView modelAndView, CriterioBusquedaArchivoContablePrepago criterioBusqueda)
    {

        String p1 = String.format("%03d", criterioBusqueda.getIdInstitucion());
        String p2 = String.format("%1$tY%1$tm%1$td", criterioBusqueda.getFechaInicio());
        String p3 = String.format("%1$tY%1$tm%1$td", criterioBusqueda.getFechaFin());
        String nombreArchivo = "A01-" + p1 + "-" + p2 + "-" + p3;

        Map<String, Object> params = new HashMap<>();
        params.put("param1", reporteService.buscarReporteContable(criterioBusqueda));
        params.put("param2", criterioBusqueda);
        model.addAttribute("rb_titulo", ReporteUtilYarg.buildReportBand("Titulo"));
        model.addAttribute("rb_criterioBusqueda", ReporteUtilYarg.buildReportBand("CriteriosBusqueda", "CriterioBusqueda", "parameter=param2 $", "json"));
        model.addAttribute("rb_encabezado", ReporteUtilYarg.buildReportBand("Encabezado"));
        model.addAttribute("rb_datos", ReporteUtilYarg.buildReportBand("Datos", "Datos", "parameter=param1 $", "json"));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteTransaccionesPrepagoAnexo1");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, nombreArchivo);
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        modelAndView = new ModelAndView("yargView", model);
        return modelAndView;
    }

    @PreAuthorize("hasPermission('REPORT_CONT_ANEXO_02', '2')")
    @GetMapping(value = "anexo2", params = "accion=exportar")
    public ModelAndView descargarReporteAnexo2(ModelMap model, ModelAndView modelAndView, CriterioBusquedaArchivoContablePrepago criterioBusqueda)
    {
        String p1 = String.format("%03d", criterioBusqueda.getIdInstitucion());
        String p2 = String.format("%1$tY%1$tm%1$td", criterioBusqueda.getFechaInicio());
        String p3 = String.format("%1$tY%1$tm%1$td", criterioBusqueda.getFechaFin());
        String nombreArchivo = "A02-" + p1 + "-" + p2 + "-" + p3;

        Map<String, Object> params = new HashMap<>();
        params.put("param1", reporteService.buscarReporteContable(criterioBusqueda));
        params.put("param2", criterioBusqueda);
        model.addAttribute("rb_titulo", ReporteUtilYarg.buildReportBand("Titulo"));
        model.addAttribute("rb_criterioBusqueda", ReporteUtilYarg.buildReportBand("CriteriosBusqueda", "CriterioBusqueda", "parameter=param2 $", "json"));
        model.addAttribute("rb_encabezado", ReporteUtilYarg.buildReportBand("Encabezado"));
        model.addAttribute("rb_datos", ReporteUtilYarg.buildReportBand("Datos", "Datos", "parameter=param1 $", "json"));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteTransaccionesPrepagoAnexo2");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, nombreArchivo);
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        modelAndView = new ModelAndView("yargView", model);
        return modelAndView;
    }

    @PreAuthorize("hasPermission('CON_ADMINTARJETA','ANY')")
    @GetMapping(value = "tarjetas", params = "accion=exportarPorTipoDocumento")
    public ModelAndView descargarReporteTarjetas(ModelMap model, ModelAndView modelAndView, @Validated CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        Map<String, Object> params = new HashMap<>();
        params.put("reporte", tarjetaService.buscarPorTipoDocumento(criterioBusquedaTipoDocumento));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteTarjetas");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "ReporteTarjetas");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        modelAndView = new ModelAndView("jxlsView", model);
        return modelAndView;
    }

    @PreAuthorize("hasPermission('CON_ADMINTARJETA','ANY')")
    @GetMapping(value = "tarjetas", params = "accion=exportarPorCriterios")
    public ModelAndView descargarReporteTarjetasPorCriterios(ModelMap model, ModelAndView modelAndView, @Validated CriterioBusquedaTarjeta criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        Map<String, Object> params = new HashMap<>();
        params.put("reporte", tarjetaService.buscarPorCriterios(criterioBusqueda));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteTarjetas");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "ReporteTarjetas");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        modelAndView = new ModelAndView("jxlsView", model);
        return modelAndView;
    }

    @PreAuthorize("hasPermission('CON_MOVSAL','ANY')")
    @GetMapping(value = "saldos", params = "accion=exportarPorCriterios")
    public void descargarReporteSaldosPorCriterios(CriterioBusquedaSaldo criterioBusqueda, Errors errors, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<Saldo> list = this.saldoService.buscarPorCriterio(criterioBusqueda);
        this.saldoService.exportarSaldoPorCriterios(list, criterioBusqueda, request, response);
    }

    @PreAuthorize("hasPermission('CON_MOVSAL','ANY')")
    @GetMapping(value = "saldos", params = "accion=exportarPorTipoDocumento")
    public void descargarReporteSaldosPorTipoDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusqueda, Errors errors, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<Saldo> list = this.saldoService.buscarPorTipoDocumento(criterioBusqueda);
        this.saldoService.exportarSaldoPorTipoDocumento(list, criterioBusqueda, request, response);
    }

}