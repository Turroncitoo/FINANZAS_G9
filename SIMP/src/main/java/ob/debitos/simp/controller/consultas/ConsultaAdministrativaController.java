package ob.debitos.simp.controller.consultas;

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
import ob.debitos.simp.service.IBinService;
import ob.debitos.simp.service.IEmpresaService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.ILogoService;
import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.service.IPaisService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.utilitario.MultiTablaUtil;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.VisitaConsulta)
@RequestMapping("/consulta/administrativa")
public @Controller class ConsultaAdministrativaController
{

    private @Autowired IMultiTabDetService multiTabDetService;
    private @Autowired IBinService binService;
    private @Autowired ILogoService logoService;
    private @Autowired IInstitucionService institucionService;
    private @Autowired IEmpresaService empresaService;
    private @Autowired IMonedaService monedaService;
    private @Autowired IPaisService paisService;
    private @Autowired IParametroGeneralService parametroGeneralService;

    @Audit(tipo = Tipo.CON_ADMIN_AG)
    @PreAuthorize("hasPermission('CON_ADMINAG','2')")
    @GetMapping("/{consulta:agencia}")
    public String irPaginaConsultaAdministrativaAgencia(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        return "seguras/consulta/administrativa";
    }

    @Audit(tipo = Tipo.CON_ADMIN_CLIENTE)
    @PreAuthorize("hasPermission('CON_ADMINCTE','2')")
    @GetMapping("/{consulta:clientePersona}")
    public String irPaginaConsultaAdministrativaCliente(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        return "seguras/consulta/administrativa";
    }

    @Audit(tipo = Tipo.CON_ADMIN_CTA)
    @PreAuthorize("hasPermission('CON_ADMINCTA','2')")
    @GetMapping("/{consulta:cuenta}")
    public String irPaginaConsultaAdministrativaCuenta(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        return "seguras/consulta/administrativa";
    }

    @Audit(tipo = Tipo.CON_ADMIN_TARJETA)
    @PreAuthorize("hasPermission('CON_ADMINTARJETA','2')")
    @GetMapping("/{consulta:tarjeta}")
    public String irPaginaConsultaAdministrativaTarjeta(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        return "seguras/consulta/administrativa";
    }

    /* Consultas Prepago */

    @Audit(tipo = Tipo.CON_ADMIN_PERSONA_PP)
    @PreAuthorize("hasPermission('CON_ADMINPER','2')")
    @GetMapping("/{consulta:personaPP}")
    public String irPaginaConsultaAdministrativaPersonaPP(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("empresas", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("sexos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_SEXO));
        model.addAttribute("paises", paisService.buscarTodos());
        return "seguras/consulta/administrativa";
    }

    @Audit(tipo = Tipo.CON_ADMIN_TARJETA_PP)
    @PreAuthorize("hasPermission('CON_ADMINTARJETA','2')")
    @GetMapping("/{consulta:tarjetaPP}")
    public String irPaginaConsultaAdministrativaTarjetaPP(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("instituciones", institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", empresaService.buscarTodos());
        model.addAttribute("logo", logoService.buscarPorCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion()));
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("motivosBloqueo", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPOS_BLOQUEO_WS));
        model.addAttribute("regimenes", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPOS_REGIMEN_WS));
        model.addAttribute("monedas", monedaService.buscarTodos());
        return "seguras/consulta/administrativa";
    }

    @Audit(tipo = Tipo.CON_ADMIN_CUENTA_PP)
    @PreAuthorize("hasPermission('CON_ADMINCTA','2')")
    @GetMapping("/{consulta:cuentaPP}")
    public String irPaginaConsultaAdministrativaCuentaPP(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("tipoDocumentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("bines", this.binService.buscarTodos());
        return "seguras/consulta/administrativa";
    }

    @Audit(tipo = Tipo.CON_ADMIN_LOTE_PP)
    @PreAuthorize("hasPermission('CON_ADMINLOT','ANY')")
    @GetMapping("/{consulta:lotePP}")
    public String irPaginaConsultaAdministrativaLotePP(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        return "seguras/consulta/administrativa";
    }

    @Audit(tipo = Tipo.CON_ADMIN_RECARGA_PP)
    @PreAuthorize("hasPermission('CON_ADMINRECARGA','ANY')")
    @GetMapping("/{consulta:recargaPP}")
    public String irPaginaConsultaAdministrativaRecargaPP(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        return "seguras/consulta/administrativa";
    }

}