package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.seguridad.Perfil;

public interface IPerfilService extends IMantenibleService<Perfil>
{
    public List<Perfil> buscarTodos();
    
    public List<Perfil> buscarPorCodigoPerfil(String idPerfil);
    
    public List<Perfil> buscarRecursosPorIdPerfil(String idPerfil);
    
    public boolean existePerfil(String idPerfil);

    public void registrarPerfil(Perfil perfil);

    public void actualizarPerfil(Perfil perfil);

    public void eliminarPerfil(Perfil perfil);
}