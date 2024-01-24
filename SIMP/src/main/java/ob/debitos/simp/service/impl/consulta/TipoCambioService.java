package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IConsultaTipoCambioMapper;
import ob.debitos.simp.model.consulta.TipoCambio;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoCambio;
import ob.debitos.simp.service.ITipoCambioService;

@Service
public class TipoCambioService implements ITipoCambioService
{
    private @Autowired IConsultaTipoCambioMapper tipoCambioMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TipoCambio> buscarTiposCambio(
            CriterioBusquedaTipoCambio criterioBusqueda)
    {
        return tipoCambioMapper.buscarTiposCambio(criterioBusqueda);
    }
}