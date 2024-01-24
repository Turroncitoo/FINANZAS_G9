package ob.debitos.simp.utilitario;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConstantesExcepciones
{
    /* Mensajes de Error de Mantenimiento de Contabilidad */
    public static final String ESCENARIO_YA_EXISTE = "El Escenario asociado a la Cuenta Contable, ya existe.";
    public static final String ESCENARIO_NO_ENCONTRADO = "El Escenario asociado a la Cuenta Contable, no fue encontrado.";
    public static final String ESCENARIO_CON_COMISIONES_DUPLICADAS = "El Escenario tiene asociado Conceptos de Comisión Duplicados.";
    public static final String ESCENARIO_SIN_COMPENSACIONES = "El Escenario tiene asociado un Código de Transacción que no acepta compensaciones.";
    public static final String ESCENARIO_SIN_FONDO = "El Escenario debe tener asociado una Cuenta Fondo.";
    public static final String ESCENARIO_SIN_COMISION = "El Escenario debe tener asociado al menos una Cuenta Comisión.";


    /* Mensajes de Error de Mantenimiento de Parametro Generales */
    public static final String PARAMETRO_GENERAL_NO_ENCONTRADO = "Los Parámetro Generales no existen.";
    public static final String FECHA_PROCESO_NO_ENCONTRADO = "La Fecha de Proceso no fue encontrada.";
    public static final String RUTA_CONTEXTO_SIMPBUS_NO_ENCONTRADO = "La Ruta de Contexto de SimpBus no fue encontrado.";
    public static final String ID_EMPRESA_NO_ENCONTRADO = "El Código de Empresa no fue encontrado en Parámetros Generales.";
    public static final String CODIGO_INSTITUCION_NO_ENCONTRADO = "El Código de Institución no fue encontrado en Parámetros Generales.";
    public static final String BIN_RUTEO_NO_ENCONTRADO = "El BIN de Ruteo no fue encontrado.";
    public static final String LOGO_REPORTES_NO_ENCONTRADO = "Asegúrese de que exista el siguiente recurso: ";

    /* Mensajes de Error de Ajustes */
    public static final String TRANSACCION_OBSERVADA_NO_ENCONTRADA = "La Transacción Observada que desea actualizar <b>no fue encontrada</b>.";
    public static final String FECHA_ACTUALIZACION_INVALIDA = "La <b>Fecha de Actualización</b> debe ser mayor o igual que la <b>Fecha de Registro</b> de la Transacción seleccionada.";

    /* Mensajes de Error de Mantenimiento de Politicas de Seguridad */
    public static final String POLITICA_SEGURIDAD_NO_ENCONTRADO = "La Políticas de Seguridad no existen.";
    public static final String LONGITUD_MINIMA_CONTRASENIA_NO_ENCONTRADO = "La longitud mínima de contraseña no fue encontrado.";
    public static final String AUTENTICACION_ACTIVE_DIRECTORY_NO_ENCONTRADO = "La autenticación por Active Directory no fue encontrado.";
    public static final String COMPLEJIDAD_CONTRASENIA_NO_ENCONTRADO = "La complejidad de contrase\u00F1a no fue encontrado.";


    /* Mensajes de Error en Conexion */
    public static final String ERROR_CONEXION_ACTIVE_DIRECTORY = "No se logró establecer la conexión al servicio de Active Directory. Revise los parámetros de conexión.";
    public static final String ERROR_CONEXION_BASE_DATOS = "No se logró establecer la conexión a la Base de Datos. Revise los parámetros de conexión.";

    /* Codigos de Error LDAP */
    public static final String CODIGO_AD_CONTRASENIA_INCORRECTA = "52e";
    public static final String CODIGO_AD_CONTRASENIA_EXPIRADA = "532";
    public static final String CODIGO_AD_USUARIO_NO_ACTIVO = "533";
    public static final String CODIGO_AD_USUARIO_EXPIRADO = "701";
    public static final String CODIGO_AD_CONTRASENIA_DEBE_CAMBIAR = "773";
    public static final String CODIGO_AD_DIRECTORIO_NO_ENCONTRADO = "32";
    public static final String CODIGO_AD_USUARIO_NO_ENCONTRADO = "2030";

    /* Mensajes de Error REST */
    public static final String ERROR_TYPE_MISMATCH_EXCEPTION_REST = "La petición enviada por el cliente contiene un formato inválido. Reintente la operación, por favor.";
    public static final String ERROR_MISSING_PARAMETER_EXCEPTION_REST = "La petición enviada por el cliente contiene datos incompletos. Reintente la operación, por favor.";

    /* Otros Mensajes de Error */
    public static final String FECHA_PROCESO_ENCONTRADA_EJECUCION = "El Log de Control de Ejecución de Programas ya se encuentra preparado para la Fecha de Proceso actual.";
    public static final String ORDEN_EJECUCION = "Existe(n) %s Programa(s) por ejecutar antes del programa seleccionado. Siga el orden de ejecución, por favor.";
    public static final String VIOLACION_CLAVE_FORANEA = "VIOLACION_CLAVE_FORANEA";
    public static final String CAMPO_NO_DEFINIDO = "campoNoDefinido";
    public static final String ERROR_BASE_DATOS = "ERROR_BASE_DATOS";
    public static final String ERROR_SERVICIO_WEB = "Ocurrió un error en la ejecución de los Servicios Web.";
    public static final String ERROR_CONEXION_SIMP_BUS = "Ocurrió un error al conectarse con SIMPBus. Verifique que la ruta de contexto es correcta en Mantenimiento Parámetros Generales.";

    public static final String ERROR_DESCONOCIDO = "Ocurrió un error no identificado.";
    public static final String USUARIO_NO_ENCONTRADO = "El usuario %s no fue encontrado.";
    
    /* Mensajes de Error de Mantenimiento de Seguridad */
    public static final String LENGTH_CONTRASENIA = "La Contraseña debe contener entre <b> %s y %s carácteres</b>.";
    public static final String USUARIO_ACTUAL_NO_ENCONTRADO = "El Usuario asociado a esta sesión <b>no fue encontrado</b>. Consulte si el usuario no se eliminó.";
    public static final String USUARIO_ADMIN_NO_ENCONTRADO = "El Usuario <b>administrador</b> no fue especificado en el archivo de configuración.";
    public static final String CONTRASENIA_NO_ENCONTRADO = "El Usuario <b> %s </b> no tiene asociado una contraseña.";
    public static final String AUTOELIMINACION_USUARIO = "No puede eliminar el Usuario <b> %s </b> asociado a su sesión actual.";
    public static final String NO_PERMITIDA_ELIMINACION_USUARIO_ADMIN = "No puede eliminar el Usuario <b> ADMIN </b>, este usuario es necesario para la administración del sistema.";
    public static final String USUARIO_NO_ACTIVO = "El usuario %s no está activo.";
    public static final String USUARIO_EXPIRADO = "El usuario %s está expirado.";
    public static final String CONTRASENIA_INCORRECTA = "La contraseña ingresada es incorrecta.";
    public static final String CONTRASENIA_EXPIRADA = "La contraseña ingresada está expirada.";
    public static final String CONTRASENIA_DEBE_CAMBIAR = "La contraseña ingresada ha caducado, debe ser cambiada.";
     
    public static final String CODIGO_SEGUIMIENTO_NO_ENCONTRADO = "Código de seguimiento no Encontrado.";
    public static final String ID_CLIENTE_NO_ENCONTRADO = "Id de Clienre no encontrado";
    public static final String ERROR_CONVERSION_DATOS = "No se pudo convertir correctamente el JSON de respuesta";
    public static final String DIRECTORIO_NO_ENCONTRADO = "El Directorio de Usuario, especificado en Políticas de Seguridad, no existe.";
    public static final String REPOSITORIO_ARCHIVO_CONTABLE_NO_ENCONTRADO = "El repositorio para archivos contable no ha sido encontrado.";
    public static final String PROCESA_OBSERVADAS_MANUAL_NO_ENCONTRADO = "El indicador de procesamiento de txns observadas no ha sido encontrado.";
    public static final String PROGRAMA_NO_ENCONTRADO = "El Programa con el Código de Grupo %s, Código de Programa %s y Código de Submódulo %s no fue encontrado.";
    public static final String ARCHIVO_NO_ENCONTRADO = "El Archivo con el Código %s, no fue encontrado";
    public static final String RUTA_ARCHIVO_NO_ENCONTRADO = "El Archivo %s, no fue encontrado";
    public static final String ERROR_LEER_CABECERA = "Error al leer la cabecera. Cabecera: %s";
    public static final String ERROR_DESCARGA_ARCHIVO = "Error al descargar el archivo %s.";
    public static final String ERROR_SUBIDA_ARCHIVO = "Error al subir el archivo.";
    public static final String ERROR_CAMBIO_DIRECTORIO_REMOTO = "Error al cambiar de directorio remoto %s. Verifique su existencia.";
    public static final String ERROR_CONEXION_SFTP = "Error de conexión SFTP";
    
    public static final String NO_INSTANCIAR_CLASE_ESTATICA = "No puede instanciar una clase estática.";
    public static final String ERROR_LECTURA_ARCHIVO = "Error en la lectura del archivo %s.";
    public static final String REEMPLAZO_PATRON_VALOR_IMPAR = "Error en el reemplazo patrón-valor de la cadena %s. El arreglo patrón-valor debe ser par.";
    public static final String ERROR_EJECUCION_HILOS = "Error cuando se intentaba recuperar el resultado del proceso. Causa: %s";
    
    public static final Map<Integer, String> EXCEPCION_SQL_SERVER = Collections
            .unmodifiableMap(new LinkedHashMap<Integer, String>()
            {
                private static final long serialVersionUID = 1L;
                {
                    put(547, VIOLACION_CLAVE_FORANEA);
                }
            });
}