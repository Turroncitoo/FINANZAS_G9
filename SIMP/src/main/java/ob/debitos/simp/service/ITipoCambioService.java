package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.consulta.TipoCambio;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoCambio;

public interface ITipoCambioService
{
    public List<TipoCambio> buscarTiposCambio(
            CriterioBusquedaTipoCambio criterioBusqueda);    
}