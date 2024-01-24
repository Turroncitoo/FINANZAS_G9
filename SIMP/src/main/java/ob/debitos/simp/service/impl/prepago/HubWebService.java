package ob.debitos.simp.service.impl.prepago;

import static ob.debitos.simp.utilitario.ConstantesWS.OP_WS_CONSULTA_MOVIMIENTOS;
import static ob.debitos.simp.utilitario.ConstantesWS.OP_WS_CONSULTA_SALDO;
import static ob.debitos.simp.utilitario.ConstantesWS.RUTA_SIMP_HUB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import ob.debitos.simp.mapper.ITarjetaPPMapper;
import ob.debitos.simp.model.prepago.TarjetaPP;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaFiltroPrepago;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaRangoFechaPrepago;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.prepago.wshub.ConsultaMovimientos;
import ob.debitos.simp.model.prepago.wshub.ConsultaSaldo;
import ob.debitos.simp.model.prepago.wshub.MovimientoPP;
import ob.debitos.simp.model.prepago.wshub.ParametrosEntrada;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWS;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWSConsultaCliente;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWSRegistroCliente;
import ob.debitos.simp.model.prepago.wshub.solicitud.SolicitudWSActivacion;
import ob.debitos.simp.model.prepago.wshub.solicitud.SolicitudWSAsociacion;
import ob.debitos.simp.model.prepago.wshub.solicitud.SolicitudWSBloqueos;
import ob.debitos.simp.model.prepago.wshub.solicitud.SolicitudWSConsultarPorTarjeta;
import ob.debitos.simp.model.prepago.wshub.solicitud.SolicitudWSReasignacionTarjeta;
import ob.debitos.simp.model.prepago.wshub.solicitud.SolicitudWSRecarga;
import ob.debitos.simp.model.prepago.wshub.solicitud.SolicitudWSRegistroCliente;
import ob.debitos.simp.model.prepago.wshub.solicitud.SolicitudWSSaldoMovimientos;
import ob.debitos.simp.service.IHubWebService;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.service.IPersonaPPService;
import ob.debitos.simp.service.excepcion.ErrorConexionException;
import ob.debitos.simp.service.excepcion.ErrorConversionException;
import ob.debitos.simp.service.excepcion.ValorNoEncontradoException;
import ob.debitos.simp.utilitario.CollectionUtil;
import ob.debitos.simp.utilitario.Constantes;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.ConstantesWS;
import ob.debitos.simp.utilitario.ConstantesWS.AccionesWS;
import ob.debitos.simp.utilitario.ConstantesWS.RecursosWS;
import ob.debitos.simp.utilitario.DatesUtils;
import ob.debitos.simp.utilitario.JsonUtil;
import ob.debitos.simp.utilitario.StringsUtils;

@Service
public class HubWebService implements IHubWebService
{

    @Autowired
    private MessageSourceAccessor mensajes;

    @Autowired
    private ITarjetaPPMapper tarjetaPPMapper;

    @Autowired
    private IMultiTabDetService multiTabDetService;

    private @Autowired IPersonaPPService personaPPService;

    private @Autowired Logger logger;

    /*
     * @Autowired private RestTemplate restTemplate;
     */
    @Override
    public ConsultaSaldo buscarSaldo(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumento)
    {
        List<String> codigoSeguimiento = tarjetaPPMapper.buscarCodigoSeguimientoPorDocumento(criterioBusquedaTipoDocumento);
        return buscarSaldo(CollectionUtil.obtenerUnicoElemento(codigoSeguimiento).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta")));
    }

    @Override
    public ConsultaSaldo buscarSaldo(CriterioBusquedaFiltroPrepago criterioBusquedaMovimientosPP)
    {
        List<String> codigoSeguimiento = tarjetaPPMapper.buscarCodigoSeguimientoPorTarjeta(criterioBusquedaMovimientosPP);
        return buscarSaldo(CollectionUtil.obtenerUnicoElemento(codigoSeguimiento).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta")));
    }

    @Override
    public ConsultaSaldo buscarSaldo(String codigoSeguimiento)
    {
        ConsultaSaldo saldo;
        if (!codigoSeguimiento.isEmpty())
        {
            SolicitudWSSaldoMovimientos request = SolicitudWSSaldoMovimientos.builder().codigoSeguimiento(codigoSeguimiento).operacion(mensajes.getMessage(ConstantesWS.OP_WS_CONSULTA_SALDO)).secHash(ConstantesWS.SEC_HASH).build();
            RestTemplate restTemplate = new RestTemplate();
            String json = restTemplate.postForObject(obtenerRutaRec(RecursosWS.SALDO), request, String.class);
            try
            {
                saldo = convertirASaldo(JsonUtil.jsonToMap(json));
            } catch (JSONException e)
            {
                throw new ErrorConversionException(ConstantesExcepciones.ERROR_CONVERSION_DATOS + " " + e.getMessage());
            }
        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }
        return saldo;
    }

    private ConsultaSaldo convertirASaldo(Map<String, Object> jsonToMap)
    {
        return ConsultaSaldo.builder().monto(jsonToMap.get("MONTO_2") != null ? String.format(Constantes.NUM_FMT_DOS_CIFRAS_DECIMALES, Double.parseDouble((String) jsonToMap.get("MONTO_2")) / 100) : null)
                .moneda(jsonToMap.get("MONEDA") != null ? (String) jsonToMap.get("MONEDA") : null).build();
    }

    @Override
    public ConsultaMovimientos buscarMovimientos(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumento)
    {
        List<String> codigoSeguimiento = tarjetaPPMapper.buscarCodigoSeguimientoPorDocumento(criterioBusquedaTipoDocumento);
        if (!codigoSeguimiento.isEmpty())
        {
            return buscarMovimientos(CollectionUtil.obtenerUnicoElemento(codigoSeguimiento).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta")));
        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }

    }

    @Override
    public ConsultaMovimientos buscarMovimientos(CriterioBusquedaFiltroPrepago criterioBusquedaMovimientosPP)
    {
        ConsultaMovimientos consultaMovimientos = null;
        List<String> codigoSeguimiento = tarjetaPPMapper.buscarCodigoSeguimientoPorTarjeta(criterioBusquedaMovimientosPP);

        if (!codigoSeguimiento.isEmpty())
        {
            consultaMovimientos = buscarMovimientos(CollectionUtil.obtenerUnicoElemento(codigoSeguimiento).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta")));

            if (criterioBusquedaMovimientosPP.getFechaInicio() != null && criterioBusquedaMovimientosPP.getFechaFin() != null)
            {

                /*
                 * consultaMovimientos.setMovimientos(consultaMovimientos.
                 * getMovimientos().stream().filter (m -> ( //
                 * m.getFecha().compareTo(criterioBusquedaMovimientosPP.
                 * getFechaInicio()) >= 0 &&
                 * //m.getFecha().compareTo(criterioBusquedaMovimientosPP.
                 * getFechaFin( )) <= 0 )) .collect(Collectors.toList()));
                 */
            }
        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }
        return consultaMovimientos;
    }

    @Override
    public ConsultaMovimientos buscarMovimientos(String codigoSeguimiento)
    {
        ConsultaMovimientos consultaMovimiento;
        if (!codigoSeguimiento.isEmpty())
        {
            SolicitudWSSaldoMovimientos request = SolicitudWSSaldoMovimientos.builder().codigoSeguimiento(codigoSeguimiento).operacion(mensajes.getMessage(ConstantesWS.OP_WS_CONSULTA_MOVIMIENTOS)).secHash(ConstantesWS.SEC_HASH).build();

            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.postForObject(obtenerRutaRec(RecursosWS.MOVIMIENTOS), request, String.class);

            if (jsonString == null)
            {
                throw new ErrorConexionException("No se obtuvo respuesta de web service");
            }

            try
            {
                consultaMovimiento = convertirAConsultaMovimiento(JsonUtil.jsonToMap(jsonString));
            } catch (JSONException e)
            {
                throw new ErrorConversionException(ConstantesExcepciones.ERROR_CONVERSION_DATOS + " " + e.getMessage());
            }

        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }
        return consultaMovimiento;
    }

    private String obtenerRutaRec(RecursosWS recurso)
    {
        String rutaRecurso = null;
        switch (recurso)
        {
        case MOVIMIENTOS:
            rutaRecurso = StringsUtils.concatenar(mensajes.getMessage(RUTA_SIMP_HUB), mensajes.getMessage(OP_WS_CONSULTA_MOVIMIENTOS).toLowerCase());
            break;
        case SALDO:
            rutaRecurso = StringsUtils.concatenar(mensajes.getMessage(RUTA_SIMP_HUB), mensajes.getMessage(OP_WS_CONSULTA_SALDO).toLowerCase());
            break;
        default:
            break;
        }
        return rutaRecurso;
    }

    private String obtenerRuta(AccionesWS accion)
    {
        String rutaRecurso = null;
        switch (accion)
        {
        case RECARGAR:
            rutaRecurso = StringsUtils.concatenar(mensajes.getMessage(RUTA_SIMP_HUB), mensajes.getMessage(ConstantesWS.OP_WS_RECARGAR_TARJETA).toLowerCase());
            break;
        case ACTIVAR:
            rutaRecurso = StringsUtils.concatenar(mensajes.getMessage(RUTA_SIMP_HUB), mensajes.getMessage(ConstantesWS.OP_WS_ACTIVAR_TARJETA).toLowerCase());
            break;
        case REASIGNAR:
            rutaRecurso = StringsUtils.concatenar(mensajes.getMessage(RUTA_SIMP_HUB), mensajes.getMessage(ConstantesWS.OP_WS_REASIGNAR_TARJETA).toLowerCase());
            break;
        case BLOQUEAR:
            rutaRecurso = StringsUtils.concatenar(mensajes.getMessage(RUTA_SIMP_HUB), mensajes.getMessage(ConstantesWS.OP_WS_BLOQUEAR_TARJETA).toLowerCase());
            break;
        case REGISTRAR:
            rutaRecurso = StringsUtils.concatenar(mensajes.getMessage(RUTA_SIMP_HUB), mensajes.getMessage(ConstantesWS.OP_WS_REGISTRAR_CLIENTE).toLowerCase());
            break;
        case ASOCIAR:
            rutaRecurso = StringsUtils.concatenar(mensajes.getMessage(RUTA_SIMP_HUB), mensajes.getMessage(ConstantesWS.OP_WS_ASOCIAR_TARJETA).toLowerCase());
            break;
        case CONSULTAR_POR_TARJETA:
            rutaRecurso = StringsUtils.concatenar(mensajes.getMessage(RUTA_SIMP_HUB), mensajes.getMessage(ConstantesWS.OP_WS_CONSULTAR_CLIENTE_POR_TARJETA).toLowerCase());
            break;
        default:
            break;
        }
        // Manejar excepcion
        return rutaRecurso;
    }

    private static ConsultaMovimientos convertirAConsultaMovimiento(Map<String, Object> mapConsultaMovimiento)
    {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> listaMapasMovimientos = (List<Map<String, Object>>) mapConsultaMovimiento.get("MOVIMIENTOS");

        List<MovimientoPP> listaMovimientos = new ArrayList<>();
        for (Map<String, Object> map : listaMapasMovimientos)
        {
            MovimientoPP mov = convertirAMovimiento(map);
            listaMovimientos.add(mov);
        }

        Comparator<MovimientoPP> movimientoComparador = Comparator.comparing(MovimientoPP::getSecuencia).reversed();

        listaMovimientos.sort(movimientoComparador);

        ConsultaMovimientos consultaMovimiento = ConsultaMovimientos.builder().respuesta(convertirARespuesta(mapConsultaMovimiento)).movimientos(listaMovimientos).build();

        return consultaMovimiento;

    }

    private static RespuestaWS convertirARespuesta(Map<String, Object> mapConsultaMovimiento)
    {
        return RespuestaWS.builder().id((String) mapConsultaMovimiento.get("RC")).descripcion((String) mapConsultaMovimiento.get("RC_DESC")).build();
    }

    @SuppressWarnings("unchecked")
    private RespuestaWSConsultaCliente convertirARespuestaWSConsultaCliente(Map<String, Object> mapRespuesta)
    {
        List<Map<String, Object>> listaMapTarjetas = (List<Map<String, Object>>) mapRespuesta.get("TARJETAS");
        List<TarjetaPP> listaTarjetas = new ArrayList<>();
        for (Map<String, Object> map : listaMapTarjetas)
        {
            TarjetaPP tarjeta = TarjetaPP.builder().estado(map.get("PP_ESTADO") != null && map.get("PP_ESTADO") instanceof String ? (String) map.get("PP_ESTADO") : null).codigoSeguimiento((String) map.get("COD_SEG"))
                    .numeroTarjetaTruncado((String) map.get("PAN_TRUNC")).build();
            listaTarjetas.add(tarjeta);
        }

        String desc = (mapRespuesta.get("TIPO_DOC") != null && !multiTabDetService.buscarPorIdTablaIdItem(1, (String) mapRespuesta.get("TIPO_DOC")).isEmpty())
                ? multiTabDetService.buscarPorIdTablaIdItem(1, (String) mapRespuesta.get("TIPO_DOC")).get(0).getDescripcionItem()
                : "";

        return RespuestaWSConsultaCliente.builder().idCliente((String) mapRespuesta.get("ID_CLIENTE")).idUsuario((String) mapRespuesta.get("ID_USUARIO")).nombre1((String) mapRespuesta.get("NOMBRE1")).nombre2((String) mapRespuesta.get("NOMBRE2"))
                .apellidoPaterno((String) mapRespuesta.get("AP_PATERNO")).apellidoMaterno((String) mapRespuesta.get("AP_MATERNO")).numeroDocumento((String) mapRespuesta.get("NRO_DOC"))
                .tipoDocumento(((String) mapRespuesta.get("TIPO_DOC")) + " - " + desc).celular((String) mapRespuesta.get("TEL_MOVIL")).email((String) mapRespuesta.get("EMAIL")).tarjetas(listaTarjetas).build();

    }

    private static MovimientoPP convertirAMovimiento(Map<String, Object> mapConsultaMovimiento)
    {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return MovimientoPP.builder().secuencia((String) mapConsultaMovimiento.get("SECUENCIA")).tipo((String) mapConsultaMovimiento.get("TIPO"))
                .monto(String.format(Constantes.NUM_FMT_DOS_CIFRAS_DECIMALES, Double.parseDouble((String) mapConsultaMovimiento.get("MONTO")) / 100))
                .costo(String.format(Constantes.NUM_FMT_DOS_CIFRAS_DECIMALES, Double.parseDouble((String) mapConsultaMovimiento.get("COSTO")) / 100))

                .hora(DatesUtils.obtenerLocalTimeHHMMSS((String) mapConsultaMovimiento.get("HORA")).toString()).fecha(DatesUtils.obtenerLocalDateYYYYMMDD((String) mapConsultaMovimiento.get("FECHA")).format(formatter))
                .comercio((String) mapConsultaMovimiento.get("COMERCIO"))
                // .codigoOperacion((String) mapConsultaMovimiento.get("CODIGO
                // DE OPERACION"))
                .tarjetaTruncada((String) mapConsultaMovimiento.get("PAN_TRUNC")).build();
    }

    @Override
    public RespuestaWS reasignarTarjeta(ParametrosEntrada parametrosEntrada)
    {
        RespuestaWS respuestaWS;
        List<String> codigoSeguimientoNuevo = tarjetaPPMapper.buscarCodigoSeguimientoPorTarjeta(CriterioBusquedaFiltroPrepago.builder().numeroTarjeta(parametrosEntrada.getNumeroTarjetaNuevo()).build());

        List<String> codigoSeguimientoAnterior = tarjetaPPMapper.buscarCodigoSeguimientoPorTarjeta(CriterioBusquedaFiltroPrepago.builder().numeroTarjeta(parametrosEntrada.getNumeroTarjetaAnterior()).build());

        if (!codigoSeguimientoNuevo.isEmpty() && !codigoSeguimientoAnterior.isEmpty())
        {
            String codigoSegNuevo = CollectionUtil.obtenerUnicoElemento(codigoSeguimientoNuevo).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta"));
            ;
            String codigoSegAnterior = CollectionUtil.obtenerUnicoElemento(codigoSeguimientoAnterior).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta"));
            ;

            SolicitudWSReasignacionTarjeta request = SolicitudWSReasignacionTarjeta.builder().codigoSeguimientoNuevo(codigoSegNuevo).codigoSeguimientoAnterior(codigoSegAnterior).operacion(mensajes.getMessage(ConstantesWS.OP_WS_REASIGNAR_TARJETA))
                    .secHash(ConstantesWS.SEC_HASH).build();

            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.postForObject(obtenerRuta(AccionesWS.REASIGNAR), request, String.class);
            try
            {
                respuestaWS = convertirARespuesta(JsonUtil.jsonToMap(jsonString));
            } catch (JSONException e)
            {
                throw new ErrorConversionException(StringsUtils.concatenar(ConstantesExcepciones.ERROR_CONVERSION_DATOS, "-", e.getLocalizedMessage()));
            }
        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }
        return respuestaWS;
    }

    @Override
    public RespuestaWS bloquearTarjeta(ParametrosEntrada parametrosEntrada)
    {
        RespuestaWS respuestaWS;
        List<String> codigoSeguimiento = tarjetaPPMapper.buscarCodigoSeguimientoPorTarjeta(CriterioBusquedaFiltroPrepago.builder().numeroTarjeta(parametrosEntrada.getNumeroTarjetaAnterior()).build());

        if (!codigoSeguimiento.isEmpty())
        {
            String codigoSeg = CollectionUtil.obtenerUnicoElemento(codigoSeguimiento).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta"));
            ;

            SolicitudWSBloqueos request = SolicitudWSBloqueos.builder().codigoSeguimiento(codigoSeg).motivo(parametrosEntrada.getMotivo()).operacion(mensajes.getMessage(ConstantesWS.OP_WS_BLOQUEAR_TARJETA)).secHash(ConstantesWS.SEC_HASH).build();

            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.postForObject(obtenerRuta(AccionesWS.BLOQUEAR), request, String.class);
            try
            {
                respuestaWS = convertirARespuesta(JsonUtil.jsonToMap(jsonString));
            } catch (JSONException e)
            {
                throw new ErrorConversionException(StringsUtils.concatenar(ConstantesExcepciones.ERROR_CONVERSION_DATOS, "-", e.getLocalizedMessage()));
            }
        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }
        return respuestaWS;
    }

    @Override
    public RespuestaWS activarTarjeta(ParametrosEntrada parametrosEntrada)
    {
        RespuestaWS respuestaWS;
        List<String> codigoSeguimiento = tarjetaPPMapper.buscarCodigoSeguimientoPorTarjeta(CriterioBusquedaFiltroPrepago.builder().numeroTarjeta(parametrosEntrada.getNumeroTarjeta()).build());

        if (!codigoSeguimiento.isEmpty())
        {
            String codigoSeg = CollectionUtil.obtenerUnicoElemento(codigoSeguimiento).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta"));
            ;

            SolicitudWSActivacion request = SolicitudWSActivacion.builder().codigoSeguimiento(codigoSeg).operacion(mensajes.getMessage(ConstantesWS.OP_WS_ACTIVAR_TARJETA)).secHash(ConstantesWS.SEC_HASH).build();

            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.postForObject(obtenerRuta(AccionesWS.ACTIVAR), request, String.class);
            try
            {
                respuestaWS = convertirARespuesta(JsonUtil.jsonToMap(jsonString));
            } catch (JSONException e)
            {
                throw new ErrorConversionException(StringsUtils.concatenar(ConstantesExcepciones.ERROR_CONVERSION_DATOS, "-", e.getLocalizedMessage()));
            }
        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }
        return respuestaWS;
    }

    @Override
    public RespuestaWS recargarTarjeta(ParametrosEntrada parametrosEntrada)
    {
        RespuestaWS respuestaWS;
        List<String> codigoSeguimiento = tarjetaPPMapper.buscarCodigoSeguimientoPorTarjeta(CriterioBusquedaFiltroPrepago.builder().numeroTarjeta(parametrosEntrada.getNumeroTarjeta()).build());

        if (!codigoSeguimiento.isEmpty())
        {
            String codigoSeg = CollectionUtil.obtenerUnicoElemento(codigoSeguimiento).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta"));
            ;

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String formatDateTime = now.format(formatter);

            SolicitudWSRecarga request = SolicitudWSRecarga.builder().codigoSeguimiento(codigoSeg).moneda(parametrosEntrada.getMoneda()).monto(new Double(parametrosEntrada.getMonto() * 100).intValue() + "").tlocal(formatDateTime)
                    .operacion(mensajes.getMessage(ConstantesWS.OP_WS_RECARGAR_TARJETA)).secHash(ConstantesWS.SEC_HASH).build();

            try
            {
                logger.info("req: {}", JsonUtil.convertirAJSON(Arrays.asList(new SolicitudWSRecarga[] { request })));
            } catch (JsonProcessingException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.postForObject(obtenerRuta(AccionesWS.RECARGAR), request, String.class);
            try
            {
                respuestaWS = convertirARespuesta(JsonUtil.jsonToMap(jsonString));
            } catch (JSONException e)
            {
                throw new ErrorConversionException(StringsUtils.concatenar(ConstantesExcepciones.ERROR_CONVERSION_DATOS, "-", e.getLocalizedMessage()));
            }
        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }
        return respuestaWS;
    }

    @Override
    public RespuestaWS asociarTarjeta(ParametrosEntrada parametrosEntrada)
    {
        RespuestaWS respuestaWS;
        List<String> codigoSeguimiento = tarjetaPPMapper.buscarCodigoSeguimientoPorTarjeta(CriterioBusquedaFiltroPrepago.builder().numeroTarjeta(parametrosEntrada.getNumeroTarjeta()).build());

        String codigoUBA = this.personaPPService.buscarCodigoUBAPorNumeroDocumento(parametrosEntrada);

        // codigoUBA = "618684";

        if (!codigoSeguimiento.isEmpty())
        {

            if (codigoUBA != null)
            {

                String codigoSeg = CollectionUtil.obtenerUnicoElemento(codigoSeguimiento).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta"));

                SolicitudWSAsociacion request = SolicitudWSAsociacion.builder().codigoSeguimiento(codigoSeg).idCliente(codigoUBA).operacion(mensajes.getMessage(ConstantesWS.OP_WS_ASOCIAR_TARJETA)).secHash(ConstantesWS.SEC_HASH).build();

                RestTemplate restTemplate = new RestTemplate();
                String jsonString = restTemplate.postForObject(obtenerRuta(AccionesWS.ASOCIAR), request, String.class);

                try
                {
                    respuestaWS = convertirARespuesta(JsonUtil.jsonToMap(jsonString));
                    logger.info("{}", respuestaWS);
                } catch (JSONException e)
                {
                    throw new ErrorConversionException(StringsUtils.concatenar(ConstantesExcepciones.ERROR_CONVERSION_DATOS, "-", e.getLocalizedMessage()));
                }

            } else
            {
                throw new ValorNoEncontradoException(ConstantesExcepciones.ID_CLIENTE_NO_ENCONTRADO);
            }

        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }
        return respuestaWS;
    }

    @Override
    public RespuestaWSRegistroCliente registrarCliente(ParametrosEntrada parametrosEntrada)
    {
        RespuestaWSRegistroCliente respuestaWSRegistroCliente;

        String fechaCumpleanios = DatesUtils.obtenerFechaEnFormato(parametrosEntrada.getFechaCumpleanios(), DatesUtils.FORMATO_YYYYMMDD_SIN_GUION);

        SolicitudWSRegistroCliente request = SolicitudWSRegistroCliente.builder().apellidoMaterno(parametrosEntrada.getApellidoMaterno()).apellidoPaterno(parametrosEntrada.getApellidoPaterno()).fechaCumpleanios(fechaCumpleanios)
                .nombre1(parametrosEntrada.getNombre1()).nombre2(parametrosEntrada.getNombre2()).tipoDocumento(parametrosEntrada.getTipoDocumento()).numeroDocumento(parametrosEntrada.getNumeroDocumento())
                .operacion(mensajes.getMessage(ConstantesWS.OP_WS_REGISTRAR_CLIENTE).toUpperCase()).secHash(ConstantesWS.SEC_HASH).build();
        RestTemplate restTemplate = new RestTemplate();
        String jsonString = restTemplate.postForObject(obtenerRuta(AccionesWS.REGISTRAR), request, String.class);
        logger.info("JSON Rpta: {}", jsonString);
        try
        {
            respuestaWSRegistroCliente = convertirARespuestaRegistroCliente(JsonUtil.jsonToMap(jsonString));
            logger.info("OBJ Rpta: {}", respuestaWSRegistroCliente);
        } catch (JSONException e)
        {
            throw new ErrorConversionException(StringsUtils.concatenar(ConstantesExcepciones.ERROR_CONVERSION_DATOS, "-", e.getLocalizedMessage()));
        }

        return respuestaWSRegistroCliente;
    }

    private RespuestaWSRegistroCliente convertirARespuestaRegistroCliente(Map<String, Object> jsonToMap)
    {
        RespuestaWS respuestaWS = convertirARespuesta(jsonToMap);
        RespuestaWSRegistroCliente respuestaWSRegistroCliente = null;
        if (respuestaWS.getId().equals("0"))
        {
            respuestaWSRegistroCliente = RespuestaWSRegistroCliente.builder().idCliente((String) jsonToMap.get("ID_CLIENTE")).build();
            respuestaWSRegistroCliente.setRespuestaWS(respuestaWS);
        }
        return respuestaWSRegistroCliente;
    }

    @Override
    public RespuestaWSConsultaCliente consultarClientePorTarjeta(ParametrosEntrada parametrosEntrada)
    {
        RespuestaWSConsultaCliente respuestaWS;
        List<String> codigoSeguimiento = tarjetaPPMapper.buscarCodigoSeguimientoPorTarjeta(CriterioBusquedaFiltroPrepago.builder().numeroTarjeta(parametrosEntrada.getNumeroTarjeta()).build());
        if (!codigoSeguimiento.isEmpty())
        {
            String codigoSeg = CollectionUtil.obtenerUnicoElemento(codigoSeguimiento).orElseThrow(() -> new ValorNoEncontradoException("No se encontro código de seguimiento de Tarjeta"));

            SolicitudWSConsultarPorTarjeta request = SolicitudWSConsultarPorTarjeta.builder().codigoSeguimiento(codigoSeg).secHash(ConstantesWS.SEC_HASH).build();
            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.postForObject(obtenerRuta(AccionesWS.CONSULTAR_POR_TARJETA), request, String.class);
            try
            {
                respuestaWS = convertirARespuestaWSConsultaCliente(JsonUtil.jsonToMap(jsonString));
            } catch (JSONException e)
            {
                throw new ErrorConversionException(StringsUtils.concatenar(ConstantesExcepciones.ERROR_CONVERSION_DATOS, "-", e.getLocalizedMessage()));
            }
        } else
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_SEGUIMIENTO_NO_ENCONTRADO);
        }
        return respuestaWS;
    }

    @Override
    public Map<TarjetaPP, RespuestaWS> bloqueoPermanenteTarjetas(CriterioBusquedaRangoFechaPrepago criterioBusquedaRangoFechaPrepago)
    {
        Map<TarjetaPP, RespuestaWS> respuestas = new HashMap<TarjetaPP, RespuestaWS>();
        List<TarjetaPP> codigosSeguimiento = tarjetaPPMapper.buscarBloqueosPorRangoFecha(criterioBusquedaRangoFechaPrepago);
        for (TarjetaPP tarjeta : codigosSeguimiento)
        {
            RespuestaWS respuesta = null;
            ParametrosEntrada parametros = new ParametrosEntrada();
            parametros.setNumeroTarjetaAnterior(tarjeta.getNumeroTarjeta());
            parametros.setMotivo("RB");
            respuesta = bloquearTarjeta(parametros);
            respuestas.put(tarjeta, respuesta);
        }
        return respuestas;
    }

}
