package ob.debitos.simp.controller.consultas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.model.criterio.CriterioBusquedaSaldo;
import ob.debitos.simp.model.prepago.Saldo;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.service.ISaldoService;

@Validated
public @RestController class SaldoController
{

    private @Autowired ISaldoService saldoService;

    @PreAuthorize("hasPermission('CON_MOVSAL','2')")
    @GetMapping(value = "saldos", params = "accion=buscarTodos")
    public List<Saldo> buscarTodos()
    {
        return saldoService.buscarTodos();
    }

    @PreAuthorize("hasPermission('CON_MOVSAL','2')")
    @GetMapping(value = "saldos", params = "accion=buscarPorCriterios")
    public List<Saldo> buscarPorCriterio(CriterioBusquedaSaldo criterioBusquedaSaldo)
    {
        return this.saldoService.buscarPorCriterio(criterioBusquedaSaldo);
    }

    @PreAuthorize("hasPermission('CON_MOVSAL','2')")
    @GetMapping(value = "saldos", params = "accion=buscarPorTipoDocumento")
    public List<Saldo> buscarPorTipoDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumentoPrepago)
    {
        return this.saldoService.buscarPorTipoDocumento(criterioBusquedaTipoDocumentoPrepago);
    }

}
