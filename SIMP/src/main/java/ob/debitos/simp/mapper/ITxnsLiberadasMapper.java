package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsLiberadas;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionLiberada;

public interface ITxnsLiberadasMapper
{
    
    public List<TxnsLiberadas> buscarTxnsLiberadas(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);

    public List<TxnsLiberadas> filtrarTxnsLiberadas(CriterioBusquedaTransaccionLiberada criterioBusquedaTransaccionLiberada);
    
}
