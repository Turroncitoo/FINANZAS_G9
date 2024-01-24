package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaServicioWeb;
import ob.debitos.simp.model.prepago.wshub.ConsultaServicioWeb;
import ob.debitos.simp.model.prepago.wshub.LogControlWS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IConsultaServicioWebService
{

    public List<ConsultaServicioWeb> buscarTodos();

    public List<ConsultaServicioWeb> buscarPorCriterios(CriterioBusquedaConsultaServicioWeb criterioBusqueda);

    public List<ConsultaServicioWeb> buscarPorEvento(CriterioBusquedaConsultaServicioWeb criterio);

    public List<LogControlWS> buscarLogControlEnLinea(CriterioBusquedaConsultaServicioWeb criterio);

    public List<LogControlWS> buscarLogControlHistorico(CriterioBusquedaConsultaServicioWeb criterio);

    public void exportarLogControlHistorico(List<LogControlWS> list, CriterioBusquedaConsultaServicioWeb criterioBusquedaConsultaServicioWeb, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
