package ob.debitos.simp.service.impl.consulta;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.mapper.ITxnsWebServicesMapper;
import ob.debitos.simp.model.consulta.movimiento.TxnsPreAutorizadas;
import ob.debitos.simp.model.consulta.movimiento.TxnsWebServices;
import ob.debitos.simp.model.criterio.CriterioBusquedaAuthWSPendiente;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionWebServices;
import ob.debitos.simp.model.mantenimiento.ParametroWS;
import ob.debitos.simp.model.prepago.wshub.api.LoginSimpHub;
import ob.debitos.simp.service.ITxnsWebServicesService;
import ob.debitos.simp.utilitario.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TxnsWebServicesService implements ITxnsWebServicesService
{

    private @Autowired ITxnsWebServicesMapper txnsWebServicesMapper;

    private @Autowired IExportacionPOIService<TxnsWebServices> exportTxnsWebServices;
    private @Autowired IExportacionPOIService<TxnsPreAutorizadas> exportTxnsPreAutorizadas;

    private static final String SIMHUB_USER = "SIMP";
    private static final String SIMHUB_PASS = "SIMP";
    private static final String KEY_RC_OK = "RC";
    private static final String RC_OK = "0";

    private static final String KEY_TOKEN = "TOKEN";

    public static final String PATH_AUTH_SIMPHUB_LOGIN = "auth/login";

    // Control dual
    public static final String PRE_AUTORIZACION = "PRE_AUTORIZACION";
    public static final String CONTROL_EXTORNO = "CONTROL_EXTORNO";
    public static final String AUTORIZAR = "AUTORIZAR";
    public static final String DENEGAR = "DENEGAR";

    @Override
    @Truncable(clase = TxnsWebServices.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsWebServices> buscarPorCriterio(CriterioBusquedaTransaccionWebServices criterio)
    {
        return txnsWebServicesMapper.buscarPorCriterio(criterio);
    }

    @Override
    @Truncable(clase = TxnsWebServices.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TxnsWebServices buscarPorId(String idTransaccion)
    {
        return txnsWebServicesMapper.buscarPorId(idTransaccion);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Object> enviarOperacionSimpHub(Map<String, Object> input, String op)
    {
        String dataInput = JsonUtil.convertObjectToJSONString(input);
        return this.enviarPeticionASimpHub(dataInput, op);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Map<String, Object> enviarOperacionSimpHub(String dataInput, String op)
    {
        return this.enviarPeticionASimpHub(dataInput, op);
    }

    public Map<String, Object> enviarPeticionASimpHub(String data, String op)
    {
        RestTemplate restTemplate = new RestTemplate();
        ParametroWS parametroWS = txnsWebServicesMapper.obtenerURLSIMPHub();
        LoginSimpHub login = LoginSimpHub.builder().idCanal(SIMHUB_USER).password(SIMHUB_PASS).idUsuario(SecurityContextFacade.obtenerNombreUsuario()).build();
        String jsonLogin = JsonUtil.convertObjectToJSONString(login);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonLogin, headers);
        String pathUrlAuthSimpHub = parametroWS.getPathBaseParaConsultaDesdeSIMPWeb() + PATH_AUTH_SIMPHUB_LOGIN;
        try
        {
            ResponseEntity<String> echoResponse = restTemplate.exchange(pathUrlAuthSimpHub, HttpMethod.POST, entity, String.class);
            if (echoResponse.getStatusCode() == HttpStatus.OK)
            {
                Map<String, Object> response = new HashMap<String, Object>();
                ObjectMapper mapper = new ObjectMapper();
                try
                {
                    response = mapper.readValue(echoResponse.getBody(), new TypeReference<Map<String, Object>>()
                    {
                    });
                    if (response.get(KEY_RC_OK).equals(RC_OK))
                    {
                        // llmara al servicio real
                        String token = (String) response.get(KEY_TOKEN);
                        headers.set("Authorization", token);
                        String pathOP = parametroWS.getPathBaseParaConsultaDesdeSIMPWeb() + op;
                        HttpEntity<String> entity2 = new HttpEntity<String>(data, headers);
                        ResponseEntity<String> echoResponse2 = restTemplate.exchange(pathOP, HttpMethod.POST, entity2, String.class);
                        if (echoResponse2.getStatusCode() == HttpStatus.OK)
                        {
                            Map<String, Object> response2 = new HashMap<String, Object>();
                            ObjectMapper mapper2 = new ObjectMapper();
                            response2 = mapper2.readValue(echoResponse2.getBody(), new TypeReference<Map<String, Object>>()
                            {
                            });
                            return response2;
                        } else
                        {
                            Map<String, Object> response3 = new HashMap<String, Object>();
                            response3.put("RC", "602");
                            response3.put("RC_DESC", "ERROR EN PROCESAR LA PETICIÓN EN EL SIMPHUB");
                            return response3;
                        }
                    }
                }
                catch (IOException e)
                {
                    Map<String, Object> response3 = new HashMap<String, Object>();
                    response3.put("RC", "603");
                    response3.put("RC_DESC", "ERROR EN ENVIAR LA PETICIÓN AL SIMPHUB");
                    return response3;
                }

            } else
            {
                Map<String, Object> response3 = new HashMap<String, Object>();
                response3.put("RC", "602");
                response3.put("RC_DESC", "ERROR EN PROCESAR LA PETICIÓN EN EL SIMPHUB");
                return response3;
            }

        }
        catch (HttpClientErrorException e)
        {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("RC", "601");
            response.put("RC_DESC", "PROBLEMAS AL COMUNICARSE CON EL SIMPHUB, REVISAR LOS PARÁMETROS DE CONEXIÓN");
            return response;
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarPreAutorizacion(CriterioBusquedaAuthWSPendiente criterio)
    {
        criterio.setModo(PRE_AUTORIZACION);
        this.txnsWebServicesMapper.mantenerAutorizacionWebServicePendiente(criterio);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void autorizar(CriterioBusquedaAuthWSPendiente criterio)
    {
        criterio.setModo(AUTORIZAR);
        this.txnsWebServicesMapper.mantenerAutorizacionWebServicePendiente(criterio);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void denegar(CriterioBusquedaAuthWSPendiente criterio)
    {
        criterio.setModo(DENEGAR);
        this.txnsWebServicesMapper.mantenerAutorizacionWebServicePendiente(criterio);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void controlExtorno(CriterioBusquedaAuthWSPendiente criterio)
    {
        criterio.setModo(CONTROL_EXTORNO);
        this.txnsWebServicesMapper.mantenerAutorizacionWebServicePendiente(criterio);
    }

    @Truncable(clase = TxnsPreAutorizadas.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TxnsPreAutorizadas> buscarPreAutorizadasPorCriterio(CriterioBusquedaTransaccionWebServices criterio)
    {
        return this.txnsWebServicesMapper.buscarPreAutorizadasPorCriterio(criterio);
    }

    @Truncable(clase = TxnsPreAutorizadas.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TxnsPreAutorizadas buscarPreAutorizadasPorId(Integer id)
    {
        return this.txnsWebServicesMapper.buscarPreAutorizadasPorId(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsWebServicesPorCriterios(List<TxnsWebServices> list, CriterioBusquedaTransaccionWebServices criterioBusquedaTransaccionWebServices, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Transaccion", criterioBusquedaTransaccionWebServices.getDescripcionRangoFechas()},
                {"Institución", criterioBusquedaTransaccionWebServices.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaTransaccionWebServices.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaTransaccionWebServices.getDescripcionCliente()},
                {"Logo", criterioBusquedaTransaccionWebServices.getDescripcionLogoBin()},
                {"Operación", criterioBusquedaTransaccionWebServices.getDescripcionOperacion()},
                {"Número Tarjeta", criterioBusquedaTransaccionWebServices.getNumeroTarjeta()},
                {"Código Seguimiento", criterioBusquedaTransaccionWebServices.getCodigoSeguimiento()},
                {"Número Documento", criterioBusquedaTransaccionWebServices.getNumeroDocumento()}
        };
        String[][] cabeceraExportacion = {
                {"Institución", "idInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
                {"Empresa", "idEmpresa", "descripcionEmpresa", "formatCadena", "-1"},
                {"Cliente", "idCliente", "descripcionCliente", "formatCadena", "-1"},
                {"Membresía", "idMembresia", "descripcionMembresia", "formatCadena", "-1"},
                {"Logo", "idLogo", "descripcionLogoBin", "formatCadena", "-1"},
                {"Message Type", "messageType", "", "formatCadena", "-1"},
                {"Operación", "operacion", "", "formatCadena", "-1"},
                {"ID Transacción", "idTransaccion", "", "formatCadena", "-1"},
                {"ID Persona", "idPersona", "", "formatCadena", "-1"},
                {"Tipo Documento", "tipoDocumento", "descTipoDocumento", "formatCadena", "-1"},
                {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
                {"Nombres", "nombres", "", "formatCadena", "-1"},
                {"Apellido Paterno", "apellidoPaterno", "", "formatCadena", "-1"},
                {"Apellido Materno", "apellidoMaterno", "", "formatCadena", "-1"},
                {"Código Seguimiento", "codigoSeguimiento", "", "formatCadena", "-1"},
                {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
                {"Fecha Transacción", "fechaTransaccionCadena", "horaTransaccion", "formatCadenaCentrada", "-1"},
                {"Trace", "numeroTrace", "", "formatCadena", "-1"},
                {"ID Comercio", "idComercio", "", "formatCadena", "-1"},
                {"Dirección Comercio", "dirComercio", "", "formatCadena", "-1"},
                {"ID Terminal", "idTerminal", "", "formatCadena", "-1"},
                {"Local Time", "localDateCadena", "localTime", "formatCadenaCentrada", "-1"},
                {"Cod. Seg. Origen", "codigoSeguimientoOrigen", "", "formatCadena", "-1"},
                {"Cod. Seg. Destino", "codigoSeguimientoDestino", "", "formatCadena", "-1"},
                {"Éxito", "exito", "", "formatSiNo", "-1"},
                {"Respuesta", "codigoRespuesta", "", "formatCadena", "-1"},
                {"Moneda", "monedaRecarga", "descMonedaRecarga", "formatCadena", "-1"},
                {"Importe", "montoRecarga", "", "formatMonto", "-1"},
                {"Moneda Comisión", "monedaComisionExtra", "descMonedaComisionExtra", "formatCadena", "-1"},
                {"Importe Comisión", "montoComisionExtra", "", "formatComision", "-1"},
                {"Extorno", "indExtorno", "", "formatSiNo", "-1"},
                {"Trace Extorno", "traceExtorno", "", "formatCadena", "-1"},
                {"Usuario Extorno", "usuarioSolitudExtorno", "", "formatCadena", "-1"},
                {"Fecha Solicitud Extorno", "fechaSolicitudExtorno", "", "formatFecha", "-1"},
                {"Solicitó Extorno", "solicitoExtorno", "", "formatSiNo", "-1"}
        };
        this.exportTxnsWebServices.generarExportacionNormal("Consulta de Transacciones de Web Services", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarTxnsPreAutorizadasPorCriterios(List<TxnsPreAutorizadas> list, CriterioBusquedaTransaccionWebServices criterioBusquedaTransaccionWebServices, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Solicitud", criterioBusquedaTransaccionWebServices.getDescripcionRangoFechas()},
                {"Institución", criterioBusquedaTransaccionWebServices.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaTransaccionWebServices.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaTransaccionWebServices.getDescripcionCliente()},
                {"Estado", criterioBusquedaTransaccionWebServices.getDescripcionEstado()},
                {"Número Tarjeta", criterioBusquedaTransaccionWebServices.getNumeroTarjeta()},
                {"Código Seguimiento", criterioBusquedaTransaccionWebServices.getCodigoSeguimiento()}
        };
        String[][] cabeceraExportacion = {
                {"Institución", "idInstitucion", "descInstitucion", "formatCadena", "-1"},
                {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
                {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
                {"Tipo Operación", "tipoOperacion", "", "formatCadena", "-1"},
                {"Operación", "operacion", "", "formatCadena", "-1"},
                {"Código Seguimiento", "codigoSeguimiento", "", "formatCadena", "-1"},
                {"ID", "id", "", "formatCadena", "-1"},
                {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
                {"Tipo Documento", "tipoDocumento", "descTipoDocumento", "formatCadena", "-1"},
                {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
                {"Nombres", "nombres", "", "formatCadena", "-1"},
                {"Apellido Paterno", "apellidoPaterno", "", "formatCadena", "-1"},
                {"Apellido Materno", "apellidoMaterno", "", "formatCadena", "-1"},
                {"Moneda", "monedaTransaccion", "descripcionMonedaTransaccion", "formatCadena", "-1"},
                {"Importe", "montoTransaccion", "", "formatMonto", "-1"},
                {"Estado", "estado", "descripcionEstado", "formatCadena", "-1"},
                {"Fecha Solicitud", "fechaSolicitudCadena", "horaSolicitud", "formatCadenaCentrada", "-1"},
                {"Usuario Solicitud", "usuarioSolicitud", "", "formatCadena", "-1"},
                {"Fecha Aprobación", "fechaAprobacionCadena", "horaAprobacion", "formatCadenaCentrada", "-1"},
                {"Usuario Aprobación", "usuarioAprobacion", "", "formatCadena", "-1"},
                {"Éxito", "exito", "", "formatSiNo", "-1"},
                {"Nro. Reintentos", "numeroReintentos", "", "formatCantidad", "-1"},
                {"ID Transacción", "idTransaccion", "", "formatCadena", "-1"},
                {"Trace", "numeroTrace", "", "formatCadena", "-1"},
                {"Respuesta", "codigoRespuesta", "descripcionRespuesta", "formatCadena", "-1"},
                {"Envío", "fechaEnvioTransaccionCadena", "horaEnvioTransaccion", "formatCadenaCentrada", "-1"},
                {"Recepción", "fechaRecepcionTransaccionCadena", "horaRecepcionTransaccion", "formatCadenaCentrada", "-1"},
                {"Tiempo Total", "tiempoTotal", "", "formatCantidad", "-1"}
        };
        this.exportTxnsPreAutorizadas.generarExportacionNormal("Consulta de Transacciones Pre autorizadas", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

}