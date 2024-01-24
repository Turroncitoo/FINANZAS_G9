package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.contable.InterfaceContableAlegraRequest;
import ob.debitos.simp.model.contable.LogInterfaceAlegra;
import ob.debitos.simp.model.criterio.CriterioBusquedaInterfaceContableAlegra;

public interface IInterfaceContableAlegraService {

	public List<InterfaceContableAlegraRequest> interfaceAlegraComisInCompras(
			CriterioBusquedaInterfaceContableAlegra criterio);

	public List<InterfaceContableAlegraRequest> interfaceAlegraComisOutCompras(
			CriterioBusquedaInterfaceContableAlegra criterio);

	public List<InterfaceContableAlegraRequest> interfaceAlegraCompOutAdquiCompras(
			CriterioBusquedaInterfaceContableAlegra criterio);

	public List<InterfaceContableAlegraRequest> interfaceAlegraComisInTHB(
			CriterioBusquedaInterfaceContableAlegra criterio);
	
	public List<InterfaceContableAlegraRequest> interfaceAlegraMiscelaneoUBA(CriterioBusquedaInterfaceContableAlegra criterio);
	
	public List<InterfaceContableAlegraRequest> interfaceAlegraOtrosConceptosMaximo(CriterioBusquedaInterfaceContableAlegra criterio);
	
	public void registrarLogAPIAlegra(LogInterfaceAlegra criterio);

}
