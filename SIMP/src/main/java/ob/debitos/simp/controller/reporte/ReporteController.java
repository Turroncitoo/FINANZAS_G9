package ob.debitos.simp.controller.reporte;

import static ob.debitos.simp.utilitario.ConstantesGenerales.P_REPORTE;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_MEMBRESIAS;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_ROLES_TRANSACCION;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_CLASES_TRANSACCIONES;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_CODIGO_RESPUESTA;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_CANALES;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_EMPRESAS;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_ORIGENES;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_MONEDAS;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_CONCEPTO_COMISIONES;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_CONCEPTOS_COMISION;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_INSTITUCIONES;
import static ob.debitos.simp.utilitario.ConstantesGenerales.P_INDICADORES_CONTABILIZACION;
import static ob.debitos.simp.utilitario.ReporteUtil.RESUMEN_AUTORIZACION;
import static ob.debitos.simp.utilitario.ReporteUtil.RESUMEN_SW_DMP_LOG;
import static ob.debitos.simp.utilitario.ReporteUtil.RESUMEN_TRANSACCION_APROBADA_AGENCIA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.service.IAgenciaService;
import ob.debitos.simp.service.IAtmService;
import ob.debitos.simp.service.ICanalService;
import ob.debitos.simp.service.ICodigoProcesoSwitchService;
import ob.debitos.simp.service.IRptaConcilUbaService;
import ob.debitos.simp.service.IClaseTransaccionService;
import ob.debitos.simp.service.IOrigenService;
import ob.debitos.simp.service.ICodigoTransaccionService;
import ob.debitos.simp.service.IConceptoComisionService;
import ob.debitos.simp.service.IEmpresaService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.IMembresiaService;
import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.utilitario.MultiTablaUtil;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.Visita)
@RequestMapping("/reporte")
public @Controller class ReporteController
{
    private @Autowired IAgenciaService agenciaService;
    private @Autowired IAtmService atmService;
    private @Autowired ICanalService canalService;
    private @Autowired ICodigoProcesoSwitchService codigoProcesoService;
    private @Autowired IMonedaService monedaService;
    private @Autowired IMembresiaService membresiaService;
    private @Autowired IMultiTabDetService multiTabDetService;
    private @Autowired IConceptoComisionService conceptoComisionService;
    private @Autowired IClaseTransaccionService claseTransaccionService;
    private @Autowired ICodigoTransaccionService codigoTransaccionService;
    private @Autowired IRptaConcilUbaService rptaConcilUbaService;
    private @Autowired IEmpresaService empresaService;
    private @Autowired IInstitucionService institucionService;
    private @Autowired IOrigenService origenService;

    @Audit(tipo = Tipo.RPT_COMIS_CUADRE)
    @PreAuthorize("hasPermission('RPT_COMISCUADRE','ANY')")
    @GetMapping("/resumen/{reporte:comisionInstResumen}")
    public String irPaginaReporteComisionBancoAdministrador(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute(P_EMPRESAS, empresaService.buscarTodos());
        model.addAttribute(P_MONEDAS, monedaService.buscarTodos());
        model.addAttribute(P_CONCEPTO_COMISIONES, conceptoComisionService.buscarTodos().stream().filter(concepto -> concepto.getRolEmisor() == 1 && concepto.getActivado() == 1).toArray());
        model.addAttribute(P_INSTITUCIONES, institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_REPORTE, reporte);
        return "seguras/reporte/reporteResumen";
    }

    @Audit(tipo = Tipo.RPT_AUT_CANAL)
    @PreAuthorize("hasPermission('RPT_AUTCANAL', 'ANY')")
    @GetMapping("/autorizacion/{reporte:canal}")
    public String irPaginaReporteAutorizacionCanal(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        return "seguras/reporte/reporteAutorizacion";
    }

    @Audit(tipo = Tipo.RPT_AUT_ID_PROC)
    @PreAuthorize("hasPermission('RPT_AUTIDPROC', 'ANY')")
    @GetMapping("/autorizacion/{reporte:procesoSwitch}")
    public String irPaginaReporteAutorizacionProcesoSwitch(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        return "seguras/reporte/reporteAutorizacion";
    }

    @Audit(tipo = Tipo.RPT_AUT_COD_RPTA)
    @PreAuthorize("hasPermission('RPT_AUTCODRPTA', 'ANY')")
    @GetMapping("/autorizacion/{reporte:respuestaSwitch}")
    public String irPaginaReporteAutorizacionRespuestaSwitch(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        return "seguras/reporte/reporteAutorizacion";
    }

    @Audit(tipo = Tipo.RPT_CONCIL_OBS)
    @PreAuthorize("hasPermission('RPT_CONCILOBS','ANY')")
    @GetMapping("/{reporte:conciliacionesObservadas}")
    public String irPaginaReporteConciliacionesObservadas(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("origenesArchivo", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ORIGEN_ARCHIVO));
        return "seguras/reporte/reporteConciliacionesObservadas";
    }

    @Audit(tipo = Tipo.RPT_CONT_MOV)
    @PreAuthorize("hasPermission('RPT_CONTMOV','ANY')")
    @GetMapping("/contabilizacion/{reporte:movimiento}")
    public String irPaginaReporteContabilizacionMovimiento(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute(P_REPORTE, reporte);
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        model.addAttribute(P_INDICADORES_CONTABILIZACION, this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_INDICADOR_CONTABILIZACION));
        return "seguras/reporte/reporteContabilizacion";
    }

    @Audit(tipo = Tipo.RPT_CONT_COMIS)
    @PreAuthorize("hasPermission('RPT_CONTCOMIS','ANY')")
    @GetMapping("/contabilizacion/{reporte:comisiones}")
    public String irPaginaReporteContabilizacionComisiones(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute(P_REPORTE, reporte);
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, empresaService.buscarTodos());
        model.addAttribute(P_INDICADORES_CONTABILIZACION, multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_INDICADOR_CONTABILIZACION));
        model.addAttribute(P_CONCEPTO_COMISIONES, conceptoComisionService.buscarTodos().stream().filter(concepto -> concepto.getRolEmisor() == 1 && concepto.getActivado() == 1).toArray());
        return "seguras/reporte/reporteContabilizacion";
    }

    @Audit(tipo = Tipo.RPT_CONT_DEFAULT)
    @PreAuthorize("hasPermission('RPT_CONTDEFAULT','ANY')")
    @GetMapping("/contabilizacion/detalle/{reporte:cuentaDefault}")
    public String irPaginReporteContabilizacionEnCuentaDefault(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, empresaService.buscarTodos());
        return "seguras/reporte/reporteContabilizacion";
    }

    @Audit(tipo = Tipo.RPT_COMP_EMI_CANAL)
    @PreAuthorize("hasPermission('RPT_COMPEMICANAL','ANY')")
    @GetMapping("/compensacion/emisor/{reporte:canal}")
    public String irPaginaReporteCompensacionEmisorCanal(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        return "seguras/reporte/reporteCompensacion";
    }

    @Audit(tipo = Tipo.RPT_COMP_EMI_INST)
    @PreAuthorize("hasPermission('RPT_COMPEMIINST','ANY')")
    @GetMapping("/compensacion/emisor/{reporte:institucion}")
    public String irPaginaReporteCompensacionEmisorInstitucion(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute(P_REPORTE, reporte);
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        return "seguras/reporte/reporteCompensacion";
    }

    @Audit(tipo = Tipo.RPT_COMP_EMI_GIRO_NEG)
    @PreAuthorize("hasPermission('RPT_COMPEMIGIRNEG','ANY')")
    @GetMapping("/compensacion/emisor/{reporte:giroDeNegocio}")
    public String irPaginaReporteCompensacionEmisorPorPorGiroDeNegocio(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute(P_REPORTE, reporte);
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        return "seguras/reporte/reporteCompensacion";
    }

    @Audit(tipo = Tipo.RPT_COMP_REC)
    @PreAuthorize("hasPermission('RPT_COMPREC','ANY')")
    @GetMapping("/compensacion/{reporte:receptor}")
    public String irPaginaReporteCompensacionReceptor(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("monedas", monedaService.buscarTodos());
        model.addAttribute("membresias", membresiaService.buscarTodos());
        return "seguras/reporte/reporteCompensacion";
    }

    @Audit(tipo = Tipo.RPT_MOV_AUT)
    @PreAuthorize("hasPermission('RPT_MOVAUT','ANY')")
    @GetMapping("/resumen/movimiento/{reporte:autorizacion}")
    public String irPaginaReporteResumenMovimiento(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        agregarAtributos(model, reporte);
        return "seguras/reporte/reporteResumenMovimiento";
    }

    @Audit(tipo = Tipo.RPT_MOV_SWDMPLOG)
    @PreAuthorize("hasPermission('RPT_MOVSWDMPLOG','ANY')")
    @GetMapping("/resumen/movimiento/{reporte:swDmpLog}")
    public String irPaginaReporteResumenMovimientoSwDmpLog(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute(P_REPORTE, reporte);
        agregarAtributos(model, reporte);
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        return "seguras/reporte/reporteResumenMovimiento";
    }

    @Audit(tipo = Tipo.RPT_MOV_TRANS_AG)
    @PreAuthorize("hasPermission('RPT_MOVTRANSAG','ANY')")
    @GetMapping("/resumen/movimiento/{reporte:transaccionAprobadaAgencia}")
    public String irPaginaReporteResumenMovimientoTransaccionAprobadaAgencia(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        agregarAtributos(model, reporte);
        return "seguras/reporte/reporteResumenMovimiento";
    }

    @Audit(tipo = Tipo.RPT_MOV_LG_CNT_EMI)
    @PreAuthorize("hasPermission('RPT_MOVLGCNTEMI','ANY')")
    @GetMapping("/resumen/movimiento/{reporte:logContableEmisor}")
    public String irPaginaReporteResumenLogContableEmisor(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute(P_REPORTE, reporte);
        model.addAttribute(P_MEMBRESIAS, membresiaService.buscarTodos());
        model.addAttribute(P_ROLES_TRANSACCION, multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
        model.addAttribute(P_CANALES, canalService.buscarTodos());
        model.addAttribute(P_CLASES_TRANSACCIONES, claseTransaccionService.buscarTodos());
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        model.addAttribute(P_ORIGENES, origenService.buscarTodos());
        model.addAttribute(P_CONCEPTO_COMISIONES, conceptoComisionService.buscarTodos().stream().filter(concepto -> concepto.getRolEmisor() == 1 && concepto.getActivado() == 1).toArray());
        return "seguras/reporte/reporteResumenMovimiento";
    }

    @Audit(tipo = Tipo.RPT_MOV_LG_CNT_REC)
    @PreAuthorize("hasPermission('RPT_MOVLGCNTREC','ANY')")
    @GetMapping("/resumen/movimiento/{reporte:logContableReceptor}")
    public String irPaginaReporteResumenLogContableReceptor(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("atms", atmService.buscarPorCodigoInstitucionActual());
        model.addAttribute("membresias", membresiaService.buscarTodos());
        model.addAttribute("rolesTransaccion", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
        model.addAttribute("canales", canalService.buscarTodos());
        model.addAttribute("codigoTransacciones", codigoTransaccionService.buscarTodos());
        model.addAttribute("conceptoComisiones", conceptoComisionService.buscarTodos());
        return "seguras/reporte/reporteResumenMovimiento";
    }

    private void agregarAtributos(ModelMap model, String reporte)
    {
        switch (reporte)
        {
        case RESUMEN_AUTORIZACION:
            model.addAttribute("rolesTransaccion", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
            model.addAttribute("canales", canalService.buscarTodos());
            model.addAttribute("codigosProceso", codigoProcesoService.buscarTodos());
            break;
        case RESUMEN_SW_DMP_LOG:
            model.addAttribute("rolesTransaccion", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
            model.addAttribute("canales", canalService.buscarTodos());
            model.addAttribute("codigosProceso", codigoProcesoService.buscarTodos());
            break;
        case RESUMEN_TRANSACCION_APROBADA_AGENCIA:
            model.addAttribute("agencias", agenciaService.buscarTodos());
            model.addAttribute("sexos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_SEXO));
            model.addAttribute("estadosCivil", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTADO_CIVIL));
            break;
        }
    }

    @PreAuthorize("hasPermission('REPORT_CONT_ANEXO_01','ANY')")
    @GetMapping("/prepago/{reporte:anexo1}")
    public String irPaginaReporteContablePrepagoAnexo1(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        return "seguras/reporte/reporteContablePrepago";
    }

    @PreAuthorize("hasPermission('REPORT_CONT_ANEXO_02','ANY')")
    @GetMapping("/prepago/{reporte:anexo2}")
    public String irPaginaReporteContablePrepagoAnexo2(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        return "seguras/reporte/reporteContablePrepago";
    }

    @PreAuthorize("hasPermission('RPT_ESTCTA','ANY')")
    @GetMapping("/prepago/{reporte:estadoCuenta}")
    public String irPaginaReporteEstadoCuenta(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        return "seguras/reporte/reporteEstadoCuenta";
    }

    @Audit(tipo = Tipo.RPT_SUMARIO_COMP)
    @PreAuthorize("hasPermission('RPT_SUMARIOCOMP','ANY')")
    @GetMapping("/resumen/sumario-compensacion")
    public String irPaginaReporteSumarioCompensacion(ModelMap model)
    {
        model.addAttribute("monedas", this.monedaService.buscarTodos());
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        return "seguras/reporte/reporteSumarioCompensacion";
    }

    @Audit(tipo = Tipo.RPT_CONTABLE)
    @PreAuthorize("hasPermission('RPT_CONT','ANY')")
    @GetMapping("/{reporte:contable}")
    public String irPaginReporteContable(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute("monedas", this.monedaService.buscarTodos());
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("roles", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_REPORTE_CONTABLE));
        model.addAttribute("tiposDetalle", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DETALLE_REPORTE_CONTABLE));
        model.addAttribute("filtros", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_FILTRO_DETALLE_REPORTE_CONTABLE));
        return "seguras/reporte/reporteContable";
    }

    @Audit(tipo = Tipo.RPT_EXTORNOS_CONTR)
    @PreAuthorize("hasPermission('RPT_EXTCONT','ANY')")
    @GetMapping("/{reporte:extornosControversias}")
    public String irPaginaReporteExtornosControversias(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        model.addAttribute(P_ROLES_TRANSACCION, multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
        model.addAttribute(P_CODIGO_RESPUESTA, this.rptaConcilUbaService.buscarTodos());
        return "seguras/reporte/reporteExtornosControversias";
    }

    @Audit(tipo = Tipo.RPT_CONT_MISC)
    @PreAuthorize("hasPermission('RPT_CONTMIS','ANY')")
    @GetMapping("/contabilizacion/{reporte:miscelaneos}")
    public String irPaginaReporteContabilizacionMiscelaneo(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute("reporte", reporte);
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        model.addAttribute(P_INDICADORES_CONTABILIZACION, this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_INDICADOR_CONTABILIZACION));
        return "seguras/reporte/reporteContabilizacion";
    }

    @Audit(tipo = Tipo.RPT_CTAS_PAGAR)
    @PreAuthorize("hasPermission('RPT_CUADREXPAGAR','ANY')")
    @GetMapping("/resumen/{reporte:cuadreCuentasPorPagar}")
    public String irPaginaReporteCuadreCuentasPorPagar(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute(P_REPORTE, reporte);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("monedas", this.monedaService.buscarTodos());
        model.addAttribute("indicadoresConciliacion", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_INDICADOR_CONCILIACION));
        return "seguras/reporte/reporteCuadreCuentasPorPagar";

    }

    @Audit(tipo = Tipo.RPT_FACTURACION_UBA)
    @PreAuthorize("hasPermission('RPT_FACTURACIONUBA','ANY')")
    @GetMapping("/{reporte:facturacionUBA}")
    public String irPaginaReporteFacturacion(@PathVariable String reporte, ModelMap model)
    {
        model.addAttribute(P_REPORTE, reporte);
        model.addAttribute(P_MONEDAS, this.monedaService.buscarTodos());
        model.addAttribute(P_CONCEPTOS_COMISION, this.conceptoComisionService.buscarTodos());
        model.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        return "seguras/reporte/reporteFacturacionUBA";
    }

    @Audit(tipo = Tipo.RPT_CONCILIASALDOS)
    // @PreAuthorize("hasPermission('RPT_FACTURACIONUBA','ANY')")
    @GetMapping("/conciliacion/saldos")
    public String irPaginaReporteConciliacionSaldos(ModelMap model)
    {
        return "seguras/reporte/reporteConciliacionSaldos";
    }

    @Audit(tipo = Tipo.RPT_CONSALDOSRESUMEN)
    @PreAuthorize("hasPermission('RPT_CONSALDOSRESUMEN','ANY')")
    @GetMapping("/conciliacion/resumenSaldos")
    public String irPaginaReporteConciliacionResumenSaldos(ModelMap model)
    {
        model.addAttribute("monedas", this.monedaService.buscarTodos());
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        return "seguras/reporte/reporteConciliacionResumenSaldos";
    }

    @Audit(tipo = Tipo.RPT_TXNSTARJETA)
    @PreAuthorize("hasPermission('RPT_TXNSTARJETA','ANY')")
    @GetMapping("/txns/tarjetas")
    public String irPaginaReporteTransaccionesTarjetas(ModelMap model)
    {
        model.addAttribute("monedas", this.monedaService.buscarTodos());
        return "seguras/reporte/reporteTransaccionesTarjetas";
    }

    @Audit(tipo = Tipo.RPT_ESTADOTARJETAS)
    @PreAuthorize("hasPermission('RPT_ESTADOTARJETAS','ANY')")
    @GetMapping("/estadoTarjeta/tarjetas")
    public String irPaginaReporteEstadoTarjeta(ModelMap model)
    {
        return "seguras/reporte/reporteEstadoTarjeta";
    }

    @Audit(tipo = Tipo.RPT_GENERACION_TARJ)
    @PreAuthorize("hasPermission('RPT_GENERACION_TARJ','ANY')")
    @GetMapping("/tarjetas/generadas")
    public String irPaginaReporteGeneracionTarjetas(ModelMap model)
    {
        model.addAttribute("membresias", this.membresiaService.buscarTodos());
        return "seguras/reporte/reporteGeneracionTarjetas";
    }

    @Audit(tipo = Tipo.RPT_INGRESOS_COSTOS_TXN)
    @PreAuthorize("hasPermission('RPT_INGRESOS_COSTOS_TXN','ANY')")
    @GetMapping("/transaccion/ingresosCostos")
    public String irPaginaReporteIngresosYCostosPorTxn(ModelMap model)
    {
        return "seguras/reporte/reporteIngresosCostosPorTxn";
    }

    @Audit(tipo = Tipo.RPT_RECARGAS)
    @PreAuthorize("hasPermission('RPT_RECARGAS','ANY')")
    @GetMapping("/resumen/recargas")
    public String irPaginaReporteResumenRecargas(ModelMap model)
    {
        return "seguras/reporte/reporteResumenRecargas";
    }

    @Audit(tipo = Tipo.RPT_TARJETASVENDIDAS)
    @PreAuthorize("hasPermission('RPT_TARJETAS_VENDIDAS','ANY')")
    @GetMapping("/tarjetas/vendidas")
    public String irPaginaReporteTarjetaVendidas(ModelMap model)
    {
        return "seguras/reporte/reporteTarjetasVendidas";
    }

    @Audit(tipo = Tipo.RPT_PERSONAS)
    @PreAuthorize("hasPermission('RPT_PERSONAS','ANY')")
    @GetMapping("/resumen/personas")
    public String irPaginaReportePersonas(ModelMap model)
    {
        return "seguras/reporte/reportePersonas";
    }

    @Audit(tipo = Tipo.RPT_INTER_CONTABLE)
    @PreAuthorize("hasPermission('RPT_INTER_CONTABLE','ANY')")
    @GetMapping("/{reporte:interfaceContable}")
    public String irPaginaReporteInterfaceContable(@PathVariable String reporte, ModelMap modelMap)
    {
        modelMap.addAttribute("reporte", reporte);
        modelMap.addAttribute(P_INSTITUCIONES, this.institucionService.buscarPorInstitucionEmpresa());
        modelMap.addAttribute(P_EMPRESAS, this.empresaService.buscarTodos());
        return "seguras/reporte/reporteInterfaceContable";
    }

}
