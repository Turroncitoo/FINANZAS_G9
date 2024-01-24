package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsLiberadas;
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
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionLiberada;
import ob.debitos.simp.service.ITxnsLiberadasService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Recibe las peticiones del usuario para la generación del reporte del
 * resultado de la consulta de transacciones liberadas.
 * <p>
 * Especificamente genera los reportes de los resultados retornados por las
 * consultas de
 * {@link ob.debitos.simp.controller.consultas.rest.TxnsLiberadasController}.
 * <p>
 * Los reportes generados por esta clase son de tipo XLSX y utilizan la vista
 * {@link ob.debitos.simp.configuracion.view.JxlsView}.
 *
 * @author Fernando Fonseca
 */
@Audit(accion = Accion.Reporte, tipo = Tipo.CON_MOV_OBS)
@RequestMapping("/prepago/consulta/txnsLiberadas")
public @Controller class ReporteConsultaTxnsLiberadasExportacionController
{

    private @Autowired ITxnsLiberadasService tnxsLiberadasService;

    @Audit(comentario = Comentario.ReporteCriterioBusqueda)
    @PreAuthorize("hasPermission('CON_MOVLIB','5')")
    @GetMapping(params = "accion=exportarPorCriterios")
    public void descargarReporteConsultaTxnsLiberadasPorCriterios(@Validated CriterioBusquedaTransaccionLiberada criterioBusqueda, Errors errors,
                                                                  HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TxnsLiberadas> list = this.tnxsLiberadasService.filtrarTxnsLiberadas(criterioBusqueda);
        this.tnxsLiberadasService.exportarTxnsLiberadasPorCriterios(list, criterioBusqueda, request, response);
    }

    @Audit(comentario = Comentario.ReporteTipoDocumento)
    @PreAuthorize("hasPermission('CON_MOVLIB','5')")
    @GetMapping(params = "accion=exportarPorTipoDocumento")
    public void descargarReporteConsultaTxnsLiberadasTipoDocumento(@Validated CriterioBusquedaTipoDocumento criterioBusqueda, Errors errors,
                                                                   HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TxnsLiberadas> list = this.tnxsLiberadasService.buscarTxnsLiberadas(criterioBusqueda);
        this.tnxsLiberadasService.exportarTxnsLiberadasPorTipoDocumento(list, criterioBusqueda, request, response);
    }
}
