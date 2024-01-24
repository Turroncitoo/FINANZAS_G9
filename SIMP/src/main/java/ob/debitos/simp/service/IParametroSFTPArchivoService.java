package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ParametrosSFTPArchivo;

public interface IParametroSFTPArchivoService extends IMantenibleService<ParametrosSFTPArchivo> {
	public List<ParametrosSFTPArchivo> buscarTodos();
	public List<ParametrosSFTPArchivo> buscarPorIdArchivo(String idArchivo);
	public void registrarParametroSFTPArchivo(ParametrosSFTPArchivo parametroSFTP);
	public void actualizarParametroSFTPArchivo(ParametrosSFTPArchivo parametroSFTP);
	public void eliminarParametroSFTPArchivo(ParametrosSFTPArchivo parametrosSFTP);
	public boolean existeParametroSFTPArchivo(String idParametroSFTP);
}
