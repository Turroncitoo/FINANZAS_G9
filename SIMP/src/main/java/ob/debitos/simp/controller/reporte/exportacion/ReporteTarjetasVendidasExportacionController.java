package ob.debitos.simp.controller.reporte.exportacion;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjetasVendidas;
import ob.debitos.simp.model.reporte.ReporteTarjetasVendidas;
import ob.debitos.simp.service.IReporteTarjetasVendidasService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Vista
@Audit
@RequestMapping("/reporte/tarjetas/vendidas")
@Controller
public class  ReporteTarjetasVendidasExportacionController {

	@Autowired
	private IReporteTarjetasVendidasService iReporteTarjetasVendidasService;
	
	/* Se declara e implementa el formato para los porcentajes */
	private DecimalFormat formato = new DecimalFormat("0.##");
	private DecimalFormatSymbols simboloFormatoDecimal = new DecimalFormatSymbols();
	
	
	@Audit(tipo = Tipo.RPT_TARJETASVENDIDAS)
    @PreAuthorize("hasPermission('RPT_TARJETAS_VENDIDAS','5')")
	@GetMapping(value = "resumen", params = "accion=exportar" )
	public ModelAndView descargarReporteMensualTarjetasVendidas(ModelMap model, CriterioBusquedaTarjetasVendidas criterioBusquedaTarjetasVendidas)
	{
		
		//Configurando las variables para darle formato al porcentaje//
		this.simboloFormatoDecimal.setDecimalSeparator('.');
		this.formato.setDecimalFormatSymbols(simboloFormatoDecimal);
		
		//TODO
		 /*Realizar las operaciones para calcular el porcentaje de transacciones y tarjetas recargadas*/
		
		List<ReporteTarjetasVendidas> listaReporteTarjetasVendidas  = this.iReporteTarjetasVendidasService.buscarResumenMensualTarjetasVendidas(criterioBusquedaTarjetasVendidas);

		for (ReporteTarjetasVendidas reporteTarjetasVendidas : listaReporteTarjetasVendidas) {
			
			/*Declarando variables locales para el seteo de los porcentajes*/
			
			Double porcentajeTarjetasRecargadas = null;
			Double porcentajeTarjetasEnUso = null;

			/*Porcentaje de tarjetas en uso*/
		
			porcentajeTarjetasEnUso = Double.valueOf(this.formato.format((reporteTarjetasVendidas.getTarjetasEnUso()*1.0/reporteTarjetasVendidas.getTarjetasActivadas()*100.0))); 
				
				if(porcentajeTarjetasEnUso.isNaN() || porcentajeTarjetasEnUso.isInfinite())
					porcentajeTarjetasEnUso = 0.00;
					
				reporteTarjetasVendidas.setPorcentajeTarjetasEnUso(porcentajeTarjetasEnUso);
									
			/*Porcentaje de tarjetas recargadas*/
				porcentajeTarjetasRecargadas = Double.valueOf(this.formato.format((reporteTarjetasVendidas.getTarjetasRecargadas()*1.0/reporteTarjetasVendidas.getTarjetasActivadas()*100.0)));
				
				
				if(porcentajeTarjetasRecargadas.isNaN() || porcentajeTarjetasRecargadas.isInfinite())
					porcentajeTarjetasRecargadas = 0.00;

				reporteTarjetasVendidas.setPorcentajeTarjetasRecargadas(porcentajeTarjetasRecargadas);
				

		}
		
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("reporte", listaReporteTarjetasVendidas);
		params.put("mesInicio",obtenerMes(criterioBusquedaTarjetasVendidas.getMesInicio()));
		params.put("anioInicio",criterioBusquedaTarjetasVendidas.getAnioInicio());
		params.put("mesFin", obtenerMes(criterioBusquedaTarjetasVendidas.getMesFin()));
		params.put("anioFin", criterioBusquedaTarjetasVendidas.getAnioFin());
		
		
		model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE,"reporteTarjetasVendidas4");
		model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,"Reporte de Tarjetas Vendidas - Periodo " + obtenerMes(criterioBusquedaTarjetasVendidas.getMesInicio()) + " del " + criterioBusquedaTarjetasVendidas.getAnioInicio().toString() +" - " + obtenerMes(criterioBusquedaTarjetasVendidas.getMesFin()) + " del " +criterioBusquedaTarjetasVendidas.getAnioFin());
		model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS,params);
		
		return new ModelAndView("jxlsView",model);
	}
	
	private  String obtenerMes(Integer mes)
	{
		switch(mes)
		{
		case 1 : return "Enero";
		case 2 : return "Febrero";
		case 3 : return "Marzo";
		case 4 : return "Abril";
		case 5 : return "Mayo";
		case 6 : return "Junio";
		case 7 : return "Julio";
		case 8 : return "Agosto";
		case 9 : return "Septiembre";
		case 10 : return "Octubre";
		case 11 : return "Noviembre";
		case 12 : return "Diciembre";
		default : return "Error";
		
		}
	}
	
}
