package ob.debitos.simp.controller.consultas.rest;

import java.util.List;

import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.Pagina;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsSwdmplog;
import ob.debitos.simp.utilitario.PaginacionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.consulta.movimiento.TxnSwDmpLogDetalle;
import ob.debitos.simp.model.consulta.movimiento.TxnsSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionSwDmpLog;
import ob.debitos.simp.service.ITxnsSwDmpLogService;

@Audit(tipo = Tipo.CON_MOV_SWDMPLOG, accion = Accion.Consulta)
@RequestMapping("/txnsSwDmpLog")
public @RestController class TxnsSwDmpLogController
{

    private @Autowired ITxnsSwDmpLogService txnsSwDmpLogService;

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @PreAuthorize("hasPermission('CON_MOVSWDMPLOG', '2')")
    @PostMapping(params = "accion=buscarPorDocumento")
    public ResponseEntity<Pagina> buscarPorTipoDocumento(@RequestBody CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsSwdmplog> criterioPaginacion)
    {
        List<TxnsSwDmpLog> data = txnsSwDmpLogService.buscarTxnsSwDmpLogPaginado(criterioPaginacion);
        return new ResponseEntity<>(PaginacionUtil.generarPagina(data, criterioPaginacion), HttpStatus.OK);
    }

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_MOVSWDMPLOG', '2')")
    @PostMapping(params = "accion=buscarPorFiltro")
    public ResponseEntity<Pagina> buscarPorFiltros(@RequestBody CriterioPaginacion<CriterioBusquedaTransaccionSwDmpLog, FiltroDtTxnsSwdmplog> criterioPaginacion)
    {
        List<TxnsSwDmpLog> data = txnsSwDmpLogService.filtrarTxnsSwDmpLogPaginado(criterioPaginacion);
        return new ResponseEntity<>(PaginacionUtil.generarPagina(data, criterioPaginacion), HttpStatus.OK);
    }

    @Audit(comentario = Comentario.ConsultaDetalle)
    @PreAuthorize("hasPermission('CON_MOVSWDMPLOG','7')")
    @GetMapping(params = "accion=buscarDetalle")
    public List<TxnSwDmpLogDetalle> buscarDetalle(CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion)
    {
        return txnsSwDmpLogService.buscarDetalleTxnsSwDmpLog(criterioBusquedaDetalleTransaccion);
    }

}