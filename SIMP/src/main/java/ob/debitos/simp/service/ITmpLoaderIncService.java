package ob.debitos.simp.service;

public interface ITmpLoaderIncService {
	
	public void uploadFileBatch(String filePath, int size, int idInstitucion);
	
	public void uploadFileBySize(String filePath, int size, int idInstitucion);
	
    public void uploadFileByLine(String rutaArchivo, int idInstitucion);

    public void eliminarCargaTemporal();
    
}