package ob.debitos.simp.service;

import java.net.URISyntaxException;

import ob.debitos.simp.controller.excepcion.AlegraException;
import ob.debitos.simp.model.contable.CuentaContableAlegraRequest;
import ob.debitos.simp.model.criterio.CriterioBusquedaInterfaceContableAlegra;
import ob.debitos.simp.model.mantenimiento.RespuestaAlegra;


public interface IAlegraService {

	public RespuestaAlegra crearCuentaContable(CuentaContableAlegraRequest cuentaContableAlegraRequest)
			throws URISyntaxException;

	public String editarCuentaContable(CuentaContableAlegraRequest cuentaContableAlegraRequest)
			throws URISyntaxException;

	public void enviarInterfaceContableAlegra(CriterioBusquedaInterfaceContableAlegra criterio, Integer modo)
			throws URISyntaxException, AlegraException;

}
