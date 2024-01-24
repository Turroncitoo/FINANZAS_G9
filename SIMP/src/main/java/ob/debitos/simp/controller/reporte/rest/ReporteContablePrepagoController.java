package ob.debitos.simp.controller.reporte.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.model.proceso.ReporteContableLogCont;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IReporteContablePrepagoService;

public @RestController class ReporteContablePrepagoController {
	private @Autowired IReporteContablePrepagoService reporteContablePrepagoService;
	private @Autowired IParametroGeneralService parametroGeneralService;


	@PreAuthorize("hasPermission('RPT_CONTDEFAULT', '2')")
	@GetMapping(value = "/reporte/prepago/contabilizacion/{tipo:fondos|comisiones}")
	public Integer generarArchivoContable(@PathVariable String tipo) throws IOException {
		CriterioBusquedaArchivoContablePrepago criterioBusquedaArchivoContablePrepago = 
		CriterioBusquedaArchivoContablePrepago.builder()
			.fechaProceso(parametroGeneralService.buscarFechaProceso())
			.idInstitucion(parametroGeneralService.buscarCodigoInstitucion())
			.tipo(tipo.toUpperCase())
			.build();
		return reporteContablePrepagoService.generarArchivoContable(criterioBusquedaArchivoContablePrepago);
	}

	@PreAuthorize("hasPermission('RPT_CONTDEFAULT', '2')")
	@GetMapping(value = "/reporte/prepago/contabilizacion/{tipo:logc|swdl}")
	public Integer generarReporteContable(@PathVariable String tipo) throws IOException {
		CriterioBusquedaArchivoContablePrepago criterioBusquedaReporteContablePrepago = 
		CriterioBusquedaArchivoContablePrepago.builder()
			.fechaProceso(parametroGeneralService.buscarFechaProceso())
			.idInstitucion(parametroGeneralService.buscarCodigoInstitucion())
			.tipo(tipo.toUpperCase())
			.build();
		return reporteContablePrepagoService.generarReporteContable(criterioBusquedaReporteContablePrepago);
	}

	@PreAuthorize("hasPermission('RPT_CONTDEFAULT', '2')")
	 @GetMapping(value = "/reporte/prepago/contable/anexo", params = "accion=buscar")
	 public List<ReporteContableLogCont> buscarResumenAutorizacion(
			 CriterioBusquedaArchivoContablePrepago criterioBusqueda)
	 {
	    return reporteContablePrepagoService.buscarReporteContable(criterioBusqueda);
	 }
}
