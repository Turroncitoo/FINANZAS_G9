package ob.debitos.simp.controller.consultas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.consulta.movimiento.TxnCompensacionComisiones;
import ob.debitos.simp.model.consulta.movimiento.TxnsCompensacion;
import ob.debitos.simp.model.consulta.movimiento.TxnsCompensacionDetalle;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionCompensacion;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.Pagina;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsCompensacion;
import ob.debitos.simp.service.ITxnsCompensacionService;
import ob.debitos.simp.utilitario.PaginacionUtil;

@Audit(tipo = Tipo.CON_MOV_LG_CNT, accion = Accion.Consulta)
public @RestController class TxnsCompensacionController
{
    private @Autowired ITxnsCompensacionService txnsCompensacionService;

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @PreAuthorize("hasPermission('CON_MOVLGCNT','2')")
    @PostMapping(value = "txnsCompensacion", params = "accion=buscarPorDocumento")
    public ResponseEntity<Pagina> buscarTodosPorDocumento(
            @RequestBody CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsCompensacion> criterioPaginacion)
    {
        List<TxnsCompensacion> data = txnsCompensacionService
                .buscarTxnsCompensacion(criterioPaginacion);
        return new ResponseEntity<>(
                PaginacionUtil.generarPagina(data, criterioPaginacion),
                HttpStatus.OK);
    }

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_MOVLGCNT','2')")
    @PostMapping(value = "txnsCompensacion", params = "accion=buscarPorFiltro")
    public ResponseEntity<Pagina> buscarPorFiltro(
            @RequestBody CriterioPaginacion<CriterioBusquedaTransaccionCompensacion, FiltroDtTxnsCompensacion> criterioPaginacion)
    {
        List<TxnsCompensacion> data = txnsCompensacionService
                .filtrarTxnsCompensacion(criterioPaginacion);
        return new ResponseEntity<>(
                PaginacionUtil.generarPagina(data, criterioPaginacion),
                HttpStatus.OK);
    }

    @Audit(comentario = Comentario.ConsultaDetalle)
    @PreAuthorize("hasPermission('CON_MOVLGCNT','7')")
    @GetMapping(value = "txnsCompensacion", params = "accion=buscarDetalle")
    public List<TxnsCompensacionDetalle> buscarDetalle(
            CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion)
    {
        return txnsCompensacionService.buscarDetalleTxnCompensacion(
                criterioBusquedaDetalleTransaccion);
    }

    @Audit(comentario = Comentario.ConsultaComisiones)
    @PreAuthorize("hasPermission('CON_MOVLGCNT','8')")
    @GetMapping(value = "txnsCompensacion", params = "accion=buscarComision")
    public List<TxnCompensacionComisiones> buscarComision(
            TxnCompensacionComisiones criterioBusqueda)
    {
        return txnsCompensacionService
                .buscarComisionesPorCompensacion(criterioBusqueda);
    }
}