package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsEmbossing;
import ob.debitos.simp.model.criterio.CriterioBusquedaTxnsEmbossing;

public interface ITxnsEmbossingService {
	
	List<TxnsEmbossing> buscarPorCriterio(CriterioBusquedaTxnsEmbossing criterio);
}
