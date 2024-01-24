package ob.debitos.simp.service.impl.proceso;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IArchivoLoteMapper;
import ob.debitos.simp.mapper.IGestionLotesMapper;
import ob.debitos.simp.model.parametro.ParametroLote;
import ob.debitos.simp.model.prepago.LotePP;
import ob.debitos.simp.model.proceso.ArchivoAfiliacionDeUBA;
import ob.debitos.simp.model.proceso.ArchivoLote;
import ob.debitos.simp.model.proceso.TmpRequerimiento;
import ob.debitos.simp.service.IArchivoLoteService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.ITmpRequerimientoService;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.utilitario.Conversor;
import ob.debitos.simp.utilitario.DatabaseUtil;
import ob.debitos.simp.utilitario.DatesUtils;

@Service
public class ArchivoLoteService implements IArchivoLoteService
{

    private static final Integer ID_INSTANCIA_AFILIACION = 1;
    private static final Integer ID_ESTADO = 1;
    private static final Integer CANTIDAD_ZERO = 0;
    private static final Integer ID_LENGTH = 4;
    private static final String ZERO_FILLER = "000";

    private static final String ARCHIVO_AFILIACIONES = "A0010";
    private static final String ARCHIVO_RECARGA_SOLES = "A0012";
    private static final String ARCHIVO_RECARGA_DOLARES = "A0013";
    private static final String ARCHIVO_DEBITO_SOLES = "A0006";
    private static final String ARCHIVO_DEBITO_DOLARES = "A0007";
    
    private static final String RECARGA = "R";
    private static final String DEBITO = "D";
    private static final Integer SOLES = 604;
    private static final Integer DOLARES = 840;

    private @Autowired Logger logger;
    private @Autowired IArchivoLoteMapper archivoLoteMapper;
    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired ITmpRequerimientoService tmpRequerimientoService;
    private @Autowired DataSource dataSource;
    private @Autowired IGestionLotesMapper gestionLotesMapper;

    @Override
    public void registrarLote(ArchivoLote archivoLote)
    {
        archivoLote.setFechaProceso(parametroGeneralService.buscarFechaProceso());
        archivoLote.setIdInstancia(ID_INSTANCIA_AFILIACION);
        archivoLote.setIdEstadoLote(ID_ESTADO);

        // Empieza relleno de idCliente y idEmpresa
        String idCliente = archivoLote.getIdCliente();
        idCliente = ZERO_FILLER + idCliente;
        idCliente = idCliente.substring(idCliente.length() - ID_LENGTH, idCliente.length());
        String idEmpresa = archivoLote.getIdEmpresa();
        idEmpresa = ZERO_FILLER + idEmpresa;
        idEmpresa = idEmpresa.substring(idEmpresa.length() - ID_LENGTH, idEmpresa.length());
        // Finaliza relleno de idCliente y idEmpresa

        archivoLote.setIdCliente(idCliente);
        archivoLote.setIdEmpresa(idEmpresa);
        archivoLote.setCantidadPedido(archivoLote.getCantidadPedido() == null ? CANTIDAD_ZERO : archivoLote.getCantidadPedido());
        logger.info("Lote {} ", archivoLote);
        archivoLoteMapper.registrarLote(archivoLote);
    }

    @Override
    public void registrarControlLote(Integer idLote)
    {
        archivoLoteMapper.registrarControlLote(idLote);
    }

    @Override
    public void insertarCSVAfiliacionBatch(List<TmpRequerimiento> tmpRequerimientos, Integer idLote)
    {
        this.tmpRequerimientoService.eliminarCargaTemporal();
        LocalDateTime inicio, fin;
        logger.info("Inicio de carga batch del archivo {}", tmpRequerimientos);
        inicio = LocalDateTime.now();
        tmpRequerimientos.forEach(tmp -> tmp.setIdLote(idLote));
        try (Connection conn = dataSource.getConnection())
        {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn
                    .prepareStatement("INSERT INTO TmpRequerimiento(" + "nIdLote, " + "vIdBIN, " + "vIdSubBIN, " + "nTipoDocumento, " + "vNumeroDocumento, " + "vNombre, " + "vApellidoPaterno, " + "vApellidoMaterno, " + "vNombreEmbossing, " + "vNombreCliente, " + "nCodigoMoneda, " + "nMontoRecarga, "
                            + "nMesesVencimiento, " + "vCorreo, " + "vDireccion, " + "vTelefonoMovil, " + "vTelefonoFijo, " + "dFechaNacimiento, " + "vNacionalidad, " + "vAfinidad, " + "vTipoEmision" + ")" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");)
            {
                for (TmpRequerimiento tmp : tmpRequerimientos)
                {
                    stmt.setInt(1, tmp.getIdLote());
                    stmt.setString(2, tmp.getIdBIN());
                    stmt.setString(3, tmp.getIdSubBIN());
                    stmt.setInt(4, tmp.getTipoDocumento());
                    stmt.setString(5, tmp.getNumeroDocumento());
                    stmt.setString(6, tmp.getNombre());
                    stmt.setString(7, tmp.getApellidoPaterno());
                    stmt.setString(8, tmp.getApellidoMaterno());
                    stmt.setString(9, tmp.getNombreEmbossing());
                    stmt.setString(10, tmp.getNombreCliente());
                    stmt.setInt(11, tmp.getCodigoMoneda());
                    stmt.setBigDecimal(12, tmp.getMontoRecarga());
                    stmt.setInt(13, tmp.getMesesVencimiento());
                    stmt.setString(14, tmp.getCorreo());
                    stmt.setString(15, tmp.getDireccion());
                    stmt.setString(16, tmp.getTelefonoMovil());
                    stmt.setString(17, tmp.getTelefonoFijo());
                    stmt.setDate(18, Conversor.utilDateToSqlDate(tmp.getFechaNacimiento()));
                    stmt.setString(19, tmp.getNacionalidad());
                    stmt.setString(20, tmp.getIdAfinidad());
                    stmt.setString(21, tmp.getIdTipoEmision());
                    stmt.addBatch();
                }
                stmt.executeBatch();
                conn.commit();
                logger.info("Inserto {}", tmpRequerimientos.size());
            } catch (SQLException e)
            {
                logger.error(e.getMessage(), e);
                DatabaseUtil.rollback(conn);
            }
        } catch (SQLException e)
        {
            logger.error(e.getMessage(), e);
        }
        logger.info("Finaliza carga masiva");
        fin = LocalDateTime.now();
        logger.info("@F Tiempo de Ejecución : {}", DatesUtils.tiempoEntre(inicio, fin));
    }

    @Override
    public void descargarArchivoAfiliaciones(String ruta, Integer idInstitucion)
    {
        List<ArchivoAfiliacionDeUBA> afiliacionesDeUBA = new ArrayList<>();
        String linea = "";
        File file = new File(ruta);
        LineIterator iterator;
        try
        {
            iterator = FileUtils.lineIterator(file, "UTF-8");
            try
            {
                ArchivoAfiliacionDeUBA archivoAfilDeUBA = new ArchivoAfiliacionDeUBA();
                int idxRpta = 1;
                // Leemos encabezado
                if (iterator.hasNext())
                    linea = iterator.nextLine();
                // Leemos cuerpo
                if (iterator.hasNext())
                    linea = iterator.nextLine();
                while (iterator.hasNext() && !linea.startsWith("Z"))
                {
                    archivoAfilDeUBA.setNumeroTarjeta(linea.substring(5, 21));
                    archivoAfilDeUBA.setNumeroTrace(linea.substring(31, 37));
                    archivoAfilDeUBA.setCodigoTransaccionAdm(linea.substring(51, 55));
                    archivoAfilDeUBA.setFechaHora(linea.substring(21, 31));
                    switch (idxRpta)
                    {
                    case 1:
                        archivoAfilDeUBA.setCodigoSeguimiento(linea.substring(351, 367));
                        archivoAfilDeUBA.setCodigoRespuestaTarjeta(linea.substring(49, 51));
                        break;
                    case 2:
                        archivoAfilDeUBA.setNumeroCuenta(linea.substring(55, 83));
                        archivoAfilDeUBA.setCodigoRespuestaCuenta(linea.substring(49, 51));
                        break;
                    case 3:
                        archivoAfilDeUBA.setCodigoRespuestaTrjCta(linea.substring(49, 51));
                        afiliacionesDeUBA.add(archivoAfilDeUBA);
                        archivoAfilDeUBA = new ArchivoAfiliacionDeUBA();
                        idxRpta = 0;
                        break;
                    default:
                        throw new SimpException("Error contador de lectura");
                    }
                    idxRpta++;
                    linea = iterator.nextLine();
                }
                insertarRespuestaAfiliacionUBABatch(afiliacionesDeUBA, idInstitucion);
            } catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            } finally
            {
                LineIterator.closeQuietly(iterator);
            }
        } catch (IOException ioE)
        {
            logger.error(ioE.getMessage(), ioE);
        }
    }

    @Override
    public void insertarRespuestaAfiliacionUBABatch(List<ArchivoAfiliacionDeUBA> afiliacionesDeUBA, Integer idInstitucion)
    {
        LocalDateTime inicio;
        this.logger.info("Inicio de carga batch del archivo {}", afiliacionesDeUBA);
        inicio = LocalDateTime.now();
        try (Connection conn = dataSource.getConnection())
        {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO TmpAfiliacionesDeUBA(" + "numeroTrace, " + "numeroTarjeta, " + "codigoSeguimiento, " + "numeroCuenta, " + "fechaHora, " + "codigoRespuestaTarjeta, " + "codigoRespuestaCuenta, " + "codigoRespuestaTrjCta," + "idInstitucion"
                    + ")" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");)
            {
                for (ArchivoAfiliacionDeUBA tmp : afiliacionesDeUBA)
                {
                    stmt.setString(1, tmp.getNumeroTrace());
                    stmt.setString(2, tmp.getNumeroTarjeta());
                    stmt.setString(3, tmp.getCodigoSeguimiento());
                    stmt.setString(4, tmp.getNumeroCuenta());
                    stmt.setString(5, tmp.getFechaHora());
                    stmt.setString(6, tmp.getCodigoRespuestaTarjeta());
                    stmt.setString(7, tmp.getCodigoRespuestaCuenta());
                    stmt.setString(8, tmp.getCodigoRespuestaTrjCta());
                    stmt.setInt(9, idInstitucion);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                conn.commit();
                archivoLoteMapper.registrarAfiliacionRespuestaUBA(idInstitucion);
                this.logger.info("Inserto {}", afiliacionesDeUBA.size());
            } catch (SQLException e)
            {
                logger.error(e.getMessage(), e);
                DatabaseUtil.rollback(conn);
            }
        } catch (SQLException e)
        {
            logger.error(e.getMessage(), e);
        }
        this.logger.info("Finaliza carga masiva");
        LocalDateTime fin = LocalDateTime.now();
        logger.info("@F Tiempo de Ejecución : {}", DatesUtils.tiempoEntre(inicio, fin));
    }

    /*
     * @Override public int generarArchivoAfiliaciones(String ruta, Integer
     * idInstitucion) { int r = -1; List<LoteDetalle> lotesAfiliacion =
     * this.gestionLotesMapper.consultaLoteAfiliacionPendiente(
     * CriterioBusquedaLoteTarjetas.builder().idInstitucion(idInstitucion).build
     * ()); int numRegistros = lotesAfiliacion.size(); if (numRegistros > 0){
     * try(RandomAccessFile stream = new RandomAccessFile(ruta, "rw");
     * FileChannel channel = stream.getChannel();) { String cantidad =
     * ZERO_FILLER_CANTIDAD + numRegistros; cantidad =
     * cantidad.substring(cantidad.length() - 8, cantidad.length()); String
     * institucion = ZERO_FILLER_INSTITUCION + idInstitucion; String fecha =
     * "0000" + DatesUtils.obtenerFechaEnFormato(this.parametroGeneralService.
     * buscarFechaProceso(), "yyMMdd") + "00000000"; String header = "A" +
     * cantidad + institucion + fecha; stream.setLength(0); byte[] strBytes;
     * ByteBuffer buffer; strBytes = header.concat(LINE_FEED).getBytes(); buffer
     * = ByteBuffer.allocate(strBytes.length); buffer.put(strBytes);
     * buffer.flip(); channel.write(buffer); for (LoteDetalle lote :
     * lotesAfiliacion) { strBytes =
     * lote.getDatos().concat(LINE_FEED).getBytes(); buffer =
     * ByteBuffer.allocate(strBytes.length); buffer.put(strBytes);
     * buffer.flip(); channel.write(buffer); } String trailer =
     * "Z".concat(cantidad); strBytes = trailer.concat(LINE_FEED).getBytes();
     * buffer = ByteBuffer.allocate(strBytes.length); buffer.put(strBytes);
     * buffer.flip(); channel.write(buffer); r = 0; } catch (Exception e) {
     * logger.error(e.getMessage(), e); } } else { r = -2; } return r; }
     */

    @Override
    public int generarArchivoPrepago(String ruta, Integer idInstitucion, String codigoArchivo)
    {
        int r = -1;
        List<String> datosArchivo = new ArrayList<>();
        switch (codigoArchivo)
        {
        case ARCHIVO_AFILIACIONES:
            datosArchivo = this.gestionLotesMapper.consultaLoteAfiliacionPendiente();
            break;
        case ARCHIVO_RECARGA_SOLES:
            datosArchivo = this.gestionLotesMapper.consultaLoteRecargasDebitosPendientes(RECARGA, SOLES);
            break;
        case ARCHIVO_RECARGA_DOLARES:
            datosArchivo = this.gestionLotesMapper.consultaLoteRecargasDebitosPendientes(RECARGA, DOLARES);
            break;
        case ARCHIVO_DEBITO_SOLES:
            datosArchivo = this.gestionLotesMapper.consultaLoteRecargasDebitosPendientes(DEBITO, SOLES);
            break;
        case ARCHIVO_DEBITO_DOLARES:
            datosArchivo = this.gestionLotesMapper.consultaLoteRecargasDebitosPendientes(DEBITO, DOLARES);
            break;
        }
        Integer numRegistros = datosArchivo.size();
        if (numRegistros > 2)
        { // Dos registros, excluyendo header y trailer
            try (RandomAccessFile stream = new RandomAccessFile(ruta, "rw"); FileChannel channel = stream.getChannel();)
            {
                byte[] strBytes;
                ByteBuffer buffer;
                for (String lote : datosArchivo)
                {
                    strBytes = lote.concat("\n").getBytes();
                    buffer = ByteBuffer.allocate(strBytes.length);
                    buffer.put(strBytes);
                    buffer.flip();
                    channel.write(buffer);
                }
                r = 0;
            } catch (Exception e)
            {
                logger.error(e.getMessage(), e);
            }
        } else
        {
            r = -2;
        }
        return r;
    }

    @Override
    public int generarArchivoActivaciones(String ruta, Integer idInstitucion)
    {
        // int r = -1;
        // List<String> datosArchivo = datosArchivo
        // Integer numRegistros = datosArchivo.size();
        // if (numRegistros >= 0){
        // try(RandomAccessFile stream = new
        // RandomAccessFile(rutaDirectorioConFichero, "rw");
        // FileChannel channel = stream.getChannel();) {
        // byte[] strBytes;
        // ByteBuffer buffer;
        // for (String lote : datosArchivo) {
        // strBytes = lote.concat("\n").getBytes();
        // buffer = ByteBuffer.allocate(strBytes.length);
        // buffer.put(strBytes);
        // buffer.flip();
        // channel.write(buffer);
        // }
        // r = 0;
        // } catch (Exception e) {
        // logger.error(e.getMessage(), e);
        // }
        // } else {
        // r = -2;
        // }
        // return r;
        return 0;
    }

    public void eliminarTmpAfiliacionesDeUba()
    {
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin;
        logger.info("@I Truncamiento de TmpAfiliacionesDeUBA");
        try (Connection conn = dataSource.getConnection();)
        {
            try (PreparedStatement stmt = conn.prepareStatement("DELETE TmpAfiliacionesDeUBA");)
            {
                stmt.executeUpdate();
                conn.commit();
                logger.info("El truncamiento de la TmpAfiliacionesDeUBA se ha realizado.");
            } catch (SQLException e)
            {
                logger.error(e.getMessage(), e);
                DatabaseUtil.rollback(conn);
            }
        } catch (SQLException e)
        {
            logger.error(e.getMessage(), e);
        }
        logger.info("El truncamiento de la TmpAfiliacionesDeUBA ha finalizado.");
        fin = LocalDateTime.now();
        logger.info("@F Tiempo de Ejecución : {}", DatesUtils.tiempoEntre(inicio, fin));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LotePP> actualizarLote(ParametroLote parametro)
    {
        return this.archivoLoteMapper.actualizarLote(parametro);
    }

}
