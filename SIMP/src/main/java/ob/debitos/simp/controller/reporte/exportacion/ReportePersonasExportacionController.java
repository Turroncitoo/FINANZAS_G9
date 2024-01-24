package ob.debitos.simp.controller.reporte.exportacion;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaReportePersonas;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.service.IReportePersonasService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/personas/")
public @Controller class ReportePersonasExportacionController {
	private @Autowired IReportePersonasService reportePersonasService;

	@Audit(tipo = Tipo.RPT_RECARGAS)
	@PreAuthorize("hasPermission('RPT_PERSONAS', '5')")
	@GetMapping(value = "resumenMensual", params = "accion=exportar")
	public ModelAndView descargarResumenMensualRecarga(ModelMap model,
			@Validated CriterioBusquedaReportePersonas criterioBusqueda) {
		Map<String, Object> params = new HashMap<>();
		List<ReporteMoneda> reporte = reportePersonasService.obtenerReportePersonas(criterioBusqueda);
		params.put("reporte", reporte);
		params.put("criterio", criterioBusqueda);
		String mesInicio=mesPalabra(criterioBusqueda.getMesInicio());
		String mesFin = mesPalabra(criterioBusqueda.getMesFin());
		params.put("mesInicio", mesInicio);
		params.put("mesFin", mesFin);
		model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reportePersonas5");
		model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte de Personas");
		model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
		return new ModelAndView("jxlsView", model);
	}
	
	public String mesPalabra(Integer mes) {
		String mesString = "";
		switch (mes) {
		case 1:
			mesString = "Enero";
			break;
		case 2:
			mesString = "Febrero";
			break;
		case 3:
			mesString = "Marzo";
			break;
		case 4:
			mesString = "Abril";
			break;
		case 5:
			mesString = "Mayo";
			break;
		case 6:
			mesString = "Junio";
			break;
		case 7:
			mesString = "Julio";
			break;
		case 8:
			mesString = "Agosto";
			break;
		case 9:
			mesString = "Septiembre";
			break;
		case 10:
			mesString = "Octubre";
			break;
		case 11:
			mesString = "Noviembre";
			break;
		case 12:
			mesString = "Diciembre";
			break;
		default:
			mesString = "Mes invalido";
			break;
		}
		return mesString;
	}
}
