package ob.debitos.simp.service.impl.prepago;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.mapper.IConsultaServicioWebMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaServicioWeb;
import ob.debitos.simp.model.prepago.wshub.ConsultaServicioWeb;
import ob.debitos.simp.model.prepago.wshub.LogControlWS;
import ob.debitos.simp.service.IConsultaServicioWebService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ConsultaServicioWebService implements IConsultaServicioWebService
{

    private @Autowired IConsultaServicioWebMapper consultaServicioWebMapper;

    private @Autowired IExportacionPOIService<LogControlWS> exportLogControlWS;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ConsultaServicioWeb> buscarTodos()
    {
        return consultaServicioWebMapper.buscarTodos();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ConsultaServicioWeb> buscarPorCriterios(CriterioBusquedaConsultaServicioWeb criterioBusqueda)
    {
        return consultaServicioWebMapper.buscarPorCriterios(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ConsultaServicioWeb> buscarPorEvento(CriterioBusquedaConsultaServicioWeb criterio)
    {
        return consultaServicioWebMapper.buscarPorEvento(criterio);
    }

    @Truncable(clase = LogControlWS.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LogControlWS> buscarLogControlEnLinea(CriterioBusquedaConsultaServicioWeb criterio)
    {
        return this.consultaServicioWebMapper.buscarLogControlEnLinea(criterio);
    }

    @Truncable(clase = LogControlWS.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LogControlWS> buscarLogControlHistorico(CriterioBusquedaConsultaServicioWeb criterio)
    {
        return this.consultaServicioWebMapper.buscarLogControlHistorico(criterio);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarLogControlHistorico(List<LogControlWS> list, CriterioBusquedaConsultaServicioWeb criterioBusquedaConsultaServicioWeb, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Transacción", criterioBusquedaConsultaServicioWeb.getDescripcionRangoFechas()},
                {"Institución", criterioBusquedaConsultaServicioWeb.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaConsultaServicioWeb.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaConsultaServicioWeb.getDescripcionCliente()},
                {"Operación", criterioBusquedaConsultaServicioWeb.getDescripcionOperacion()},
                {"Número Tarjeta", criterioBusquedaConsultaServicioWeb.getNumeroTarjeta()},
                {"Código Seguimiento", criterioBusquedaConsultaServicioWeb.getCodigoSeguimiento()},
                {"Número Documento", criterioBusquedaConsultaServicioWeb.getNumeroDocumento()},
                {"Dirección IP", criterioBusquedaConsultaServicioWeb.getDireccionIP()},
                {"Éxito Cliente", criterioBusquedaConsultaServicioWeb.getDescripcionExitoCliente()},
                {"Éxito UBA", criterioBusquedaConsultaServicioWeb.getDescripcionExitoUBA()},
                {"Éxito SIMP", criterioBusquedaConsultaServicioWeb.getDescripcionExitoSIMP()},
        };
        String[][] cabeceraExportacion = {
                {"Fecha Transacción", "fechaTransaccionCadena", "hora", "formatCadenaCentrada", "-1"},
                {"Institución", "idInstitucion", "descInstitucion", "formatCadena", "-1"},
                {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
                {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
                {"Secuencia", "secuencia", "", "formatCadena", "-1"},
                {"ID Transacción", "transaccion", "", "formatCadena", "-1"},
                {"Código Seguimiento", "codigoSeguimiento", "", "formatCadena", "-1"},
                {"Tipo Documento", "tipoDocumento", "descripcionTipoDocumento", "formatCadena", "-1"},
                {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
                {"Nombre Cliente", "nombreCliente", "", "formatCadena", "-1"},
                {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
                {"Moneda Transacción", "monedaTransaccion", "descripcionMonedaTransaccion", "formatCadena", "-1"},
                {"Importe Transacción", "montoTransaccion", "", "formatMonto", "-1"},
                {"Canal", "idCanal", "", "formatCadena", "-1"},
                {"Usuario", "usuario", "", "formatCadena", "-1"},
                {"Operación", "operacion", "", "formatCadena", "-1"},
                {"Trace", "numeroTrace", "", "formatCadena", "-1"},
                {"Dirección IP", "direccionIP", "", "formatCadena", "-1"},
                {"Tipo UTC", "tiempoUTC", "", "formatCadena", "-1"},
                {"Éxito Cliente", "exitoCliente", "", "formatSiNo", "-1"},
                {"Respuesta Cliente", "codigoRespuestaCliente", "descripcionRespuestaCliente", "formatCadena", "-1"},
                {"Fecha Solicitud Cliente", "fechaRequestClientCadena", "horaRequestClient", "formatCadenaCentrada", "-1"},
                {"Fecha Respuesta Cliente", "fechaResponseClientCadena", "horaResponseClient", "formatCadenaCentrada", "-1"},
                {"Tiempo SIMPhub (ms)", "tiempoSIMPHub", "", "formatCantidad", "-1"},
                {"Éxito UBA", "exitoUBA", "", "formatSiNo", "-1"},
                {"Respuesta UBA", "codigoRespuestaUBA", "descripcionRespuestaUBA", "formatCadena", "-1"},
                {"Fecha Solicitud UBA", "fechaRequestUBACadena", "horaRequestUBA", "formatCadenaCentrada", "-1"},
                {"Fecha Respuesta UBA", "fechaResponseUBACadena", "horaResponseUBA", "formatCadenaCentrada", "-1"},
                {"Tiempo UBA (ms)", "tiempoRespuestaUBA", "", "formatCantidad", "-1"},
                {"Éxito SIMP", "exitoSIMP", "", "formatSiNo", "-1"},
                {"Respuesta SIMP", "codigoRespuestaSP", "descripcionRespuestaSP", "formatCadena", "-1"},
                {"Nombre SP", "nombreSP", "", "formatCadena", "-1"},
                {"Línea Error", "lineaErrorSP", "", "formatCadena", "-1"},
                {"Tiempo Total (ms)", "tiempoTotal", "", "formatCantidad", "-1"}
        };
        this.exportLogControlWS.generarExportacionNormal("Log de Control Web Services - Historial", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }
}
