package ob.debitos.simp.controller.consultas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.consulta.movimiento.TxnsPendientes;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionPendiente;
import ob.debitos.simp.service.ITxnsPendientesService;

@Audit(tipo = Tipo.CON_MOV_PEND, accion = Accion.Consulta)
@RequestMapping("/txnsPendientes")
public @RestController class TxnsPendientesController
{
    private @Autowired ITxnsPendientesService txnsPendientesService;

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @PreAuthorize("hasPermission('CON_MOVPEND','ANY')")
    @GetMapping(params = "accion=buscarPorDocumento")
    public List<TxnsPendientes> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
    {
        return txnsPendientesService.buscarTxnsPendientes(criterioBusquedaTipoDocumento);
    }

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_MOVPEND','ANY')")
    @GetMapping(params = "accion=buscarPorFiltro")
    public List<TxnsPendientes> buscarPorFiltros(
            CriterioBusquedaTransaccionPendiente criterioBusquedaTransaccionPendiente)
    {
        return txnsPendientesService.filtrarTxnsPendientes(criterioBusquedaTransaccionPendiente);
    }
}