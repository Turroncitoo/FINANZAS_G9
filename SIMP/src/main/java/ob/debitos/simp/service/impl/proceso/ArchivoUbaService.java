
package ob.debitos.simp.service.impl.proceso;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ob.debitos.simp.utilitario.*;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IArchivoUbaMapper;
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTP;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPArchivo;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPDirectorio;
import ob.debitos.simp.model.parametro.ParametroCarga;
import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;
import ob.debitos.simp.model.proceso.ParametroValidaArchivo;
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.service.IArchivoUbaService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IParametroSFTPArchivoService;
import ob.debitos.simp.service.IParametroSFTPDirectorioService;
import ob.debitos.simp.service.IParametroSFTPService;
import ob.debitos.simp.service.ITmpLoaderIncService;
import ob.debitos.simp.service.ITmpLoaderService;
import ob.debitos.simp.service.excepcion.HeaderTrailerExcepcion;

@Service
public class ArchivoUbaService implements IArchivoUbaService
{

    private @Autowired IArchivoUbaMapper archivoUbaMapper;
    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired IParametroSFTPService parametroSFTPService;
    private @Autowired IParametroSFTPArchivoService parametroSFTPArchivoService;
    private @Autowired IParametroSFTPDirectorioService parametroSFTPDirectorioService;
    private @Autowired ITmpLoaderService tmpLoaderService;
    private @Autowired ITmpLoaderIncService tmpLoaderIncService;
    private @Autowired IInstitucionService institucionService;
    private @Autowired Logger logger;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LogControlProgramaDetalle> cargarArchivoUBA(Programa programa, Integer idInstitucion)
    {
        Date fechaProceso = this.parametroGeneralService.buscarFechaProceso();
        String codigoArchivo = programa.getArchivo();
        List<ParametrosSFTPArchivo> archivos = this.parametroSFTPArchivoService.buscarPorIdArchivo(codigoArchivo);
        ParametrosSFTPArchivo archivo = archivos.get(0);
        String directorioDestino = this.parametroSFTPDirectorioService.buscarParametroSFTPDirectorio(archivo.getCodigoProceso(), archivo.getTipoOperacion()).get(0).getDirectorioDestino();
        logger.info("CodigoArchivo {} ", codigoArchivo);
        String nombreArchivo = archivo.getOrigen() + "." + archivo.getExtensionOrigen();
        logger.info("Nombre del archivo: {} ", nombreArchivo);
        ParametroCarga parametroCarga = ParametroCarga.builder().codigoInstitucion(idInstitucion).directorioDestino(directorioDestino).nombreArchivoUBA(nombreArchivo).fechaProceso(fechaProceso).longitudArchivoUBA(archivo.getNumeroBytes()).cargaIncremental(archivo.getCargaIncremental()).build();
        this.cargarTablaTmpArchivoUba(parametroCarga, programa);
        if (archivo.getValidaHeader() == Constantes.ACTIVO)
        {
            return this.validarHeaderTrailerArchivo(programa, idInstitucion);
        } else
        {
            return new ArrayList<>();
        }
    }

    @Override
    public int obtenerArchivoUba(Programa programa, Integer idInstitucion)
    {
        boolean resultado = false;
        ParametrosSFTP parametrosSFTP = this.parametroSFTPService.buscarTodos().get(0);
        String host = parametrosSFTP.getHost();
        String usuario = parametrosSFTP.getUsuario();
        String contrasenia = parametrosSFTP.getContrasenia();
        Integer puerto = parametrosSFTP.getPuerto();

        String codigoArchivo = programa.getArchivo();
        List<ParametrosSFTPArchivo> archivos = this.parametroSFTPArchivoService.buscarPorIdArchivo(codigoArchivo);
        Validate.notEmpty(archivos, String.format(ConstantesExcepciones.ARCHIVO_NO_ENCONTRADO, codigoArchivo));
        ParametrosSFTPArchivo archivo = archivos.get(0);
        Integer habilitado = archivo.getHabilitado();
        if (habilitado == Constantes.ACTIVO)
        {
            ParametrosSFTPDirectorio parametroSFTPDirectorio = this.parametroSFTPDirectorioService.buscarParametroSFTPDirectorio(archivo.getCodigoProceso(), archivo.getTipoOperacion()).get(0);
            String directorioRemoto = parametroSFTPDirectorio.getDirectorioOrigen();
            String directorioLocal = parametroSFTPDirectorio.getDirectorioDestino();
            Integer borraFichero = parametroSFTPDirectorio.getBorraFichero();
            Date fechaProcesoSIMP = parametroGeneralService.buscarFechaProceso();
            logger.info("Fecha proceso {}", fechaProcesoSIMP);
            logger.info("-- Iniciando Proceso de Descarga de Archivos --");
            String archivoRemoto = archivo.getOrigen() + "." + archivo.getExtensionOrigen();
            archivoRemoto = reemplazarPatrones(archivoRemoto, idInstitucion);
            logger.info("--- Descarga de archivo -> {}", archivoRemoto);
            String directorioAbuscar = reemplazarPatrones(directorioLocal, fechaProcesoSIMP);
            logger.info("Nombre {} dir: {}", archivoRemoto, directorioAbuscar);
            /*
             * if (ArchivoUtil.existeFicheroLocal(archivoRemoto,
             * directorioAbuscar)) { logger.info(" {} ya fue descargado.",
             * archivoRemoto); resultado = true; } else {
             */
            boolean resultadoConexion = SFtp.conectar(host, usuario, contrasenia, puerto);
            logger.info("Conexión al SFTP : {}", resultadoConexion);
            Validate.isTrue(resultadoConexion, ConstantesExcepciones.ERROR_CONEXION_SFTP);
            String archivoAdescargar = SFtp.existeFicheroRemoto(archivoRemoto, directorioRemoto);
            logger.info("Archivo a descargar del SFTP : {}", archivoAdescargar);
            if (archivoAdescargar == null)
            {
                logger.info("El archivo no existe en el SFTP");
                logger.info("Revisando si el archivo a descargar se puede omitir");
                logger.info("Los programas de codigo grupo PREPAGO se pueden omitir, el codigo de grupo es: {}", programa.getCodigoGrupo());
                if (programa.getCodigoGrupo().equals("PREPAGO") || programa.getCodigoGrupo().equals("PREPAGO1"))
                {
                    logger.info("Programa omitido!");
                    SFtp.desconectar();
                    return -5;
                } else
                {
                    logger.info("El programa no se puede omitir");
                }
            }
            Validate.notNull(archivoAdescargar, String.format(ConstantesExcepciones.ERROR_DESCARGA_ARCHIVO, archivoRemoto));
            archivoRemoto = archivoAdescargar;
            SFtp.cambiarDirectorio(directorioRemoto);
            logger.info("fechaproceso != null {} {}", directorioLocal, fechaProcesoSIMP);
            directorioLocal = reemplazarPatrones(directorioLocal, fechaProcesoSIMP, idInstitucion);
            resultado = SFtp.descargarFichero(archivoRemoto, directorioLocal);
            if (resultado && borraFichero == Constantes.ACTIVO)
            {
                resultado = SFtp.borrarFicheroRemoto(archivoRemoto);
            }
            SFtp.retornarEstado();
            SFtp.desconectar();
            /* } */
        }
        return resultado ? 0 : 1;
    }

    private String reemplazarPatrones(String nombreArchivo, Date fechaProceso)
    {
        Integer idInstitucion = this.parametroGeneralService.buscarCodigoInstitucion();
        Institucion institucion = this.institucionService.buscarPorCodigoInstitucion(idInstitucion).get(0);
        return nombreArchivo.replace(Constantes.PMG_PATRON_ID_INSTITUCION, StringUtils.leftPad(String.valueOf(idInstitucion), 2, "0")).replace(Constantes.PMG_PATRON_BIN, this.parametroGeneralService.buscarBIN()).replace(Constantes.PMG_PATRON_RUTA_FECHA, Util.formarRutaCarpetaPorFecha(fechaProceso))
                .replace(Constantes.PMG_PATRON_MMDD, DatesUtils.obtenerFechaEnFormato(this.parametroGeneralService.buscarFechaProceso(), DatesUtils.PATTERN_MMdd)).replace(Constantes.PMG_PATRON_ID_INSTITUCION_VISANET, institucion.getCodigoVisanet() == null ? "" : institucion.getCodigoVisanet());
    }

    private String reemplazarPatrones(String nombreArchivo, Integer idInstitucion)
    {
        Institucion institucion = this.institucionService.buscarPorCodigoInstitucion(idInstitucion).get(0);
        return nombreArchivo.replace(Constantes.PMG_PATRON_ID_INSTITUCION, StringUtils.leftPad(String.valueOf(idInstitucion), 2, "0")).replace(Constantes.PMG_PATRON_BIN, this.parametroGeneralService.buscarBIN())
                .replace(Constantes.PMG_PATRON_MMDD, DatesUtils.obtenerFechaEnFormato(this.parametroGeneralService.buscarFechaProceso(), DatesUtils.PATTERN_MMdd)).replace(Constantes.PMG_PATRON_ID_INSTITUCION_VISANET, institucion.getCodigoVisanet() == null ? "" : institucion.getCodigoVisanet());
    }

    private String reemplazarPatrones(String nombreArchivo, Date fechaProceso, Integer idInstitucion)
    {
        Institucion institucion = this.institucionService.buscarPorCodigoInstitucion(idInstitucion).get(0);
        return nombreArchivo.replace(Constantes.PMG_PATRON_ID_INSTITUCION, StringUtils.leftPad(String.valueOf(idInstitucion), 2, "0")).replace(Constantes.PMG_PATRON_BIN, this.parametroGeneralService.buscarBIN()).replace(Constantes.PMG_PATRON_RUTA_FECHA, Util.formarRutaCarpetaPorFecha(fechaProceso))
                .replace(Constantes.PMG_PATRON_MMDD, DatesUtils.obtenerFechaEnFormato(this.parametroGeneralService.buscarFechaProceso(), DatesUtils.PATTERN_MMdd)).replace(Constantes.PMG_PATRON_RUTA_FECHA, Util.formarRutaCarpetaPorFecha(fechaProceso));
    }

    public String obtenerRutaDestinoArchivo(ParametrosSFTPArchivo parametroSFTPArchivo, Date fechaProceso, Integer idInstitucion)
    {
        ParametrosSFTPDirectorio parametroSFTPDirectorio = this.parametroSFTPDirectorioService.buscarParametroSFTPDirectorio(parametroSFTPArchivo.getCodigoProceso(), parametroSFTPArchivo.getTipoOperacion()).get(0);
        String directorioDestino = parametroSFTPDirectorio.getDirectorioDestino();
        String nombreArchivo = parametroSFTPArchivo.getOrigen() + "." + parametroSFTPArchivo.getExtensionOrigen();
        nombreArchivo = reemplazarPatrones(nombreArchivo, idInstitucion);
        directorioDestino = reemplazarPatrones(directorioDestino, fechaProceso, idInstitucion);
        return StringsUtils.concatenarCadena(directorioDestino, File.separator, nombreArchivo);
    }

    public String obtenerRutaOrigenArchivo(ParametrosSFTPArchivo parametroSFTPArchivo, Date fechaProceso, Integer idInstitucion)
    {
        ParametrosSFTPDirectorio parametroSFTPDirectorio = this.parametroSFTPDirectorioService.buscarParametroSFTPDirectorio(parametroSFTPArchivo.getCodigoProceso(), parametroSFTPArchivo.getTipoOperacion()).get(0);
        String directorioOrigen = parametroSFTPDirectorio.getDirectorioOrigen();
        String nombreArchivo = parametroSFTPArchivo.getOrigen() + "." + parametroSFTPArchivo.getExtensionOrigen();
        nombreArchivo = reemplazarPatrones(nombreArchivo, idInstitucion);
        directorioOrigen = reemplazarPatrones(directorioOrigen, fechaProceso, idInstitucion);
        return StringsUtils.concatenarCadena(directorioOrigen, File.separator, nombreArchivo);
    }

    @Override
    public int enviarArchivoUba(Programa programa, Integer idInstitucion)
    {
        Date fechaProcesoSIMP = parametroGeneralService.buscarFechaProceso();
        ParametrosSFTP parametrosSFTP = this.parametroSFTPService.buscarTodos().get(0);
        String host = parametrosSFTP.getHost();
        String usuario = parametrosSFTP.getUsuario();
        String contrasenia = parametrosSFTP.getContrasenia();
        Integer puerto = parametrosSFTP.getPuerto();
        String codigoArchivo = programa.getArchivo();
        List<ParametrosSFTPArchivo> archivos = this.parametroSFTPArchivoService.buscarPorIdArchivo(codigoArchivo);
        Validate.notEmpty(archivos, String.format(ConstantesExcepciones.ARCHIVO_NO_ENCONTRADO, codigoArchivo));
        ParametrosSFTPArchivo archivo = archivos.get(0);
        logger.info("Mensaje 4: {}", archivo);
        ParametrosSFTPDirectorio parametroSFTPDirectorio = this.parametroSFTPDirectorioService.buscarParametroSFTPDirectorio(archivo.getCodigoProceso(), archivo.getTipoOperacion()).get(0);
        String directorioRemoto = parametroSFTPDirectorio.getDirectorioDestino();
        String directorioLocal = parametroSFTPDirectorio.getDirectorioOrigen();
        String archivoLocal;
        boolean resultadoConexion = SFtp.conectar(host, usuario, contrasenia, puerto);
        logger.info("Conexión al SFTP : {}", resultadoConexion);
        logger.info("Directorio remoto : {}", directorioRemoto);
        Validate.isTrue(resultadoConexion, ConstantesExcepciones.ERROR_CONEXION_SFTP);
        boolean resultadoCambioDir = SFtp.cambiarDirectorio(directorioRemoto);
        Validate.isTrue(resultadoCambioDir, String.format(ConstantesExcepciones.ERROR_CAMBIO_DIRECTORIO_REMOTO, directorioRemoto));
        archivoLocal = archivo.getOrigen() + "." + archivo.getExtensionOrigen();
        archivoLocal = reemplazarPatrones(archivoLocal, idInstitucion);
        directorioLocal = reemplazarPatrones(directorioLocal, fechaProcesoSIMP, idInstitucion);
        logger.info("Directorio local : {}", directorioLocal);
        logger.info("Archivo local : {}", archivoLocal);
        boolean resultadoSubida = SFtp.subirFicheros(directorioLocal, archivoLocal);
        Validate.isTrue(resultadoSubida, ConstantesExcepciones.ERROR_SUBIDA_ARCHIVO);
        if (resultadoSubida && parametroSFTPDirectorio.getBorraFichero() == 1)
        {
            SFtp.eliminarFicherosLocales(directorioLocal, archivoLocal);
        }
        logger.info("Resultado de subida al SFTP : {}", resultadoSubida);
        SFtp.retornarEstado();
        SFtp.desconectar();
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<LogControlProgramaDetalle> validarHeaderTrailerArchivo(Programa programa, Integer idInstitucion)
    {
        if (!programa.isEjecucionDetallada())
        {
            return new ArrayList<>();
        }
        List<LogControlProgramaDetalle> logControlProgramaDetalles = this.archivoUbaMapper.validarHeaderTrailerArchivo(programa.getCodigoPrograma(), idInstitucion);
        logger.info("{}", logControlProgramaDetalles);
        if (!Util.verificarHeaderTrailerValido(logControlProgramaDetalles))
        {
            logger.info("Header trailer inválido");
            throw new HeaderTrailerExcepcion("El Header/trailer del archivo es inválido.", logControlProgramaDetalles);
        }
        logger.info("Return logdetalle");
        return logControlProgramaDetalles;
    }

    /**
     * En elaboración
     *
     */
    @Override
    public Date obtenerFechaArchivo(String tipoArchivo, String cabecera)
    {
        ParametroValidaArchivo parametroValidaArchivo = new ParametroValidaArchivo(tipoArchivo, cabecera, null);
        archivoUbaMapper.obtenerFechaArchivo(parametroValidaArchivo);
        return parametroValidaArchivo.getFechaArchivo();

    }

    @Override
    public int realizarBackup(Programa programa)
    {
        logger.info("--- Iniciando el Proceso de BackUp ---");
        ParametrosSFTP parametrosSFTP = this.parametroSFTPService.buscarTodos().get(0);
        String host = parametrosSFTP.getHost();
        String usuario = parametrosSFTP.getUsuario();
        String contrasenia = parametrosSFTP.getContrasenia();
        Integer puerto = parametrosSFTP.getPuerto();

        String[] datos = programa.getArchivo().split("-");
        logger.info(programa.getArchivo());
        String codigoProceso = datos[0];
        String tipoOperacion = datos[1];
        ParametrosSFTPDirectorio parametroSFTPDirectorio = this.parametroSFTPDirectorioService.buscarParametroSFTPDirectorio(codigoProceso, tipoOperacion).get(0);

        String directorioRemoto = parametroSFTPDirectorio.getDirectorioOrigen();
        String directorioLocal = parametroSFTPDirectorio.getDirectorioDestino();
        directorioLocal = reemplazarPatrones(directorioLocal, this.parametroGeneralService.buscarFechaProceso());

        boolean resultadoConexion = SFtp.conectar(host, usuario, contrasenia, puerto);
        logger.info("Conexión al SFTP : {}", resultadoConexion);
        Validate.isTrue(resultadoConexion, ConstantesExcepciones.ERROR_CONEXION_SFTP);
        SFtp.cambiarDirectorio(directorioRemoto);
        File f = new File(directorioLocal + File.separator + "Backup");
        logger.info("Creando  carpeta: {}", directorioLocal + File.separator + "Backup");
        SFtp.copiarCarpeta(directorioRemoto, f.getAbsolutePath());

        SFtp.retornarEstado();
        SFtp.desconectar();
        logger.info("-- Proceso de Backup Finalizado");
        return 1;
    }

    private void cargarTablaTmpArchivoUba(ParametroCarga parametroCarga, Programa programa)
    {
        Date fechaProceso = parametroCarga.getFechaProceso();
        String rutaPorFecha = Util.formarRutaCarpetaPorFecha(fechaProceso);
        String directorioDestino = parametroCarga.getDirectorioDestino();
        directorioDestino = directorioDestino.replace("[RUTA_FECHA]", rutaPorFecha);
        String nombreArchivo = parametroCarga.getNombreArchivoUBA();
        nombreArchivo = this.reemplazarPatrones(nombreArchivo, parametroCarga.getFechaProceso(), parametroCarga.getCodigoInstitucion());
        logger.info("Directorio {}, archivo {}", directorioDestino, nombreArchivo);
        String archivoDescargado = ArchivoUtil.buscarFicheroLocal(nombreArchivo, directorioDestino);
        logger.info("Archivo {}", archivoDescargado);
        if (archivoDescargado == null)
        {
            logger.info("El archivo no existe en el SFTP");
            logger.info("Revisando si el archivo a descargar se puede omitir");
            logger.info("Los programas de codigo grupo PREPAGO se pueden omitir, el codigo de grupo es: {}", programa.getCodigoGrupo());
            if (programa.getCodigoGrupo().equals("PREPAGO"))
            {
                logger.info("Programa omitido!");
                SFtp.desconectar();
                return;
            } else
            {
                logger.info("El programa no se puede omitir");
            }
        }
        Validate.notNull(archivoDescargado, String.format(ConstantesExcepciones.RUTA_ARCHIVO_NO_ENCONTRADO, StringsUtils.concatenarCadena(directorioDestino, File.separator, nombreArchivo)));
        nombreArchivo = archivoDescargado;
        if (parametroCarga.getCargaIncremental() == Constantes.ACTIVO)
        {
            this.tmpLoaderIncService.eliminarCargaTemporal();
            this.tmpLoaderIncService.uploadFileBatch(StringsUtils.concatenarCadena(directorioDestino, File.separator, nombreArchivo), parametroCarga.getLongitudArchivoUBA(), parametroCarga.getCodigoInstitucion());
        } else
        {
            this.tmpLoaderService.eliminarCargaTemporal();
            this.tmpLoaderService.uploadFileBatch(StringsUtils.concatenarCadena(directorioDestino, File.separator, nombreArchivo), parametroCarga.getLongitudArchivoUBA(), parametroCarga.getCodigoInstitucion());
        }
    }

}
