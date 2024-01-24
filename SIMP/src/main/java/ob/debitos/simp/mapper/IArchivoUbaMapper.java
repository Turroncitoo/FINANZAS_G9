package ob.debitos.simp.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;
import ob.debitos.simp.model.proceso.ParametroValidaArchivo;

public interface IArchivoUbaMapper {
	public Date obtenerFechaArchivo(ParametroValidaArchivo parametroValidaArchivo);

	 /**
     * Ejecuta y devuelve los resultados de las evaluaciones de control de
     * header y trailer realizadas a los archivos UBA.
     * <p>
     * Este método delega la ejecución de evaluación al procedimiento almacenado
     * {@code CTRL_ARCHIVO_UBA}.
     * 
     * @param codigoPrograma
     *            representa el programa asociado al archivo UBA
     * @return el resultado de las evaluaciones de control
     */
    public List<LogControlProgramaDetalle> validarHeaderTrailerArchivo(@Param("codigoPrograma")String codigoPrograma, 
    		@Param("idInstitucion") Integer idInstitucion);
	
}
