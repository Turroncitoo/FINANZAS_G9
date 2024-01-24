package ob.debitos.simp.mapper;

import java.util.List;
import ob.debitos.simp.model.mantenimiento.ParametroWS;

public interface IParametroWSMapper
{
	public List<ParametroWS> buscarTodos();

	public void actualizarParametroWS(ParametroWS parametroWS);
}