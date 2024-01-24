package ob.debitos.simp.service;

import ob.debitos.simp.model.seguridad.Perfil;
import ob.debitos.simp.model.seguridad.PerfilRecurso;

public interface IPerfilRecursoService extends IMantenibleService<PerfilRecurso> 
{
	
	public void asignarPermisos(Perfil perfil);
}