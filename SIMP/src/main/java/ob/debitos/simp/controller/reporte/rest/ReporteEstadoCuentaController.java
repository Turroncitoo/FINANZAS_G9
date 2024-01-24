package ob.debitos.simp.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoCuenta;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.reporte.ReporteEstadoCuentaPrepago;
import ob.debitos.simp.service.IReporteEstadoCuentaService;

public @RestController class ReporteEstadoCuentaController {
	
	private @Autowired IReporteEstadoCuentaService reporteEstadoCuentaService;


	@PreAuthorize("hasPermission('RPT_ESTCTA', '2')")
	@GetMapping(value = "/reporte/estadoCuenta/prepago", params="accion=buscarPorCriterio")
	public List<ReporteEstadoCuentaPrepago> buscarPorCriterio(CriterioBusquedaEstadoCuenta criterioBusquedaEstadoCuenta)  {
		return reporteEstadoCuentaService.buscarPorCriterio(criterioBusquedaEstadoCuenta);
	}

	@PreAuthorize("hasPermission('RPT_ESTCTA', '2')")
	@GetMapping(value = "/reporte/estadoCuenta/prepago", params="accion=buscarPorNumeroDocumento")
    public List<ReporteEstadoCuentaPrepago> buscarPorNumeroDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaEstadoCuenta)  {
        return reporteEstadoCuentaService.buscarPorNumeroDocumento(criterioBusquedaEstadoCuenta);        
    }


}
