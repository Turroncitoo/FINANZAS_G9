package ob.debitos.simp.controller.tarjetas.exportacion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Audit(accion = Accion.Consulta, comentario = Comentario.Reporte)
@RequestMapping("/plantilla/lote/nominada")
public @Controller class PlantillaAfiliacionNominadaExportacionController
{

    @Audit(tipo = Tipo.RPT_CONCIL_OBS)
    @GetMapping(params = "accion=exportar")
    public ModelAndView descargarPlantillaAfiliacionLoteNominada(ModelMap model)
    {
        Map<String, Object> params = new HashMap<>();
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "plantillaLoteAfiliacionNominada");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Planfilla para afiliar tarjetas");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }

}
