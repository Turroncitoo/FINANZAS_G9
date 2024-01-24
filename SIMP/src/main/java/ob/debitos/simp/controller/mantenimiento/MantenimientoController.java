package ob.debitos.simp.controller.mantenimiento;

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
import ob.debitos.simp.service.IBinService;
import ob.debitos.simp.service.ICanalEmisorService;
import ob.debitos.simp.service.IClaseTransaccionService;
import ob.debitos.simp.service.IClienteService;
import ob.debitos.simp.service.IConceptoComisionService;
import ob.debitos.simp.service.IEmpresaService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.ILogoService;
import ob.debitos.simp.service.IMembresiaService;
import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.service.IOrigenService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IParametroSFTPDirectorioService;
import ob.debitos.simp.service.IParametroSFTPProcesoService;
import ob.debitos.simp.service.IProductoService;
import ob.debitos.simp.utilitario.MultiTablaUtil;

import static ob.debitos.simp.utilitario.ConstantesGenerales.P_MANTENIMIENTO;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.Visita)
public @Controller class MantenimientoController
{
    private @Autowired IBinService binService;
    private @Autowired ICanalEmisorService canalEmisorService;
    private @Autowired IClienteService clienteService;
    private @Autowired IMonedaService monedaService;
    private @Autowired IOrigenService origenService;
    private @Autowired IEmpresaService empresaService;
    private @Autowired IConceptoComisionService conceptoComisionService;
    private @Autowired IMembresiaService membresiaService;
    private @Autowired IProductoService productoService;
    private @Autowired ILogoService logoService;
    private @Autowired IInstitucionService institucionService;
    private @Autowired IParametroGeneralService parametroService;
    private @Autowired IClaseTransaccionService claseTransaccionService;
    private @Autowired IMultiTabDetService multiTabDetService;
    private @Autowired IParametroSFTPProcesoService parametroSFTPProcesoService;
    private @Autowired IParametroSFTPDirectorioService parametroSFTPDirectorioService;

    @Audit(tipo = Tipo.MODO_ENT_POS)
    @PreAuthorize("hasPermission('MANT_MODOENTPOS','ANY')")
    @GetMapping("/{mantenimiento:modoEntradaPos}")
    public String irPaginaMantenimientoModoEntradaPos(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CLS_TXN)
    @PreAuthorize("hasPermission('MANT_CLSTXN','ANY')")
    @GetMapping("/{mantenimiento:claseTransaccion}")
    public String irPaginaMantenimientoClaseTransaccion(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.INST)
    @PreAuthorize("hasPermission('MANT_INST','ANY')")
    @GetMapping("/{mantenimiento:institucion}")
    public String irPaginaMantenimientoInstitucion(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.RPTA_CONCIL_UBA)
    @PreAuthorize("hasPermission('MANT_RPTACONCILUBA','ANY')")
    @GetMapping("/{mantenimiento:rptaConcilUba}")
    public String irPaginaMantenimientoRptaConcilUba(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.COD_RPTA_VISA)
    @PreAuthorize("hasPermission('MANT_RPTAVISA','ANY')")
    @GetMapping("/{mantenimiento:codigoRptaVisa}")
    public String irPaginaMantenimientoCodigoRptaVisa(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.TI_TERM_POS)
    @PreAuthorize("hasPermission('MANT_TITERMPOS','ANY')")
    @GetMapping("/{mantenimiento:tipoTerminalPos}")
    public String irPaginaMantenimientoTipoTerminalPos(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.MET_ID_THB)
    @PreAuthorize("hasPermission('MANT_METIDTHB','ANY')")
    @GetMapping("/{mantenimiento:metodoIdThb}")
    public String irPaginaMantenimientoMetodoIdThb(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.ORIGEN)
    @PreAuthorize("hasPermission('MANT_ORIGEN','ANY')")
    @GetMapping("/{mantenimiento:origen}")
    public String irPaginaMantenimientoOrigen(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.IND_COL_TEL)
    @PreAuthorize("hasPermission('MANT_INDCOTEL','ANY')")
    @GetMapping("/{mantenimiento:indCorreoTelefono}")
    public String irPaginaMantenimientoIndCorreoTelefono(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CANAL_EMI)
    @PreAuthorize("hasPermission('MANT_CANALEMI','ANY')")
    @GetMapping("/{mantenimiento:canalEmisor}")
    public String irPaginaMantenimientoCanalEmisor(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.MOT_RECLAMO)
    @PreAuthorize("hasPermission('MANT_MOTRECLAMO','ANY')")
    @GetMapping("/{mantenimiento:motivoReclamo}")
    public String irPaginaMantenimientoMotivoReclamo(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CANAL)
    @PreAuthorize("hasPermission('MANT_CANAL','ANY')")
    @GetMapping("/{mantenimiento:canal}")
    public String irPaginaMantenimientoCanal(@PathVariable String mantenimiento,
            ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.MEMB)
    @PreAuthorize("hasPermission('MANT_MEMB','ANY')")
    @GetMapping("/{mantenimiento:membresia}")
    public String irPaginaMantenimientoMembresia(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.EMP)
    @PreAuthorize("hasPermission('MANT_EMP','ANY')")
    @GetMapping("/{mantenimiento:empresa}")
    public String irPaginaMantenimientoEmpresa(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.BIN)
    @PreAuthorize("hasPermission('MANT_BIN','ANY')")
    @GetMapping("/{mantenimiento:bin}")
    public String irPaginaMantenimientoBIN(@PathVariable String mantenimiento,
            ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("instituciones",
                institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("membresias", membresiaService.buscarTodos());
        // model.addAttribute("institucion",
        // institucionService.buscarPorCodigoInstitucionActual());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.PRODUCTO)
    @PreAuthorize("hasPermission('MANT_PRODUCT','ANY')")
    @GetMapping("/{mantenimiento:producto}")
    public String irPaginaMantenimientoProducto(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("logo", logoService.buscarTodosInstitucion());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("monedas", monedaService.buscarTodos());
        model.addAttribute("instituciones",
                institucionService.buscarPorCodigoInstitucionActual());
        return "seguras/mantenimiento/producto";
    }

    // @Audit(tipo = Tipo.MANT_CANALSEGUROWS)
    @PreAuthorize("hasPermission('MANT_CANALSEGUROWS','ANY')")
    @GetMapping("/{mantenimiento:canalesSegurosWS}")
    public String irPaginaMantenimientoCanalesSegurosWS(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("empresas", empresaService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.SUB_BIN)
    @PreAuthorize("hasPermission('MANT_SUBBIN','ANY')")
    @GetMapping("/{mantenimiento:subBin}")
    public String irPaginaMantenimientoSubBin(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("bines", binService.buscarTodos());
        model.addAttribute("membresias", membresiaService.buscarTodos());
        model.addAttribute("institucion",
                institucionService.buscarPorCodigoInstitucionActual());
        model.addAttribute("regimenes", multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_REGIMENES_PREPAGO));
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CLS_SERV)
    @PreAuthorize("hasPermission('MANT_CLSSERV','ANY')")
    @GetMapping("/{mantenimiento:claseServicio}")
    public String irPaginaMantenimientoClaseServicio(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("membresias", membresiaService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.COD_TXN)
    @PreAuthorize("hasPermission('MANT_CODTXN','ANY')")
    @GetMapping("/{mantenimiento:codigoTransaccion}")
    public String irPaginaMantenimientoCodigoTransaccion(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("claseTransacciones",
                claseTransaccionService.buscarTodos());
        model.addAttribute("registrosContable", multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_REGISTRO_CONTABLE));
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.COD_PROC_SW)
    @PreAuthorize("hasPermission('MANT_PROCSW','ANY')")
    @GetMapping("/{mantenimiento:codigoProcesoSwitch}")
    public String irPaginaMantenimientoCodigoProcesoSwitch(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("claseTransacciones",
                claseTransaccionService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.MUL_TAB_CAB)
    @PreAuthorize("hasPermission('MANT_MULTABCAB','ANY') or hasPermission('MANT_MULTABDET','ANY')")
    @GetMapping("/multiTabCab")
    public String irPaginaMultiTabla()
    {
        return "seguras/mantenimiento/multiTabla";
    }

    @Audit(tipo = Tipo.PARAM_GRAL)
    @PreAuthorize("hasPermission('MANT_PARAMGRAL','ANY')")
    @GetMapping("/parametroGeneral")
    public String irPaginaParametroGeneral(ModelMap model)
    {
        model.addAttribute("instituciones", institucionService.buscarTodos());
        model.addAttribute("empresas", empresaService.buscarTodos());
        return "seguras/mantenimiento/parametroGeneral";
    }

    @Audit(tipo = Tipo.PARAM_WS)
    @PreAuthorize("hasPermission('MANT_PARAMWS','ANY')")
    @GetMapping("/parametroWS")
    public String irPaginaParametroWebServices(ModelMap model)
    {
        return "seguras/mantenimiento/parametroWS";
    }

    @Audit(tipo = Tipo.PARAM_SFTP)
    @PreAuthorize("hasPermission('MANT_PARAMSFTP','ANY')")
    @GetMapping("/parametroSFTP")
    public String irPaginaParametroSFTP(ModelMap model)
    {
        return "seguras/mantenimiento/parametroSFTP";
    }

    @Audit(tipo = Tipo.PARAM_INT_CONT)
    @PreAuthorize("hasPermission('MANT_PARAMINTCONT','ANY')")
    @GetMapping("/parametroIntCon")
    public String irPaginaParametroIntCont(ModelMap model)
    {
        return "seguras/mantenimiento/parametroIntCon";
    }

    @Audit(tipo = Tipo.PARAM_SFTP)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPDIR','ANY')")
    @GetMapping("/{mantenimiento:parametroSFTPDirectorios}")
    public String irPaginaParametroSFTPDirectorios(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("tiposOperacion", multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_OPERACION_SFTP));
        model.addAttribute("procesos",
                this.parametroSFTPProcesoService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.PARAM_SFTP)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPPRO','ANY')")
    @GetMapping("/{mantenimiento:parametroSFTPProcesos}")
    public String irPaginaParametroSFTPProcesos(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("tiposOperacion", multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_OPERACION_SFTP));
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.PARAM_SFTP)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPARC','ANY')")
    @GetMapping("/{mantenimiento:parametroSFTPArchivos}")
    public String irPaginaParametroSFTPArchivos(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("directorios",
                this.parametroSFTPDirectorioService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CLTE)
    @PreAuthorize("hasPermission('MANT_CLTE','ANY')")
    @GetMapping("/{mantenimiento:cliente}")
    public String irPaginaMantenimientoCliente(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("bines", binService.buscarTodosBinOcho());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("institucion",
                institucionService.buscarPorCodigoInstitucionActual());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CONTAB_EMI)
    @PreAuthorize("hasPermission('MANT_CONTABEMI','ANY')")
    @GetMapping("/{mantenimiento:cuentaContableEmisor}")
    public String irPaginaCuentaContableEmisor(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("instituciones",
                institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("clientes", clienteService.buscarTodos());
        model.addAttribute("monedas", monedaService.buscarTodos());
        model.addAttribute("membresias", membresiaService.buscarTodos());
        model.addAttribute("origenes", origenService.buscarTodos());
        model.addAttribute("conceptoComisiones",
                conceptoComisionService.buscarTodos());
        model.addAttribute("producto", productoService.buscarTodos());
        model.addAttribute("claseTransacciones",
                claseTransaccionService.buscarTodos());
        model.addAttribute("logos", logoService.buscarTodosInstitucion());
        model.addAttribute("rolesTransaccion", multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CONTAB_MIS)
    @PreAuthorize("hasPermission('MANT_CONTABMIS','ANY')")
    @GetMapping("/{mantenimiento:cuentaContableMiscelaneo}")
    public String irPaginaCuentaContableMiscelaneo(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("instituciones",
                institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("clientes", clienteService.buscarTodos());
        model.addAttribute("monedas", monedaService.buscarTodos());
        model.addAttribute("membresias", membresiaService.buscarTodos());
        model.addAttribute("origenes", origenService.buscarTodos());
        model.addAttribute("claseTransacciones",
                claseTransaccionService.buscarTodos());
        model.addAttribute("rolesTransaccion", multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.RPTA_SW)
    @PreAuthorize("hasPermission('MANT_RPTASW','ANY')")
    @GetMapping("/{mantenimiento:codigoRptaSwitch}")
    public String irPaginaMantenimientoCodigoRptaSwitch(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("tipoRespuestas", multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_RESPUESTA));
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.PROC_BTEC)
    @PreAuthorize("hasPermission('MANT_PROCBTEC','ANY')")
    @GetMapping("/{mantenimiento:codigoProcBevertec}")
    public String irPaginaMantenimientoCodigoProcBevertec(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("canalesEmisores", canalEmisorService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.TRANS_BTEC)
    @PreAuthorize("hasPermission('MANT_TRANSBTEC','ANY')")
    @GetMapping("/{mantenimiento:codigoTxnBevertec}")
    public String irPaginaMantenimientoCodigoTxnBevertec(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("canalesEmisores", canalEmisorService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.AFIN)
    @PreAuthorize("hasPermission('MANT_AFINIDAD','ANY')")
    @GetMapping("/{mantenimiento:afinidad}")
    public String irPaginaMantenimientoAfinidad(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("logo", logoService.buscarTodosInstitucion());
        model.addAttribute("empresas", empresaService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.AFIN)
    @PreAuthorize("hasPermission('MANT_CLTEAFIN','ANY')")
    @GetMapping("/{mantenimiento:clienteAfinidad}")
    public String irPaginaMantenimientoClienteAfinidad(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("instituciones",
                parametroService.buscarinstitucionParametroGeneral());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("logo", logoService.buscarTodosInstitucion());
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.MANT_CONCEPTO_COMISION)
    @PreAuthorize("hasPermission('MANT_CONCEPTO_COMISION','ANY')")
    @GetMapping("/{mantenimiento:conceptoComision}")
    public String irPaginaMantenimientoConceptoComision(
            @PathVariable String mantenimiento, ModelMap model)
    {

        model.addAttribute(P_MANTENIMIENTO, mantenimiento);
        model.addAttribute("conceptosComisiones",
                conceptoComisionService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.PAN_ENTRY_MODE)
    @PreAuthorize("hasPermission('MANT_PANENTRYMODE','ANY')")
    @GetMapping("/{mantenimiento:modoIngresoPAN}")
    public String irPaginaMantenimientoPANEntryMode(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.PIN_ENTRY_CAP)
    @PreAuthorize("hasPermission('MANT_PINENTRYCAPABILITY','ANY')")
    @GetMapping("/{mantenimiento:capacidadIngresoPIN}")
    public String irPaginaMantenimientoPINEntryCapability(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CODIGO_FACTURACION)
    @PreAuthorize("hasPermission('MANT_CODIGOFACTURACION','ANY')")
    @GetMapping("/{mantenimiento:codigoFacturacion}")
    public String irPaginaMantenimientoCodigosFacturacion(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);

        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CTA_CONTABLE)
    @PreAuthorize("hasPermission('MANT_CTA','ANY')")
    @GetMapping("/{mantenimiento:cuentaContable}")
    public String irPaginaMantenimientoCuentaContable(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("monedas", this.monedaService.buscarTodos());
        model.addAttribute("tipoCuentaAlegra", this.multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_ESTADO_DE_EXTORNO));
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.CTA_AJUSTE)
    @PreAuthorize("hasPermission('MANT_CTAAJUS','ANY')")
    @GetMapping("/{mantenimiento:cuentaAjuste}")
    public String irPaginaMantenimuentoCuentaAjuste(
            @PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("monedas", this.monedaService.buscarTodos());
        model.addAttribute("rolesTransaccion", this.multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_ROL_TRANSACCION));
        model.addAttribute("tiposMovimiento", this.multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_MOVIMIENTO));
        model.addAttribute("registrosContables", this.multiTabDetService
                .buscarPorIdTabla(MultiTablaUtil.TABLA_REGISTRO_CONTABLE));
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.MANT_CONTAC_RECAUDA)
    @PreAuthorize("hasPermission('MANT_CONTAC_RECAUDA', 'ANY')")
    @GetMapping("/mantenimientos/{mantenimiento:recaudacionRecargas}")
    public String irPaginaMantenimientoReglaContable(
            @PathVariable String mantenimiento, ModelMap modelMap)
    {
        modelMap.addAttribute("mantenimiento", mantenimiento);
        modelMap.addAttribute("bancoRecaudador",
                this.multiTabDetService.buscarPorIdTabla(86));
        modelMap.addAttribute("empresas", this.empresaService.buscarTodos());
        modelMap.addAttribute("tipoOperacion",
                this.multiTabDetService.buscarPorIdTabla(87));
        modelMap.addAttribute("monedas", this.monedaService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }

    @Audit(tipo = Tipo.LOGO)
    @PreAuthorize("hasPermission('MANT_LOGO','ANY')")
    @GetMapping("/{mantenimiento:logo}")
    public String irPaginaMantenimientoLogo(@PathVariable String mantenimiento,
            ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("instituciones",
                institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("membresias", membresiaService.buscarTodos());
        return "seguras/mantenimiento/logo";
    }

}