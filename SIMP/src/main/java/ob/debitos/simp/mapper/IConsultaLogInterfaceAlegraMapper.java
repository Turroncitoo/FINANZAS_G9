package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.ConsultaLogInterfaceAlegra;
import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaLogInterfaceAlegra;

/**
 * 
 * @author Carlos Mirano
 *
 */
public interface IConsultaLogInterfaceAlegraMapper
{
	public List<ConsultaLogInterfaceAlegra> buscarInterfaceAlegra(
			CriterioBusquedaConsultaLogInterfaceAlegra criterioBusquedaConsultaLogInterfaceAlegra);
}
