package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.ITxnsConsolidadaMapper;
import ob.debitos.simp.model.consulta.movimiento.TxnsConsolidada;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionConsolidada;
import ob.debitos.simp.service.ITxnsConsolidadaService;

@Service
public class TxnsConsolidadaService implements ITxnsConsolidadaService
{

	private @Autowired ITxnsConsolidadaMapper consultaMapper;
	
	@Truncable(clase = TxnsConsolidada.class)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<TxnsConsolidada> buscarTxnsConsolidada(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento) 
	{
		return consultaMapper.buscarTxnsConsolidada(criterioBusquedaTipoDocumento);
	}

	@Truncable(clase = TxnsConsolidada.class)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<TxnsConsolidada> filtrarTxnsConsolidada(CriterioBusquedaTransaccionConsolidada criterioBusquedaTransaccionConsolidada)
	{
		return consultaMapper.filtrarTxnsConsolidada(criterioBusquedaTransaccionConsolidada);
	}

}
