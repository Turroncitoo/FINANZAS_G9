package ob.debitos.simp.utilitario;


public class Constantes {

public static final String NUM_FMT_DOS_CIFRAS = "%02d";
    
    public static final String FTP_HOST = "sftp.host";
    public static final String FTP_USUARIO = "sftp.usuario";
    public static final String FTP_CONTRASENIA = "sftp.password";
    public static final String FTP_PUERTO = "sftp.puerto";
    public static final CharSequence FTP_PATRON_ITERA_ARCHIVO = "[ii]";
        
    public static final String FTP_PREPAGO_DESCARGA_NUMERO_ARCHIVOS = "sftp.prepago.descarga.numero-archivos";
    public static final String FTP_PREPAGO_DESCARGA_DIRECTORIO_REMOTO = "sftp.prepago.descarga.directorio.origen";
    public static final String FTP_PREPAGO_DESCARGA_DIRECTORIO_LOCAL = "sftp.prepago.descarga.directorio.destino";
    public static final String FTP_PREPAGO_DESCARGA_PATRON_ARCHIVO = "sftp.prepago.descarga.archivo"+ FTP_PATRON_ITERA_ARCHIVO + ".origen";
    public static final String FTP_PREPAGO_DESCARGA_INDICADOR_BORRADO = "sftp.debito.descarga.borrado";
    
    public static final String FTP_PREPAGO_SUBIDA_NUMERO_ARCHIVOS = "sftp.prepago.subida.numero-archivos";
    public static final String FTP_PREPAGO_SUBIDA_DIRECTORIO_REMOTO = "sftp.prepago.subida.directorio.destino";
    public static final String FTP_PREPAGO_SUBIDA_DIRECTORIO_LOCAL = "sftp.prepago.subida.directorio.origen";
    public static final String FTP_PREPAGO_SUBIDA_PATRON_ARCHIVO = "sftp.prepago.subida.archivo"+ FTP_PATRON_ITERA_ARCHIVO + ".destino";
    public static final String FTP_PREPAGO_SUBIDA_INDICADOR_BORRADO = "sftp.prepago.subida.borrado";
     
    public static final String FTP_DEBITO_DESCARGA_NUMERO_ARCHIVOS = "sftp.debito.descarga.numero-archivos";
    public static final String FTP_DEBITO_DESCARGA_DIRECTORIO_REMOTO = "sftp.debito.descarga.directorio.origen";
    public static final String FTP_DEBITO_DESCARGA_DIRECTORIO_LOCAL = "sftp.debito.descarga.directorio.destino";
    public static final String FTP_DEBITO_DESCARGA_PATRON_ARCHIVO = "sftp.debito.descarga.archivo"+ FTP_PATRON_ITERA_ARCHIVO + ".origen";
    public static final String FTP_DEBITO_VALIDA_HEADER = "sftp.debito.descarga.valida-header";
    public static final String FTP_DEBITO_DESCARGA_INDICADOR_BORRADO = "sftp.prepago.subida.borrado";
    
    // no se usan
    public static final String FTP_PREPAGO_DESCARGA_HABILITADO = "sftp.prepago.descarga.habilitado";
    public static final String FTP_PREPAGO_SUBIDA_HABILITADO = "sftp.prepago.subida.habilitado";
    public static final String FTP_DEBITO_DESCARGA_HABILITADO = "sftp.debito.descarga.habilitado";
    
    public static final String CREATED = "201";
    public static final String BAD_REQUEST = "400";
    

    
    public static final String FTP_DEBITO_DESCARGA_BORRA = "sftp.debito.descarga.borra-fichero";

    public static final Integer ACTIVO = 1;

    public static final String CORRECTO = null;

    public static final String EXITO = "Éxito";

    public static final String FALLO = "Fallo";

    public static final String GUION = " - ";

   
  
    public static final String NUM_FMT_SIN_DECIMALES = "%d";
    public static final String NUM_FMT_DOS_CIFRAS_DECIMALES = "%1$,.2f";
    
    public static final String STR_FMT_FECHA_YYYYMMDD = "%1$tY%1$tm%1$td";
    
    public static final String NUM_FMT_DOS_CIFRAS_DECIMAL_DF = "#####0.00";
    public static final String PUNTO_DECIMAL = ".";
    public static final String COMA_DECIMAL = ",";

    public static final String PMG_PATRON_BIN = "[BIN]";
    public static final String PMG_PATRON_ID_INSTITUCION = "[BB]";
    public static final String PMG_PATRON_RUTA_FECHA = "[RUTA_FECHA]";
    public static final String PMG_PATRON_FECHA = "[FECHA]";
    public static final String PMG_PATRON_ID_INSTITUCION_VISANET = "[INST_VN]";
    public static final String PMG_PATRON_MMDD = "[MMdd]";
    public static final String PATTERN_ONE_NUMBER_1_9 = "[1-9]";

    public static final String PMG_RUTA_COMPENSACION = "pmg.ruta.compensacion";
    public static final String PMG_ID_INSTITUCION = "pmg.id-institucion";
    public static final String PROP_NOMBRE_ARCHIVO_CONTABLE = "nombreArchivoContable";
    public static final String CARACTER_LINEA_INICIO_ARCHIVO = "A";
    public static final String CARACTER_LINEA_FINAL_ARCHIVO = "Z";
    
    public static final String FTP_BACKUP_HABILITADO = "sftp.backup.habilitado";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    
    // submodulos
    public static final String SEND = "SEND";
    public static final String LOAD = "LOAD";
    public static final String BCKP = "BCKP";
    
}

