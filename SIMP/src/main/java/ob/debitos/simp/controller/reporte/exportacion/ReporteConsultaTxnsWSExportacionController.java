package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsPreAutorizadas;
import ob.debitos.simp.model.consulta.movimiento.TxnsWebServices;
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
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionWebServices;
import ob.debitos.simp.service.ITxnsWebServicesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Audit(accion = Accion.Reporte, tipo = Tipo.CON_TXNWS)
@RequestMapping("/txnsWebServices")
public @Controller class ReporteConsultaTxnsWSExportacionController
{

    private @Autowired ITxnsWebServicesService txnsWebServices;

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('CON_TXNWS', '5')")
    @GetMapping(params = "accion=exportarPorCriterio")
    public void descargarReporteConsultaTxnsWebServicesPorCriterios(CriterioBusquedaTransaccionWebServices criterioBusqueda, Errors errors,
                                                                    HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TxnsWebServices> list = this.txnsWebServices.buscarPorCriterio(criterioBusqueda);
        this.txnsWebServices.exportarTxnsWebServicesPorCriterios(list, criterioBusqueda, request, response);
    }

    @PreAuthorize("hasPermission('CON_PREAUTTXNWS', 'ANY')")
    @GetMapping(value = "/preAutorizadas", params = "accion=exportarPorCriterio")
    public void descargarReporteConsultaTxnsPreAutorizadasPorCriterios(CriterioBusquedaTransaccionWebServices criterioBusqueda, Errors errors,
                                                                       HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TxnsPreAutorizadas> list = this.txnsWebServices.buscarPreAutorizadasPorCriterio(criterioBusqueda);
        this.txnsWebServices.exportarTxnsPreAutorizadasPorCriterios(list, criterioBusqueda, request, response);
    }

}
