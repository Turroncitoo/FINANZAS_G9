package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.ajuste.ActualizacionExtornoDevolucion;
import ob.debitos.simp.model.ajuste.ActualizacionTrace;
import ob.debitos.simp.model.consulta.movimiento.TransaccionObservada;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionObservada;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITxnsObservadasService
{

    public List<TransaccionObservada> buscarTransaccionesObservadasPorCriterios(CriterioBusquedaTransaccionObservada criterioBusqueda);

    public List<TransaccionObservada> buscarTransaccionesObservadasPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);

    public List<TransaccionObservada> buscarConciliacionObservada(CriterioBusquedaTransaccionObservada criterioBusqueda);

    public void actualizarTrace(ActualizacionTrace actualizaTrace);

    public void actualizarExtornoDevolucion(ActualizacionExtornoDevolucion actualizaExtornoDev);

    public void exportarTxnsObservadasPorCriterios(List<TransaccionObservada> list, CriterioBusquedaTransaccionObservada criterioBusquedaTransaccionObservada, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void exportarTxnsObservadasPorTipoDocumento(List<TransaccionObservada> list, CriterioBusquedaTipoDocumento criterioBusquedaTransaccionObservada, HttpServletRequest request, HttpServletResponse response) throws IOException;

}