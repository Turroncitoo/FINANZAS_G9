package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ReglaContable;

public interface IReglaContableService extends IMantenibleService<ReglaContable>
{
	public List<ReglaContable> buscarTodos();
	
	public List<ReglaContable> buscarPorClave(String idComercio, String idCliente);

	public boolean existeReglaContable(String idComercio, String idCliente);

	public void registrarReglaContable(ReglaContable reglaContable);

	public void actualizarReglaContable(ReglaContable reglaContable);

	public void eliminarReglaContable(ReglaContable reglaContable);
}
