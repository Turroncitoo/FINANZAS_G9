package ob.debitos.simp.controller.proceso;

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
import ob.debitos.simp.service.IProcesoAutomaticoService;
import ob.debitos.simp.service.ISubModuloService;
import ob.debitos.simp.utilitario.MultiTablaUtil;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.Visita)
@RequestMapping("/proceso/mantenimiento")
public @Controller class MantenimientoProcesoController
{
    private @Autowired ISubModuloService subModuloService;
    private @Autowired IMultiTabDetService multiTabDetService;
    private @Autowired IProcesoAutomaticoService procesoAutomaticoService;

    @Audit(tipo = Tipo.PROG)
    @PreAuthorize("hasPermission('MANT_PROG','ANY')")
    @GetMapping("/{mantenimiento:programa}")
    public String irPaginaMantenimientoPrograma(@PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("subModulos", subModuloService.buscarTodos());
        model.addAttribute("grupos", procesoAutomaticoService.buscarTodos());
        return "seguras/proceso/mantenimiento";
    }

    @Audit(tipo = Tipo.PROC_AUTO)
    @PreAuthorize("hasPermission('MANT_PROCAUTO','ANY')")
    @GetMapping("/{mantenimiento:procesoAutomatico}")
    public String irPaginaMantenimientoProcesoAutomatico(@PathVariable String mantenimiento,
            ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("tiposProcesoAutomatico",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_PROCESO_AUTOMATICO));
        return "seguras/proceso/mantenimiento";
    }

    @Audit(tipo = Tipo.SUB_MOD)
    @PreAuthorize("hasPermission('MANT_SUBMOD','ANY')")
    @GetMapping("/{mantenimiento:subModulo}")
    public String irPaginaMantenimientoSubModulo(@PathVariable String mantenimiento, ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        return "seguras/proceso/mantenimiento";
    }
}