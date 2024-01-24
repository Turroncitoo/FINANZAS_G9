package ob.debitos.simp.controller.consultas.exportacion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaTxnsEmbossing;
import ob.debitos.simp.service.ITxnsEmbossingService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Vista
@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/txnsEmbossing")
public @Controller class TxnsEmbossingExportacionController
{

    private @Autowired ITxnsEmbossingService txnsEmbossingService;

    @Audit(tipo = Tipo.CON_EMBOSSING)
    @PreAuthorize("hasPermission('CON_EMBOSSING','5')")
    @GetMapping(value = "exportar", params = "accion=exportarPorCriterios")
    public ModelAndView descargarPersonasPorCriterios(ModelMap model, CriterioBusquedaTxnsEmbossing criterioBusqueda)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("reporte", txnsEmbossingService.buscarPorCriterio(criterioBusqueda));
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteTarjetasEmbozadas");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Reporte Tarjetas Embozadas");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }

}
