package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.ITxnsEmbossingMapper;
import ob.debitos.simp.model.consulta.movimiento.TxnsEmbossing;
import ob.debitos.simp.model.criterio.CriterioBusquedaTxnsEmbossing;
import ob.debitos.simp.service.ITxnsEmbossingService;

@Service
public class TxnsEmbossingService implements ITxnsEmbossingService{
	
	private @Autowired ITxnsEmbossingMapper txnsEmbossingMapper;
	
	@Truncable(clase = TxnsEmbossing.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<TxnsEmbossing> buscarPorCriterio(CriterioBusquedaTxnsEmbossing criterio) {
		return this.txnsEmbossingMapper.buscarPorCriterio(criterio);
	}

}
