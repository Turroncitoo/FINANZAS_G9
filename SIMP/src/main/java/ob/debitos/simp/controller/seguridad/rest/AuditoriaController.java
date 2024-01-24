package ob.debitos.simp.controller.seguridad.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.model.criterio.CriterioBusquedaAuditoria;
import ob.debitos.simp.model.seguridad.Auditoria;
import ob.debitos.simp.service.IAuditoriaService;

@RequestMapping("seguridad/auditoria")
public @RestController class AuditoriaController
{
    private @Autowired IAuditoriaService auditoriaService;

    private static final Logger logger = LoggerFactory.getLogger(AuditoriaController.class);

    @PreAuthorize("hasPermission('CON_AUDIT', '2')")
    @GetMapping(params = "accion=buscar")
    public List<Auditoria> buscarPistasAuditoriaPorCriterio(CriterioBusquedaAuditoria criterioBusquedaAuditoria)
    {
        logger.info(criterioBusquedaAuditoria.toString());
        return this.auditoriaService.buscarPistasAuditoriaPorCriterio(criterioBusquedaAuditoria);
    }
}