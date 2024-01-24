package ob.debitos.simp.controller.tarjetas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.service.IEmpresaService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.ILogoService;
import ob.debitos.simp.service.IMonedaService;
import ob.debitos.simp.service.IMultiTabDetService;
import ob.debitos.simp.service.IPaisService;
import ob.debitos.simp.utilitario.MultiTablaUtil;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.Visita)
@RequestMapping("/tarjetas")
public @Controller class TarjetasController
{

    private @Autowired IMultiTabDetService multiTabDetService;
    private @Autowired IEmpresaService empresaService;
    private @Autowired IMonedaService monedaService;
    private @Autowired IInstitucionService institucionService;
    private @Autowired ILogoService logoService;
    private @Autowired IPaisService paisService;

    private static final String NO_APLICA = "N/A";

    @PreAuthorize("hasPermission('EJEC_AFILIACIONES','ANY')")
    @GetMapping("/afiliaciones")
    public String irPaginaRequerimientosAfiliaciones(ModelMap model)
    {
        model.addAttribute("instituciones", this.institucionService.buscarPorInstitucionEmpresa());
        model.addAttribute("empresas", this.empresaService.buscarTodos());
        model.addAttribute("logos", this.logoService.buscarTodosInstitucion());
        model.addAttribute("distribucion", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_INDICADOR_DISTRIBUCION_AFILIACION));
        model.addAttribute("tipoOrdenTarjeta", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_TIPO_ORDEN_TARJETA).stream().filter(a -> !a.getIdItem().equals(NO_APLICA)).toArray());
        model.addAttribute("tipoAfiliaciones", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_AFILIACION_TARJETA).stream().filter(a -> !a.getIdItem().equals(NO_APLICA)).toArray());
        model.addAttribute("tipoDocumentos", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
        model.addAttribute("sexos", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_SEXO));
        model.addAttribute("monedas", this.monedaService.buscarTodos());
        model.addAttribute("paises", this.paisService.buscarTodos());
        model.addAttribute("categorias", this.multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_CATEGORIAS_REGIMEN));
        return "seguras/tarjetas/afiliaciones/afiliaciones";
    }

}
