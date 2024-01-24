package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnSwDmpLogDetalle;
import ob.debitos.simp.model.consulta.movimiento.TxnsSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionSwDmpLog;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsSwdmplog;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;

public interface ITxnsSwDmpLogMapper
{
    public List<TxnSwDmpLogDetalle> buscarDetalleTxnsSwDmpLog(
            CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion);

    public List<TxnsSwDmpLog> buscarTxnsSwDmpLogPaginado(
            CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsSwdmplog> criterioPaginacion);

    public List<TxnsSwDmpLog> filtrarTxnsSwDmpLogPaginado(
            CriterioPaginacion<CriterioBusquedaTransaccionSwDmpLog, FiltroDtTxnsSwdmplog> criterioPaginacion);
}