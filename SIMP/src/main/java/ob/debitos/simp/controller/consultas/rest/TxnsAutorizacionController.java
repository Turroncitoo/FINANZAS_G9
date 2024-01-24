package ob.debitos.simp.controller.consultas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.consulta.movimiento.TxnAutorizadaComisiones;
import ob.debitos.simp.model.consulta.movimiento.TxnsAutorizacion;
import ob.debitos.simp.model.consulta.movimiento.TxnsAutorizacionDetalle;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleTransaccion;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionAutorizada;
import ob.debitos.simp.service.ITxnsAutorizacionService;

@Audit(tipo = Tipo.CON_MOV_AUT, accion = Accion.Consulta)
@RequestMapping("/txnsAutorizacion")
public @RestController class TxnsAutorizacionController
{
    private @Autowired ITxnsAutorizacionService txnsAutorizacionService;

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @PreAuthorize("hasPermission('CON_MOVAUT','ANY')")
    @GetMapping(params = "accion=buscarPorTipoDocumento")
    public List<TxnsAutorizacion> buscarPorTipoDocumento(
            CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
    {
        return txnsAutorizacionService.buscarTxnsAutorizaciones(criterioBusquedaTipoDocumento);
    }

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_MOVAUT','ANY')")
    @GetMapping(params = "accion=buscarPorFiltros")
    public List<TxnsAutorizacion> buscarPorFiltros(
            CriterioBusquedaTransaccionAutorizada criterioBusquedaTransaccionAutorizada)
    {
        return txnsAutorizacionService
                .filtrarTxnsAutorizacion(criterioBusquedaTransaccionAutorizada);
    }

    @Audit(comentario = Comentario.ConsultaDetalle)
    @PreAuthorize("hasPermission('CON_MOVAUT','ANY')")
    @GetMapping(params = "accion=buscarDetalle")
    public List<TxnsAutorizacionDetalle> buscarDetalle(
            CriterioBusquedaDetalleTransaccion criterioBusquedaDetalleTransaccion)
    {
        return txnsAutorizacionService
                .buscarDetalleTxnAutorizada(criterioBusquedaDetalleTransaccion);
    }

    @Audit(comentario = Comentario.ConsultaComisiones)
    @PreAuthorize("hasPermission('CON_MOVAUT','ANY')")
    @GetMapping(params = "accion=buscarComision")
    public List<TxnAutorizadaComisiones> buscarComision(@RequestParam String numeroDocumento)
    {
        return txnsAutorizacionService.buscarComisionesPorAutorizacion(
                TxnAutorizadaComisiones.builder().numeroDocumento(numeroDocumento).build());
    }
}