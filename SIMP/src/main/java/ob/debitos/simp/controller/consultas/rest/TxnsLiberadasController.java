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
import ob.debitos.simp.model.consulta.movimiento.TxnsLiberadas;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionLiberada;
import ob.debitos.simp.service.ITxnsLiberadasService;

@Audit(tipo = Tipo.CON_MOV_LIB, accion = Accion.Consulta)
@RequestMapping("/txnsLiberadas")
public @RestController class TxnsLiberadasController
{

    private @Autowired ITxnsLiberadasService txnsLiberadasService;

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @PreAuthorize("hasPermission('CON_MOVLIB','ANY')")
    @GetMapping(params = "accion=buscarPorDocumento")
    public List<TxnsLiberadas> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
    {
        return txnsLiberadasService.buscarTxnsLiberadas(criterioBusquedaTipoDocumento);
    }

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_MOVLIB','ANY')")
    @GetMapping(params = "accion=buscarPorFiltro")
    public List<TxnsLiberadas> buscarPorFiltros(CriterioBusquedaTransaccionLiberada criterioBusquedaTransaccionLiberada)
    {
        return txnsLiberadasService.filtrarTxnsLiberadas(criterioBusquedaTransaccionLiberada);
    }

}