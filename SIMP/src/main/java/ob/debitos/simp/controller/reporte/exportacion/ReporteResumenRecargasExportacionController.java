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
import ob.debitos.simp.model.RecargasTemp;
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteResumenRecargas;
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.model.reporte.ReporteResumenRecargas;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.IReporteResumenRecargasService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/recargas/")
public @Controller class ReporteResumenRecargasExportacionController {

	private @Autowired IReporteResumenRecargasService reporteResumenRecargasService;
	private @Autowired IInstitucionService institucionService;

	@Audit(tipo = Tipo.RPT_RECARGAS)
	@PreAuthorize("hasPermission('RPT_RECARGAS', '5')")
	@GetMapping(value = "resumenMensual", params = "accion=exportar")
	public ModelAndView descargarResumenMensualRecarga(ModelMap model,
			@Validated CriterioBusquedaReporteResumenRecargas criterioBusqueda) {
		Map<String, Object> params = new HashMap<>();

		// f1
		List<ReporteResumenRecargas> reporte = reporteResumenRecargasService
				.obtenerReporteResumenRecargar(criterioBusqueda);
		params.put("reporte", reporte);

		// f2

		List<Institucion> instituciones = institucionService.buscarPorInstitucionEmpresa();
		List<RecargasTemp> recargasTemps = reporteResumenRecargasService.obtenerReporteRecargasFormateada(criterioBusqueda, instituciones);
		params.put("recargasTemps", recargasTemps);

		//fecha
		Calendar cal = Calendar.getInstance();
		cal.setTime(criterioBusqueda.getFecha());
		int mes = cal.get(Calendar.MONTH) + 1;
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
		int anio = cal.get(Calendar.YEAR);
		String periodo = mesString + " de " + anio;
		params.put("periodo", periodo);
		model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteRecargas");
		model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte de Recargas");
		model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
		return new ModelAndView("jxlsView", model);
	}
}
