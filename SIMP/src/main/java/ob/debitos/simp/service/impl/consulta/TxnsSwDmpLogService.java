package ob.debitos.simp.service.impl.consulta;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.service.IExportacionPOIService;
import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsSwdmplog;
import ob.debitos.simp.service.ITxnsSwDmpLogService;
import ob.debitos.simp.utilitario.PaginacionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ITxnsSwDmpLogMapper;
import ob.debitos.simp.model.consulta.movimiento.TxnSwDmpLogDetalle;
import ob.debitos.simp.model.consulta.movimiento.TxnsSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionSwDmpLog;

@Service
public class TxnsSwDmpLogService implements ITxnsSwDmpLogService
{
    private @Autowired ITxnsSwDmpLogMapper consultaMapper;

    private @Autowired IExportacionPOIService<TxnsSwDmpLog> exportTxnsSwDmpLog;

    @Truncable(clase = TxnSwDmpLogDetalle.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnSwDmpLogDetalle> buscarDetalleTxnsSwDmpLog(CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion)
    {
        return consultaMapper.buscarDetalleTxnsSwDmpLog(criterioBusquedaDetalleTransaccion);
    }

    @Truncable(clase = TxnsSwDmpLog.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsSwDmpLog> buscarTxnsSwDmpLogPaginado(CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsSwdmplog> criterioPaginacion)
    {
        criterioPaginacion.setOrderExpression(PaginacionUtil.obtenerExpresionOrdenamiento(criterioPaginacion));
        criterioPaginacion.setFiltrosDatatable(PaginacionUtil.armarFiltroDatatableSwdmplog(criterioPaginacion));
        return consultaMapper.buscarTxnsSwDmpLogPaginado(criterioPaginacion);
    }

    @Truncable(clase = TxnsSwDmpLog.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsSwDmpLog> filtrarTxnsSwDmpLogPaginado(CriterioPaginacion<CriterioBusquedaTransaccionSwDmpLog, FiltroDtTxnsSwdmplog> criterioPaginacion)
    {
        criterioPaginacion.setOrderExpression(PaginacionUtil.obtenerExpresionOrdenamiento(criterioPaginacion));
        criterioPaginacion.setFiltrosDatatable(PaginacionUtil.armarFiltroDatatableSwdmplog(criterioPaginacion));
        return consultaMapper.filtrarTxnsSwDmpLogPaginado(criterioPaginacion);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsSwDmpLogPorCriteriosDetallado(List<TxnsSwDmpLog> list, CriterioBusquedaTransaccionSwDmpLog criterioBusquedaTransaccionSwDmpLog, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso", criterioBusquedaTransaccionSwDmpLog.getDescripcionRangoFechasProceso()},
                {"Fecha Transacción", criterioBusquedaTransaccionSwDmpLog.getDescripcionRangoFechasTransaccion()},
                {"Institución", criterioBusquedaTransaccionSwDmpLog.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaTransaccionSwDmpLog.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaTransaccionSwDmpLog.getDescripcionCliente()},
                {"Rol Transacción", criterioBusquedaTransaccionSwDmpLog.getDescripcionRolTransaccion()},
                {"Código Proceso", criterioBusquedaTransaccionSwDmpLog.getDescripcionCodigoProceso()},
                {"Código Respuesta", criterioBusquedaTransaccionSwDmpLog.getDescripcionCodigoRespuesta()},
                {"Canal", criterioBusquedaTransaccionSwDmpLog.getDescripcionCanal()},
                {"Número Tarjeta", criterioBusquedaTransaccionSwDmpLog.getNumeroTarjeta()},
                {"Código Seguimiento", criterioBusquedaTransaccionSwDmpLog.getCodigoSeguimiento()},
                {"Trace", criterioBusquedaTransaccionSwDmpLog.getTrace()},
                {"Nombre Completo", criterioBusquedaTransaccionSwDmpLog.getNombreCompleto()}
        };
        String[][] cabeceraExportacion = {
                {"Fecha Proceso", "fechaProceso", "", "formatFecha", "-1"},
                {"Institución", "codigoInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
                {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
                {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
                {"Logo", "descripcionLogoCompleto", "", "formatCadena", "-1"},
                {"Producto", "codigoProducto", "descProducto", "formatCadena", "-1"},
                {"Rol Transacción", "rolTransaccion", "descripcionRol", "formatCadena", "-1"},
                {"Código Proceso", "idProceso", "descripcionProceso", "formatCadena", "-1"},
                {"Código Respuesta", "codigoRespuesta", "descripcionRespuesta", "formatCadena", "-1"},
                {"Canal", "idCanal", "descripcionCanal", "formatCadena", "-1"},
                {"Fecha Transacción", "fechaTransmisionCadena", "horaTransmision", "formatCadenaCentrada", "-1"},
                {"Tipo Mensaje", "tipoMensaje", "", "formatCadena", "-1"},
                {"Tipo Documento", "tipoDocumento", "descTipoDocumento", "formatCadena", "-1"},
                {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
                {"Nombres", "nombres", "", "formatCadena", "-1"},
                {"Apellido Paterno", "apellidoPaterno", "", "formatCadena", "-1"},
                {"Apellido Materno", "apellidoMaterno", "", "formatCadena", "-1"},
                {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
                {"Código Seguimiento", "codigoSeguimiento", "", "formatCadena", "-1"},
                {"Cuenta", "identificadorCuenta", "", "formatCadena", "-1"},
                {"Estado Tarjeta", "estadoTarjeta", "", "formatCadena", "-1"},
                {"Moneda Transacción", "moneda", "descMoneda", "formatCadena", "-1"},
                {"Importe Transacción", "importe", "", "formatMonto", "-1"},
                {"Giro Negocio", "descripcionGiroNegocioUnido", "", "formatCadena", "-1"},
                {"Trace", "trace", "", "formatCadena", "-1"},
                {"Código Autorización", "codigoAutorizacion", "", "formatCadena", "-1"},
                {"Descripción Adquirente", "descripcionAdquirente", "", "formatCadena", "-1"},
                {"Institución Adquirente", "institucionAdquirente", "descripcionInstitucionAdquirente", "formatCadena", "-1"},
                {"Institución Respuesta", "institucionSolicitante", "descripcionInstitucionSolicitante", "formatCadena", "-1"},
                {"Fecha SW", "fechaSwitch", "", "formatFecha", "-1"},
                {"Fecha Transacción Local", "fechaTransaccionLocalCadena", "horaTransaccionLocal", "formatCadenaCentrada", "-1"},
                {"Modo Ingreso PAN", "panEntryMode", "descripcionPANEntryMode", "formatCadena", "-1"},
                {"Capacidad Ingreso PAN", "pinEntryCapability", "descripcionPINEntryCapability", "formatCadena", "-1"},
                {"Cta. Cargo", "cuentaCargo", "", "formatCadena", "-1"},
                {"Cta. Abono", "cuentaAbono", "", "formatCadena", "-1"},
                {"Código Analítico", "codigoAnalitico", "", "formatCadena", "-1"},
                {"Membresía", "dMembresia", "descMembresia", "formatCadena", "-1"},
                {"Servicio", "idClaseServicio", "descClaseServicio", "formatCadena", "-1"},
                {"Origen", "idOrigen", "descOrigen", "formatCadena", "-1"},
                {"Clase Transacción", "idClaseTransaccion", "descClaseTransaccion", "formatCadena", "-1"},
                {"Código Transacción", "idCodigoTransaccion", "descCodigoTransaccion", "formatCadena", "-1"},
                {"Ind. Store Forward", "indStoreForward", "", "formatCadena", "-1"},
                {"Cta. From", "cuentaFrom", "", "formatCadena", "-1"},
                {"Cta. To", "cuentaTo", "", "formatCadena", "-1"},
                {"Settlement Currency", "settlementCurrency", "", "formatCadena", "-1"},
                {"Settlement Amount", "settlementAmount", "", "formatMonto", "-1"},
                {"Card Issuer Amount", "cardIssuerAmount", "", "formatMonto", "-1"},
                {"Valor Surcharge", "valorSurcharge", "", "formatMonto", "-1"},
                {"Expiry Date", "expiryDate", "", "formatFecha", "-1"},
                {"Settlement Date", "setllementDate", "", "formatFecha", "-1"},
                {"Conversion Date", "conversionDate", "", "formatFecha", "-1"},
                {"Network Identifier", "networkIdentifier", "", "formatCadena", "-1"},
                {"Sett Amount Transaction Fee", "settAmountTransactionFee", "", "formatComision", "-1"},
                {"Sett Amount Processing Fee", "settAmountProcessingFee", "", "formatComision", "-1"},
                {"Track2 Data", "track2Data", "", "formatCadena", "-1"},
                {"Reference Number", "referenceNumber", "", "formatCadena", "-1"},
                {"Card Acceptor Term ID", "cardAcceptorTermId", "", "formatCadena", "-1"},
                {"Billing Currency", "billingCurrency", "", "formatCadena", "-1"},
                {"Original Message Type", "originalMessageType", "", "formatCadena", "-1"},
                {"Original Trace Number", "originalTraceNumber", "", "formatCadena", "-1"},
                {"Original Date", "originalDate", "", "formatFecha", "-1"},
                {"Original Time", "originalTime", "", "formatCadenaCentrada", "-1"},
                {"Original Adquiring Inst", "originalAdquiringInst", "", "formatCadena", "-1"},
                {"Original Forwarding Inst", "originalForwardingInst", "", "formatCadena", "-1"},
                {"Repla Transaction Amount", "replaTransactionAmount", "", "formatMonto", "-1"},
                {"Repla Settlement Amount", "replaSettlementAmount", "", "formatMonto", "-1"},
                {"Repla Transaction Fee", "replaTransactionFee", "", "formatComision", "-1"},
                {"Repla Settlement Fee", "replaSettlementFee", "", "formatComision", "-1"},
                {"Card Category", "cardCategory", "", "formatCadena", "-1"},
                {"TierII", "tierII", "", "formatCadena", "-1"},
                {"Card Acceptor ID", "cardAcceptorId", "", "formatCadena", "-1"},
                {"Concilia Autorización", "conciliaAutorizacion", "", "formatSiNo", "-1"},
                {"Concilia Log Contable", "conciliaLogContable", "", "formatSiNo", "-1"},
                {"Contab Fondos", "contabFondos", "", "formatSiNo", "-1"},
                {"ATM", "idATM", "", "formatCadena", "-1"},
                {"Fecha Concilia Log", "fechaConciliaLog", "", "formatFecha", "-1"},
        };
        this.exportTxnsSwDmpLog.generarExportacionNormal("Consulta de SwDmpLog - Detallado", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsSwDmpLogPorTipoDocumento(List<TxnsSwDmpLog> list, CriterioBusquedaTipoDocumento criterioBusquedaTransaccionSwDmpLog, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Tipo Documento", criterioBusquedaTransaccionSwDmpLog.getDescripcionTipoDocumento()},
                {"Número Documento", criterioBusquedaTransaccionSwDmpLog.getNumeroDocumento()}
        };
        String[][] cabeceraExportacion = {
                {"Fecha Proceso", "fechaProceso", "", "formatFecha", "-1"},
                {"Institución", "codigoInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
                {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
                {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
                {"BIN", "idBin", "descripcionBin", "formatCadena", "-1"},
                {"Sub BIN", "idSubBin", "descripcionSubBin", "formatCadena", "-1"},
                {"Rol Transacción", "rolTransaccion", "descripcionRol", "formatCadena", "-1"},
                {"Código Proceso", "idProceso", "descripcionProceso", "formatCadena", "-1"},
                {"Código Respuesta", "codigoRespuesta", "descripcionRespuesta", "formatCadena", "-1"},
                {"Canal", "idCanal", "descripcionCanal", "formatCadena", "-1"},
                {"Fecha Transacción", "fechaTransmisionCadena", "horaTransmision", "formatCadenaCentrada", "-1"},
                {"Tipo Mensaje", "tipoMensaje", "", "formatCadena", "-1"},
                {"Tipo Documento", "tipoDocumento", "descTipoDocumento", "formatCadena", "-1"},
                {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
                {"Nombres", "nombres", "", "formatCadena", "-1"},
                {"Apellido Paterno", "apellidoPaterno", "", "formatCadena", "-1"},
                {"Apellido Materno", "apellidoMaterno", "", "formatCadena", "-1"},
                {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
                {"Código Seguimiento", "codigoSeguimiento", "", "formatCadena", "-1"},
                {"Cuenta", "identificadorCuenta", "", "formatCadena", "-1"},
                {"Estado Tarjeta", "estadoTarjeta", "", "formatCadena", "-1"},
                {"Moneda Transacción", "moneda", "descMoneda", "formatCadena", "-1"},
                {"Importe Transacción", "importe", "", "formatMonto", "-1"},
                {"Giro Negocio", "descripcionGiroNegocioUnido", "", "formatCadena", "-1"},
                {"Trace", "trace", "", "formatCadena", "-1"},
                {"Código Autorización", "codigoAutorizacion", "", "formatCadena", "-1"},
                {"Descripción Adquirente", "descripcionAdquirente", "", "formatCadena", "-1"},
                {"Institución Adquirente", "institucionAdquirente", "descripcionInstitucionAdquirente", "formatCadena", "-1"},
                {"Institución Respuesta", "institucionSolicitante", "descripcionInstitucionSolicitante", "formatCadena", "-1"},
                {"Fecha SW", "fechaSwitch", "", "formatFecha", "-1"},
                {"Fecha Transacción Local", "fechaTransaccionLocalCadena", "horaTransaccionLocal", "formatCadenaCentrada", "-1"},
                {"Modo Ingreso PAN", "panEntryMode", "descripcionPANEntryMode", "formatCadena", "-1"},
                {"Capacidad Ingreso PAN", "pinEntryCapability", "descripcionPINEntryCapability", "formatCadena", "-1"},
                {"Cta. Cargo", "cuentaCargo", "", "formatCadena", "-1"},
                {"Cta. Abono", "cuentaAbono", "", "formatCadena", "-1"},
                {"Código Analítico", "codigoAnalitico", "", "formatCadena", "-1"}
        };
        this.exportTxnsSwDmpLog.generarExportacionNormal("Consulta de SwDmpLog - Tipo Documento", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

}