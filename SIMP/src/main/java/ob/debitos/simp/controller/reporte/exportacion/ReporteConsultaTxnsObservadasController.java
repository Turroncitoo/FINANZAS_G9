package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TransaccionObservada;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionObservada;
import ob.debitos.simp.service.ITxnsObservadasService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/consulta")
public @Controller class ReporteConsultaTxnsObservadasController
{

    private @Autowired ITxnsObservadasService txnsObservadasService;

    @Audit
    @PreAuthorize("hasPermission('CON_MOVOBS','5')")
    @GetMapping(value = "/txnsObservadas", params = "accion=buscarPorCriterios")
    public void descargarReporteConsultaTxnsObservadasPorCriterios(@Validated CriterioBusquedaTransaccionObservada criterioBusqueda, Errors errors,
                                                                   HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TransaccionObservada> list = this.txnsObservadasService.buscarTransaccionesObservadasPorCriterios(criterioBusqueda);
        this.txnsObservadasService.exportarTxnsObservadasPorCriterios(list, criterioBusqueda, request, response);
    }

    @Audit
    @PreAuthorize("hasPermission('CON_MOVOBS','5')")
    @GetMapping(value = "/txnsObservadas", params = "accion=buscarPorTipoDocumento")
    public void descargarReporteConsultaTxnsObservadasTipoDocumento(@Validated CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento, Errors errors,
                                                                    HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TransaccionObservada> list = this.txnsObservadasService.buscarTransaccionesObservadasPorTipoDocumento(criterioBusquedaTipoDocumento);
        this.txnsObservadasService.exportarTxnsObservadasPorTipoDocumento(list, criterioBusquedaTipoDocumento, request, response);
    }

}
