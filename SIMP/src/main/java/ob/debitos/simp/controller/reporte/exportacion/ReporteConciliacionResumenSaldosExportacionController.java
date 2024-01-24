package ob.debitos.simp.controller.reporte.exportacion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaConciliacionResumenSaldos;
import ob.debitos.simp.model.reporte.ReporteConciliacionResumenSaldos;
import ob.debitos.simp.service.IReporteConciliacionResumenSaldosService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Vista
@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/conciliacion")
public @Controller class ReporteConciliacionResumenSaldosExportacionController {
	
	private @Autowired IReporteConciliacionResumenSaldosService reporteConciliacionResumenSaldosService;
	
	@Audit(tipo = Tipo.RPT_ESTADOTARJETAS)
    @PreAuthorize("hasPermission('RPT_CONSALDOSRESUMEN','5')")
    @GetMapping(value = "/saldosUba", params = "accion=exportar")
    public ModelAndView descargarResumenConciliacionSaldosUba(ModelMap model, CriterioBusquedaConciliacionResumenSaldos criterioBusqueda)
    {
        Map<String, Object> params = new HashMap<>();
        ReporteConciliacionResumenSaldos reporteConciliacionResumenSaldos = ReporteConciliacionResumenSaldos.builder()
				.saldoFinal(reporteConciliacionResumenSaldosService.buscarResumenSaldosFinal(criterioBusqueda))
				.saldoLiberadas(reporteConciliacionResumenSaldosService.buscarResumenLiberadas(criterioBusqueda))
				.saldoNoCompensado(reporteConciliacionResumenSaldosService.buscarResumenNoCompensadas(criterioBusqueda))
				.build();
        
    	if(reporteConciliacionResumenSaldos.getSaldoLiberadas().size() != 0){
    		reporteConciliacionResumenSaldos.getSaldoLiberadas().forEach(rep->{
    			rep.getReporteConciliacionResumen().forEach(r->{
    				Double liberadas = r.getValorTotal();
    				if(rep.getCodigoMoneda() == 604){
    					reporteConciliacionResumenSaldos.getSaldoFinal().forEach(saldoFinal->{
    						saldoFinal.getReporteConciliacionResumen().forEach(saldo->{
    							if(saldoFinal.getCodigoMoneda() == 604){
    								saldo.setLiberadas(liberadas);
    							}
    						});
    					});
    				}
    				if(rep.getCodigoMoneda() == 840){
    					reporteConciliacionResumenSaldos.getSaldoFinal().forEach(saldoFinal->{
    						saldoFinal.getReporteConciliacionResumen().forEach(saldo->{
    							if(saldoFinal.getCodigoMoneda() == 840){
    								saldo.setLiberadas(liberadas);
    							}
    						});
    					});
    				}
    			});
            });
    	}
    	if(reporteConciliacionResumenSaldos.getSaldoNoCompensado().size() != 0){
    		reporteConciliacionResumenSaldos.getSaldoNoCompensado().forEach(rep->{
    			rep.getReporteConciliacionResumen().forEach(r->{
    				Double pendientes = r.getValorTotal();
    				if(rep.getCodigoMoneda() == 604){
    					reporteConciliacionResumenSaldos.getSaldoFinal().forEach(saldoFinal->{
    						saldoFinal.getReporteConciliacionResumen().forEach(saldo->{
    							if(saldoFinal.getCodigoMoneda() == 604){
    								saldo.setPendientes(pendientes);
    							}
    						});
    					});
    				}
    				if(rep.getCodigoMoneda() == 840){
    					reporteConciliacionResumenSaldos.getSaldoFinal().forEach(saldoFinal->{
    						saldoFinal.getReporteConciliacionResumen().forEach(saldo->{
    							if(saldoFinal.getCodigoMoneda() == 840){
    								saldo.setPendientes(pendientes);
    							}
    						});
    					});
    				}
    			});
            });
    	}
        
        params.put("saldoFinal", reporteConciliacionResumenSaldos.getSaldoFinal());
        params.put("criterioBusqueda", criterioBusqueda);    
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE,"reporteConciliacionSaldosUba");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,"Reporte Conciliacion Saldos UBA");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    
    }

}
