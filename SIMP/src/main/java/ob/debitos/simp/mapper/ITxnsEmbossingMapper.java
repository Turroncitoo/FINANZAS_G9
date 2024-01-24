package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsEmbossing;
import ob.debitos.simp.model.criterio.CriterioBusquedaTxnsEmbossing;

public interface ITxnsEmbossingMapper {
	
	List<TxnsEmbossing> buscarPorCriterio(CriterioBusquedaTxnsEmbossing criterio);
}
