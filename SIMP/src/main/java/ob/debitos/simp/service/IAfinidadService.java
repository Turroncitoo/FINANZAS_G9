package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.Afinidad;

public interface IAfinidadService extends IMantenibleService<Afinidad> 
{

	public List<Afinidad> buscarTodos();
	
	public List<Afinidad> buscarAfinidadPorLogo(String idLogo);
	
	public List<Afinidad> buscarAfinidadPorCodigoIdLogo(String codigo, String idLogo);
	
	public boolean existeAfinidad(String codigo, String idLogo);
	
	public void registrarAfinidad(Afinidad afinidad);
	
	public void actualizarAfinidad(Afinidad afinidad);
	
	public void eliminarAfinidad(Afinidad afinidad);
	
}
