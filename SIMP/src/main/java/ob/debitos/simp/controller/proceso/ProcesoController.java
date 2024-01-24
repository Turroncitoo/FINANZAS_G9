package ob.debitos.simp.controller.proceso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.service.IConsultaProcesoAutomaticoService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.service.IProcesoAutomaticoService;
import ob.debitos.simp.service.IUsuarioService;
import ob.debitos.simp.utilitario.MultiTablaUtil;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.Visita)
@RequestMapping("/proceso")
public @Controller class ProcesoController
{
    
    private @Autowired IUsuarioService usuarioService;
    private @Autowired IMultiTabDetService multiTabDetService;
    private @Autowired IConsultaProcesoAutomaticoService consultaProcesoAutomaticoService;
    private @Autowired IProcesoAutomaticoService procesoAutomaticoService;
    private @Autowired IInstitucionService institucionService;

    @Audit(tipo = Tipo.EJEC_MAN)
    @PreAuthorize("hasPermission('[EJEC_AVANCEFECHAPROCESO],[EJEC_PREP],[EJEC_COMPENSACION],[EJEC_CONCILIACION],[EJEC_CONTABILIZACION],[EJEC_PAIU],[EJEC_CTRLPROC],[EJEC_PREP1]','PARENT_MENU')")
    @GetMapping("/ejecucion/manual")
    public String irPaginaEjecucionProcesoManual(ModelMap model)
    {
        model.addAttribute("procesosAutomaticos", consultaProcesoAutomaticoService.buscarProcesosAutomaticos());
        return "seguras/proceso/ejecucion/manual";
    }

    @Audit(tipo = Tipo.CON_LOG_PROC_AUTOM)
    @PreAuthorize("hasPermission('CON_LOGPROCAUTOM','ANY')")
    @GetMapping("/logControlPrograma")
    public String irPaginaConsultaLogControlPrograma(ModelMap model)
    {
        model.addAttribute("usuarios", usuarioService.buscarTodos());
        model.addAttribute("tiposEjecucion", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_EJECUCION));
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        return "seguras/proceso/logControlPrograma";
    }

    @Audit(tipo = Tipo.CON_LOGCON_PROGRESUMEN)
    @PreAuthorize("hasPermission('RPT_LOGCONPROCRES','ANY')")
    @GetMapping("/reporteLogControlPrograma")
    public String irPaginaReporteLogControlPrograma(ModelMap model)
    {
        model.addAttribute("grupos", this.procesoAutomaticoService.buscarTodos());
        return "seguras/proceso/reporteLogControlPrograma";
    }

}
