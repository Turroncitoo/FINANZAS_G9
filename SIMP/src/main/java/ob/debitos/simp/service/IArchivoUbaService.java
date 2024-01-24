package ob.debitos.simp.service;

import java.util.Date;
import java.util.List;

import ob.debitos.simp.model.mantenimiento.ParametrosSFTPArchivo;
import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;
import ob.debitos.simp.model.proceso.Programa;

public interface IArchivoUbaService
{

    public int obtenerArchivoUba(Programa programa, Integer idInstitucion);

    public int realizarBackup(Programa programa);

    public int enviarArchivoUba(Programa programa, Integer idInstitucion);

    public Date obtenerFechaArchivo(String ruta, String tipoArchivo);

    public List<LogControlProgramaDetalle> cargarArchivoUBA(Programa programa, Integer idInstitucion);

    /**
     * Ejecuta y devuelve los resultados de las evaluaciones de control de
     * header y trailer realizadas a los archivos UBA.
     * <p>
     * El método lanza la excepción {@code HeaderTrailerExcepcion} si el header
     * o trailer no son válidos.
     * 
     * @param codigoPrograma
     *            representa el programa asociado al archivo UBA
     * @return el resultado de las evaluaciones de control
     */
    public List<LogControlProgramaDetalle> validarHeaderTrailerArchivo(Programa programa, Integer idInstitucion);

    /**
     * Para descarga de archivos obtiene la ruta local Para subida obtiene el
     * ruta remota
     * 
     * @param parametroSFTPArchivo
     * @param fechaProceso
     * @param idInstitucion
     * @return
     */
    public String obtenerRutaDestinoArchivo(ParametrosSFTPArchivo parametroSFTPArchivo, Date fechaProceso, Integer idInstitucion);

    /**
     * Para subida de archivos obtiene la ruta local origen Para descarga de
     * archivos obtiene la ruta remota
     * 
     * @param parametroSFTPArchivo
     * @param fechaProceso
     * @param idInstitucion
     * @return
     */
    public String obtenerRutaOrigenArchivo(ParametrosSFTPArchivo parametroSFTPArchivo, Date fechaProceso, Integer idInstitucion);

}
