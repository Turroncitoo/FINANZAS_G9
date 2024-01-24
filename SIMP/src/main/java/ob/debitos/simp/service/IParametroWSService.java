package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ParametroWS;

public interface IParametroWSService {

	public List<ParametroWS> buscarTodos();

	public void actualizarParametroWS(ParametroWS parametroWS);

}
