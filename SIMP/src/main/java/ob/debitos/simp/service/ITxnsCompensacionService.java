package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.model.consulta.movimiento.*;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionCompensacion;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsCompensacion;

public interface ITxnsCompensacionService
{

    public List<TxnsCompensacion> buscarTxnsCompensacion(
            CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsCompensacion> criterioPaginacion);

    public List<TxnsCompensacion> filtrarTxnsCompensacion(
            CriterioPaginacion<CriterioBusquedaTransaccionCompensacion, FiltroDtTxnsCompensacion> criterioPaginacion);

    public List<TxnsCompensacionDetalle> buscarDetalleTxnCompensacion(
            CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion);

    public List<TxnCompensacionComisiones> buscarComisionesPorCompensacion(
            TxnCompensacionComisiones txnCompensacionComisionesDTO);

    public void exportarTxnsCompensacionPorCriterios(
            List<TxnsCompensacion> list,
            CriterioBusquedaTransaccionCompensacion criterioBusquedaTransaccionCompensacion,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException;

    public void exportarTxnsCompensacionPorTipoDocumento(
            List<TxnsCompensacion> list,
            CriterioBusquedaTipoDocumento criterioBusquedaTransaccionCompensacion,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException;

}