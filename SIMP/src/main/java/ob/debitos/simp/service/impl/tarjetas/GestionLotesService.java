package ob.debitos.simp.service.impl.tarjetas;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.mapper.IGestionLotesMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaLoteTarjetas;
import ob.debitos.simp.model.proceso.AfiliacionesCarga;
import ob.debitos.simp.model.tarjetas.AuxLotes;
import ob.debitos.simp.model.tarjetas.Lote;
import ob.debitos.simp.model.tarjetas.LoteDetalle;
import ob.debitos.simp.model.tarjetas.LoteFormulario;
import ob.debitos.simp.model.tarjetas.LoteParametro;
import ob.debitos.simp.model.tarjetas.LoteRecargaDebito;
import ob.debitos.simp.model.tarjetas.LoteRequest;
import ob.debitos.simp.service.IExportacionPOIService;
import ob.debitos.simp.service.IGestionLotesService;
import ob.debitos.simp.service.IPersonaPPService;
import ob.debitos.simp.service.ITarjetaPPService;
import ob.debitos.simp.service.excepcion.ArchivoExcelNoValidoException;
import ob.debitos.simp.utilitario.DatabaseUtil;
import ob.debitos.simp.utilitario.LoteTarjetasUtils;
import ob.debitos.simp.utilitario.StringsUtils;

/**
 * Servicio que gestiona los lotes -Agrega lotes de innomidas segun el
 * formulario -Validar de lote de nominadas segun el excel cargado y lo procesa
 * 
 * @author Mario Cortez
 * @author Anthony Oroche
 *
 */
@Service
public class GestionLotesService implements IGestionLotesService
{

    private @Autowired DataSource dataSource;
    
    private @Autowired IExportacionPOIService<Lote> exportLoteService;

    private final IGestionLotesMapper gestionLotesMapper;

    private static final String INNOMINADAS = "INNOMINADAS";
    private static final String DELETE = "DELETE";
    private static final String UPDATE = "UPDATE";
    private static final String NOMINADAS_CAB = "NOMINADAS_CAB";
    private static final String NOMINADAS_DET = "NOMINADAS_DET";
    private static final String AFILIACION = "AFILIACION";
    private static final String RECARGA_DEBITO_CAB_DET = "RECARGA_DEBITO_CAB_DET";
    private static final String RECARGA_DEBITO_CAB = "RECARGA_DEBITO_CAB";
    //private static final String ACTIVACION = "ACTIVACION";
    private static final String RECARGA_SOLES = "RECARGA_SOLES";
    private static final String RECARGA_DOLARES = "RECARGA_DOLARES";
    private static final String DEBITO_SOLES = "DEBITO_SOLES";
    private static final String DEBITO_DOLARES = "DEBITO_DOLARES";
    
    private static final String[][] cabeceraExportacionLote = {
            {"Fecha Proceso", "fechaProceso", "", "formatFecha", "-1"},
            {"Institución", "idInstitucion", "descInstitucion", "formatCadena", "-1"},
            {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
            {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
            {"Logo", "idLogo", "descLogoBin", "formatCadena", "-1"},
            {"Prodcuto", "codigoProducto", "descProducto", "formatCadena", "-1"},
            {"Afinidad", "idAfinidad", "descAfinidad", "formatCadena", "-1"},
            {"Categoría", "idCategoria", "descCategoria", "formatCadena", "-1"},
            {"ID Lote", "idLote", "", "formatCadena", "-1"},
            {"Fecha Registro", "fechaRegistroTexto", "horaRegistro", "formatCadenaCentrada", "-1"},
            {"Tipo Lote", "tipoLote", "descTipoLote", "formatCadena", "-1"},
            {"Activado", "indActivado", "", "formatSiNo", "-1"},
            {"Recargado", "indRecargado", "", "formatSiNo", "-1"},
            {"Tipo Afiliación", "tipoAfiliacion", "desctipoAfiliacion", "formatCadena", "-1"},
            {"Tipo Tarjetas", "tipoTarjetas", "descTipoTarjetas", "formatCadena", "-1"},
            {"Estado Lote", "estadoLote", "descEstadoLote", "formatCadena", "-1"},
            {"Registros", "registros", "", "formatCantidad", "-1"},
            {"Enviado a UBA", "enviadoUBA", "", "formatSiNo", "-1"},
            {"Fecha Env. UBA", "fechaEmisionUBA", "", "formatFecha", "-1"},
            {"Respondió UBA", "recibioRespuesta", "", "formatSiNo", "-1"},
            {"Fecha Res. UBA", "fechaRespuesta", "", "formatFecha", "-1"},
            {"ID Lote Padre", "idLotePadre", "", "formatCadena", "-1"},
            {"ID Lote UBA", "idLoteUBA", "", "formatCadena", "-1"}
    };

    private @Autowired Logger logger;

    public @Autowired IPersonaPPService personaPPService;
    public @Autowired ITarjetaPPService tarjetaPPService;

    public GestionLotesService(IGestionLotesMapper mapper)
    {
        this.gestionLotesMapper = (IGestionLotesMapper) mapper;
    }

    /**
     * Registra un lote de tarjetas innominadas segun el formulario
     * 
     * @author Mario
     */

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void registroAfiliacionesLoteInnominadas(Lote lote)
    {
        lote.setModo(INNOMINADAS);
        this.gestionLotesMapper.registroAfiliacionesLoteInnominadas(lote);
    }

    /**
     * Consulta de lote por criterios segun formulario
     * 
     * @author Mario
     */

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Lote> consultaLotesPorCriterios(CriterioBusquedaLoteTarjetas criterio)
    {
        return this.gestionLotesMapper.consultaLotesPorCriterios(criterio);
    }

    /**
     * Consulta de detalle de lote (boton ver detalle)
     * 
     * @author Mario
     */
    @Truncable(clase = LoteDetalle.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LoteDetalle> consultaLotesDetalleAfiliacion(CriterioBusquedaLoteTarjetas criterio)
    {
        criterio.setTipoDetalle("AF");
        return this.gestionLotesMapper.consultaLotesDetalle(criterio);
    }
    
    @Truncable(clase = LoteDetalle.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LoteDetalle> consultaLotesDetalleRecargaDebito(CriterioBusquedaLoteTarjetas criterio)
    {
        return this.gestionLotesMapper.consultaLotesDetalle(criterio);
    }

    /**
     * Elimina un lote de innomindas / nominadas Sol se pueden eliminar hasta
     * antes de ser enviados a UNIBANCA
     * 
     * @author Mario
     */

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void eliminarLote(Integer idLote)
    {
        Lote lote = Lote.builder().idLote(idLote).modo(DELETE).usuario(SecurityContextFacade.obtenerNombreUsuario()).build();
        this.gestionLotesMapper.mantenerLote(lote);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void actualizarLote(Lote lote)
    {
        Lote a = Lote.builder().modo(UPDATE).idLote(lote.getIdLote()).idAfinidad(lote.getIdAfinidad()).idCategoria(lote.getIdCategoria()).tipoTarjetas(lote.getTipoTarjetas()).usuario(SecurityContextFacade.obtenerNombreUsuario()).build();
        this.gestionLotesMapper.mantenerLote(a);
    }

    /**
     * Actualiza estado del lote afiliaciones
     * 
     * @author Mario
     */

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void actualizarEstadoLoteAfiliacionesAProcesada(LoteParametro loteParametro)
    {
        loteParametro.setModo(AFILIACION);
        this.gestionLotesMapper.actualizarEstadoLoteProcesado(loteParametro);
    }

    /**
     * Actualiza estado del lote recarga Soles
     * 
     * @author Mario
     */

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void actualizarEstadoLoteRecargaSolesAProcesada(LoteParametro loteParametro)
    {
        loteParametro.setModo(RECARGA_SOLES);
        this.gestionLotesMapper.actualizarEstadoLoteProcesado(loteParametro);
    }

    /**
     * Actualiza estado del lote recarga dolares
     * 
     * @author Mario
     */

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void actualizarEstadoLoteRecargaDolaresAProcesada(LoteParametro loteParametro)
    {
        loteParametro.setModo(RECARGA_DOLARES);
        this.gestionLotesMapper.actualizarEstadoLoteProcesado(loteParametro);
    }

    /**
     * Actualiza estado del lote debito Soles
     * 
     * @author Mario
     */

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void actualizarEstadoLoteDebitoSolesAProcesada(LoteParametro loteParametro)
    {
        loteParametro.setModo(DEBITO_SOLES);
        this.gestionLotesMapper.actualizarEstadoLoteProcesado(loteParametro);
    }

    /**
     * Actualiza estado del lote debito dolares
     * 
     * @author Mario
     */

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void actualizarEstadoLoteDebitoDolaresAProcesada(LoteParametro loteParametro)
    {
        loteParametro.setModo(DEBITO_DOLARES);
        this.gestionLotesMapper.actualizarEstadoLoteProcesado(loteParametro);
    }

    /**
     * Validar carga excel de nominadas
     * 
     * @author Mario
     */

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<AfiliacionesCarga> validarArchivoAfiliacionesNominadas(MultipartFile archivoAfiliaciones)
    {
        List<AfiliacionesCarga> resultado = null;
        try (Workbook workbook = WorkbookFactory.create(archivoAfiliaciones.getInputStream()))
        {
            Sheet sheet = workbook.getSheetAt(LoteTarjetasUtils.HOJA_EXCEL_CON_DATOS);
            LoteTarjetasUtils.validarCabeceraExcel(LoteTarjetasUtils.getCabeceraExcelAfiliaciones(), sheet.getRow(LoteTarjetasUtils.NUM_FILA_CABECERA));
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            rowIterator.next();
            resultado = new ArrayList<>();
            List<Object[]> batchValues = new ArrayList<>();
            while (rowIterator.hasNext())
            {
                row = rowIterator.next();
                String tipoDocumento = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_TIP_DOC));
                String numeroDocumento = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_NUM_DOC));
                String nombre = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_NOMBRE));
                String apellidoPaterno = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_APELLIDO_PAT));
                String apellidoMaterno = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_APELLIDO_MAT));
                String nombreEmbossing = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_NOMBRE_EMBOSSING));
                String ruc = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_RUC_EMPRESA));
                String nombreCliente = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_NOMBRE_CLIENTE));
                String fechaVencimiento = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_FECHA_VENCIMIENTO));
                String direccion = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_DIRECCION));

                String telefonoMovil = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_TELEFONO_CELULAR));
                String sexo = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_SEXO));
                String fechaNacimiento = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_FECHA_NACIMIENTO));
                String indicador = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_INDICADOR_DISTRIBUCION));
                String nacionalidad = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_NACIONALIDAD));
                String nombreManda1 = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_NOMBRE_MANDA1));
                String tipoManda1 = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_TIPODOC_MANDA1));
                String numManda1 = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_NUMDOC_MANDA1));
                String fonoMnada1 = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_FONO_MANDA1));
                String nombreManda2 = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_NOMBRE_MANDA2));

                String tipoManda2 = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_TIPODOC_MANDA2));
                String numManda2 = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_NUMDOC_MANDA2));
                String fonoMnada2 = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_AFI_FONO_MANDA2));

                batchValues.add(new Object[] { tipoDocumento, numeroDocumento, nombre, apellidoPaterno, apellidoMaterno, nombreEmbossing, ruc, nombreCliente, fechaVencimiento, direccion,

                        telefonoMovil, sexo, fechaNacimiento, indicador, nacionalidad, nombreManda1, tipoManda1, numManda1, fonoMnada1, nombreManda2,

                        tipoManda2, numManda2, fonoMnada2 });
            }
            try (Connection conn = dataSource.getConnection())
            {
                conn.setAutoCommit(false);
                try (Statement truncateStmt = conn.createStatement())
                {
                    truncateStmt.executeUpdate("TRUNCATE TABLE TmpAfiliacionesVal");
                } catch (SQLException e)
                {
                    this.logger.error(e.getMessage());
                    DatabaseUtil.rollback(conn);
                }
                try (PreparedStatement stmt = conn.prepareStatement(""
                        + "INSERT INTO TmpAfiliacionesVal ("
                        + "  vTipoDocumento"
                        + " ,vNumeroDocumento"
                        + " ,vNombre"
                        + " ,vApellidoPaterno"
                        + " ,vApellidoMaterno"
                        + " ,vNombreEmbossing"
                        + " ,vRuc"
                        + " ,vNombreCliente"
                        + " ,vFechaVencimiento"
                        + " ,vDireccion"
                        + " ,vTelefonoMovil"
                        + " ,vSexo"
                        + " ,vFechaNacimiento"
                        + " ,vIndicadorDistribucion"
                        + " ,vNacionalidad"
                        + " ,vNombreManda1"
                        + " ,vTipoManda1"
                        + " ,vNumManda1"
                        + " ,vFonoMnada1"
                        + " ,vNombreManda2"
                        + " ,vTipoManda2"
                        + " ,vNumManda2"
                        + " ,vFonoMnada2"
                        + ") VALUES ("
                        + "  ?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + " ,?"
                        + ");");)
                {
                    for (Object[] values : batchValues)
                    {
                        stmt.setString(1, values[0].toString());
                        stmt.setString(2, values[1].toString());
                        stmt.setString(3, values[2].toString());
                        stmt.setString(4, values[3].toString());
                        stmt.setString(5, values[4].toString());
                        stmt.setString(6, values[5].toString());
                        stmt.setString(7, values[6].toString());
                        stmt.setString(8, values[7].toString());
                        stmt.setString(9, values[8].toString());
                        stmt.setString(10, values[9].toString());
                        stmt.setString(11, values[10].toString());
                        stmt.setString(12, values[11].toString());
                        stmt.setString(13, values[12].toString());
                        stmt.setString(14, values[13].toString());
                        stmt.setString(15, values[14].toString());
                        stmt.setString(16, values[15].toString());
                        stmt.setString(17, values[16].toString());
                        stmt.setString(18, values[17].toString());
                        stmt.setString(19, values[18].toString());
                        stmt.setString(20, values[19].toString());
                        stmt.setString(21, values[20].toString());
                        stmt.setString(22, values[21].toString());
                        stmt.setString(23, values[22].toString());
                        stmt.addBatch();
                    }
                    stmt.executeBatch();
                    conn.commit();
                } catch (SQLException e)
                {
                    this.logger.error(e.getMessage());
                    DatabaseUtil.rollback(conn);
                }
            } catch (SQLException e)
            {
                this.logger.error(e.getMessage());
            }
            resultado = this.gestionLotesMapper.consultaValidacionAfil();
        } catch (EncryptedDocumentException e)
        {
            throw new ArchivoExcelNoValidoException("Archivo encriptado");
        } catch (InvalidFormatException e)
        {
            throw new ArchivoExcelNoValidoException("Archivo con formato inválido en su contenido");
        } catch (IOException e)
        {
            throw new ArchivoExcelNoValidoException("Error en la lectura del archivo");
        } catch (ArchivoExcelNoValidoException e)
        {
            throw new ArchivoExcelNoValidoException("El formato de la cabecera del archivo excel no es valido");
        }
        return resultado;
    }

    /**
     * Esta metodo separa los lotes que seran para recargar y afiliar para su
     * inserccion en MovLote y MovLoteDetalle Los lotes de recarga se separan
     * por moneda (soles y dolares)
     * 
     * @author Mario Cortez
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public String registroAfiliacionesLoteNominadas(LoteFormulario lote)
    {
        LoteParametro loteParametro = LoteParametro.builder()
                .modo(NOMINADAS_CAB)
                .idInstitucion(lote.getFormulario().getIdInstitucion())
                .idEmpresa(lote.getFormulario().getIdEmpresa())
                .idCliente(lote.getFormulario().getIdCliente())
                .idLogo(lote.getFormulario().getIdLogo())
                .codigoProducto(lote.getFormulario().getCodigoProducto())
                .tipoAfiliacion(lote.getFormulario().getTipoAfiliacion())
                .idAfinidad(lote.getFormulario().getIdAfinidad())
                .idCategoria(lote.getFormulario().getIdCategoria())
                .registros(lote.getAfiliaciones().size())
                .tipoTarjetas(lote.getFormulario().getTipoTarjetas())
                .build();
        this.gestionLotesMapper.registroAfiliacionesLoteNominadas(loteParametro);
        int[] contador = { 0 };
        lote.getAfiliaciones().forEach(loteReg -> {
            contador[0] = contador[0] + 1;
            LoteParametro loteParametroDetalle = LoteParametro.builder()
                    .modo(NOMINADAS_DET)
                    .idLote(loteParametro.getIdLote())
                    .contador(contador[0])

                    .idInstitucion(lote.getFormulario().getIdInstitucion())
                    .idEmpresa(lote.getFormulario().getIdEmpresa())
                    .idCliente(lote.getFormulario().getIdCliente())
                    .idLogo(lote.getFormulario().getIdLogo())
                    .codigoProducto(lote.getFormulario().getCodigoProducto())
                    .tipoAfiliacion(lote.getFormulario().getTipoAfiliacion())
                    .idAfinidad(lote.getFormulario().getIdAfinidad())
                    .idCategoria(lote.getFormulario().getIdCategoria())
                    .tipoTarjetas(lote.getFormulario().getTipoTarjetas())
                    .registros(lote.getAfiliaciones().size())
                    
                    .apellidoPaterno(loteReg.getApellidoPaterno())
                    .apellidoMaterno(loteReg.getApellidoMaterno())
                    .nombre(loteReg.getNombre())
                    .direccion(loteReg.getDireccion())
                    .fechaNacimiento(loteReg.getFechaNacimiento())
                    .nacionalidad(loteReg.getNacionalidad())
                    .nombreCliente(loteReg.getNombreCliente())
                    .nombreEmbossing(loteReg.getNombreEmbossing())
                    .ruc(loteReg.getRuc())
                    .tipoDocumento(loteReg.getTipoDocumento())
                    .numeroDocumento(loteReg.getNumeroDocumento())
                    .fechaVencimiento(loteReg.getFechaVencimiento())
                    .telefonoMovil(loteReg.getTelefonoMovil())
                    .sexo(loteReg.getSexo())
                   
                    .indicadorDistribucion(loteReg.getIndicadorDistribucion())
                    .nombreManda1(loteReg.getNombreManda1())
                    .tipoManda1(loteReg.getTipoManda1())
                    .numManda1(loteReg.getNumManda1())
                    .fonoMnada1(loteReg.getFonoMnada1())
                    .nombreManda2(loteReg.getNombreManda2())
                    .tipoManda2(loteReg.getTipoManda2())
                    .numManda2(loteReg.getNumManda2())
                    .fonoMnada2(loteReg.getFonoMnada2())
                    .build();
            this.gestionLotesMapper.registroAfiliacionesLoteNominadas(loteParametroDetalle);
        });
        // Creando Lote de activacion
        /*LoteParametro parametroActivacion = LoteParametro.builder()
                .modo(ACTIVACION)
                .idInstitucion(lote.getFormulario().getIdInstitucion())
                .idEmpresa(lote.getFormulario().getIdEmpresa())
                .idCliente(lote.getFormulario().getIdCliente())
                .idLogo(lote.getFormulario().getIdLogo())
                .codigoProducto(lote.getFormulario().getCodigoProducto())
                .tipoAfiliacion(lote.getFormulario().getTipoAfiliacion())
                .idAfinidad(lote.getFormulario().getIdAfinidad())
                .idCategoria(lote.getFormulario().getIdCategoria())
                .tipoTarjetas(lote.getFormulario().getTipoTarjetas())
                .registros(lote.getAfiliaciones().size())
                .idLotePadre(loteParametro.getIdLote())
                .build();
        this.gestionLotesMapper.registrarLoteActivaciones(parametroActivacion);*/
        return "Registro de Lote de Afiliaciones Nominadas exitoso: <b>Lote: " + loteParametro.getIdLote() + "</b>" /*+ " y registro de Lote de Activación exito: <b>Lote: " + parametroActivacion.getIdLote() + "</b>"*/;
    }

    /**
     * Validar carga excel de recargas y debitos
     * 
     * @author Mario
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LoteRecargaDebito> validarLoteRecargaDebitos(MultipartFile archivoAfiliaciones)
    {
        List<LoteRecargaDebito> resultado = null;
        try (Workbook workbook = WorkbookFactory.create(archivoAfiliaciones.getInputStream()))
        {
            resultado = new ArrayList<>();
            AuxLotes auxLoteA = this.gestionLotesMapper.obtenerTodosAuxiliares(AuxLotes.builder().verbo("GETS").build());
            if (auxLoteA.getRecargaDebito())
            {
                resultado.add(LoteRecargaDebito.builder().usandoTabla("1").build());
                return resultado;
            }

            this.gestionLotesMapper.obtenerTodosAuxiliares(AuxLotes.builder().verbo("UPDATE_ARB").valor(1).build());

            Sheet sheet = workbook.getSheetAt(LoteTarjetasUtils.HOJA_EXCEL_CON_DATOS);
            LoteTarjetasUtils.validarCabeceraExcel(LoteTarjetasUtils.getCabeceraExcelRecargaDebito(), sheet.getRow(LoteTarjetasUtils.NUM_FILA_CABECERA));
            Iterator<Row> rowIterator = sheet.iterator();
            List<Object[]> batchValues = new ArrayList<>();
            Row row;
            rowIterator.next();
            while (rowIterator.hasNext())
            {
                row = rowIterator.next();
                String codSeg = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_RECARGA_DEBITOS_COD_SEG));
                Integer moneda = Integer.parseInt(String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_RECARGA_DEBITOS_MONEDA)));
                Double monto = Double.parseDouble(row.getCell(LoteTarjetasUtils.COLUMNA_RECARGA_DEBITOS_MONTO).toString());
                String ope = String.valueOf(row.getCell(LoteTarjetasUtils.COLUMNA_RECARGA_DEBITOS_OPE));
                batchValues.add(new Object[] { codSeg, moneda, monto, ope });
            }

            try (Connection conn = dataSource.getConnection())
            {
                conn.setAutoCommit(false);
                try (Statement truncateStmt = conn.createStatement())
                {
                    truncateStmt.executeUpdate("TRUNCATE TABLE TmpRecargaDebito");
                } catch (SQLException e)
                {
                    this.logger.error(e.getMessage());
                    DatabaseUtil.rollback(conn);
                }
                try (PreparedStatement stmt = conn.prepareStatement("" + "INSERT INTO TmpRecargaDebito (" + "  vCodigoSeguimiento" + " ,nMoneda" + " ,nMonto" + " ,vOperacion" + ") VALUES (" + "  ?" + " ,?" + " ,?" + " ,?" + ");");)
                {
                    for (Object[] values : batchValues)
                    {
                        stmt.setString(1, values[0].toString());
                        stmt.setInt(2, (int) values[1]);
                        stmt.setDouble(3, (double) values[2]);
                        stmt.setString(4, values[3].toString());
                        stmt.addBatch();
                    }
                    stmt.executeBatch();
                    conn.commit();
                } catch (SQLException e)
                {
                    this.logger.error(e.getMessage());
                    DatabaseUtil.rollback(conn);
                }
            } catch (SQLException e)
            {
                this.logger.error(e.getMessage());
            }
            resultado = this.gestionLotesMapper.consultaValidacionRecargaDebito();
            this.gestionLotesMapper.obtenerTodosAuxiliares(AuxLotes.builder().verbo("UPDATE_ARB").valor(0).build());
        } catch (EncryptedDocumentException e)
        {
            throw new ArchivoExcelNoValidoException("Archivo encriptado");
        } catch (InvalidFormatException e)
        {
            throw new ArchivoExcelNoValidoException("Archivo con formato inválido en su contenido");
        } catch (IOException e)
        {
            throw new ArchivoExcelNoValidoException("Error en la lectura del archivo");
        } catch (ArchivoExcelNoValidoException e)
        {
            throw new ArchivoExcelNoValidoException("El formato de la cabecera del archivo excel no es valido");
        } catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("blaablabalbalab");
        }
        return resultado;
    }

    /**
     * Este metodo registra en BD los lotes de debitos y recargas cargados por
     * un Excel desde la pantalla
     * 
     * @author Mario Cortez
     * @author Anthony Oroche
     * 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void registroRecargaYDebitos(LoteFormulario lote)
    {
        this.registrarRecargasDebitoDetalle(lote.getRecargas());
        this.registrarRecargasDebitoDetalle(lote.getDebitos());
        LoteParametro param = LoteParametro.builder().modo(RECARGA_DEBITO_CAB).usuario(SecurityContextFacade.obtenerNombreUsuario()).build();
        this.gestionLotesMapper.registrarLoteRecargas(param);
    }

    private void registrarRecargasDebitoDetalle(List<LoteRequest> lista)
    {
        if (lista.size() != 0)
        {
            List<LoteRequest> Soles = this.dividirListaByMoneda(lista, 604);
            this.registrarLoteRecargasDebitoDetalle(Soles);
            List<LoteRequest> Dolares = this.dividirListaByMoneda(lista, 840);
            this.registrarLoteRecargasDebitoDetalle(Dolares);
        }
    }

    private List<LoteRequest> dividirListaByMoneda(List<LoteRequest> lista, Integer moneda)
    {
        List<LoteRequest> alist = new ArrayList<>();
        lista.forEach(m -> {
            if (m.getMoneda() == moneda)
            {
                alist.add(m);
            }
        });
        return lista;
    }

    private void registrarLoteRecargasDebitoDetalle(List<LoteRequest> lista)
    {
        if (lista.size() != 0)
        {
            int[] contador = { 0 };
            lista.forEach(loteReg -> {
                contador[0] = contador[0] + 1;
                LoteParametro loteParametroDetalle = LoteParametro.builder()
                        .modo(RECARGA_DEBITO_CAB_DET)
                        .contador(contador[0])
                        .codigoSeguimiento(loteReg.getCodigoSeguimiento())
                        .tipoDocumento(loteReg.getTipoDocumento())
                        .numeroDocumento(loteReg.getNumeroDocumento())
                        .operacion(loteReg.getOperacion())
                        .codigoMoneda(loteReg.getMoneda())
                        .monto(loteReg.getMonto())
                        .usuario(SecurityContextFacade.obtenerNombreUsuario())
                        .build();
                this.gestionLotesMapper.registrarLoteRecargas(loteParametroDetalle);
            });
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarLotesPorCriterios(List<Lote> list, CriterioBusquedaLoteTarjetas criterio, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso",   criterio.getDescripcionRangoFechasProceso() },
                {"Fecha Registro",  criterio.getDescripcionRangoFechasRegistro()},
                {"Tipo Tarjeta",    criterio.getDescripcionTipoTarjeta()        },
                {"Institución",     criterio.getDescripcionInstitucion()        },
                {"Empresa",         criterio.getDescripcionEmpresa()            },
                {"Clientes",        criterio.getDescripcionCliente()            },
                {"Logo",            criterio.getDescripcionLogo()               },
                {"Productos",       criterio.getDescripcionProducto()           },
                {"ID Lote",         StringsUtils.validarInteger(criterio.getIdLote())}
        };
        this.exportLoteService.generarExportacionNormal("Consulta Gestión Lotes", list, filtros, cabeceraExportacionLote, false, 3, 85, request, response);
    }
    
}