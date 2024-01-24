package ob.debitos.simp.service;

public interface ITmpLoaderService {
	
	public void eliminarCargaTemporal();
	
	public void elimianrCargaTemportalPorInstitucion(Integer idInstitucion);
	
	public void uploadFileBatch(String filePath, int size, int idInstitucion);
	
	public void uploadFileBySize(String filePath, int size, int idInstitucion);
	
	public void uploadFileByLine(String filePath, int idInstitucion);
	
	
}
