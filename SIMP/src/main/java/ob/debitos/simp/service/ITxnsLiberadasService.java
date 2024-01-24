package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsLiberadas;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionLiberada;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITxnsLiberadasService
{

    public List<TxnsLiberadas> buscarTxnsLiberadas(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);

    public List<TxnsLiberadas> filtrarTxnsLiberadas(CriterioBusquedaTransaccionLiberada criterioBusquedaTransaccionLiberada);

    public void exportarTxnsLiberadasPorCriterios(List<TxnsLiberadas> list, CriterioBusquedaTransaccionLiberada criterioBusquedaTransaccionLiberada, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void exportarTxnsLiberadasPorTipoDocumento(List<TxnsLiberadas> list, CriterioBusquedaTipoDocumento criterioBusquedaTransaccionLiberada, HttpServletRequest request, HttpServletResponse response) throws IOException;

}