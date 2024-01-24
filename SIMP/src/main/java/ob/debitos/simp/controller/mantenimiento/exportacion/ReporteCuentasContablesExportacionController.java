package ob.debitos.simp.controller.mantenimiento.exportacion;

import static ob.debitos.simp.utilitario.ConstantesGenerales.P_REPORTE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.configuracion.view.JxlsView;
import ob.debitos.simp.model.mantenimiento.CuentaContable;
import ob.debitos.simp.model.mantenimiento.CuentaContableEmisor;
import ob.debitos.simp.service.ICuentaAjusteService;
import ob.debitos.simp.service.ICuentaContableEmisorService;
import ob.debitos.simp.service.ICuentaContableMiscelaneoService;
import ob.debitos.simp.service.ICuentaContableService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

/**
 * Recibe las peticiones del usuario relacionadas a la exportación del mantenimiento
 * de cuentas contables.
 * <p>
 * Se realiza una búsqueda de todas las cuentas contables y se deja el 
 * trabajo de  generar el reporte de tipo XLSX a la vista {@code JxlsView}. 
 * Esto se logra devolviendo un {@link ModelAndView} estableciendo la vista 
 * <b>jxlsView</b>
 * 
 * @author Fernando Fonseca
 * @see ICuentaContableService
 * @see CuentaContable
 * @see JxlsView
 */
@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
public @Controller class ReporteCuentasContablesExportacionController {

    private static final String VIEW_NAME = "jxlsView";

	private @Autowired ICuentaContableEmisorService cuentaContableEmisorService;
	private @Autowired ICuentaContableMiscelaneoService cuentaContableMiscelaneoService;
	private @Autowired ICuentaContableService cuentaContableService;
	private @Autowired ICuentaAjusteService cuentaAjusteService;
	
    /**
     * Recibe la petición de exportación de cuentas emisor 
     * 
     * @param model
     *            modelo enviado a la página
     * @return {@link ModelAndView}
     */
	@Audit(tipo = Tipo.CONTAB_EMI)
    @PreAuthorize("hasPermission('MANT_CONTABEMI', '5')")
    @GetMapping(value = "/cuentaContableEmisor", params = "accion=exportar")
	public ModelAndView buscarCuentasEmisor(ModelMap model){
		
		List<CuentaContableEmisor> cuentas = cuentaContableEmisorService.buscarTodosConceptosCuentas();
		Map<String, Object> params = new HashMap<>();
	    params.put(P_REPORTE, cuentas);			
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "mantenimientos/reporteCuentaEmisor");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte Cuenta Emisor");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView(VIEW_NAME, model);
	}
	
    /**
     * Recibe la petición de exportación de cuentas misceláneo
     * 
     * @param model
     *            modelo enviado a la página
     * @return {@link ModelAndView}
     */
	@Audit(tipo = Tipo.CONTAB_MIS)
    @PreAuthorize("hasPermission('MANT_CONTABMIS', '5')")
    @GetMapping(value = "/cuentaContableMiscelaneo", params = "accion=exportar")
	public ModelAndView buscarCuentasMiscelaneos(ModelMap model){
		Map<String, Object> params = new HashMap<>();
        params.put(P_REPORTE, cuentaContableMiscelaneoService.buscarTodos());
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "mantenimientos/reporteCuentaMiscelaneo");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte Cuenta Miscelaneo");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView(VIEW_NAME, model);
	}
	
	/**
     * Recibe la petición de exportación de rubros contables 
     * 
     * @param model
     *            modelo enviado a la página
     * @return {@link ModelAndView}
     */
	@Audit(tipo = Tipo.CTA_CONTABLE)
    @PreAuthorize("hasPermission('MANT_CTA', '5')")
    @GetMapping(value = "/cuentaContable", params = "accion=exportar")
	public ModelAndView buscarRubrosContables(ModelMap model){
		Map<String, Object> params = new HashMap<>();
        params.put(P_REPORTE, cuentaContableService.buscarTodos());
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "mantenimientos/reporteCuentaContable");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte Rubros Contables");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView(VIEW_NAME, model);
	}
	
	@Audit(tipo = Tipo.CTA_AJUSTE)
    @PreAuthorize("hasPermission('MANT_CTAAJUS', '5')")
    @GetMapping(value = "/cuentaAjuste", params = "accion=exportar")
	public ModelAndView buscarCuentasAjuste(ModelMap model){
		Map<String, Object> params = new HashMap<>();
        params.put(P_REPORTE, cuentaAjusteService.buscarTodos());
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "mantenimientos/reporteCuentaAjuste");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte Cuenta Ajuste");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView(VIEW_NAME, model);
	}
	
}
