package ob.debitos.simp.utilitario;

import java.util.ArrayList;
import java.util.List;

import ob.debitos.simp.model.mantenimiento.MultiTabDet;

public class MultiTablaUtil
{

    public static final int TABLA_TIPO_DOCUMENTO = 1;
    public static final int TABLA_ESTADO_CIVIL = 2;
    public static final int TABLA_ROL_TRANSACCION = 3;
    public static final int TABLA_ORIGEN_ARCHIVO = 6;
    public static final int TABLA_TIPO_RESPUESTA = 8;
    public static final int TABLA_SEXO = 10;
    public static final int TABLA_REGISTRO_CONTABLE = 13;
    public static final int TABLA_INDICADOR_CONTABILIZACION = 22;
    public static final int TABLA_TIPO_MOVIMIENTO = 28;
    public static final int TABLA_ACCION_AUDITORIA = 29;
    public static final int TABLA_ACCION_RECURSO = 30;
    public static final int TABLA_TIPO_PROCESO_AUTOMATICO = 35;
    public static final int TABLA_TIPO_EJECUCION = 36;
    public static final int TABLA_INDICADOR_EXTORNO = 37;
    public static final int TABLA_INDICADOR_DEVOLUCION = 38;
    public static final int TABLA_CATEGORIAS_REGIMEN = 40;
    public static final int TABLA_TIPO_OPERACION_SFTP = 47;
    public static final int TABLA_ESTADO_DE_EXTORNO = 48;
    public static final int TABLA_OPERACIONES = 49;
    public static final int TABLA_ROL_REPORTE_CONTABLE = 42;
    public static final int TABLA_TIPO_DETALLE_REPORTE_CONTABLE = 43;
    public static final int TABLA_FILTRO_DETALLE_REPORTE_CONTABLE = 44;
    public static final int TABLA_INDICADOR_CONCILIACION = 23;
    public static final int TABLA_TIPO_WS_SIMPHUB_PARA_FILTRO = 62;
    public static final int TABLA_TIPO_TIPO_ORDEN_TARJETA = 75;
    public static final int TABLA_TIPO_AFILIACION_TARJETA = 77;
    public static final int TABLA_TIPO_OP_CONSULTA_WS = 79;
    public static final int TABLA_TIPOS_BLOQUEO_WS = 81;
    public static final int TABLA_TIPOS_REGIMEN_WS = 82;
    public static final int TABLA_ESTADOS_PREAUTH_WS = 83;
    public static final int TABLA_REGIMENES_PREPAGO = 84;
    public static final int TABLA_TIPO_CUENTA_ALEGRA = 85;
    public static final int TABLA_INDICADOR_DISTRIBUCION_AFILIACION = 91;

    public static List<MultiTabDet> convertirAMultiTabDetAccionesRecurso(String accionesEnCadena, List<MultiTabDet> accionesRecursos)
    {
        List<MultiTabDet> accionesConvertidas = new ArrayList<>();
        List<String> accionesEnListaCadena = StringsUtils.obtenerTokens(accionesEnCadena, ",");
        for (String idAccionEnListaCadena : accionesEnListaCadena)
        {
            MultiTabDet accionRecursoEncontrado = accionesRecursos.stream().filter(accionRecurso -> accionRecurso.getIdItem().equals(idAccionEnListaCadena)).findAny().orElse(null);
            if (accionRecursoEncontrado != null)
            {
                accionesConvertidas.add(accionRecursoEncontrado);
            }
        }
        return accionesConvertidas;
    }

}