package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaSaldo;
import ob.debitos.simp.model.prepago.Saldo;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;

public interface ISaldoMapper
{
    
    public List<Saldo> buscarTodos();

    public List<Saldo> buscarPorCriterio(CriterioBusquedaSaldo criterioBusquedaSaldo);

    public List<Saldo> buscarPorTipoDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumentoPrepago);
    
}
