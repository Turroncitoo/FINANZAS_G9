package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnCompensacionComisiones;
import ob.debitos.simp.model.consulta.movimiento.TxnsCompensacion;
import ob.debitos.simp.model.consulta.movimiento.TxnsCompensacionDetalle;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionCompensacion;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsCompensacion;

public interface ITxnsCompensacionMapper
{
    public List<TxnsCompensacion> buscarTxnsCompensacion(
            CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsCompensacion> criterioPaginacion);

    public List<TxnsCompensacion> filtrarTxnsCompensacion(
            CriterioPaginacion<CriterioBusquedaTransaccionCompensacion, FiltroDtTxnsCompensacion> criterioPaginacion);

    public List<TxnsCompensacionDetalle> buscarDetalleTxnCompensacion(
            CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion);

    public List<TxnCompensacionComisiones> buscarComisionesPorCompensacion(
            TxnCompensacionComisiones txnCompensacionComisionesDTO);
}