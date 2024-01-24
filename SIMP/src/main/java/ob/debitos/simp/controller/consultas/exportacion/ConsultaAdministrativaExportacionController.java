package ob.debitos.simp.controller.consultas.exportacion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ob.debitos.simp.model.prepago.TarjetaPP;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.service.IPersonaPPService;
import ob.debitos.simp.service.ITarjetaPPService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Vista
@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/consulta/administrativa/")
public @Controller class ConsultaAdministrativaExportacionController
{

    private @Autowired IPersonaPPService personaPPService;
    private @Autowired ITarjetaPPService tarjetaPPService;

    @Audit(tipo = Tipo.CON_ADMIN_PERSONA_PP)
    @PreAuthorize("hasPermission('CON_ADMINPER','5')")
    @GetMapping(value = "personaPP", params = "accion=exportarPorCriterios")
    public ModelAndView descargarPersonasPorCriterios(ModelMap model, CriterioBusquedaClientePersona criterioBusqueda)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("reporte", personaPPService.buscarPorCriterios(criterioBusqueda));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteConsultaPersonas");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte Personas");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }

    @Audit(tipo = Tipo.CON_ADMIN_PERSONA_PP)
    @PreAuthorize("hasPermission('CON_ADMINPER','5')")
    @GetMapping(value = "personaPP", params = "accion=exportarPorTipoDocumento")
    public ModelAndView descargarPersonasPorTipoDocumento(ModelMap model, CriterioBusquedaTipoDocumento criterioBusqueda)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("reporte", personaPPService.buscarPorTipoDocumento(criterioBusqueda));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteConsultaPersonas");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte Personas");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }

    @Audit(tipo = Tipo.CON_ADMIN_PERSONA_PP)
    @PreAuthorize("hasPermission('CON_ADMINTARJETA','5')")
    @GetMapping(value = "tarjetasPP", params = "accion=exportarPorCriterios")
    public void descargarTarjetasPorCriterios(CriterioBusquedaTarjeta criterioBusqueda, Errors errors,
                                              HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TarjetaPP> list = this.tarjetaPPService.buscarPorCriterios(criterioBusqueda);
        this.tarjetaPPService.exportarTarjetasPPPorCriterios(list, criterioBusqueda, request, response);
    }

    @Audit(tipo = Tipo.CON_ADMIN_PERSONA_PP)
    @PreAuthorize("hasPermission('CON_ADMINTARJETA','5')")
    @GetMapping(value = "tarjetasPP", params = "accion=exportarPorTipoDocumento")
    public void descargarTarjetasPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda, Errors errors,
                                                  HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TarjetaPP> list = this.tarjetaPPService.buscarPorTipoDocumento(criterioBusqueda);
        this.tarjetaPPService.exportarTarjetasPPPorTipoDocumento(list, criterioBusqueda, request, response);
    }

}
