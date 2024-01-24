package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ob.debitos.simp.model.consulta.movimiento.TxnsSwDmpLog;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsSwdmplog;
import ob.debitos.simp.utilitario.ExcepcionUtil;
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
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionSwDmpLog;
import ob.debitos.simp.service.ITxnsSwDmpLogService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Audit(accion = Accion.Reporte, tipo = Tipo.CON_MOV_OBS)
@RequestMapping("/prepago/consulta/txnsSwDmpLog")
public @Controller class ReporteConsultaTxnsSwDmpLogController
{

    private @Autowired ITxnsSwDmpLogService tnxsSwDmpLogService;

    @Audit(comentario = Comentario.ReporteTipoDocumento)
    @PreAuthorize("hasPermission('RPT_MOVSWDMPLOG','5')")
    @GetMapping(params = "accion=exportarPorTipoDocumento")
    public void descargarReporteConsultaTxnsSwDmpLogTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda, Errors errors,
                                                                  HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsSwdmplog> criterioPaginacion = new CriterioPaginacion<>();
        criterioPaginacion.setCriterioBusqueda(criterioBusqueda);
        List<TxnsSwDmpLog> list = this.tnxsSwDmpLogService.buscarTxnsSwDmpLogPaginado(criterioPaginacion);
        this.tnxsSwDmpLogService.exportarTxnsSwDmpLogPorTipoDocumento(list, criterioBusqueda, request, response);
    }

}
