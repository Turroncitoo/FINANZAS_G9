package ob.debitos.simp.utilitario;

import java.io.File;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchivoUtil {
	private static final Logger logger = LoggerFactory.getLogger(ArchivoUtil.class);

	private ArchivoUtil()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }
	
	public static void crearDirectorio(File file){
		if (!file.exists()) {
			if (file.mkdirs()) {
				logger.info("Directorio creado");
			} else {
				logger.info("Error al crear directorio");
			}
		}
	}
	
	/**
	 * Identifica si un archivo existe en un directorio
	 * @param nombreArchivo Nombre del archivo a buscar, el nombre puede contener patrones 
	 * @param dirCarpeta Directorio a buscar
	 * @return indicador de existencia 
	 */
	public static boolean existeFicheroLocal(String nombreArchivo, String dirCarpeta){
		File directorio = Paths.get(dirCarpeta).toFile();
		File[] files = directorio.listFiles((dir1, name) -> name.toLowerCase().matches(nombreArchivo.toLowerCase()));
		return files != null && files.length > 0;		
	}
	
	/**
	 * Identifica un archivo en un directorio
	 * @param nombreArchivo Nombre del archivo a buscar, el nombre puede contener patrones 
	 * @param dirCarpeta Directorio a buscar
	 * @return nombre del archivo, null si no fue encontrado, si hay coincidencias devolverá la primera 
	 */
	public static String buscarFicheroLocal(String nombreArchivo, String dirCarpeta){
		File directorio = Paths.get(dirCarpeta).toFile();
		File[] files = directorio.listFiles((dir1, name) -> name.toLowerCase().matches(nombreArchivo.toLowerCase()));
		if(files != null && files.length > 0){
			return files[0].getName();
		}
		return null;	
	}
}
