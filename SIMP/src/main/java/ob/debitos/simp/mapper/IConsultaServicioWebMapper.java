package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaServicioWeb;
import ob.debitos.simp.model.prepago.wshub.ConsultaServicioWeb;
import ob.debitos.simp.model.prepago.wshub.LogControlWS;

public interface IConsultaServicioWebMapper
{

    public List<ConsultaServicioWeb> buscarTodos();

    public List<ConsultaServicioWeb> buscarPorCriterios(CriterioBusquedaConsultaServicioWeb criterioBusqueda);

    public List<ConsultaServicioWeb> buscarPorEvento(CriterioBusquedaConsultaServicioWeb criterioBusqueda);

    public List<LogControlWS> buscarLogControlEnLinea(CriterioBusquedaConsultaServicioWeb criterio);

    public List<LogControlWS> buscarLogControlHistorico(CriterioBusquedaConsultaServicioWeb criterio);

}
