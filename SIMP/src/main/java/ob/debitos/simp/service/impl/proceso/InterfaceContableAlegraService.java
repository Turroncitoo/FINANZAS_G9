package ob.debitos.simp.service.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IInterfaceContableAlegraMapper;
import ob.debitos.simp.model.contable.InterfaceContableAlegraRequest;
import ob.debitos.simp.model.contable.LogInterfaceAlegra;
import ob.debitos.simp.model.criterio.CriterioBusquedaInterfaceContableAlegra;
import ob.debitos.simp.service.IInterfaceContableAlegraService;

@Service
public class InterfaceContableAlegraService implements IInterfaceContableAlegraService {

	@Autowired
	private IInterfaceContableAlegraMapper interfaceContableAlegraMapper;

	public List<InterfaceContableAlegraRequest> interfaceAlegraComisInCompras(
			CriterioBusquedaInterfaceContableAlegra criterio) {

		return this.interfaceContableAlegraMapper.interfaceAlegraComisInCompras(criterio);
	}

	@Override
	public List<InterfaceContableAlegraRequest> interfaceAlegraComisOutCompras(
			CriterioBusquedaInterfaceContableAlegra criterio) {

		return this.interfaceContableAlegraMapper.interfaceAlegraComisOutCompras(criterio);
	}

	@Override
	public List<InterfaceContableAlegraRequest> interfaceAlegraCompOutAdquiCompras(
			CriterioBusquedaInterfaceContableAlegra criterio) {

		return this.interfaceContableAlegraMapper.interfaceAlegraCompOutAdquiCompras(criterio);
	}

	@Override
	public List<InterfaceContableAlegraRequest> interfaceAlegraComisInTHB(CriterioBusquedaInterfaceContableAlegra criterio) {

		return this.interfaceContableAlegraMapper.interfaceAlegraComisInTHB(criterio);
	}
	
	@Override
	public List<InterfaceContableAlegraRequest> interfaceAlegraMiscelaneoUBA(CriterioBusquedaInterfaceContableAlegra criterio) {

		return this.interfaceContableAlegraMapper.interfaceAlegraMiscelaneoUBA(criterio);
	}
	
	@Override
	public List<InterfaceContableAlegraRequest> interfaceAlegraOtrosConceptosMaximo(CriterioBusquedaInterfaceContableAlegra criterio) {

		return this.interfaceContableAlegraMapper.interfaceAlegraOtrosConceptosMaximo(criterio);
	}

	@Override
	public void registrarLogAPIAlegra(LogInterfaceAlegra criterio){
		this.interfaceContableAlegraMapper.registrarLogAPIAlegra(criterio);
	}

}
