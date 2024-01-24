package ob.debitos.simp.controller.reporte.exportacion;

import static ob.debitos.simp.utilitario.ConstantesGenerales.P_CRITERIO_BUSQUEDA;
import static ob.debitos.simp.utilitario.ConstantesGenerales.ROL_TRANSACCION_RECEPTOR;
import static ob.debitos.simp.utilitario.ConstantesGenerales.V_JXLS_VIEW;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacion;
import ob.debitos.simp.model.criterio.CriterioBusquedaCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.reporte.ReporteCompensacionPorGiroDeNegocio;
import ob.debitos.simp.model.reporte.ReporteCompensacionInstitucion;
import ob.debitos.simp.model.reporte.ReporteCompensacionCanal;
import ob.debitos.simp.service.IReporteCompensacionService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Vista
@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/compensacion")
public @Controller class ReporteCompensacionExportacionController
{

    private @Autowired IReporteCompensacionService reporteCompensacionService;

    @Audit(tipo = Tipo.RPT_COMP_EMI_INST)
    @PreAuthorize("hasPermission('RPT_COMPEMIINST', '5')")
    @GetMapping(value = "/emisor/{tipoCompensacion:institucion}", params = "accion=exportar")
    public void generarExcelInstitucion(CriterioBusquedaCompensacion criterioBusqueda, Errors errors, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteCompensacionInstitucion> reporte = this.reporteCompensacionService.buscarCompensacionInstitucion(criterioBusqueda);
        this.reporteCompensacionService.exportarDetalleCompensacionInstitucion(reporte, criterioBusqueda, request, response);
    }

    @Audit(tipo = Tipo.RPT_COMP_EMI_CANAL)
    @PreAuthorize("hasPermission('RPT_COMPEMICANAL', '5')")
    @GetMapping(value = "/emisor/{tipoCompensacion:canal}", params = "accion=exportar")
    public void generarExcelCanal(CriterioBusquedaCompensacion criterioBusqueda, @PathVariable String tipoCompensacion, Errors errors, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        criterioBusqueda.setTipoCompensacion(tipoCompensacion);
        List<ReporteCompensacionCanal> list = this.reporteCompensacionService.buscarCompensacionCanal(criterioBusqueda);
        this.reporteCompensacionService.exportarReporteCompensacionCanal(list, criterioBusqueda, request, response);
    }

    @Audit(tipo = Tipo.RPT_COMP_REC)
    @PreAuthorize("hasPermission('RPT_COMPREC', '5')")
    @GetMapping(value = "/{tipoCompensacion:receptor}", params = "accion=exportar")
    public ModelAndView generarExcelReceptor(ModelMap model, ModelAndView modelAndView, CriterioBusquedaCompensacion criterioBusquedaCompensacion, @PathVariable String tipoCompensacion)
    {
        criterioBusquedaCompensacion.setTipoCompensacion(tipoCompensacion);
        criterioBusquedaCompensacion.setRolTransaccion(ROL_TRANSACCION_RECEPTOR);
        Map<String, Object> params = new HashMap<>();
        params.put(P_CRITERIO_BUSQUEDA, criterioBusquedaCompensacion);
        params.put("reportesCompensacion", reporteCompensacionService.buscarCompensaciones(criterioBusquedaCompensacion));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteCompensacionReceptor");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte Compensación Receptor");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView(V_JXLS_VIEW, model);
    }

    @Audit(tipo = Tipo.RPT_COMP_EMI_GIRO_NEG)
    @PreAuthorize("hasPermission('RPT_COMPEMIGIRNEG', '5')")
    @GetMapping(value = "/emisor/giroDeNegocio", params = "accion=exportar")
    public void descargarReporteAutorizacionNoConciliadasPorCriterio(CriterioBusquedaCompensacionPorGiroDeNegocio criterioBusqueda, Errors errors, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteCompensacionPorGiroDeNegocio> list = this.reporteCompensacionService.buscarCompensacionEmisorPorPorGiroDeNegocio(criterioBusqueda);
        this.reporteCompensacionService.exportarReporteCompensacionEmisorPorPorGiroDeNegocio(list, criterioBusqueda, request, response);
    }

}