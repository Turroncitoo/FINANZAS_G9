package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.TipoCambio;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoCambio;

public interface IConsultaTipoCambioMapper
{
    public List<TipoCambio> buscarTiposCambio(
            CriterioBusquedaTipoCambio parametro);
}
