package ob.debitos.simp.controller.reporte.exportacion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaFiltroPrepago;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.prepago.wshub.ConsultaMovimientos;
import ob.debitos.simp.model.prepago.wshub.ConsultaSaldo;
import ob.debitos.simp.service.IHubWebService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/ws/")
public @Controller class ReporteSaldosMovimientosExportacionController {

	private @Autowired IHubWebService hubWebService;
	
	@Audit
	@GetMapping(value="saldosMovimientos",params="accion=exportarPorTipoDocumento")
	public ModelAndView descargarSaldosMovimientosPorTipoDocumento(ModelMap model,
            ModelAndView modelAndView,
            @Validated CriterioBusquedaTipoDocumentoPrepago criterioBusqueda, 
            Errors error){
        if (error.hasErrors()){
        	modelAndView =  new ModelAndView("forward:/400");
        	return modelAndView;
        }
        try{
            Map<String, Object> params = new HashMap<>();
            ConsultaMovimientos consultaMovimientos;
            ConsultaSaldo saldo;
            /* List<MovimientoPP> lista = new ArrayList<MovimientoPP>();
         	lista.add(MovimientoPP.builder().codigoOperacion("codigooperacion")
         			.comercio("comercio").costo("20.00").fecha("2018-02-10").hora("10:20:30").monto("20.00")
         			.secuencia("secuencia").tarjetaTruncada("1").build());
         	consultaMovimientos = ConsultaMovimientos.builder().movimientos(lista).build();
         	saldo = ConsultaSaldo.builder().moneda("SOLES").monto("100.00").build();*/
         	consultaMovimientos = this.hubWebService.buscarMovimientos(criterioBusqueda);
         	saldo = this.hubWebService.buscarSaldo(criterioBusqueda);
            params.put("movimientos", consultaMovimientos.getMovimientos());
            params.put("saldo", saldo);
            params.put("criterioBusqueda", criterioBusqueda);
            
            model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteSaldosMovimientos");
            model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                     "Reporte de Saldos Movivimientos");
            model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
            modelAndView = new ModelAndView("jxlsView", model);
        }catch(Exception e){
        	modelAndView =  new ModelAndView("forward:/500");
        	return modelAndView;
        }
        return modelAndView;
	}
	
	@Audit
	@GetMapping(value="saldosMovimientos",params="accion=exportarPorCriterios")
	public ModelAndView descargarSaldosMovimientosPorFiltro(ModelMap model,
            ModelAndView modelAndView,
            @Validated CriterioBusquedaFiltroPrepago criterioBusqueda, 
            Errors error){
        if (error.hasErrors()){
        	modelAndView =  new ModelAndView("forward:/400");
        	return modelAndView;
        }
        try{
        	Map<String, Object> params = new HashMap<>();
        	ConsultaMovimientos consultaMovimientos;
            ConsultaSaldo saldo;
            /* List<MovimientoPP> lista = new ArrayList<MovimientoPP>();
         	lista.add(MovimientoPP.builder().codigoOperacion("codigooperacion")
         			.comercio("comercio").costo("20.00").fecha("2018-02-10").hora("10:20:30").monto("20.00")
         			.secuencia("secuencia").tarjetaTruncada("1").build());
         	consultaMovimientos = ConsultaMovimientos.builder().movimientos(lista).build();
         	saldo = ConsultaSaldo.builder().moneda("SOLES").monto("100.00").build();*/
         	consultaMovimientos = this.hubWebService.buscarMovimientos(criterioBusqueda);
         	saldo = this.hubWebService.buscarSaldo(criterioBusqueda);
            params.put("movimientos", consultaMovimientos.getMovimientos());
            params.put("saldo", saldo);
            params.put("criterioBusqueda", criterioBusqueda);
            
            model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteSaldosMovimientos");
            model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                    "Reporte de Saldos Movivimientos");
            model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
            modelAndView = new ModelAndView("jxlsView", model);
        }catch(Exception e){
        	modelAndView =  new ModelAndView("forward:/500");
        	return modelAndView;
        }
        
        return modelAndView;
	}
}
