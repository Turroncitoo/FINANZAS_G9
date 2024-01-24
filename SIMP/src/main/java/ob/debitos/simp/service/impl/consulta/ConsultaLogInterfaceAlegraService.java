package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IConsultaLogInterfaceAlegraMapper;
import ob.debitos.simp.model.consulta.ConsultaLogInterfaceAlegra;
import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaLogInterfaceAlegra;
import ob.debitos.simp.service.IConsultaLogInterfaceAlegraService;

/**
 * 
 * @author Carlos Mirano
 *
 */
@Service
public class ConsultaLogInterfaceAlegraService implements IConsultaLogInterfaceAlegraService
{
	private @Autowired IConsultaLogInterfaceAlegraMapper consultaLogInterfaceAlegraMapper;

	@Override
	public List<ConsultaLogInterfaceAlegra> buscarInterfaceAlegra(
			CriterioBusquedaConsultaLogInterfaceAlegra criterioBusquedaConsultaLogInterfaceAlegra)
	{
		return this.consultaLogInterfaceAlegraMapper.buscarInterfaceAlegra(criterioBusquedaConsultaLogInterfaceAlegra);
	}
}
