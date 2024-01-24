package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.seguridad.SecRecurso;

public interface ISecRecursoService extends IMantenibleService<SecRecurso>
{
    public List<SecRecurso> buscarTodos();

    public void registrarRecurso(SecRecurso recurso);

    public void actualizarRecurso(SecRecurso recurso);

    public List<SecRecurso> buscarPorCodigoRecurso(String recurso);

	public void eliminarRecurso(SecRecurso recurso);

	public List<SecRecurso> buscarPorIdUsuario(String login);

	public boolean existeRecurso(String idRecurso);
	
	public List<SecRecurso> buscarAuditables();

}