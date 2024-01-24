package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.jquery.jstree.JsTreeAttribute;
import ob.debitos.simp.model.jquery.jstree.criterio.JsTreeFilter;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTP;
import ob.debitos.simp.model.respuestas.RespuestaConexionSFTP;

public interface IParametroSFTPService extends IMantenibleService<ParametrosSFTP>{
	public List<ParametrosSFTP> buscarTodos();
	public List<ParametrosSFTP> buscarTodosParaMantenimiento();
	public RespuestaConexionSFTP probarConexion();
	public List<JsTreeAttribute> verTreeSFTP(JsTreeFilter filtro);
	public void actualizarParametrosSFTP(ParametrosSFTP parametrosSFTP);
}
