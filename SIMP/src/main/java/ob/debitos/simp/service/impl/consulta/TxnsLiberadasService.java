package ob.debitos.simp.service.impl.consulta;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.ITxnsLiberadasMapper;
import ob.debitos.simp.model.consulta.movimiento.TxnsLiberadas;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionLiberada;
import ob.debitos.simp.service.ITxnsLiberadasService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Truncable(clase = TxnsLiberadas.class)
public class TxnsLiberadasService implements ITxnsLiberadasService
{

    private @Autowired ITxnsLiberadasMapper consultaMapper;

    private @Autowired IExportacionPOIService<TxnsLiberadas> exportTxnsLiberadas;

    private static final String[][] cabeceraExportacion = {
            {"Fecha Proceso", "fechaProceso", "", "formatFecha", "-1"},
            {"Institución", "codigoInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
            {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
            {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
            {"Rol Transacción", "rolTransaccion", "descRolTransaccion", "formatCadena", "-1"},
            {"Origen", "origen", "descripcionOrigen", "formatCadena", "-1"},
            {"Canal", "idCanal", "descCanal", "formatCadena", "-1"},
            {"Código Proceso", "proceso", "descripcionProceso", "formatCadena", "-1"},
            {"Código Respuesta", "codigoRespuesta", "descCodigoRespuesta", "formatCadena", "-1"},
            {"Tipo Mensaje", "tipoMensaje", "", "formatCadena", "-1"},
            {"Motivo", "motivoLiberacion", "descMotivoLiberacion", "formatCadena", "-1"},
            {"Tipo Documento", "tipoDocumento", "descTipoDocumento", "formatCadena", "-1"},
            {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
            {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
            {"Estado", "estadoTarjeta", "descEstadoTarjeta", "formatCadena", "-1"},
            {"Fecha Transacción", "fechaTransaccionCadena", "horaTransaccion", "formatCadenaCentrada", "-1"},
            {"Moneda Autorización", "monedaAutorizacion", "descMonedaAutorizacion", "formatCadena", "-1"},
            {"Importe Autorización", "valorAutorizacion", "", "formatMonto", "-1"},
            {"Modo Entrada POS", "modoEntradaPos", "descModoEntradaPos", "formatCadena", "-1"},
            {"Trace", "trace", "", "formatCadena", "-1"},
            {"Número Referencia", "numeroReferencia", "", "formatCadena", "-1"},
            {"Nombre Afiliado", "nombreAfiliado", "", "formatCadena", "-1"},
            {"Cta. From", "cuentaFrom", "", "formatCadena", "-1"},
            {"Cta. To", "cuentaTo", "", "formatCadena", "-1"},
            {"Conciliación Autorización", "conciliacionAutorizacion", "descConciliacionAutorizacion", "formatCadena", "-1"},
            {"Fecha Captura", "fechaCaptura", "", "formatFecha", "-1"},
            {"Fecha Captura Switch", "fechaCapturaSwitch", "", "formatFecha", "-1"},
            {"Número Documento", "numeroDocumentoLiberada", "", "formatCadena", "-1"},
            {"Código Autorización", "codigoAutorizacion", "", "formatCadena", "-1"}
    };

    @Truncable
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsLiberadas> buscarTxnsLiberadas(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
    {
        return consultaMapper.buscarTxnsLiberadas(criterioBusquedaTipoDocumento);
    }

    @Truncable
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsLiberadas> filtrarTxnsLiberadas(CriterioBusquedaTransaccionLiberada criterioBusquedaTransaccionLiberada)
    {
        return consultaMapper.filtrarTxnsLiberadas(criterioBusquedaTransaccionLiberada);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsLiberadasPorCriterios(List<TxnsLiberadas> list, CriterioBusquedaTransaccionLiberada criterioBusquedaTransaccionLiberada, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso", criterioBusquedaTransaccionLiberada.getDescripcionRangoFechasProceso()},
                {"Institución", criterioBusquedaTransaccionLiberada.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaTransaccionLiberada.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaTransaccionLiberada.getDescripcionCliente()},
                {"Código Proceso", criterioBusquedaTransaccionLiberada.getDescripcionCodigoProceso()},
                {"Canal", criterioBusquedaTransaccionLiberada.getDescripcionCanal()},
                {"Número Tarjeta", criterioBusquedaTransaccionLiberada.getNumeroTarjeta()},
                {"Trace", criterioBusquedaTransaccionLiberada.getTrace()}
        };
        this.exportTxnsLiberadas.generarExportacionNormal("Consulta de Liberadas - Criterios", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsLiberadasPorTipoDocumento(List<TxnsLiberadas> list, CriterioBusquedaTipoDocumento criterioBusquedaTransaccionLiberada, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Tipo Documento", criterioBusquedaTransaccionLiberada.getDescripcionTipoDocumento()},
                {"Número Documento", criterioBusquedaTransaccionLiberada.getNumeroDocumento()}
        };
        this.exportTxnsLiberadas.generarExportacionNormal("Consulta de Liberadas - Tipo Documento", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

}