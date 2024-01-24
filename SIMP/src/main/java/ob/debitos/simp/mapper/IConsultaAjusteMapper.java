package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.ajuste.TxnsAjuste;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionAjuste;

public interface IConsultaAjusteMapper
{

    public List<TxnsAjuste> buscarTransaccionesAjustesPorCriterios(CriterioBusquedaTransaccionAjuste criterioBusqueda);

    public List<TxnsAjuste> buscarTransaccionesAjustesPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);
    
}
