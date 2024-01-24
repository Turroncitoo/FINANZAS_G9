package ob.debitos.simp.controller.consultas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.consulta.movimiento.Bloqueo;
import ob.debitos.simp.model.criterio.CriterioBusquedaBloqueos;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.service.IBloqueoService;

@Audit(tipo = Tipo.CON_BLOQ, accion = Accion.Consulta)
@RequestMapping("/bloqueo")
public @RestController class BloqueoController
{

    private @Autowired IBloqueoService bloqueoService;

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @PreAuthorize("hasPermission('CON_BLOQ','ANY')")
    @GetMapping(params = "accion=buscarPorDocumento")
    public List<Bloqueo> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterio)
    {
        return bloqueoService.buscarBloqueos(criterio);
    }

    @Audit(comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('CON_BLOQ','ANY')")
    @GetMapping(params = "accion=buscarPorFiltro")
    public List<Bloqueo> buscarPorFiltros(CriterioBusquedaBloqueos criterio)
    {
        return bloqueoService.filtrarBloqueos(criterio);
    }

}
