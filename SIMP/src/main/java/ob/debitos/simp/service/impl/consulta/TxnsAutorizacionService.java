package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ITxnsAutorizacionMapper;
import ob.debitos.simp.model.consulta.movimiento.TxnAutorizadaComisiones;
import ob.debitos.simp.model.consulta.movimiento.TxnsAutorizacion;
import ob.debitos.simp.model.consulta.movimiento.TxnsAutorizacionDetalle;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionAutorizada;
import ob.debitos.simp.service.ITxnsAutorizacionService;

@Service
public class TxnsAutorizacionService implements ITxnsAutorizacionService
{
    private @Autowired ITxnsAutorizacionMapper consultaMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsAutorizacion> buscarTxnsAutorizaciones(
            CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
    {
        return consultaMapper.buscarTxnsAutorizacion(criterioBusquedaTipoDocumento);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsAutorizacion> filtrarTxnsAutorizacion(
            CriterioBusquedaTransaccionAutorizada criterioBusquedaTransaccionAutorizada)
    {
        return consultaMapper.filtrarTxnsAutorizacion(criterioBusquedaTransaccionAutorizada);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsAutorizacionDetalle> buscarDetalleTxnAutorizada(
            CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion)
    {
        return consultaMapper.buscarDetalleTxnAutorizada(criterioBusquedaDetalleTransaccion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<TxnAutorizadaComisiones> buscarComisionesPorAutorizacion(
			TxnAutorizadaComisiones txnAutorizadaComisiones) {
		return consultaMapper.buscarComisionesPorAutorizacion(txnAutorizadaComisiones);
	}
}