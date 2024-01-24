package ob.debitos.simp.controller.wshub;

import ob.debitos.simp.service.IEmpresaService;
import ob.debitos.simp.service.IInstitucionService;
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
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.service.ICodigoRptaSwitchService;
import ob.debitos.simp.utilitario.FiltrarDatos;
import ob.debitos.simp.utilitario.MultiTablaUtil;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.VisitaConsulta)
@Controller
@RequestMapping("/prepago/wshub/consultaws")
public class WebServiceController
{

    private @Autowired ICodigoRptaSwitchService codigoRptaSwitchService;
    private @Autowired IMultiTabDetService multiTabDetService;
    private @Autowired IInstitucionService institucionService;
    private @Autowired IEmpresaService empresaService;

    @Audit(tipo = Tipo.CON_WS_SALDOS_MOV)
    @PreAuthorize("hasPermission('WS_SALDOS_MOVIMIENTOS','ANY')")
    @GetMapping("/{consulta:saldosMovimientos}")
    public String irPaginaConsultaSaldosMovimientos(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("tipoDocumentos",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        return "seguras/wshub/consultaWS";
    }

    @Audit(tipo = Tipo.CON_WS_TARJ_CLTES)
    @PreAuthorize("hasPermission('WS_CLIENTES_TARJETAS','ANY')")
    @GetMapping("/{consulta:clientesTarjetas}")
    public String irPaginaConsultaSaldosTarjetas(@PathVariable String consulta, ModelMap model)
    {

        model.addAttribute("consulta", consulta);
        model.addAttribute("tipoDocumentos",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        return "seguras/wshub/consultaWS";
    }

    @Audit(tipo = Tipo.CON_WS_SER_WEB)
    @PreAuthorize("hasPermission('WS_CON_SERVICIO_WEB','ANY')")
    @GetMapping("/{consulta:consultas}")
    public String irPaginaConsultaServicioWeb(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("estadosExtornos",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTADO_DE_EXTORNO));
        model.addAttribute("operacionesFinancieras",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_OPERACIONES));

        model.addAttribute("localErroresCode", FiltrarDatos.obtenerPorTipoError(codigoRptaSwitchService.buscarTodos(), "localErroresCode"));
        model.addAttribute("UBAErroresCode", FiltrarDatos.obtenerPorTipoError(codigoRptaSwitchService.buscarTodos(), "UBAErroresCode"));

        return "seguras/wshub/consultaServicioWeb";
    }

    @Audit(tipo = Tipo.CON_WS_SER_WEB)
    @PreAuthorize("hasPermission('WS_LOG_CONTROL','ANY')")
    @GetMapping("/logControl")
    public String irPaginaControlLogServicioWeb(ModelMap model)
    {
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("operaciones", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_WS_SIMPHUB_PARA_FILTRO));
        return "seguras/wshub/logControlWS";
    }

}
