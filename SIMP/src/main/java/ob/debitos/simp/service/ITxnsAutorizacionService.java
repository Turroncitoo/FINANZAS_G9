package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnAutorizadaComisiones;
import ob.debitos.simp.model.consulta.movimiento.TxnsAutorizacion;
import ob.debitos.simp.model.consulta.movimiento.TxnsAutorizacionDetalle;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionAutorizada;

public interface ITxnsAutorizacionService
{
    public List<TxnsAutorizacion> buscarTxnsAutorizaciones(
            CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);

    public List<TxnsAutorizacion> filtrarTxnsAutorizacion(
            CriterioBusquedaTransaccionAutorizada criterioBusquedaTransaccionAutorizada);
    
    public List<TxnsAutorizacionDetalle> buscarDetalleTxnAutorizada(
            CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion);
    
    public List<TxnAutorizadaComisiones> buscarComisionesPorAutorizacion(
    		TxnAutorizadaComisiones txnAutorizadaComisiones);
}