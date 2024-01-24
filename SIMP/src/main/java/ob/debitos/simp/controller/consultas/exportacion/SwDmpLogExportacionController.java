package ob.debitos.simp.controller.consultas.exportacion;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.consulta.movimiento.TxnsSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionSwDmpLog;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsSwdmplog;
import ob.debitos.simp.service.ITxnsSwDmpLogService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Vista
@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/exportacion/")
public @Controller class SwDmpLogExportacionController
{

    private @Autowired ITxnsSwDmpLogService txnsSwDmpLogService;

    @Audit(tipo = Tipo.CON_MOV_SWDMPLOG)
    @PreAuthorize("hasPermission('CON_MOVSWDMPLOG', '5')")
    @GetMapping(value = "txnsSwDmpLog/detallado", params = "accion=buscarPorFiltro")
    public void generarExcelSwDmpLogDetalladoPorCriterios(
            CriterioBusquedaTransaccionSwDmpLog criterioBusqueda, Errors errors,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        CriterioPaginacion<CriterioBusquedaTransaccionSwDmpLog, FiltroDtTxnsSwdmplog> criterioPaginacion = new CriterioPaginacion<>();
        criterioPaginacion.setCriterioBusqueda(criterioBusqueda);
        List<TxnsSwDmpLog> list = this.txnsSwDmpLogService
                .filtrarTxnsSwDmpLogPaginado(criterioPaginacion);
        this.txnsSwDmpLogService.exportarTxnsSwDmpLogPorCriteriosDetallado(list,
                criterioBusqueda, request, response);
    }

}
