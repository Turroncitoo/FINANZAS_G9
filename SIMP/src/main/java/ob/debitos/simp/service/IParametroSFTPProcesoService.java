package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ParametrosSFTPProceso;

public interface IParametroSFTPProcesoService extends IMantenibleService<ParametrosSFTPProceso> {
	public List<ParametrosSFTPProceso> buscarTodos();
	public List<ParametrosSFTPProceso> buscarParametroSFTPProceso(String codigo);
	public void registrarParametroSFTPProceso(ParametrosSFTPProceso parametroSFTP);
	public void actualizarParametroSFTPProceso(ParametrosSFTPProceso parametroSFTP);
	public void eliminarParametroSFTPProceso(ParametrosSFTPProceso parametroSFTP);
	public boolean existeParametroSFTPProceso(String codigo);
}
