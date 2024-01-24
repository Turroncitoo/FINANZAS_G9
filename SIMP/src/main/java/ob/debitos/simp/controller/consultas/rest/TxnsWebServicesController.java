package ob.debitos.simp.controller.consultas.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.model.consulta.movimiento.TxnsPreAutorizadas;
import ob.debitos.simp.model.consulta.movimiento.TxnsWebServices;
import ob.debitos.simp.model.criterio.CriterioBusquedaApiSimphub;
import ob.debitos.simp.model.criterio.CriterioBusquedaAuthWSPendiente;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionWebServices;
import ob.debitos.simp.model.prepago.PersonaPP;
import ob.debitos.simp.service.IPersonaPPService;
import ob.debitos.simp.service.ITxnsWebServicesService;
import ob.debitos.simp.utilitario.DatesUtils;
import ob.debitos.simp.utilitario.JsonUtil;
import ob.debitos.simp.utilitario.UtilWS;

@Audit(tipo = Tipo.CON_TXNWS, accion = Accion.Consulta)
@RequestMapping("/txnsWebServices")
public @RestController class TxnsWebServicesController
{

    private @Autowired ITxnsWebServicesService txnsWebServices;
    
    public static final String PATH_TXN_FINAN = "txn-finan";
    public static final String PATH_TXN_ADMIN = "txn-admin";
    // Financieras
    public static final String PATH_OP_CONSULTA_SALDO = PATH_TXN_FINAN + "/consulta-saldo";
    public static final String PATH_OP_CONSULTA_MOV = PATH_TXN_FINAN + "/consulta-movimientos";
    public static final String PATH_OP_RECARGA = PATH_TXN_FINAN + "/recarga";
    public static final String PATH_OP_DEBITO = PATH_TXN_FINAN + "/debito";
    public static final String PATH_OP_TRANSFERENCIA = PATH_TXN_FINAN + "/transferencia";
    public static final String PATH_OP_EXTORNO_RECARGA = PATH_TXN_FINAN + "/extorno-recarga";
    public static final String PATH_OP_EXTORNO_DEBITO = PATH_TXN_FINAN + "/extorno-debito";
    public static final String PATH_OP_EXTORNO_TRANSFERENCIA = PATH_TXN_FINAN + "/extorno-transferencia";

    // Administrativas
    public static final String PATH_OP_ASOCIAR_TARJETA_CLIENTE_EMPRESA = PATH_TXN_ADMIN + "/asociacion-tarjeta-cliente-empresa";
    public static final String PATH_OP_ACTUALIZA_REGIMEN = PATH_TXN_ADMIN + "/actualiza-regimen";
    public static final String PATH_OP_ACTIVA_TARJETA = PATH_TXN_ADMIN + "/activacion-tarjeta";
    public static final String PATH_OP_BLOQUEO_TARJETA = PATH_TXN_ADMIN + "/bloqueo-tarjeta";
    public static final String PATH_OP_REASIGNA_TARJETA = PATH_TXN_ADMIN + "/reasignacion-tarjeta";
    public static final String PATH_OP_EMITIR_TARJETA_VIRTUAL = PATH_TXN_ADMIN + "/emitir-tarjeta-virtual";
    public static final String PATH_OP_HABILITA_TARJETA_FISICA = PATH_TXN_ADMIN + "/habilita-tarjeta-fisica";

    public static final String PATH_OP_REGISTRO_CLIENTE = PATH_TXN_ADMIN + "/registro-cliente";
    public static final String PATH_OP_EDICION_CLIENTE = PATH_TXN_ADMIN + "/edicion-cliente";

    public static final String RECARGA = "RECARGA";
    public static final String EXTORNO_RECARGA = "EXTORNO RECARGA";
    public static final String DEBITO = "DEBITO CASHOUT";
    public static final String RECARGA_PROMO = "RECARGA PROMO";
    public static final String EXTORNO_DEBITO = "EXTORNO DEBITO";
    public static final String TRANSFERENCIA = "TRANSFERENCIA";
    public static final String EXTORNO_TRANSFERENCIA = "EXTORNO TRANSFERENCIA";

    private @Autowired IPersonaPPService personaPPService;

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('CON_TXNWS','2')")
    @GetMapping(params = "accion=buscarPorCriterio")
    public List<TxnsWebServices> buscarPorCriterio(CriterioBusquedaTransaccionWebServices criterio)
    {
        return this.txnsWebServices.buscarPorCriterio(criterio);
    }

    /**
     * Se exponen servicios para realizar las operaciones libres al nuevo
     * SIMPHub
     * 
     * @author Mario Cortez
     */

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/consulta-saldo")
    public Map<String, Object> consultarSaldo(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        return this.txnsWebServices.enviarOperacionSimpHub(obj, PATH_OP_CONSULTA_SALDO);
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/consulta-movimientos")
    public Map<String, Object> consultarMovimientos(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        return this.txnsWebServices.enviarOperacionSimpHub(obj, PATH_OP_CONSULTA_MOV);
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/asociar-tarjeta-cliente-empresa")
    public Map<String, Object> asociarTarjetaCliente(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        obj.put("ID_CLIENTE", criterio.getClienteUBAAsociar());
        obj.put("COD_EMPRESA", criterio.getCodigoEmpresa());
        return this.txnsWebServices.enviarOperacionSimpHub(obj, PATH_OP_ASOCIAR_TARJETA_CLIENTE_EMPRESA);
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/actualiza-regimen")
    public Map<String, Object> actualizaRegimen(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        obj.put("IND_REGIMEN", criterio.getRegimen());
        obj.put("IND_CATEGORIA", criterio.getIndCategoria());
        obj.put("TLOCAL", DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_yyyyMMddHHmmss));
        return this.txnsWebServices.enviarOperacionSimpHub(obj, PATH_OP_ACTUALIZA_REGIMEN);
    }

    /**
     * Se exponen servicios para realizar las operaciones libres al nuevo
     * SIMPHub
     * 
     * Los siguientes si piden realizar una preautorizacion (Control dual)
     * 
     * activacion-tarjeta bloqueo-tarjeta reasigna-tarjeta emitir tarjeta
     * virtual habilitar tarjeta virtual
     * 
     * recarga extorno-recarga debito extorno-debito transferencia
     * extorno-transferencia
     * 
     * @author Mario Cortez
     */

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/activacion-tarjeta")
    public Integer activacionTarjeta(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_ACTIVA_TARJETA);
        criterioBusq.setTipoOperacion("ACTIVACION DE TARJETA");
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        return criterioBusq.getIdGenerado();
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/bloqueo-tarjeta")
    public Integer bloqueoTarjeta(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        obj.put("MOTIVO", criterio.getMotivoBloqueo());
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_BLOQUEO_TARJETA);
        criterioBusq.setTipoOperacion("BLOQUEO DE TARJETA");
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        return criterioBusq.getIdGenerado();
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/reasignacion-tarjeta")
    public Integer reasignacionTarjeta(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("COD_SEG_ANTERIOR", criterio.getCodigoSeguimiento());
        obj.put("COD_SEG_NUEVO", criterio.getCodigoSeguimientoNuevo());
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_REASIGNA_TARJETA);
        criterioBusq.setTipoOperacion("REASINACIÓN DE TARJETA");
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        return criterioBusq.getIdGenerado();
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/emitir-tarjeta-virtual")
    public Integer emitirTarjetaVirtual(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        obj.put("TIPO_DOC", criterio.getTipoDocumento());
        obj.put("NRO_DOC", criterio.getNumeroDocumento());
        obj.put("AFINIDAD", criterio.getAfinidad());
        obj.put("NUM_PRO_LEAL", criterio.getProgramaLealtad());
        obj.put("NOM_EMPRESA", criterio.getEmpresa());
        obj.put("TLOCAL", DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_yyyyMMddHHmmss));
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_EMITIR_TARJETA_VIRTUAL);
        criterioBusq.setTipoOperacion("EMTIR TARJETA VIRTUAL");
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        return criterioBusq.getIdGenerado();
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/habilita-tarjeta-fisica")
    public Integer habilitaTarjetaFisica(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_HABILITA_TARJETA_FISICA);
        criterioBusq.setTipoOperacion("HABILITAR TARJETA FÍSICA");
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        return criterioBusq.getIdGenerado();
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/recarga")
    public Integer recarga(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("CIUDAD_COMERCIO", criterio.getCiudad());
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        obj.put("COM_EXTRA_MONEDA", criterio.getMonedaComision());
        obj.put("COM_EXTRA_MONTO_2", criterio.getMontoComision());
        obj.put("DIR_COMERCIO", criterio.getDireccion());
        obj.put("ID_COMERCIO", criterio.getComercio());
        obj.put("ID_TERMINAL", criterio.getTerminal());
        obj.put("MONEDA", criterio.getMonedaRecarga());
        obj.put("MONTO_2", criterio.getMontoRecarga());
        obj.put("TLOCAL", DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_yyyyMMddHHmmss));
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_RECARGA);
        criterioBusq.setTipoOperacion(RECARGA);
        criterioBusq.setMonedaTransaccion(Integer.parseInt(criterio.getMonedaRecarga()));
        criterioBusq.setMontoTransaccion(criterio.getMontoBD());
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        return criterioBusq.getIdGenerado();
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/debito")
    public Integer debito(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("CIUDAD_COMERCIO", criterio.getCiudad());
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        obj.put("DIR_COMERCIO", criterio.getDireccion());
        obj.put("ID_TERMINAL", criterio.getTerminal());
        obj.put("MONEDA", criterio.getMonedaDebito());
        obj.put("MONTO", criterio.getMontoDebito());
        obj.put("TLOCAL", DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_yyyyMMddHHmmss));
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_DEBITO);
        criterioBusq.setTipoOperacion(DEBITO);
        criterioBusq.setMonedaTransaccion(Integer.parseInt(criterio.getMonedaDebito()));
        criterioBusq.setMontoTransaccion(criterio.getMontoBD());
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        return criterioBusq.getIdGenerado();
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/transferencia")
    public Integer transferencia(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("CIUDAD_COMERCIO", criterio.getCiudad());
        obj.put("COD_SEG_DESTINO", criterio.getCodigoSeguimientoDestino());
        obj.put("COD_SEG_ORIGEN", criterio.getCodigoSeguimiento());
        obj.put("DIR_COMERCIO", criterio.getDireccion());
        obj.put("ID_COMERCIO", criterio.getComercio());
        obj.put("ID_TERMINAL", criterio.getTerminal());
        obj.put("MONEDA", criterio.getMonedaTransferencia());
        obj.put("MONTO_2", criterio.getMontoTransferencia());
        obj.put("TLOCAL", DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_yyyyMMddHHmmss));
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_TRANSFERENCIA);
        criterioBusq.setTipoOperacion(TRANSFERENCIA);
        criterioBusq.setMonedaTransaccion(Integer.parseInt(criterio.getMonedaTransferencia()));
        criterioBusq.setMontoTransaccion(criterio.getMontoBD());
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        return criterioBusq.getIdGenerado();
    }

    /**
     * Extornos llamados desde la pantalla de consulta de web services
     * 
     * @author Mario Cortez
     */

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/extorno-recarga")
    public TxnsWebServices extornoRecarga(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("CIUDAD_COMERCIO", criterio.getCiudad());
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        obj.put("COM_EXTRA_MONEDA", criterio.getMonedaComision());
        obj.put("COM_EXTRA_MONTO_2", criterio.getMontoComision());
        obj.put("DIR_COMERCIO", criterio.getDireccion());
        obj.put("ID_COMERCIO", criterio.getComercio());
        obj.put("ID_TERMINAL", criterio.getTerminal());
        obj.put("MONEDA", criterio.getMonedaRecarga());
        obj.put("MONTO_2", criterio.getMontoRecarga());
        obj.put("SECUENCIA_ORIG", criterio.getSecuenciaOriginal());
        obj.put("TLOCAL_ORIG", criterio.getTLocalOriginal());
        obj.put("TLOCAL", DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_yyyyMMddHHmmss));
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_EXTORNO_RECARGA);
        criterioBusq.setTipoOperacion(EXTORNO_RECARGA);
        criterioBusq.setMonedaTransaccion(Integer.parseInt(criterio.getMonedaRecarga()));
        criterioBusq.setMontoTransaccion(criterio.getMontoBD());
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        criterioBusq.setIdTransaccion(criterio.getTransaccion());
        this.txnsWebServices.controlExtorno(criterioBusq);
        return this.txnsWebServices.buscarPorId(criterio.getTransaccion());
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/extorno-debito")
    public TxnsWebServices extornoDebito(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("CIUDAD_COMERCIO", criterio.getCiudad());
        obj.put("COD_SEG", criterio.getCodigoSeguimiento());
        obj.put("DIR_COMERCIO", criterio.getDireccion());
        obj.put("ID_TERMINAL", criterio.getTerminal());
        obj.put("MONEDA", criterio.getMonedaDebito());
        obj.put("MONTO_2", criterio.getMontoDebito());
        obj.put("SECUENCIA_ORIG", criterio.getSecuenciaOriginal());
        obj.put("TLOCAL_ORIG", criterio.getTLocalOriginal());
        obj.put("TLOCAL", DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_yyyyMMddHHmmss));
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_EXTORNO_DEBITO);
        criterioBusq.setTipoOperacion(EXTORNO_DEBITO);
        criterioBusq.setMonedaTransaccion(Integer.parseInt(criterio.getMonedaDebito()));
        criterioBusq.setMontoTransaccion(criterio.getMontoBD());
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        criterioBusq.setIdTransaccion(criterio.getTransaccion());
        this.txnsWebServices.controlExtorno(criterioBusq);
        return this.txnsWebServices.buscarPorId(criterio.getTransaccion());
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @GetMapping(value = "/api/extorno-transferencia")
    public TxnsWebServices extornoTransferencia(CriterioBusquedaApiSimphub criterio)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("CIUDAD_COMERCIO", criterio.getCiudad());
        obj.put("COD_SEG_DESTINO", criterio.getCodigoSeguimientoDestino());
        obj.put("COD_SEG_ORIGEN", criterio.getCodigoSeguimiento());
        obj.put("COM_EXTRA_MONEDA", criterio.getMonedaComision());
        obj.put("COM_EXTRA_MONTO_2", criterio.getMontoComision());
        obj.put("DIR_COMERCIO", criterio.getDireccion());
        obj.put("ID_COMERCIO", criterio.getComercio());
        obj.put("ID_TERMINAL", criterio.getTerminal());
        obj.put("MONEDA", criterio.getMonedaTransferencia());
        obj.put("MONTO_2", criterio.getMontoTransferencia());
        obj.put("SECUENCIA_ORIG", criterio.getSecuenciaOriginal());
        obj.put("TLOCAL_ORIG", criterio.getTLocalOriginal());
        obj.put("TLOCAL", DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_yyyyMMddHHmmss));
        CriterioBusquedaAuthWSPendiente criterioBusq = CriterioBusquedaAuthWSPendiente.builder().build();
        criterioBusq.setOperacion(PATH_OP_EXTORNO_TRANSFERENCIA);
        criterioBusq.setTipoOperacion(EXTORNO_TRANSFERENCIA);
        criterioBusq.setMonedaTransaccion(Integer.parseInt(criterio.getMonedaTransferencia()));
        criterioBusq.setMontoTransaccion(criterio.getMontoBD());
        criterioBusq.setCodigoSeguimiento(criterio.getCodigoSeguimiento());
        criterioBusq.setJsonOperacion(JsonUtil.convertObjectToJSONString(obj));
        criterioBusq.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        this.txnsWebServices.registrarPreAutorizacion(criterioBusq);
        criterioBusq.setIdTransaccion(criterio.getTransaccion());
        this.txnsWebServices.controlExtorno(criterioBusq);
        return this.txnsWebServices.buscarPorId(criterio.getTransaccion());
    }

    /**
     * Consulta de transacciones preautorizadas
     * 
     * @param
     * @author Mario Cortez
     */

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('CON_PREAUTTXNWS','ANY')")
    @GetMapping(value = "/preAutorizadas", params = "accion=buscarPreAutorizadas")
    public List<TxnsPreAutorizadas> buscarPreAutorizadasPorCriterio(CriterioBusquedaTransaccionWebServices criterio)
    {
        return this.txnsWebServices.buscarPreAutorizadasPorCriterio(criterio);
    }

    /**
     * Autorizar transacciones
     * 
     * @param
     * @author Mario Cortez
     */

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('CON_PREAUTTXNWS','ANY')")
    @GetMapping(value = "/api/autorizar")
    public TxnsPreAutorizadas autorizar(TxnsPreAutorizadas preAutorizacion)
    {
        Integer id = preAutorizacion.getId();
        TxnsPreAutorizadas txns = this.txnsWebServices.buscarPreAutorizadasPorId(id);
        CriterioBusquedaAuthWSPendiente criterio = CriterioBusquedaAuthWSPendiente.builder().build();
        criterio.setFechaEnvioTxn(DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_YYYYMMDD_HHMMSS));
        Map<String, Object> response = this.txnsWebServices.enviarOperacionSimpHub(txns.getJsonOperacion(), txns.getOperacion());
        criterio.setFechaRecepcionTxn(DatesUtils.getLocalDateTimeToString(DatesUtils.PATTERN_YYYYMMDD_HHMMSS));
        criterio.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        criterio.setIdGenerado(id);
        criterio.setCodigoRespuesta(UtilWS.replaceNullPorCadenaVacia(response.get("RC")));
        criterio.setDescripcionRespuesta(UtilWS.replaceNullPorCadenaVacia(response.get("RC_DESC")));
        criterio.setTrace(UtilWS.replaceNullPorCadenaVacia(response.get("SECUENCIA")));
        criterio.setIdTransaccion(UtilWS.replaceNullPorCadenaVacia(response.get("TRANSACCION")));
        this.txnsWebServices.autorizar(criterio);
        return this.txnsWebServices.buscarPreAutorizadasPorId(id);
    }

    /**
     * Denegar transacciones
     * 
     * @param
     * @author Mario Cortez
     */

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('CON_PREAUTTXNWS','ANY')")
    @GetMapping(value = "/api/denegar")
    public TxnsPreAutorizadas denegar(TxnsPreAutorizadas preAutorizacion)
    {
        Integer id = preAutorizacion.getId();
        CriterioBusquedaAuthWSPendiente criterio = CriterioBusquedaAuthWSPendiente.builder().build();
        criterio.setUsuario(SecurityContextFacade.obtenerNombreUsuario());
        criterio.setIdGenerado(id);
        this.txnsWebServices.denegar(criterio);
        return this.txnsWebServices.buscarPreAutorizadasPorId(id);
    }

    /**
     * Registro y edicion de cliente
     * 
     * @author Mario Cortez
     */

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PostMapping(value = "/api/cliente")
    public PersonaPP registroCliente(@RequestBody PersonaPP personaPP)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("TIPO_DOC", personaPP.getTipoDocumento());
        obj.put("NRO_DOC", personaPP.getNumeroDocumento());
        obj.put("NOMBRE1", personaPP.getNombres());
        obj.put("AP_PATERNO", personaPP.getApellidoPaterno());
        obj.put("AP_MATERNO", personaPP.getApellidoMaterno());
        obj.put("BIRTHDATE", personaPP.getFechaNacimientoFormateado());
        obj.put("TEL_MOVIL", personaPP.getTelefonoMovil());
        obj.put("SEXO", personaPP.getSexo());
        obj.put("EMAIL", personaPP.getCorreoElectronico());
        obj.put("PAIS", personaPP.getPaisWs());
        obj.put("ID_PAIS", personaPP.getIdPaisWs());
        Map<String, Object> response = this.txnsWebServices.enviarOperacionSimpHub(obj, PATH_OP_REGISTRO_CLIENTE);
        PersonaPP personaPPResponse = PersonaPP.builder().build();
        personaPPResponse.setRespuestaSimpHub(UtilWS.replaceNullPorCadenaVacia(response.get("RC")));
        personaPPResponse.setDescripcionRespuestaSimpHub(UtilWS.replaceNullPorCadenaVacia(response.get("RC_DESC")));
        return personaPPResponse;
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PutMapping(value = "/api/cliente")
    public PersonaPP edicionCliente(@RequestBody PersonaPP personaPP)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("TIPO_DOC", personaPP.getTipoDocumento());
        obj.put("NRO_DOC", personaPP.getNumeroDocumento());
        obj.put("NOMBRE1", personaPP.getNombres());
        obj.put("AP_PATERNO", personaPP.getApellidoPaterno());
        obj.put("AP_MATERNO", personaPP.getApellidoMaterno());
        obj.put("BIRTHDATE", personaPP.getFechaNacimientoFormateado());
        obj.put("TEL_MOVIL", personaPP.getTelefonoMovil());
        obj.put("SEXO", personaPP.getSexo());
        obj.put("EMAIL", personaPP.getCorreoElectronico());
        obj.put("ID_CLIENTE", personaPP.getCodUBA());
        obj.put("PAIS", personaPP.getPaisWs());
        obj.put("ID_PAIS", personaPP.getIdPaisWs());
        Map<String, Object> response = this.txnsWebServices.enviarOperacionSimpHub(obj, PATH_OP_EDICION_CLIENTE);
        personaPP = this.personaPPService.buscarPorCodigoUBA(personaPP.getCodUBA());
        personaPP.setRespuestaSimpHub(UtilWS.replaceNullPorCadenaVacia(response.get("RC")));
        personaPP.setDescripcionRespuestaSimpHub(UtilWS.replaceNullPorCadenaVacia(response.get("RC_DESC")));
        return personaPP;
    }
    
}
