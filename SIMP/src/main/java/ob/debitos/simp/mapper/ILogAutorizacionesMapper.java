package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.LogAutorizacion;
import ob.debitos.simp.model.consulta.LogAutorizacionNoConciliadaPorFecha;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionNoConciliada;

public interface ILogAutorizacionesMapper
{
    public List<LogAutorizacion> buscarAutorizacionesNoConciliadasPorCriterios(
            CriterioBusquedaTransaccionNoConciliada criterioBusquedaTransaccionNoConciliada);

    public List<LogAutorizacionNoConciliadaPorFecha> buscarAutorizacionesNoConciliadasPorDia(
            CriterioBusquedaTransaccionNoConciliada criterioBusquedaTransaccionNoConciliada);
}