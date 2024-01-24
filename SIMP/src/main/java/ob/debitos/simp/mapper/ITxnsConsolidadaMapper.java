package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsConsolidada;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionConsolidada;

public interface ITxnsConsolidadaMapper 
{

	public List<TxnsConsolidada> buscarTxnsConsolidada(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);
	
	public List<TxnsConsolidada> filtrarTxnsConsolidada(CriterioBusquedaTransaccionConsolidada criterioBusquedaTransaccionConsolidada);
}
