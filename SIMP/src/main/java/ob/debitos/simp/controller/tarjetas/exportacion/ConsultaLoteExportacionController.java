package ob.debitos.simp.controller.tarjetas.exportacion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaLoteTarjetas;
import ob.debitos.simp.model.tarjetas.Lote;
import ob.debitos.simp.service.IGestionLotesService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Audit(accion = Accion.Reporte, tipo = Tipo.CON_TXNWS)
@RequestMapping("/consulta/lote/exportacion")
public @Controller class ConsultaLoteExportacionController
{

    public @Autowired IGestionLotesService gestionLotesService;

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '5')")
    @GetMapping(params = "accion=exportarLote")
    public void descargarConsultaLotePorCriterios(CriterioBusquedaLoteTarjetas criterioBusqueda, Errors errors, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<Lote> list = this.gestionLotesService.consultaLotesPorCriterios(criterioBusqueda);
        this.gestionLotesService.exportarLotesPorCriterios(list, criterioBusqueda, request, response);
    }

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '5')")
    @GetMapping(params = "accion=exportarLoteDetalle")
    public ModelAndView descargarConsultaLoteDetallePorCriterios(ModelMap model, CriterioBusquedaLoteTarjetas criterioBusqueda)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("reporte", this.gestionLotesService.consultaLotesDetalleAfiliacion(criterioBusqueda));
        params.put("criterioBusqueda", criterioBusqueda);
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE, "reporteConsultaLoteDetalle");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, "Consulta Lote Detalle");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }
    
}
