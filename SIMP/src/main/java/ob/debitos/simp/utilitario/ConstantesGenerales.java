package ob.debitos.simp.utilitario;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConstantesGenerales
{

    public static final String REGISTRO_EXITOSO = "Se registró correctamente";
    public static final String ACTUALIZACION_EXITOSA = "Se actualizó correctamente";
    public static final String ACTUALIZACION_EXTORNO_EXITOSA = "Se actualizó el <b>Indicador de Extorno</b> correctamente. Verifique por favor.";
    public static final String ACTUALIZACION_DEVOLUCION_EXITOSA = "Se actualizó el <b>Indicador de Devolución</b> correctamente. Verifique por favor.";
    public static final String ELIMINACION_EXITOSA = "Se eliminó correctamente";
    public static final String COMPENSACION_COMISION = "COMISION";
    public static final String COMPENSACION_FONDO = "FONDO";
    public static final String UTF_8 = "UTF-8";
    public static final int ROL_TRANSACCION_RECEPTOR = 2;
    public static final String ASIGNACION_PERMISO_EXITOSO = "Se asignó los permisos seleccionados correctamente.";
    public static final String ACTIVE_DIRECTORY = "Active Directory: ";
    public static final String RUTA_REPORTE_XLSX = "xlsx/";

    /* Instancia de Pedidos */
    public static final int INSTANCIA_AFILIACION = 1;
    public static final int INSTANCIA_ACTIVACION = 2;
    public static final int INSTANCIA_RECARGA = 3;

    /* Ruta A Página */
    public static final String PAGINA_MANTENIMIENTO_SEGURIDAD = "seguras/seguridad/mantenimiento";
    public static final String PAGINA_MANTENIMIENTO = "seguras/mantenimiento/mantenimiento";
    public static final String PAGINA_TARIFARIO = "seguras/tarifario";
    public static final String PAGINA_CONSULTA_ADMINISTRATIVA = "seguras/consulta/administrativa";
    public static final String PAGINA_CONSULTA_MOVIMIENTO = "seguras/consulta/movimiento";
    public static final String PAGINA_CONSULTA_TIPO_CAMBIO = "seguras/consulta/tipoCambio";
    public static final String PAGINA_REPORTE_AUTORIZACION = "seguras/reporte/reporteAutorizacion";
    public static final String PAGINA_REPORTE_TARIFARIO = "seguras/reporte/reporteTarifario";
    public static final String PAGINA_REPORTE_CONTABILIZACION = "seguras/reporte/reporteContabilizacion";
    public static final String PAGINA_REPORTE_RESUMEN_MOVIMIENTO = "seguras/reporte/reporteResumenMovimiento";
    public static final String PAGINA_REPORTE_COMPENSACION = "seguras/reporte/reporteCompensacion";
    public static final String PAGINA_MANTENIMIENTO_PROCESO = "seguras/proceso/mantenimiento";

    /* Nombre de Variable Model de Controller */
    public static final String P_CONSULTA = "consulta";
    public static final String P_MEMBRESIAS = "membresias";
    public static final String P_INSTITUCION = "institucion";
    public static final String P_INSTITUCIONES = "instituciones";
    public static final String P_CLASE_TRANSACCIONES = "claseTransacciones";
    public static final String P_CLASES_TRANSACCIONES = "clasesTransacciones";
    public static final String P_CODIGO_TRANSACCIONES = "codigoTransacciones";
    public static final String P_CODIGO_RESPUESTA = "codigosRpta";
    public static final String P_TIPOS_TARIFAS = "tipoTarifas";
    public static final String P_EMPRESAS = "empresas";
    public static final String P_CLIENTES = "clientes";
    public static final String P_LOGOS = "logos";
    public static final String P_ORIGENES = "origenes";
    public static final String P_MONEDAS = "monedas";
    public static final String P_ROLES_TRANSACCION = "rolesTransaccion";
    public static final String P_CONCEPTO_COMISIONES = "conceptoComisiones";
    public static final String P_TARIFARIO = "tarifario";
    public static final String P_CANALES = "canales";
    public static final String P_CODIGO_PROCESOS = "codigoProcesos";
    public static final String P_TIPO_DOCUMENTOS = "tipoDocumentos";
    public static final String P_HAS_COMISION_AUTORIZADA = "hasComisionAutorizada";
    public static final String P_HAS_COMISION = "hasComision";
    public static final String P_HAS_DETALLE = "hasDetalle";
    public static final String P_MENSAJE = "mensaje";
    public static final String P_MENSAJE_EXCEPCION = "mensajeExcepcion";
    public static final String P_INDICADORES_CONTABILIZACION = "indicadoresContabilizacion";
    public static final String P_AGENCIAS = "agencias";
    public static final String P_TIPOS_ATM = "tiposAtm";
    public static final String P_TRANSPORTADORAS = "transportadoras";
    public static final String P_LOCACIONES = "locaciones";
    public static final String P_CONCEPTOS_COMISION = "conceptosComision";
    public static final String P_ETVS = "etvs";
    public static final String P_ATMS = "atms";
    public static final String P_UBIGEOS = "ubigeos";
    public static final String P_TIPOS_UBICACION = "tiposUbicacion";

    /* Nombre de Variable Model de Reporte */
    public static final String P_DATOS = "Datos";
    public static final String P_PARAM1 = "param1";
    public static final String P_PARAM2 = "param2";
    public static final String P_TITULO = "Titulo";
    public static final String P_ENCABEZADO = "Encabezado";
    public static final String RB_TITULO = "rb_titulo";
    public static final String RB_CRITERIO_BUSQUEDA = "rb_criterioBusqueda";
    public static final String RB_DATOS = "rb_datos";
    public static final String RB_ENCABEZADO = "rb_encabezado";
    public static final String V_YARG_VIEW = "yargView";
    public static final String P_TOTAL = "Total";
    public static final String RB_TOTAL = "rb_total";

    /* Estados de Pedidos */
    public static final int LOTE_EMISION = 1;
    public static final int LOTE_ENVIO_EMISION = 2;
    public static final int LOTE_EMITIDO = 3;
    public static final int LOTE_RECARGA = 4;
    public static final int LOTE_ENVIO_RECARGA = 5;
    public static final int LOTE_RECARGADO = 6;
    public static final int LOTE_ERROR = -1;

    public static final int TIPO_DOCUMENTO_DNI = 1;
    public static final int TIPO_DOCUMENTO_PASAPORTE = 2;
    public static final int TIPO_DOCUMENTO_CARNET_EXTRANJERIA = 3;
    public static final int TIPO_DOCUMENTO_AUTOGENERADO = 4;

    /* Seguridad */
    public static final String CONTRASENIA_DEFAULT = "-1";
    public static final String CAMPO_CONTRASENIA_CONFIRMACION = "contraseniaConfirmacion";
    public static final String CAMPO_CONTRASENIA_ACTUAL = "contraseniaActual";
    public static final String CONTRASENIAS_NO_COINCIDEN = "Las Contrasenias no coinciden.";

    /* Proceso Manual */
    public static final String MENSAJE_EXITO = "Proceso ejecutado correctamente";
    public static final String EJECUCION_MANUAL = "M";
    public static final int ESTADO_EXITO = 1;
    public static final int ESTADO_ERROR = 0;
    public static final Integer ESTADO_EN_EJECUCION = 2;
    public static final String MENSAJE_NO_PROCESA_SABADO = "El Proceso no se ejecuta los sábados.";
    public static final String MENSAJE_REV_LOG_CONTROL = "Revise Log de Control";
    public static final String MENSAJE_EJECUCION_POR_INSTITUCION = "Se ejecutó el programa para %d de %d instituciones.";
    public static final String MENSAJE_EJECUCION_PROGRAMA = "Programa ejecutado correctamente.";
    public static final int RESULTADO_EJECUCION_COMPLETA = 1;
    public static final int RESULTADO_EJECUCION_PARCIAL = 2;

    public static final String V_JXLS_VIEW = "jxlsView";
    public static final String V_JXLS_MULTIPLE_VIEW = "jxlsMultipleView";

    /* Ruta a página HTML */
    public static final String PAGINA_MANT_SEGURIDAD = "seguras/seguridad/mantenimiento";

    /**/
    public static final String PAGINA_LLAVES_TRANSPORTE = "seguras/seguridad/llavesTransporte";
    public static final String PAGINA_LLAVES_TRABAJO = "seguras/seguridad/llavesTrabajo";

    /* Parametros de ModelMap */
    public static final String P_MANTENIMIENTO = "mantenimiento";

    /* URL */
    public static final String URL_LOGIN = "/login";

    /* Header AJAX */
    public static final String AJAX_HEADER = "XMLHttpRequest";

    public static final String P_REPORTE = "reporte";
    public static final String P_CRITERIO_BUSQUEDA = "criterioBusqueda";

    /* Para el procesamiento batch */

    public static final String USER_SIMP_BATCH = "SIMP_BATCH";
    public static final String EJECUCION_AUTOMATICA = "A";
    public static final String TIPO_PROCEDIMIENTO = "P";
    public static final String TIPO_SERVICIO_WEB = "W";
    public static final String TIPO_EJECUTADO_TERCERO = "T";
    public static final Object TIPO_EJECUCION_JAVA = "J";

    /* Generacion Archivo Contable */
    public static final String NUM_FMT_DOS_CIFRAS = "%02d";
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

    public static final String PMG_RUTA_COMPENSACION = "pmg.ruta.compensacion";
    public static final String PMG_ID_INSTITUCION = "pmg.id-institucion";
    public static final String PROP_NOMBRE_ARCHIVO_CONTABLE = "nombreArchivoContable";
    public static final String CARACTER_LINEA_INICIO_ARCHIVO = "A";
    public static final String CARACTER_LINEA_FINAL_ARCHIVO = "Z";

    public static final String FTP_BACKUP_HABILITADO = "sftp.backup.habilitado";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    public static final String COMPENSACION_FONDOS = "FONDOS";
    public static final String COMPENSACION_COMISIONES = "COMISIONES";

    public static final String PROGRAMA_FONDOS = "AFON";
    public static final String PROGRAMA_COMISIONES = "ACOM";

    public static final Map<String, String> TIPO_CONSULTA_SEGUN_PROGRAMA = Collections.unmodifiableMap(new LinkedHashMap<String, String>()
    {
        private static final long serialVersionUID = 1L;
        {
            put(PROGRAMA_FONDOS, COMPENSACION_FONDOS);
            put(PROGRAMA_COMISIONES, COMPENSACION_COMISIONES);
        }
    });
    public static final String ERROR_SFTP_CONEXION = "Error de Conexión con el servidor SFTP. ";
    public static final String INFO_SFTP_DESCONECTADO = "Se ha desconectado del servidor SFTP. ";

    public static final String PROGRAMA_SWDL = "SWDL";
    public static final String PROGRAMA_LCON = "LCON";
    public static final String PROGRAMA_SALD = "SALD";

    public static final Map<String, String> NOMBRE_PATRON_ARCHIVO_SEGUN_PROGRAMA = Collections.unmodifiableMap(new LinkedHashMap<String, String>()
    {
        private static final long serialVersionUID = 1L;
        {
            put(PROGRAMA_SWDL, "swd0".concat(PMG_PATRON_ID_INSTITUCION).concat(".dat"));
            put(PROGRAMA_LCON, PMG_PATRON_ID_INSTITUCION.concat("logcnt.dat"));
            put(PROGRAMA_SALD, PMG_PATRON_ID_INSTITUCION.concat("SALDOS.DAT"));
        }
    });

    public static final Map<String, Integer> LONGITUD_REGISTRO_ARCHIVO_SEGUN_PROGRAMA = Collections.unmodifiableMap(new LinkedHashMap<String, Integer>()
    {
        private static final long serialVersionUID = 1L;
        {
            put(PROGRAMA_SWDL, 560);
            put(PROGRAMA_LCON, 550);
            put(PROGRAMA_SALD, 0);
        }
    });

    public static final Map<String, Boolean> RESTA_FECHA_ARCHIVO_SEGUN_PROGRAMA = Collections.unmodifiableMap(new LinkedHashMap<String, Boolean>()
    {
        private static final long serialVersionUID = 1L;
        {
            put(PROGRAMA_SWDL, false);
            put(PROGRAMA_LCON, false);
            put(PROGRAMA_SALD, true);
        }
    });

}