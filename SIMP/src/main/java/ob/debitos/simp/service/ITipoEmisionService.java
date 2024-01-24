package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.TipoEmision;

public interface ITipoEmisionService extends IMantenibleService<TipoEmision> {

	public List<TipoEmision> buscarTodos();
	
	public List<TipoEmision> buscarTipoEmisionPorCodigo(String codigo);
	
	public boolean existeTipoEmision(String codigo);
	
	public void registrarTipoEmision(TipoEmision tipoEmision);
	
	public void actualizarTipoEmision(TipoEmision tipoEmision);
	
	public void eliminarTipoEmision(TipoEmision tipoEmision);
	
}
