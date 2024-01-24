package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsPendientes;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionPendiente;

public interface ITxnsPendientesService
{
	 public List<TxnsPendientes> buscarTxnsPendientes(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);
	 
	 public List<TxnsPendientes> filtrarTxnsPendientes(CriterioBusquedaTransaccionPendiente criterioBusquedaTransaccionPendiente);
}
