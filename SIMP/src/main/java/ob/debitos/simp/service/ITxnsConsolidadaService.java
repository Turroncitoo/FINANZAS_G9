package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsConsolidada;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionConsolidada;

public interface ITxnsConsolidadaService 
{	
	public List<TxnsConsolidada> buscarTxnsConsolidada(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);
	
	public List<TxnsConsolidada> filtrarTxnsConsolidada(CriterioBusquedaTransaccionConsolidada criterioBusquedaTransaccionConsolidada);
}
