package ob.debitos.simp.service.impl.contable;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.controller.excepcion.AlegraException;
import ob.debitos.simp.model.contable.CuentaContableAlegraRequest;
import ob.debitos.simp.model.contable.InterfaceContableAlegraRequest;
import ob.debitos.simp.model.contable.LogInterfaceAlegra;
import ob.debitos.simp.model.criterio.CriterioBusquedaInterfaceContableAlegra;
import ob.debitos.simp.model.mantenimiento.ParametrosInterfaceContable;
import ob.debitos.simp.model.mantenimiento.RespuestaAlegra;
import ob.debitos.simp.service.IAlegraService;
import ob.debitos.simp.service.IInterfaceContableAlegraService;
import ob.debitos.simp.service.IParametroInterfaceContableService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.DatesUtils;
import ob.debitos.simp.utilitario.JsonUtil;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpHeaders;

@Service
public class AlegraService extends MantenibleService<ParametrosInterfaceContable> implements IAlegraService
{

    private @Autowired IParametroInterfaceContableService parametroInterfaceContableService;
    private @Autowired IInterfaceContableAlegraService interfaceContableAlegraService;

    private static final String REGISTRAR_CTA_CONTABLE = "Envío de registro de cuenta contable";
    private static final String EDITAR_CTA_CONTABLE = "Envío de edición de cuenta contable";
    private static final String ASIENTO_COMIS_INGRESO_COMPRA = "Envío de asiento contable ingresos por compras";
    private static final String ASIENTO_COMIS_EGRESO_COMPRA = "Envío de asiento contable egresos por compras";
    private static final String ASIENTO_PAGOS_ADQ = "Envío de asiento contable pagos al adquirente";
    private static final String ASIENTO_COMIS_AL_THB = "Envío de asiento contable ingresos por comisión al tarjetahabiente";
    private static final String ASIENTO_MISCELANEOS_UBA = "Envío de asiento contable misceláneos UNIBANCA";
    private static final String ASIENTO_OTROS_CONCEPTOS_MAXIMO = "Envío de asiento contable otros conceptos MAXIMO";

    @Override
    public RespuestaAlegra crearCuentaContable(CuentaContableAlegraRequest cuentaContableAlegraRequest) throws URISyntaxException
    {

        LogInterfaceAlegra logInterfaceAlegra = LogInterfaceAlegra.builder().build();

        List<ParametrosInterfaceContable> lista = parametroInterfaceContableService.buscarTodos();
        ParametrosInterfaceContable resultado = lista.get(0);

        String usuario = resultado.getUsuarioAlegra().trim();
        String token = resultado.getTokenAlegra().trim();
        String url = resultado.getCuentaContableURI().trim();

        String originalInput = usuario + ":" + token;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        String base64 = "Basic " + encodedString;

        logInterfaceAlegra.setUriResquest(url);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", base64);

        String jsonRequest = JsonUtil.convertObjectToJSONString(cuentaContableAlegraRequest);

        HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);

        logInterfaceAlegra.setFechaRequest(DatesUtils.obtenerFechaDelSistemaEnDateTimeString());
        logInterfaceAlegra.setJsonRequest(jsonRequest);

        RespuestaAlegra respuestaAlegra = new RespuestaAlegra();

        try
        {
            ResponseEntity<String> echoResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (echoResponse.getStatusCode() == HttpStatus.CREATED)
            {
                Map<String, Object> objeto = JsonUtil.convertJSONStringToMapObject(echoResponse.getBody());

                String idCuentaAlegra = (String) objeto.get("id");
                respuestaAlegra.setIdCuentaAlegra(idCuentaAlegra);
                respuestaAlegra.setDescripcion("Registro de cuenta exitosa");
                respuestaAlegra.setCodigo("201");

                logInterfaceAlegra.setHttpStatus(echoResponse.getStatusCode().toString());
                logInterfaceAlegra.setFechaResponse(DatesUtils.obtenerFechaDelSistemaEnDateTimeString());
                logInterfaceAlegra.setJsonResponse(echoResponse.getBody().toString());
                logInterfaceAlegra.setExito(1);
                interfaceContableAlegraService.registrarLogAPIAlegra(logInterfaceAlegra);

                return respuestaAlegra;

            } else if (echoResponse.getStatusCode() == HttpStatus.BAD_REQUEST)
            {

                Map<String, Object> objeto = JsonUtil.convertJSONStringToMapObject(echoResponse.getBody());

                respuestaAlegra.setDescripcion((String) objeto.get("message"));
                respuestaAlegra.setCodigo((String) objeto.get("code"));

                logInterfaceAlegra.setHttpStatus(echoResponse.getStatusCode().toString());
                logInterfaceAlegra.setFechaResponse(DatesUtils.obtenerFechaDelSistemaEnDateTimeString());
                logInterfaceAlegra.setJsonResponse(echoResponse.getBody().toString());
                logInterfaceAlegra.setExito(0);
                interfaceContableAlegraService.registrarLogAPIAlegra(logInterfaceAlegra);

                return respuestaAlegra;

            } else
            {
                respuestaAlegra.setDescripcion("Problemas al integrarse con Alegra. Consultar con su administrador");
                respuestaAlegra.setCodigo(echoResponse.getStatusCode().toString());

                logInterfaceAlegra.setHttpStatus(echoResponse.getStatusCode().toString());
                logInterfaceAlegra.setFechaResponse(DatesUtils.obtenerFechaDelSistemaEnDateTimeString());
                logInterfaceAlegra.setJsonResponse(echoResponse.getBody().toString());
                logInterfaceAlegra.setExito(0);
                interfaceContableAlegraService.registrarLogAPIAlegra(logInterfaceAlegra);

                return respuestaAlegra;
            }

        } catch (HttpStatusCodeException e)
        {
            respuestaAlegra.setCodigo(e.getStatusCode().toString());
            if (e.getStatusCode().toString().equals("400"))
            {
                respuestaAlegra.setDescripcion("El request es incorrecto");
            } else if (e.getStatusCode().toString().equals("404"))
            {
                respuestaAlegra.setDescripcion("No se encontro la API o la cuenta esta suspendida");
            } else if (e.getStatusCode().toString().equals("500"))
            {
                respuestaAlegra.setDescripcion("Hubo un error con la aplicacion");
            } else if (e.getStatusCode().toString().equals("401"))
            {
                respuestaAlegra.setDescripcion("Error en autenticación. La autenticación fallo o no se encontró la información para autenticar el request.");
            } else if (e.getStatusCode().toString().equals("402"))
            {
                respuestaAlegra.setDescripcion("La acción no se pudo realizar exitosamente ya que la cuenta se encuentra suspendida o el plan actual de la compañía no permite realizar la acción.");
            } else if (e.getStatusCode().toString().equals("503"))
            {
                respuestaAlegra.setDescripcion("Servicio no disponible. Eje: Modo mantenimiento de la aplicación.");
            } else
            {
                respuestaAlegra.setDescripcion("Ocurrió un error no identificado");
            }
            logInterfaceAlegra.setHttpStatus(e.getStatusCode().toString());
            logInterfaceAlegra.setFechaResponse(DatesUtils.obtenerFechaDelSistemaEnDateTimeString());
            logInterfaceAlegra.setExito(0);
            logInterfaceAlegra.setTraceError(e.getMessage());
            e.printStackTrace();
            interfaceContableAlegraService.registrarLogAPIAlegra(logInterfaceAlegra);
        }
        return respuestaAlegra;

    }

    @Override
    public String editarCuentaContable(CuentaContableAlegraRequest cuentaContableAlegraRequest)
    {

        RestTemplate restTemplate = new RestTemplate();

        List<ParametrosInterfaceContable> lista = parametroInterfaceContableService.buscarTodos();
        ParametrosInterfaceContable resultado = lista.get(0);

        String usuario = resultado.getUsuarioAlegra().trim();
        String token = resultado.getTokenAlegra().trim();
        String url = resultado.getCuentaContableURI().trim() + "/" + cuentaContableAlegraRequest.getId();
        String originalInput = usuario + ":" + token;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        String base64 = "Basic " + encodedString;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", base64);

        HttpEntity<String> entity = new HttpEntity<String>(JsonUtil.convertObjectToJSONString(cuentaContableAlegraRequest), headers);

        ResponseEntity<String> echoResponse = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        if (echoResponse.getStatusCode() == HttpStatus.OK)
        {

            return null;
        }

        return null;

    }

    @SuppressWarnings("finally")
    @Override
    public void enviarInterfaceContableAlegra(CriterioBusquedaInterfaceContableAlegra criterio, Integer modo) throws URISyntaxException, AlegraException
    {

        List<InterfaceContableAlegraRequest> listaInterfaceAlegra = new ArrayList<>();

        LogInterfaceAlegra logInterfaceAlegra = LogInterfaceAlegra.builder().build();
        logInterfaceAlegra.setUsuario(SecurityContextFacade.obtenerNombreUsuario());

        RestTemplate restTemplate = new RestTemplate();

        List<ParametrosInterfaceContable> lista = parametroInterfaceContableService.buscarTodos();
        ParametrosInterfaceContable resultado = lista.get(0);

        String usuario = resultado.getUsuarioAlegra().trim();
        String token = resultado.getTokenAlegra().trim();
        String url = resultado.getPagoURI().trim();
        String originalInput = usuario + ":" + token;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        String base64 = "Basic " + encodedString;

        logInterfaceAlegra.setUriResquest(url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", base64);

        switch (modo)
        {
        case 1:
            listaInterfaceAlegra = interfaceContableAlegraService.interfaceAlegraComisInCompras(criterio);
            logInterfaceAlegra.setIdEnvio("1");
            logInterfaceAlegra.setDescripcion(ASIENTO_COMIS_INGRESO_COMPRA);
            break;
        case 2:
            listaInterfaceAlegra = interfaceContableAlegraService.interfaceAlegraComisOutCompras(criterio);
            logInterfaceAlegra.setIdEnvio("2");
            logInterfaceAlegra.setDescripcion(ASIENTO_COMIS_EGRESO_COMPRA);
            break;
        case 3:
            listaInterfaceAlegra = interfaceContableAlegraService.interfaceAlegraCompOutAdquiCompras(criterio);
            logInterfaceAlegra.setIdEnvio("3");
            logInterfaceAlegra.setDescripcion(ASIENTO_PAGOS_ADQ);
            break;
        case 4:
            listaInterfaceAlegra = interfaceContableAlegraService.interfaceAlegraComisInTHB(criterio);
            logInterfaceAlegra.setIdEnvio("4");
            logInterfaceAlegra.setDescripcion(ASIENTO_COMIS_AL_THB);
        case 5:
            listaInterfaceAlegra = interfaceContableAlegraService.interfaceAlegraMiscelaneoUBA(criterio);
            logInterfaceAlegra.setIdEnvio("5");
            logInterfaceAlegra.setDescripcion(ASIENTO_MISCELANEOS_UBA);
        case 6:
            listaInterfaceAlegra = interfaceContableAlegraService.interfaceAlegraOtrosConceptosMaximo(criterio);
            logInterfaceAlegra.setIdEnvio("6");
            logInterfaceAlegra.setDescripcion(ASIENTO_OTROS_CONCEPTOS_MAXIMO);
            break;

        }
        listaInterfaceAlegra.forEach(objeto -> {
            String jsonRequest = JsonUtil.convertObjectToJSONString(objeto);
            HttpEntity<String> entity = new HttpEntity<String>(jsonRequest, headers);
            logInterfaceAlegra.setFechaRequest(DatesUtils.obtenerFechaDelSistemaEnDateTimeString());
            logInterfaceAlegra.setJsonRequest(jsonRequest);
            try
            {
                ResponseEntity<String> echoResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                logInterfaceAlegra.setHttpStatus(echoResponse.getStatusCode().toString());
                logInterfaceAlegra.setFechaResponse(DatesUtils.obtenerFechaDelSistemaEnDateTimeString());
                logInterfaceAlegra.setJsonResponse(echoResponse.getBody().toString());
                logInterfaceAlegra.setTraceError(null);
                logInterfaceAlegra.setExito(1);
                interfaceContableAlegraService.registrarLogAPIAlegra(logInterfaceAlegra);
            } catch (HttpStatusCodeException error)
            {
                logInterfaceAlegra.setHttpStatus(error.getStatusCode().toString());
                logInterfaceAlegra.setFechaResponse(DatesUtils.obtenerFechaDelSistemaEnDateTimeString());
                logInterfaceAlegra.setExito(0);
                logInterfaceAlegra.setTraceError(error.getMessage());
                error.printStackTrace();
                interfaceContableAlegraService.registrarLogAPIAlegra(logInterfaceAlegra);
                throw new AlegraException(-80);
            }
        });
    }

}
