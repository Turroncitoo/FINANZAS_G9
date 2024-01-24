package ob.debitos.simp.service.impl.consulta;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.ITransaccionObservadaMapper;
import ob.debitos.simp.model.ajuste.ActualizacionExtornoDevolucion;
import ob.debitos.simp.model.ajuste.ActualizacionTrace;
import ob.debitos.simp.model.consulta.movimiento.TransaccionObservada;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionObservada;
import ob.debitos.simp.model.parametro.MensajeValidacion;
import ob.debitos.simp.service.ITxnsObservadasService;
import ob.debitos.simp.service.excepcion.BadRequestException;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.service.excepcion.ValorNoEncontradoException;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.Verbo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TransaccionObservadaService implements ITxnsObservadasService
{

    private @Autowired ITransaccionObservadaMapper transaccionObservadaMapper;

    private @Autowired IExportacionPOIService<TransaccionObservada> exportTxnsObservadas;

    private static final String[][] cabeceraExportacion = {
            {"Fecha Proceso", "fechaProceso", "", "formatFecha", "-1"},
            {"Institución", "codigoInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
            {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
            {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
            {"Rol Transacción", "idRolTransaccion", "descripcionRolTransaccion", "formatCadena", "-1"},
            {"Origen Archivo", "idOrigenArchivo", "descripcionOrigenArchivo", "formatCadena", "-1"},
            {"Código Proceso", "codigoProcesoSwitch", "descripcionProcesoSwitch", "formatCadena", "-1"},
            {"Código Respuesta", "codigoRespuestaSwitch", "descripcionCodigoRespuestaSwitch", "formatCadena", "-1"},
            {"Canal", "idCanal", "descripcionCanal", "formatCadena", "-1"},
            {"Tipo Documento", "codigoTipoDocumento", "descripcionTipoDocumento", "formatCadena", "-1"},
            {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
            {"ID Persona", "idPersona", "", "formatCadena", "-1"},
            {"Nombres", "nombres", "", "formatCadena", "-1"},
            {"Apellido Paterno", "apellidoPaterno", "", "formatCadena", "-1"},
            {"Apellido Materno", "apellidoMaterno", "", "formatCadena", "-1"},
            {"Número Cuenta", "numeroCuenta", "", "formatCadena", "-1"},
            {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
            {"Estado", "estado", "descEstado", "formatCadena", "-1"},
            {"Moneda Cargo", "monedaCargoThb", "descMonedaCargoThb", "formatCadena", "-1"},
            {"Importe Cargo", "montoCargoThb", "", "formatMonto", "-1"},
            {"Fecha Transacción", "fechaTransaccionCadena", "horaTransaccion", "formatCadenaCentrada", "-1"},
            {"Fecha Switch", "fechaSwitch", "", "formatFecha", "-1"},
            {"Trace", "numeroTrace", "", "formatCadena", "-1"},
            {"Sin Autorización", "sinAutorizacion", "", "formatSiNo", "-1"},
            {"Código Autorización", "autorizacion", "", "formatCadena", "-1"},
            {"Nombre Adquirente", "nombreAdquirente", "", "formatCadena", "-1"},
            {"Moneda Autorización", "codigoMonedaTransaccion", "descripcionMonedaTransaccion", "formatCadena", "-1"},
            {"Importe Autorización", "valorTransaccion", "", "formatMonto", "-1"},
            {"Pendiente Compensar", "pendienteCompensar", "", "formatSiNo", "-1"},
            {"Días Pendiente", "diasPendiente", "", "formatCantidad", "-1"},
            {"Moneda Compensación", "txnCurrencyComp", "descTxnCurrencyComp", "formatCadena", "-1"},
            {"Importe Compensación", "txnAmountComp", "", "formatMonto", "-1"},
            {"Fecha Compensación", "fechaCompensacion", "", "formatFecha", "-1"},
            {"Indicador Devolución", "idIndicadorDevolucion", "descripcionIndicadorDevolucion", "formatCadena", "-1"},
            {"Indicador Extorno", "idIndicadorExtorno", "descripcionIndicadorExtorno", "formatCadena", "-1"},
            {"Indicador Conciliación", "idIndicadorConciliacion", "descripcionIndicadorConciliacion", "formatCadena", "-1"},
            {"Fecha Registro", "fechaRegistroCadena", "horaRegistro", "formatCadenaCentrada", "-1"},
            {"Fecha Regularización", "fechaRegulCadena", "horaRegul", "formatCadenaCentrada", "-1"},
            {"Secuencia", "idSecuencia", "", "formatCadena", "-1"}
    };

    @Truncable(clase = TransaccionObservada.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TransaccionObservada> buscarTransaccionesObservadasPorCriterios(CriterioBusquedaTransaccionObservada criterioBusqueda)
    {
        return transaccionObservadaMapper.buscarTransaccionesObservadasPorCriterios(criterioBusqueda);
    }

    @Truncable(clase = TransaccionObservada.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TransaccionObservada> buscarTransaccionesObservadasPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
    {
        return transaccionObservadaMapper.buscarTransaccionesObservadasPorTipoDocumento(criterioBusquedaTipoDocumento);
    }

    @Truncable(clase = TransaccionObservada.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TransaccionObservada> buscarConciliacionObservada(CriterioBusquedaTransaccionObservada criterioBusqueda)
    {
        return transaccionObservadaMapper.buscarConciliacionObservada(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarTrace(ActualizacionTrace actualizaTraceDTO)
    {
        try
        {
            transaccionObservadaMapper.actualizarTrace(actualizaTraceDTO);
        } catch (Exception e)
        {
            throw new SimpException(e.getCause().getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarExtornoDevolucion(ActualizacionExtornoDevolucion actualizacionExtornoDevolucion)
    {
        if (!this.existeTransaccionObservada(actualizacionExtornoDevolucion))
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.TRANSACCION_OBSERVADA_NO_ENCONTRADA);
        }
        actualizacionExtornoDevolucion.setVerbo(Verbo.UPDATE);
        transaccionObservadaMapper.mantenerExtornoDevolucion(actualizacionExtornoDevolucion);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeTransaccionObservada(ActualizacionExtornoDevolucion actualizacionExtornoDevolucion)
    {
        boolean existeTransaccionObservada = false;
        actualizacionExtornoDevolucion.setVerbo(Verbo.EXIST);
        List<ActualizacionExtornoDevolucion> txn = transaccionObservadaMapper.mantenerExtornoDevolucion(actualizacionExtornoDevolucion);
        if (!txn.isEmpty())
        {
            this.validarFechaActualizacion(txn.get(0).getFechaRegistro(), actualizacionExtornoDevolucion.getFechaActualizacion());
            existeTransaccionObservada = true;
        }
        return existeTransaccionObservada;
    }

    public void validarFechaActualizacion(Date fechaRegistro, Date fechaActualizacion)
    {
        if (fechaRegistro != null && !(fechaActualizacion.equals(fechaRegistro) || fechaActualizacion.after(fechaRegistro)))
        {
            throw new BadRequestException(Arrays.asList(new MensajeValidacion("fechaActualizacion", ConstantesExcepciones.FECHA_ACTUALIZACION_INVALIDA)));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsObservadasPorCriterios(List<TransaccionObservada> list, CriterioBusquedaTransaccionObservada criterioBusquedaTransaccionObservada, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso", criterioBusquedaTransaccionObservada.getDescripcionRangoFechasProceso()},
                {"Institución", criterioBusquedaTransaccionObservada.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaTransaccionObservada.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaTransaccionObservada.getDescripcionCliente()},
                {"Número Tarjeta", criterioBusquedaTransaccionObservada.getNumeroTarjeta()},
                {"Trace", criterioBusquedaTransaccionObservada.getNumeroTrace()},
                {"Indicador Devolución", criterioBusquedaTransaccionObservada.getDescripcionIndicadorDevolucion()},
                {"Indicador Extorno", criterioBusquedaTransaccionObservada.getDescripcionIndicadorExtorno()},
                {"Indicador Conciliación", criterioBusquedaTransaccionObservada.getDescripcionIndicadorConciliacion()},
        };
        this.exportTxnsObservadas.generarExportacionNormal("Consulta de Transacciones Observadas - Criterios", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsObservadasPorTipoDocumento(List<TransaccionObservada> list, CriterioBusquedaTipoDocumento criterioBusquedaTransaccionObservada, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Tipo Documento", criterioBusquedaTransaccionObservada.getDescripcionTipoDocumento()},
                {"Número Documento", criterioBusquedaTransaccionObservada.getNumeroDocumento()}
        };
        this.exportTxnsObservadas.generarExportacionNormal("Consulta de Transacciones Observadas - Tipo Documento", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

}
