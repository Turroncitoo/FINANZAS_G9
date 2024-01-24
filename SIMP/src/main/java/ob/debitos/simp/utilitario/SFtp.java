package ob.debitos.simp.utilitario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import ob.debitos.simp.model.jquery.jstree.JsTreeAttribute;
import ob.debitos.simp.model.jquery.jstree.JsTreeData;
import ob.debitos.simp.model.jquery.jstree.JsTreeObject;
import ob.debitos.simp.model.sftp.LsArchivosSFTP;
import ob.debitos.simp.service.excepcion.EjecucionManualException;

/**
 * Clase que gestiona la conexión a un servidor y la de sus ficheros.
 *
 */
public class SFtp
{

    private static final String PATHSEPARATOR = "/";
    // Variables de clase.
    private static String dirActual;
    private static JSch jsch = new JSch();

    private static Session session = null;
    private static Channel channel = null;
    private static ChannelSftp channelSftp = null;

    private static final Logger logger = LoggerFactory.getLogger(SFtp.class);

    public static void iniciar()
    {
        jsch = new JSch();
    }

    // Métodos:
    /**
     * Nos conecta a un servidor mediante usuario y contraseña.
     * 
     * @param server
     *            Servidor al que nos queremos conectar.
     * @param user
     *            Usuario para poder acceder.
     * @param pwd
     *            Contraseña para poder acceder.
     * @return True, si la conexión se estableció.<br>
     *         False, es caso contrario.
     */
    public static boolean conectar(String server, String user, String pwd, Integer port)
    {
        try
        {
            session = jsch.getSession(user, server, port);
            session.setPassword(pwd);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;

        } catch (JSchException e)
        {
            logger.error("{}", ConstantesGenerales.ERROR_SFTP_CONEXION.concat(e.getMessage()));
            return false;
        }
        return true;
    }

    /**
     * Cierra sesión del usuario y se desconecta del servidor.
     * 
     * @return True, si se ha desconectado correctamente.<br>
     *         False, en caso contrario.
     */
    public static boolean desconectar()
    {
        channel.disconnect();
        session.disconnect();
        logger.info("{}", ConstantesGenerales.INFO_SFTP_DESCONECTADO);
        return true;
    }

    /**
     * Cambia de directorio dentro de un servidor.
     * 
     * @param dirCarpeta
     *            Dirección completa de la carpeta a la cual queremos acceder
     *            dentro del servidor.
     * @return True, si ha cambiado de directorio.<br>
     *         False, en caso contrario.
     */
    public static boolean cambiarDirectorio(String dirCarpeta)
    {
        try
        {
            channelSftp.cd(dirCarpeta);
            return true;
        } catch (SftpException e)
        {
            logger.error("problema al cambiar de carpeta [{}] {}", dirCarpeta, e.getMessage());
            return false;
        }
    }

    /**
     * Buscar un archivo en un directorio remoto
     * 
     * @param nombreArchivo
     *            Nombre del archivo a buscar, el nombre puede contener patrones
     * @param dirCarpeta
     *            Ruta remota
     * @return nombre del archivo, null si no fue encontrado, si hay
     *         coincidencias devolverá la primera
     */
    public static String existeFicheroRemoto(String nombreArchivo, String dirCarpeta)
    {
        try
        {
            @SuppressWarnings("unchecked")
            final List<ChannelSftp.LsEntry> files = channelSftp.ls(dirCarpeta);
            ChannelSftp.LsEntry result = files.stream().filter(file -> !file.getAttrs().isDir() && file.getFilename().toLowerCase().matches(nombreArchivo.toLowerCase())).findFirst().orElse(null);
            logger.info("{}", result);
            return result != null ? result.getFilename() : null;
        } catch (SftpException e)
        {
            logger.error("problema al buscar fichero [{}] {}", dirCarpeta, e.getMessage());
            return null;
        }
    }

    /**
     * Descarga un fichero del servidor y lo almacena en una ruta específica.
     * 
     * @param nomFich
     *            Nombre del fichero a descargar.
     * @param destino
     *            Dirección donde se quiere almacenar el zip.
     * @return True, si la descarga y almacenamiento ha sido correcto.<br>
     *         False, en caso contrario.
     */
    public static boolean descargarFichero(String nomFich, String destino)
    {
        logger.info("Iniciando la descarga del Fichero {}", nomFich);
        boolean fichDescargado = false;
        try
        {
            File ftpFile = new File(nomFich);
            boolean t = true;
            File archivoDestino = null;
            File directorioDestino = Paths.get(destino).toFile();
            logger.info("{} {}", directorioDestino, !directorioDestino.exists());
            if (!directorioDestino.exists())
            {
                logger.info("Creando directorios");
                t = directorioDestino.mkdirs();
            }
            if (t)
            {
                archivoDestino = Paths.get(directorioDestino.getAbsolutePath() + File.separator + ftpFile.getName()).toFile();
                logger.info("{}", archivoDestino);
                InputStream inputStream = channelSftp.get(ftpFile.getName());
                if (Optional.ofNullable(inputStream).isPresent())
                {
                    FileOutputStream fileOutputStream = new FileOutputStream(archivoDestino);
                    IOUtils.copy(inputStream, fileOutputStream);
                    fileOutputStream.flush();
                    IOUtils.closeQuietly(fileOutputStream);
                    IOUtils.closeQuietly(inputStream);
                    fichDescargado = true;
                }
            }
        } catch (IOException e)
        {
            logger.error("Problema con el archivo: {} {}", destino, e.getMessage());
            fichDescargado = false;
            throw new EjecucionManualException("Problema con el archivo: " + destino + "." + e.getMessage());
        } catch (SftpException e)
        {
            logger.error("Problema con el SFTP: {} {}", destino, e.getMessage());
            fichDescargado = false;
            throw new EjecucionManualException("Problema con el SFTP: " + destino + "." + e.getMessage());
        }
        return fichDescargado;
    }

    /**
     * Borra un fichero de la ubicación actual del servidor.
     * 
     * @param nomFich
     *            Nombre del fichero ZIP a borrar.
     * @return True, si se ha borrado del servidor.<br>
     *         False, en caso contrario.
     */
    public static boolean borrarFicheroRemoto(String nomFich)
    {
        boolean borrado = false;
        try
        {
            channelSftp.rm(nomFich);
            borrado = true;
            logger.info("Se ha borrado el fichero: {}", nomFich);
        } catch (SftpException e)
        {
            logger.error("No ha sido posible borrar el fichero: {}", nomFich);
            borrado = false;
        }
        return borrado;
    }

    /**
     * Borra un fichero de la ubicación actual del cliente.
     * 
     * @param nomFich
     *            Nombre del fichero a borrar.
     * @return True, si se ha borrado del sistema.<br>
     *         False, en caso contrario.
     */
    public static boolean borrarFicheroLocal(String nomFich)
    {
        boolean borrado = false;
        try
        {
            borrado = Files.deleteIfExists(Paths.get(nomFich));
        } catch (IOException e)
        {
            logger.error("No se pudo borrar el fichero local. {}", e.getMessage());
        }
        return borrado;
    }

    public static boolean eliminarFicherosLocales(String dirCarpeta, String patronNombreArchivo)
    {
        List<Boolean> borrados = new ArrayList<>();
        File directorio = Paths.get(dirCarpeta).toFile();
        File[] files = directorio.listFiles((dir1, name) -> name.toLowerCase().matches(patronNombreArchivo.toLowerCase()));
        Arrays.asList(files).forEach(file -> {
            try
            {
                boolean borrado = Files.deleteIfExists(Paths.get(file.getPath()));
                borrados.add(borrado);

            } catch (IOException e)
            {
                logger.error("No se pudo borrar el fichero local. {}", e.getMessage());
            }
        });
        return files != null && borrados.stream().filter(s -> s.equals(true)).toArray().length == files.length;
    }

    /**
     * Sube un fichero al servidor en la ubicación actual.
     * 
     * @param pathFich
     *            Dirección del fichero, incluido el fichero. Ejm:
     *            /hola/queHay/pepe.txt
     * @param fich
     *            Nombre del fichero y su extensión. Ejm: pepe.txt
     * @return True, si ha subido de forma satisfactoria el fichero.<br>
     *         False, en caso contrario.
     */
    public static boolean subirFichero(String pathFich, String fich)
    {
        boolean fichSubido = false;
        File f = new File(pathFich);
        try (FileInputStream fis = new FileInputStream(f))
        {
            channelSftp.put(fis, fich);
            fichSubido = true;
        } catch (IOException | SftpException e)
        {
            logger.error(e.getMessage(), e);
            fichSubido = false;
        }
        return fichSubido;
    }

    public static boolean subirFicheros(String dirCarpeta, String patronNomreArchivo)
    {
        File directorio = Paths.get(dirCarpeta).toFile();
        File[] files = directorio.listFiles((dir1, name) -> name.toLowerCase().matches(patronNomreArchivo.toLowerCase()));
        List<Boolean> subidos = new ArrayList<>();
        Arrays.asList(files).forEach(file -> {
            try (FileInputStream fis = new FileInputStream(file))
            {
                channelSftp.put(fis, file.getName());
                subidos.add(true);
            } catch (IOException | SftpException e)
            {
                logger.error(e.getMessage(), e);
                subidos.add(false);
            }
        });
        return files != null && subidos.stream().filter(s -> s.equals(true)).toArray().length == files.length;
    }

    // Getters y setters:
    /**
     * Nos dice en que ubicación del servidor estamos actualmente.
     * 
     * @return Un String que contiene el path de ubicación actual.
     */
    public static String dameDirActual()
    {
        return dirActual;
    }

    public static String retornarEstado()
    {
        return "";
    }

    public static void copiarCarpeta(String origen, String destino)
    {
        recursiveFolderDownload(origen, destino);
    }

    @SuppressWarnings("unchecked")
    private static void recursiveFolderDownload(String sourcePath, String destinationPath)
    {
        Vector<ChannelSftp.LsEntry> fileAndFolderList = new Vector<>();
        try
        {
            if (sourcePath != null && !sourcePath.trim().equals(""))
            {
                fileAndFolderList = channelSftp.ls(sourcePath);
            }

            for (ChannelSftp.LsEntry item : fileAndFolderList)
            {

                if (!item.getAttrs().isDir())
                { // Check if it is a file (not a
                  // directory).
                    if (!(new File(destinationPath + PATHSEPARATOR + item.getFilename())).exists() || (item.getAttrs().getMTime() > Long.valueOf(new File(destinationPath + PATHSEPARATOR + item.getFilename()).lastModified() / (long) 1000).intValue()))
                    { // Download only if changed
                      // later.

                        File directorioDestino = Paths.get(destinationPath).toFile();
                        if (!directorioDestino.exists())
                        {
                            directorioDestino.mkdirs();
                        }

                        new File(destinationPath + PATHSEPARATOR + item.getFilename());
                        channelSftp.get(sourcePath + PATHSEPARATOR + item.getFilename(), destinationPath + PATHSEPARATOR + item.getFilename()); // Download
                                                                                                                                                // file
                                                                                                                                                // from
                                                                                                                                                // source
                                                                                                                                                // (source
                                                                                                                                                // filename,
                                                                                                                                                // destination
                                                                                                                                                // filename).

                    }
                } else if (!(".".equals(item.getFilename()) || "..".equals(item.getFilename())))
                {
                    new File(destinationPath + PATHSEPARATOR + item.getFilename()).mkdirs(); // Empty
                                                                                             // folder
                                                                                             // copy.
                    recursiveFolderDownload(sourcePath + PATHSEPARATOR + item.getFilename(), destinationPath + PATHSEPARATOR + item.getFilename()); // Enter
                                                                                                                                                    // found
                                                                                                                                                    // folder
                                                                                                                                                    // on
                                                                                                                                                    // server
                                                                                                                                                    // to
                                                                                                                                                    // read
                                                                                                                                                    // its
                                                                                                                                                    // contents
                                                                                                                                                    // and
                                                                                                                                                    // create
                                                                                                                                                    // locally.
                }
            }

        } catch (SftpException e1)
        {
            // TODO Auto-generated catch block
            throw new RuntimeException("No se pudo realizar la descarga desde el SFTP. " + e1.getMessage());
        } // Let list of folder content

    }

    /**
     * Covertir de bytes a kbytes.
     * 
     * El parametro debe estar en bytes y en tipo de dato long
     * 
     * @return conversion en kbytes
     */
    public static String convertirDeBytesAKBytes(long pesoEnBytes)
    {
        double pesoEnKBytes = (double) pesoEnBytes / 1024.0;
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(pesoEnKBytes);
    }

    /**
     * Revisar contenido en SFTP
     * 
     * @param dirCarpeta
     *            Ruta remota
     * @return lista LsArchivosSFTP
     */
    public static List<LsArchivosSFTP> revisarContenidoSFTP(String dirCarpeta)
    {
        try
        {
            List<LsArchivosSFTP> lista = new ArrayList<>();
            @SuppressWarnings("unchecked")
            final List<ChannelSftp.LsEntry> files = channelSftp.ls(dirCarpeta);
            files.forEach(archivos -> {
                if (!archivos.getFilename().equals(".") && !archivos.getFilename().equals(".."))
                {
                    String nombre = archivos.getFilename();
                    String suffix = nombre.substring(nombre.lastIndexOf(".") + 1);
                    lista.add(LsArchivosSFTP.builder().nombre(archivos.getFilename()).extension(suffix).peso(SFtp.convertirDeBytesAKBytes(archivos.getAttrs().getSize())) // conviertiendo
                                                                                                                                                                          // a
                                                                                                                                                                          // KB
                            .permisos(archivos.getAttrs().getPermissionsString()).build());
                }
            });
            return lista;
        } catch (SftpException e)
        {
            logger.error("problema al buscar fichero [{}] {}", dirCarpeta, e.getMessage());
            return null;
        }
    }

    /**
     * Revisar y llenar contenido en SFTP para JsTree
     * 
     * @param dirCarpeta
     *            Ruta remota
     * @return lista LsArchivosSFTP
     */
    public static List<JsTreeAttribute> revisarAgregarContenidoSFTP(String dirCarpeta)
    {
        List<LsArchivosSFTP> lista = SFtp.revisarContenidoSFTP(dirCarpeta);
        if (lista != null)
        {
            List<JsTreeAttribute> data = new ArrayList<JsTreeAttribute>();
            lista.forEach(l -> {
                JsTreeAttribute attr = JsTreeAttribute.builder().build();
                attr.setId(dirCarpeta.concat("/").concat(l.getNombre()));
                attr.setText(l.getNombre());

                // Si contiene un punto es un archivo, por ende no tiene hijos
                if (l.getNombre().contains("."))
                {
                    attr.setText(l.getNombre().concat(" ").concat("(").concat(l.getPeso()).concat(" ").concat("KB)"));
                    attr.setChildren(false);
                } else
                {
                    attr.setText(l.getNombre().concat(" ").concat("(").concat(l.getPermisos()).concat(")"));
                    attr.setChildren(true);
                }

                String extension = l.getExtension().toLowerCase();
                switch (extension)
                {
                case "xls":
                    attr.setIcon("fa fa-file-excel-o jstree-color-verde");
                    break;
                case "xlsx":
                    attr.setIcon("fa fa-file-excel-o jstree-color-verde");
                    break;
                case "pdf":
                    attr.setIcon("fa fa-file-pdf-o jstree-color-rojo");
                    break;
                case "dat":
                    attr.setIcon("fa fa-file-text");
                    break;
                case "rtf":
                    attr.setIcon("fa fa-file-word-o jstree-color-azul");
                    break;
                case "docx":
                    attr.setIcon("fa fa-file-word-o jstree-color-azul");
                    break;
                case "doc":
                    attr.setIcon("fa fa-file-word-o jstree-color-azul");
                    break;
                case "txt":
                    attr.setIcon("fa fa-file-text");
                    break;
                case "log":
                    attr.setIcon("fa fa-file-text");
                    break;
                default:
                    attr.setIcon(null);
                    break;
                }
                data.add(attr);
            });
            return data;
        } else
        {
            logger.error("Problema en metodo revisarContenidoSFTP");
            return null;
        }
    }
}