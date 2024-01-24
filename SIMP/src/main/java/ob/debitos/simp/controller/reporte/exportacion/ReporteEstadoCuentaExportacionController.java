package ob.debitos.simp.controller.reporte.exportacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoCuenta;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.reporte.ReporteEstadoCuentaPrepago;
import ob.debitos.simp.model.reporte.ReporteEstadoCuentaPrepagoMovimiento;
import ob.debitos.simp.service.IReporteEstadoCuentaService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@RequestMapping("/reporte/estadoCuenta/prepago")
public @Controller class ReporteEstadoCuentaExportacionController {
	private @Autowired IReporteEstadoCuentaService reporteEstadoCuentaService;

	@PreAuthorize("hasPermission('RPT_ESTCTA','ANY')")
	@GetMapping(value="/xlsx",params = "accion=exportarCriterios")
	public ModelAndView exportarXLSXCriterios(ModelMap model, ModelAndView modelAndView,
			CriterioBusquedaEstadoCuenta criterioBusquedaEstadoCuenta){
		Map<String, Object> params = new HashMap<>();
        params.put("estadosCuenta", this.reporteEstadoCuentaService.buscarPorCriterio(criterioBusquedaEstadoCuenta));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteEstadoCuentaCliente");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte de Estado Cuenta");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        modelAndView = new ModelAndView("jxlsView", model);
        return modelAndView;
	}

	@PreAuthorize("hasPermission('RPT_ESTCTA','ANY')")
	@GetMapping(value="/xlsx", params = "accion=exportarTipoDocumento")
	public ModelAndView exportarXLSXTipoDocumento(ModelMap model, ModelAndView modelAndView,
			CriterioBusquedaTipoDocumentoPrepago criterioBusquedaEstadoCuenta){
		Map<String, Object> params = new HashMap<>();
        params.put("estadosCuenta", this.reporteEstadoCuentaService.buscarPorNumeroDocumento(criterioBusquedaEstadoCuenta));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteEstadoCuentaCliente");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte de Estado Cuenta");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        modelAndView = new ModelAndView("jxlsView", model);
        return modelAndView;
	}

	@PreAuthorize("hasPermission('RPT_ESTCTA','ANY')")
	@GetMapping(value="/pdf", params = "accion=exportarCriterios")
	public ModelAndView exportarPDFCriterios(ModelMap model, ModelAndView modelAndView,
			CriterioBusquedaEstadoCuenta criterioBusquedaEstadoCuenta){
		List<ReporteEstadoCuentaPrepago> estados = this.reporteEstadoCuentaService.buscarPorCriterio(criterioBusquedaEstadoCuenta);
        if(!estados.isEmpty()){
        	model.put("datasource", estados.get(0).getMovimientos());
        	model.put("numeroTarjeta", estados.get(0).getNumeroTarjeta());
        	model.put("nombreCompleto", estados.get(0).getNombreCompleto());
        	model.put("fechaActivacionFmt", estados.get(0).getFechaActivacionFmt());
        	model.put("fechaBloqueoFmt", estados.get(0).getFechaBloqueoFmt());
        	model.put("saldoDisponible", estados.get(0).getSaldoDisponible());
        }else{
        	model.put("datasource", new ArrayList<ReporteEstadoCuentaPrepagoMovimiento>());
        }
        model.put("format", "pdf");
        return new ModelAndView("rpt_ReporteEstadoCuentaCliente", model);
	}

	@PreAuthorize("hasPermission('RPT_ESTCTA','ANY')")
	@GetMapping(value="/pdf", params = "accion=exportarTipoDocumento")
	public ModelAndView exportarPDFTipoDocumento(ModelMap model, ModelAndView modelAndView,
			CriterioBusquedaTipoDocumentoPrepago criterioBusquedaEstadoCuenta){
		List<ReporteEstadoCuentaPrepago> estados = this.reporteEstadoCuentaService.buscarPorNumeroDocumento(criterioBusquedaEstadoCuenta);
        if(!estados.isEmpty()){
        	model.put("datasource", estados.get(0).getMovimientos());
        	model.put("numeroTarjeta", estados.get(0).getNumeroTarjeta());
        	model.put("nombreCompleto", estados.get(0).getNombreCompleto());
        	model.put("fechaActivacionFmt", estados.get(0).getFechaActivacionFmt());
        	model.put("fechaBloqueoFmt", estados.get(0).getFechaBloqueoFmt());
        	model.put("saldoDisponible", estados.get(0).getSaldoDisponible());
        }else{
        	model.put("datasource", new ArrayList<ReporteEstadoCuentaPrepagoMovimiento>());
        }
        model.put("format", "pdf");
        return new ModelAndView("rpt_ReporteEstadoCuentaCliente", model);
	}
	
	
}
