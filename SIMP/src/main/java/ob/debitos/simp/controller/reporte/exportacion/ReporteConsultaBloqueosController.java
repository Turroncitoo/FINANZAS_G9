package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.Bloqueo;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaBloqueos;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.service.IBloqueoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Audit(accion = Accion.Reporte, tipo = Tipo.CON_BLOQ)
@RequestMapping("/prepago/consulta/bloqueo")
public @Controller class ReporteConsultaBloqueosController
{

    private @Autowired IBloqueoService bloqueoService;

    @Audit(comentario = Comentario.ReporteCriterioBusqueda)
    @PreAuthorize("hasPermission('CON_BLOQ', '5')")
    @GetMapping(params = "accion=exportarPorCriterios")
    public void descargarReporteConsultaTxnsObservadasPorCriterios(CriterioBusquedaBloqueos criterioBusqueda, Errors errors,
                                                                   HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<Bloqueo> list = this.bloqueoService.filtrarBloqueos(criterioBusqueda);
        this.bloqueoService.exportarBloqueosPorCriterios(list, criterioBusqueda, request, response);
    }

    @Audit(comentario = Comentario.ReporteTipoDocumento)
    @PreAuthorize("hasPermission('CON_BLOQ','5')")
    @GetMapping(params = "accion=exportarPorTipoDocumento")
    public void descargarReporteConsultaTxnsObservadasTipoDocumento(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento, Errors errors,
                                                                    HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<Bloqueo> list = this.bloqueoService.buscarBloqueos(criterioBusquedaTipoDocumento);
        this.bloqueoService.exportarBloqueosPorTipoDocumento(list, criterioBusquedaTipoDocumento, request, response);
    }

}
