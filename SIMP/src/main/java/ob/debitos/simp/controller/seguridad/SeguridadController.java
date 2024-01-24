package ob.debitos.simp.controller.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.service.ICategoriaRecursoService;
import ob.debitos.simp.service.ILlavesService;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.service.IPerfilService;
import ob.debitos.simp.service.ISecRecursoService;
import ob.debitos.simp.service.IUsuarioService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.MultiTablaUtil;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.Visita)
@RequestMapping("/seguridad")
public @Controller class SeguridadController
{
    private @Autowired IPerfilService perfilService;
    private @Autowired IUsuarioService usuarioService;
    private @Autowired IMultiTabDetService multiTabDetService;
    private @Autowired ISecRecursoService secRecursoService;
    private @Autowired ICategoriaRecursoService categoriaRecursoService;
    private @Autowired ILlavesService llavesService;

    @Audit(tipo = Tipo.PERFIL)
    @PreAuthorize("hasPermission('MANT_PERFIL','ANY')")
    @GetMapping("/{mantenimiento:perfil}")
    public String irPaginaMantenimientoPerfil(@PathVariable String mantenimiento, Model model)
    {
        model.addAttribute(ConstantesGenerales.P_MANTENIMIENTO, mantenimiento);
        return ConstantesGenerales.PAGINA_MANT_SEGURIDAD;
    }

    @Audit(tipo = Tipo.USUARIO)
    @PreAuthorize("hasPermission('MANT_USUARIO','ANY')")
    @GetMapping("/{mantenimiento:usuario}")
    public String irPaginaMantenimientoUsuario(@PathVariable String mantenimiento, Model model)
    {
        model.addAttribute(ConstantesGenerales.P_MANTENIMIENTO, mantenimiento);
        model.addAttribute("perfiles", this.perfilService.buscarTodos());
        return ConstantesGenerales.PAGINA_MANT_SEGURIDAD;
    }

    @Audit(tipo = Tipo.PERFIL_RECURSO)
    @PreAuthorize("hasPermission('MANT_PERFIL_RECURSO','ANY')")
    @GetMapping("/perfilRecurso")
    public String irPaginaAsignacionPerfilesRecursos(Model model)
    {
        model.addAttribute("categoriasRecurso",
                this.categoriaRecursoService.buscarTodosCategoriaRecurso());
        return "seguras/seguridad/perfilRecurso";
    }

    @PreAuthorize("hasPermission('CON_AUDIT','ANY')")
    @GetMapping("/auditoria")
    public String irPaginaConsultaAuditoria(Model model)
    {
        model.addAttribute("usuarios", this.usuarioService.buscarTodos());
        model.addAttribute("recursos", this.secRecursoService.buscarAuditables());
        model.addAttribute("accionesAuditoria",
                this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ACCION_AUDITORIA));
        return "seguras/seguridad/auditoria";
    }

    @Audit(tipo = Tipo.CAMB_CONTRASENIA)
    @PreAuthorize("hasPermission('CAMB_CONTRASENIA', 'ANY')")
    @GetMapping("/contrasenia")
    public String irPaginaCambioContrasenia()
    {
        return "seguras/seguridad/contrasenia";
    }

    @Audit(tipo = Tipo.POLIT_SEG)
    @PreAuthorize("hasPermission('MANT_POLITICA_SEGURIDAD','ANY')")
    @GetMapping("/{mantenimiento:politicaSeguridad}")
    public String irPaginaMantenimientoPoliticaSeguridad(@PathVariable String mantenimiento,
            Model model)
    {
        model.addAttribute(ConstantesGenerales.P_MANTENIMIENTO, mantenimiento);
        return ConstantesGenerales.PAGINA_MANT_SEGURIDAD;
    }
    
    
    @Audit(tipo = Tipo.POLIT_SEG)
    @PreAuthorize("hasPermission('SEC_LLAVES','ANY')")
    @GetMapping("/llaves/transporte")
    public String irPaginaConfigurarLlavesTransporte(Model model)
    {	
    	model.addAttribute("existeLLave", this.llavesService.existeLlaveTransporte());
        return ConstantesGenerales.PAGINA_LLAVES_TRANSPORTE;
    }
    
    @Audit(tipo = Tipo.POLIT_SEG)
    @PreAuthorize("hasPermission('SEC_LLAVES','ANY')")
    @GetMapping("/llaves/trabajo")
    public String irPaginaConfigurarLlavesTrabajo(Model model)
    {	
    	model.addAttribute("existeLLave", this.llavesService.existeLlaveTransporte());
        return ConstantesGenerales.PAGINA_LLAVES_TRABAJO;
    }
}