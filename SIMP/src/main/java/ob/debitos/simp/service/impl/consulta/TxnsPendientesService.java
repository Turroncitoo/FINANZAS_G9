package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.ITxnsPendientesMapper;
import ob.debitos.simp.model.consulta.movimiento.TxnsPendientes;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionPendiente;
import ob.debitos.simp.service.ITxnsPendientesService;

@Service
@Truncable(clase = TxnsPendientes.class)
public class TxnsPendientesService implements ITxnsPendientesService
{
	 private @Autowired ITxnsPendientesMapper consultaMapper;

	 @Truncable
	 @Transactional(propagation = Propagation.REQUIRES_NEW)
	 public List<TxnsPendientes> buscarTxnsPendientes(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
	 {
	     return consultaMapper.buscarTxnsPendientes(criterioBusquedaTipoDocumento);
	 }
	 
	 @Truncable
	 @Transactional(propagation = Propagation.REQUIRES_NEW)
	 public List<TxnsPendientes> filtrarTxnsPendientes(
		CriterioBusquedaTransaccionPendiente criterioBusquedaTransaccionPendiente) 
	 {
		return consultaMapper.filtrarTxnsPendientes(criterioBusquedaTransaccionPendiente);
	 }
}
