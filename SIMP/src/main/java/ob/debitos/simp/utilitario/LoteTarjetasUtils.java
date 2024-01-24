package ob.debitos.simp.utilitario;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.proceso.AfiliacionesCarga;
import ob.debitos.simp.model.tarjetas.LoteDetalle;
import ob.debitos.simp.model.tarjetas.LoteRecargaDebito;
import ob.debitos.simp.service.IPersonaPPService;
import ob.debitos.simp.service.excepcion.ArchivoExcelNoValidoException;

/**
 * Utilitario para carga de archivos de lote de tarjetas nominadas, recargas y
 * debitos
 * 
 * @author Mario Cortez
 * @author Anthony Oroche
 *
 */
public class LoteTarjetasUtils
{

    private LoteTarjetasUtils()
    {
    }

    protected static final String[] CABECERA_EXCEL_AFILIACIONES = { "TIP_DOC", "NUM_DOC", "NOMBRE", "APELLIDO_PAT", "APELLIDO_MAT", "NOMBRE_EMBOSSING", "RUC_EMPRESA", "NOMBRE_CLIENTE", "FECHA_VENCIMIENTO", "DIRECCIÓN", "TELÉFONO_CELULAR", "SEXO", "FECHA_NACIMIENTO", "INDICADOR_DISTRIBUCIÓN",
            "NACIONALIDAD", "NOMBRE_MANDATORIO_1", "TIPO_DOC_MANDATORIO_1", "NUM_DOC_MANDATORIO_1", "TELÉFONO_MANDATORIO_1", "NOMBRE_MANDATORIO_2", "TIPO_DOC_MANDATORIO_2", "NUM_DOC_MANDATORIO_2", "TELÉFONO_MANDATORIO_2" };

    public static final String MONEDA_RECARGA_SOLES = "604";
    public static final String MONEDA_RECARGA_DOLARES = "840";
    public static final int COLUMNA_AFI_TIP_DOC = 0;
    public static final int COLUMNA_AFI_NUM_DOC = 1;
    public static final int COLUMNA_AFI_NOMBRE = 2;
    public static final int COLUMNA_AFI_APELLIDO_PAT = 3;
    public static final int COLUMNA_AFI_APELLIDO_MAT = 4;
    public static final int COLUMNA_AFI_NOMBRE_EMBOSSING = 5;

    public static final int COLUMNA_AFI_RUC_EMPRESA = 6;
    public static final int COLUMNA_AFI_NOMBRE_CLIENTE = 7;
    public static final int COLUMNA_AFI_FECHA_VENCIMIENTO = 8;
    public static final int COLUMNA_AFI_DIRECCION = 9;
    public static final int COLUMNA_AFI_TELEFONO_CELULAR = 10;
    public static final int COLUMNA_AFI_SEXO = 11;
    public static final int COLUMNA_AFI_FECHA_NACIMIENTO = 12;

    public static final int COLUMNA_AFI_INDICADOR_DISTRIBUCION = 13;
    public static final int COLUMNA_AFI_NACIONALIDAD = 14;

    public static final int COLUMNA_AFI_NOMBRE_MANDA1 = 15;
    public static final int COLUMNA_AFI_TIPODOC_MANDA1 = 16;
    public static final int COLUMNA_AFI_NUMDOC_MANDA1 = 17;
    public static final int COLUMNA_AFI_FONO_MANDA1 = 18;

    public static final int COLUMNA_AFI_NOMBRE_MANDA2 = 19;
    public static final int COLUMNA_AFI_TIPODOC_MANDA2 = 20;
    public static final int COLUMNA_AFI_NUMDOC_MANDA2 = 21;
    public static final int COLUMNA_AFI_FONO_MANDA2 = 22;

    public static final int NUM_FILA_CABECERA = 0;

    public static final int HOJA_EXCEL_CON_DATOS = 0;

    public static final String ACCION_RECARGAR = "RECARGA";
    public static final String ACCION_AFILIAR = "AFILIACION";
    public static final String SIN_ACCION = "SIN ACCION";
    public static final String AFILIACION_INVALIDA = "NO SE PUEDE AFILIAR";

    protected static final String[] CABECERA_EXCEL_RECARGA_DEBITO = { "CODIGO SEGUIMIENTO", "MONEDA", "MONTO", "OPERACIÓN" };
    protected static final String[] CABECERA_EXCEL_BLOQUEO = { "TIP_DOC", "NUM_DOC", "RUC_EMPRESA_CLIENTE", "ESTADO_TARJETA" };

    public static final int COLUMNA_RECARGA_DEBITOS_COD_SEG = 0;
    public static final int COLUMNA_RECARGA_DEBITOS_MONEDA = 1;
    public static final int COLUMNA_RECARGA_DEBITOS_MONTO = 2;
    public static final int COLUMNA_RECARGA_DEBITOS_OPE = 3;
    public static final String TARJETA_ACTIVA = "A";
    
    // bloqueos
    public static final int COLUMNA_TIPO_DOCUMENTO = 0;
    public static final int COLUMNA_NUMERO_DOCUMENTO = 1;
    public static final int COLUMNA_RUC = 2;
    public static final int COLUMNA_ESTADO_TARJETA = 3;

    /**
     * Valida cabecera en carga de Excel
     *
     * @author Mario Cortez
     */

    public static void validarCabeceraExcel(String[] cabeceraOriginal, Row cabeceraExcel)
    {
        boolean archivoExcelValido = true;
        for (int columna = 0; columna < cabeceraOriginal.length && archivoExcelValido; columna++)
        {
            Cell celdaCabecera = cabeceraExcel.getCell(columna);
            if (!celdaCabecera.getStringCellValue().equals(cabeceraOriginal[columna]))
            {
                archivoExcelValido = false;
            }
        }
        if (!archivoExcelValido)
        {
            throw new ArchivoExcelNoValidoException("El formato de la cabecera del archivo excel no es valido");
        }
    }

    /**
     * Determina la accion que se hara por cada registro del excel cargado Se
     * buscar por cada documento en la base de datos, si existe debe ser
     * recargado Si no existe, debe ser afiliado, por defecto debe ser afiliado
     * (si son vacios el tipo y numero de documento)
     *
     * @author Mario Cortez
     */

    public static List<AfiliacionesCarga> determinarAccionPorRegistro(List<AfiliacionesCarga> listaAfiliaciones, IPersonaPPService personaPPService)
    {
        listaAfiliaciones.forEach(afi -> {
            String tipoDocumento = afi.getTipoDocumento();
            String numeroDocumento = afi.getNumeroDocumento();
            if (tipoDocumento.equals("") || numeroDocumento.equals(""))
            {
                afi.setOperacion(AFILIACION_INVALIDA);
            } else
            {
                Integer existe = personaPPService.existePersonaPorTipoDocumento(CriterioBusquedaTipoDocumento.builder().tipoDocumento(tipoDocumento).numeroDocumento(numeroDocumento).build());
                if (existe == 0)
                {
                    afi.setOperacion(ACCION_AFILIAR);
                } else
                {
                    afi.setOperacion(AFILIACION_INVALIDA);
                    afi.setExitoRegistro(0);
                    afi.setMensajeExcepcion("El cliente ya existe.</br>");
                }
            }
        });
        return listaAfiliaciones;
    }

    /**
     * Metodo que realiza el mapeo de campos en los lotes
     *
     * @author Mario Cortez
     */

    public static List<LoteDetalle> obtenerLoteDetallePorRegistro(List<LoteDetalle> loteDetalle, AfiliacionesCarga registro)
    {
        loteDetalle.add(LoteDetalle.builder().tipoDocumento(registro.getTipoDocumento()).numeroDocumento(registro.getNumeroDocumento()).nombres(registro.getNombre()).apellidoPaterno(registro.getApellidoPaterno()).apellidoMaterno(registro.getApellidoMaterno())
                .nombreCortoThb(registro.getNombreEmbossing()).fechaNacimiento(registro.getFechaNacimiento()).direccion(registro.getDireccion()).telefono(registro.getTelefonoMovil()).build());
        return loteDetalle;
    }

    /**
     * Se llenan los datos del cliente faltantes en el Excel de recargas y
     * debitos
     *
     * @author Mario Cortez
     */
    /*public static List<LoteRecargaDebito> llenarDatosClientesEnArchivoRecargaDebitos(List<LoteRecargaDebito> lista, ITarjetaPPService tarjetaPPService)
    {
        lista.forEach(det -> {
            List<TarjetaPP> tarjetas;
            String numeroDocumento = det.getNumeroDocumento(); // ''
            String ruc = det.getRuc();
            if (numeroDocumento.length() > 0 && ruc.length() > 0)
            {
                tarjetas = tarjetaPPService.buscarPorCriterios(CriterioBusquedaTarjeta.builder().numeroDocumento(numeroDocumento).ruc(ruc).numeroTarjeta("").build());

                if (tarjetas.isEmpty())
                {
                    det.setExiste(0);
                    det.setExitoRegistro(0);
                    det.setComentario(det.getComentario() + "-No existe una tarjeta asociada al número de documento y RUC</br>");
                } else
                {
                    det.setExiste(1);
                    det.setApellidoMaterno(tarjetas.get(0).getApellidoMaterno());
                    det.setApellidoPaterno(tarjetas.get(0).getApellidoPaterno());
                    det.setNombre(tarjetas.get(0).getNombres());
                    det.setTipoDocumento(tarjetas.get(0).getTipoDocumento());
                    validarTarjetaActiva(tarjetas.get(0).getEstado(), det);
                }
            } else
            {
                det.setExiste(0);
                det.setExitoRegistro(0);
            }
            validarNumeroDocumento(numeroDocumento, det);
            validarNumeroRuc(ruc, det);

            switch (det.getMoneda())
            {
            case "604":
                det.setDescripcionMoneda("SOLES");
                break;
            case "840":
                det.setDescripcionMoneda("DÓLARES");
                break;
            default:
                det.setComentario(det.getComentario() + "-Moneda no permitida</br>");
                det.setExitoRegistro(0);
            }
            switch (det.getOperacion())
            {
            case "R":
                det.setDescripcionOperacion(ACCION_RECARGAR);
                break;
            case "D":
                det.setDescripcionOperacion("DEBITO");
                break;
            default:
                det.setComentario(det.getComentario() + "-Operacion deconocida</br>");
                det.setExitoRegistro(0);
            }
        });
        return lista;
    }*/

    // bloqueos
    /*public static List<BloqueosCarga> llenarDatosClientesEnArchivoBloqueo(List<BloqueosCarga> lista, ITarjetaPPService tarjetaPPService)
    {
        lista.forEach(det -> {
            List<TarjetaPP> tarjetas;
            String numeroDocumento = det.getNumeroDocumento(); // ''
            String ruc = det.getRuc();
            if (numeroDocumento.length() > 0 && ruc.length() > 0)
            {
                tarjetas = tarjetaPPService.buscarPorDocumentoRuc(CriterioBusquedaTarjeta.builder().numeroDocumento(numeroDocumento).ruc(ruc).numeroTarjeta("").build());

                if (tarjetas.isEmpty())
                {
                    det.setExito(0);
                    det.setMensajeError(det.getMensajeError() + "-El número del documento o ruc no existe</br>");
                } else
                {

                    validarTarjetaActivaBloqueo(tarjetas.get(0).getEstado(), det);
                    det.setExito(1);
                    det.setMensajeError("");
                }
            } else
            {
                det.setExito(0);
                det.setMensajeError(det.getMensajeError() + "-El número del documento o ruc vacio</br>");
            }

        });
        return lista;
    }*/

    public static void validarNumeroDocumento(String numeroDocumento, LoteRecargaDebito det)
    {
        if (numeroDocumento.length() <= 0)
        {
            det.setComentario(det.getComentario() + "-El número de documento esta vacio en el Excel subido</br>");
        }
    }

    public static void validarTarjetaActiva(String estado, LoteRecargaDebito det)
    {
        if (!estado.equals(TARJETA_ACTIVA))
        {
            det.setComentario(det.getComentario() + "-La tarjeta no se encuentra activa</br>");
            det.setExitoRegistro(0);
        }
    }

    public static void validarNumeroRuc(String ruc, LoteRecargaDebito det)
    {
        if (ruc.length() <= 0)
        {
            det.setComentario(det.getComentario() + "-El RUC esta vacio en el Excel subido</br>");
        }
    }

    /*public static void validarTarjetaActivaBloqueo(String estado, BloqueosCarga det)
    {
        if (!estado.equals(TARJETA_ACTIVA))
        {
            det.setMensajeError(det.getMensajeError() + "-La tarjeta no se encuentra activa</br>");
            det.setExito(0);
        }
    }*/

    public static String[] getCabeceraExcelAfiliaciones()
    {
        return CABECERA_EXCEL_AFILIACIONES;
    }

    public static String[] getCabeceraExcelRecargaDebito()
    {
        return CABECERA_EXCEL_RECARGA_DEBITO;
    }

    public static String[] getCabeceraExcelBloqueo()
    {
        return CABECERA_EXCEL_BLOQUEO;
    }

}