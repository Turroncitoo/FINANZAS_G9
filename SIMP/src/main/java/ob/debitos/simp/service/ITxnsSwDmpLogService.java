package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.model.consulta.movimiento.TxnSwDmpLogDetalle;
import ob.debitos.simp.model.consulta.movimiento.TxnsSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionSwDmpLog;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsSwdmplog;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;

public interface ITxnsSwDmpLogService
{

    public List<TxnSwDmpLogDetalle> buscarDetalleTxnsSwDmpLog(CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion);

    public List<TxnsSwDmpLog> buscarTxnsSwDmpLogPaginado(CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsSwdmplog> criterioPaginacion);

    public List<TxnsSwDmpLog> filtrarTxnsSwDmpLogPaginado(CriterioPaginacion<CriterioBusquedaTransaccionSwDmpLog, FiltroDtTxnsSwdmplog> criterioPaginacion);

    public void exportarTxnsSwDmpLogPorCriteriosDetallado(List<TxnsSwDmpLog> list, CriterioBusquedaTransaccionSwDmpLog criterioBusquedaTransaccionSwDmpLog, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void exportarTxnsSwDmpLogPorTipoDocumento(List<TxnsSwDmpLog> list, CriterioBusquedaTipoDocumento criterioBusquedaTransaccionSwDmpLog, HttpServletRequest request, HttpServletResponse response) throws IOException;

}