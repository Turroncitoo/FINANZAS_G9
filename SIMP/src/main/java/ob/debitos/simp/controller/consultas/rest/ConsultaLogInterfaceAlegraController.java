package ob.debitos.simp.controller.consultas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.model.consulta.ConsultaLogInterfaceAlegra;
import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaLogInterfaceAlegra;
import ob.debitos.simp.service.IConsultaLogInterfaceAlegraService;

/**
 * 
 * @author Carlos Mirano
 *
 */
@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/consulta/integracion")
public @RestController class ConsultaLogInterfaceAlegraController
{
	private @Autowired IConsultaLogInterfaceAlegraService consultaLogInterfaceAlegraService;

	@PreAuthorize("hasPermission('CON_LOG_INTER_ALEGRA','2')")
	@GetMapping(value = "/alegra", params = "accion=buscarPorFiltros")
	public List<ConsultaLogInterfaceAlegra> buscarPorCriterios(
			CriterioBusquedaConsultaLogInterfaceAlegra criterioBusquedaConsultaLogInterfaceAlegra)
	{
		return this.consultaLogInterfaceAlegraService.buscarInterfaceAlegra(criterioBusquedaConsultaLogInterfaceAlegra);
	}
}
