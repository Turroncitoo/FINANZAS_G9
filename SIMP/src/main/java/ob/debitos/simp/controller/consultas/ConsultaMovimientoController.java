package ob.debitos.simp.controller.consultas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.service.ICanalService;
import ob.debitos.simp.service.IClaseTransaccionService;
import ob.debitos.simp.service.ICodigoProcesoSwitchService;
import ob.debitos.simp.service.ICodigoRptaSwitchService;
import ob.debitos.simp.service.IEmpresaService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.ILogoService;
import ob.debitos.simp.service.IMembresiaService;
import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.service.IOrigenService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IRptaConcilUbaService;
import ob.debitos.simp.utilitario.MultiTablaUtil;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.VisitaConsulta)
public @Controller class ConsultaMovimientoController
{

    private @Autowired ICanalService canalService;
    private @Autowired ICodigoProcesoSwitchService codigoProcesoSwitchService;
    private @Autowired IMembresiaService membresiaService;
    private @Autowired IOrigenService origenService;
    private @Autowired IClaseTransaccionService claseTransaccionService;
    private @Autowired IInstitucionService institucionService;
    private @Autowired ICodigoRptaSwitchService codigoRptaSwitchService;
    private @Autowired IMonedaService monedaService;
    private @Autowired IMultiTabDetService multiTabDetService;
    private @Autowired IEmpresaService empresaService;
    private @Autowired IRptaConcilUbaService rptConcilUbaService;
    private @Autowired ILogoService logoService;
    private @Autowired IParametroGeneralService parametroGeneralService;

    @Audit(tipo = Tipo.CON_MOV_LG_CNT)
    @PreAuthorize("hasPermission('CON_MOVLGCNT','ANY')")
    @GetMapping(value = "/{consulta:txnsCompensacion}")
    public String irPaginaConsultaTxnsCompensacion(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("rolesTransaccion", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
        model.addAttribute("membresias", membresiaService.buscarTodos());
        model.addAttribute("origenes", origenService.buscarTodos());
        model.addAttribute("clasesTransaccion", claseTransaccionService.buscarTodos());
        model.addAttribute("logos", logoService.buscarPorCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion()));
        model.addAttribute("todasInstituciones", institucionService.buscarTodos());
        model.addAttribute("codigosRespuesta", rptConcilUbaService.buscarTodos());
        model.addAttribute("canales", canalService.buscarTodos());
        model.addAttribute("monedas", monedaService.buscarTodos());
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("hasComisionAutorizada", false);
        return "seguras/consulta/movimiento";
    }

    @Audit(tipo = Tipo.CON_MOV_SWDMPLOG)
    @PreAuthorize("hasPermission('CON_MOVSWDMPLOG','ANY')")
    @GetMapping(value = "/{consulta:txnsSwDmpLog}")
    public String irPaginaConsultaTxnsSwDmpLog(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("rolesTransaccion", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
        model.addAttribute("codigosProceso", codigoProcesoSwitchService.buscarTodos());
        model.addAttribute("codigosRespuesta", codigoRptaSwitchService.buscarTodos());
        model.addAttribute("canales", canalService.buscarTodos());
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("hasComision", false);
        model.addAttribute("hasComisionAutorizada", false);
        return "seguras/consulta/movimiento";
    }

    @Audit(tipo = Tipo.CON_MOV_LIB)
    @PreAuthorize("hasPermission('CON_MOVLIB','ANY')")
    @GetMapping(value = "/{consulta:txnsLiberadas}")
    public String irPaginaConsultaTxnsLiberadas(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("codigosProceso", codigoProcesoSwitchService.buscarTodos());
        model.addAttribute("canales", canalService.buscarTodos());
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("hasDetalle", false);
        model.addAttribute("hasComision", false);
        model.addAttribute("hasComisionAutorizada", false);
        return "seguras/consulta/movimiento";
    }

    @Audit(tipo = Tipo.CON_MOV_OBS)
    @PreAuthorize("hasPermission('CON_MOVOBS','ANY')")
    @GetMapping(value = "/{consulta:txnsObservadas}")
    public String irPaginaConsultaTxnsObservadas(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("indicadoresDevolucion", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_INDICADOR_DEVOLUCION));
        model.addAttribute("indicadoresExtorno", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_INDICADOR_EXTORNO));
        model.addAttribute("indicadoresConciliacion", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_INDICADOR_CONCILIACION));
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("origenesArchivo", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ORIGEN_ARCHIVO));
        model.addAttribute("institucionesTPP", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("hasDetalle", true);
        model.addAttribute("hasComision", false);
        model.addAttribute("hasComisionAutorizada", false);
        model.addAttribute("changeTrace", true);
        return "seguras/consulta/movimiento";
    }

    @Audit(tipo = Tipo.CON_MOV_AUT)
    @PreAuthorize("hasPermission('CON_MOVAUT','ANY')")
    @GetMapping(value = "/{consulta:txnsAutorizacion}")
    public String irPaginaConsultaTxnsAutorizadas(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("canales", canalService.buscarTodos());
        model.addAttribute("codigoProcesos", codigoProcesoSwitchService.buscarTodos());
        model.addAttribute("consulta", consulta);
        model.addAttribute("hasComision", false);
        return "seguras/consulta/movimiento";
    }

    @Audit(tipo = Tipo.CON_MOV_CONSOL)
    @PreAuthorize("hasPermission('CON_MOVCONSOL','ANY')")
    @GetMapping(value = "/{consulta:txnsConsolidada}")
    public String irPaginaTablaConsolidada(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("canales", canalService.buscarTodos());
        model.addAttribute("origenesArchivo", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ORIGEN_ARCHIVO));
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("hasComisionAutorizada", false);
        model.addAttribute("hasComision", false);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        return "seguras/consulta/movimiento";
    }

    @Audit(tipo = Tipo.CON_MOV_PEND)
    @PreAuthorize("hasPermission('CON_MOVPEND','ANY')")
    @GetMapping(value = "/{consulta:txnsPendientes}")
    public String irPaginaConsultaTxnsPendientes(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("canales", canalService.buscarTodos());
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("codigoProcesos", codigoProcesoSwitchService.buscarTodos());
        model.addAttribute("empresas", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("hasDetalle", false);
        model.addAttribute("institucionesTPP", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("hasComision", false);
        model.addAttribute("hasComisionAutorizada", false);
        return "seguras/consulta/movimiento";
    }

    @Audit(tipo = Tipo.CON_BLOQ)
    @PreAuthorize("hasPermission('CON_BLOQ','ANY')")
    @GetMapping(value = "/{consulta:bloqueos}")
    public String irPaginaConsultaBloqueos(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("hasComision", false);
        model.addAttribute("hasComisionAutorizada", false);
        model.addAttribute("hasDetalle", false);
        return "seguras/consulta";
    }

    @Audit(tipo = Tipo.CON_MOV_SAL)
    @PreAuthorize("hasPermission('CON_MOVSAL','ANY')")
    @GetMapping(value = "/{consulta:saldos}")
    public String irPaginaConsultaSaldos(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("logos", logoService.buscarPorCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion()));
        model.addAttribute("monedas", monedaService.buscarTodos());
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("hasDetalle", false);
        model.addAttribute("hasComision", false);
        model.addAttribute("hasComisionAutorizada", false);
        return "seguras/consulta";
    }

    @Audit(tipo = Tipo.CON_MOV_AJUS)
    @PreAuthorize("hasPermission('CON_MOVAJUS','ANY')")
    @GetMapping(value = "/{consulta:txnsAjustes}")
    public String irPaginaConsultaTxnsAjustes(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("hasDetalle", false);
        model.addAttribute("hasComision", false);
        model.addAttribute("hasComisionAutorizada", false);
        return "seguras/consulta/movimiento";
    }

    @Audit(tipo = Tipo.CON_TXNWS)
    @PreAuthorize("hasPermission('CON_TXNWS','ANY')")
    @GetMapping(value = "/txnsWebServices")
    public String irPaginaConsultaTxnsWebServices(ModelMap model)
    {
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("logos", logoService.buscarPorCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion()));
        model.addAttribute("operaciones", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_OP_CONSULTA_WS));
        return "seguras/consulta/txnsWebServices";
    }

    @Audit(tipo = Tipo.CON_TXNWS)
    @PreAuthorize("hasPermission('CON_PREAUTTXNWS','ANY')")
    @GetMapping(value = "/txnsPreautorizadas")
    public String irPaginaConsultaTxnsPreAutorizadas(ModelMap model)
    {
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("estados", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTADOS_PREAUTH_WS));
        return "seguras/consulta/txnsPreautorizadas";
    }

    @Audit(tipo = Tipo.CON_TXNWS)
    @PreAuthorize("hasPermission('CON_EMBOSSING','ANY')")
    @GetMapping(value = "/txnsEmbossing")
    public String irPaginaConsultaTxnsEmbossing(ModelMap model)
    {
        return "seguras/consulta/txnsEmbossing";
    }

}