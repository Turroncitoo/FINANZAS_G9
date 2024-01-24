package ob.debitos.simp.controller.reporte.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaConciliacionResumenSaldos;
import ob.debitos.simp.model.reporte.ReporteConciliacionResumenSaldos;
import ob.debitos.simp.service.IReporteConciliacionResumenSaldosService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/conciliacion")
public @RestController class ReporteConciliacionResumenSaldosController {

	private @Autowired IReporteConciliacionResumenSaldosService reporteConciliacionResumenSaldosService;

	@Audit(tipo = Tipo.RPT_CONSALDOSRESUMEN)
	@PreAuthorize("hasPermission('RPT_CONSALDOSRESUMEN', '2')")
	@GetMapping(value = "/saldosUba")
	public ReporteConciliacionResumenSaldos buscarResumenDiarioPorCriterios(CriterioBusquedaConciliacionResumenSaldos criterioBusqueda) {
		
		ReporteConciliacionResumenSaldos reporteConciliacionResumenSaldos = ReporteConciliacionResumenSaldos.builder()
				.saldoFinal(reporteConciliacionResumenSaldosService.buscarResumenSaldosFinal(criterioBusqueda))
				//.saldoInicial(reporteConciliacionResumenSaldosService.buscarResumenSaldosInicial(criterioBusqueda))
				.saldoLiberadas(reporteConciliacionResumenSaldosService.buscarResumenLiberadas(criterioBusqueda))
				.saldoNoCompensado(reporteConciliacionResumenSaldosService.buscarResumenNoCompensadas(criterioBusqueda))
				.build();
		return reporteConciliacionResumenSaldos;
	}

}
