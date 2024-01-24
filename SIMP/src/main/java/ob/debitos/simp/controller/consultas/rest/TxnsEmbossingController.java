package ob.debitos.simp.controller.consultas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.consulta.movimiento.TxnsEmbossing;
import ob.debitos.simp.model.criterio.CriterioBusquedaTxnsEmbossing;
import ob.debitos.simp.service.ITxnsEmbossingService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.CON_MOV_AJUS, accion = Accion.Consulta)
@RequestMapping("/txnsEmbossing")
public @RestController class TxnsEmbossingController
{

    private @Autowired ITxnsEmbossingService txnsEmbossingService;

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_EMBOSSING','ANY')")
    @GetMapping(value = "buscar", params = "accion=buscarPorCriterios")
    public List<TxnsEmbossing> buscarTransaccionesAjustePorCriterios(@Validated CriterioBusquedaTxnsEmbossing criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return this.txnsEmbossingService.buscarPorCriterio(criterioBusqueda);
    }

}