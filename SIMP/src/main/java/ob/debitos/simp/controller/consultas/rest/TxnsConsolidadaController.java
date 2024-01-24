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
import ob.debitos.simp.model.consulta.movimiento.TxnsConsolidada;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionConsolidada;
import ob.debitos.simp.service.ITxnsConsolidadaService;

@Audit(tipo = Tipo.CON_MOV_CONSOL, accion = Accion.Consulta)
@RequestMapping("/txnsConsolidada")
public @RestController class TxnsConsolidadaController
{
    private @Autowired ITxnsConsolidadaService txnsConsolidadaService;

    @PreAuthorize("hasPermission('CON_MOVCONSOL','ANY')")
    @GetMapping(params = "accion=buscarTodos")
    public List<TxnsConsolidada> buscarTodos()
    {
        return txnsConsolidadaService.buscarTxnsConsolidada(null);
    }

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @PreAuthorize("hasPermission('CON_MOVCONSOL','ANY')")
    @GetMapping(params = "accion=buscarPorDocumento")
    public List<TxnsConsolidada> buscarTodosPorDocumento(
            CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
    {
        return txnsConsolidadaService.buscarTxnsConsolidada(criterioBusquedaTipoDocumento);

    }

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_MOVCONSOL','ANY')")
    @GetMapping(params = "accion=buscarPorFiltro")
    public List<TxnsConsolidada> buscarPorFiltro(
            CriterioBusquedaTransaccionConsolidada criterioBusquedaTransaccionConsolidada)
    {
        return txnsConsolidadaService
                .filtrarTxnsConsolidada(criterioBusquedaTransaccionConsolidada);
    }
}