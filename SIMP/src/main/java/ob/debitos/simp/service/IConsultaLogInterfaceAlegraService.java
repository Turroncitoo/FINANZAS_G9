package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.consulta.ConsultaLogInterfaceAlegra;
import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaLogInterfaceAlegra;

/**
 * 
 * @author Carlos Mirano
 *
 */
public interface IConsultaLogInterfaceAlegraService
{
	public List<ConsultaLogInterfaceAlegra> buscarInterfaceAlegra(
			CriterioBusquedaConsultaLogInterfaceAlegra criterioBusquedaConsultaLogInterfaceAlegra);
}
