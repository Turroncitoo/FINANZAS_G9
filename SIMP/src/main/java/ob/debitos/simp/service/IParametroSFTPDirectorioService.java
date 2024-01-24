package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ParametrosSFTPDirectorio;

public interface IParametroSFTPDirectorioService extends IMantenibleService<ParametrosSFTPDirectorio>{
	public List<ParametrosSFTPDirectorio> buscarTodos();
	public List<ParametrosSFTPDirectorio> buscarParametroSFTPDirectorio(String codigoProceso, String tipo);
	public void actualizarParametrosSFTPDirectorio(ParametrosSFTPDirectorio parametrosSFTP);
	public void registrarParametrosSFTPDirectorio(ParametrosSFTPDirectorio parametrosSFTP);
	public void eliminarParametrosSFTPDirectorio(ParametrosSFTPDirectorio parametrosSFTP);
	public boolean existeParametroSFTPDirectorio(String codigoProceso, String tipo);

}
