package ob.debitos.simp.controller.consultas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.consulta.administrativa.Agencia;
import ob.debitos.simp.model.consulta.administrativa.ClientePersona;
import ob.debitos.simp.model.consulta.administrativa.Cuenta;
import ob.debitos.simp.model.consulta.administrativa.Tarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaCuenta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.prepago.CuentaPP;
import ob.debitos.simp.model.prepago.LotePP;
import ob.debitos.simp.model.prepago.PersonaPP;
import ob.debitos.simp.model.prepago.RecargaPP;
import ob.debitos.simp.model.prepago.TarjetaPP;
import ob.debitos.simp.service.IAgenciaService;
import ob.debitos.simp.service.IClientePersonaService;
import ob.debitos.simp.service.ICuentaPPService;
import ob.debitos.simp.service.ICuentaService;
import ob.debitos.simp.service.ILotePPService;
import ob.debitos.simp.service.IPersonaPPService;
import ob.debitos.simp.service.IRecargaPPService;
import ob.debitos.simp.service.ITarjetaPPService;
import ob.debitos.simp.service.ITarjetaService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/consulta/administrativa")
public @RestController class AdministrativaController
{

    private @Autowired ICuentaService cuentaService;
    private @Autowired ITarjetaService tarjetaService;
    private @Autowired IAgenciaService agenciaService;
    private @Autowired IClientePersonaService clientePersonaService;

    private @Autowired IPersonaPPService personaPPService;
    private @Autowired ITarjetaPPService tarjetaPPService;
    private @Autowired ICuentaPPService cuentaPPService;
    private @Autowired ILotePPService lotePPService;
    private @Autowired IRecargaPPService recargaPPService;

    @Audit(tipo = Tipo.CON_ADMIN_AG)
    @PreAuthorize("hasPermission('CON_ADMINAG','ANY')")
    @GetMapping(value = "/agencia", params = "accion=buscarTodos")
    public List<Agencia> buscarTodosAgencia()
    {
        return agenciaService.buscarTodos();
    }

    @Audit(tipo = Tipo.CON_ADMIN_CLIENTE)
    @PreAuthorize("hasPermission('CON_ADMINCTE','ANY')")
    @GetMapping(value = "/clientePersona", params = "accion=buscarTodos")
    public List<ClientePersona> buscarTodosCliente()
    {
        return clientePersonaService.buscarTodos();
    }

    @Audit(tipo = Tipo.CON_ADMIN_CTA)
    @PreAuthorize("hasPermission('CON_ADMINCTA','ANY')")
    @GetMapping(value = "/cuenta", params = "accion=buscarTodos")
    public List<Cuenta> buscarTodosCuenta()
    {
        return cuentaService.buscarTodos();
    }

    @Audit(tipo = Tipo.CON_ADMIN_CTA)
    @PreAuthorize("hasPermission('CON_ADMINCTA','ANY')")
    @GetMapping(value = "/cuenta", params = "accion=buscarPorTipoDocumento")
    public List<Cuenta> buscarCuentasPorTipoDocumento(
            @Validated CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return cuentaService
                .buscarPorTipoDocumento(criterioBusquedaTipoDocumento);
    }

    @Audit(tipo = Tipo.CON_ADMIN_CTA)
    @PreAuthorize("hasPermission('CON_ADMINCTA','ANY')")
    @GetMapping(value = "/cuenta", params = "accion=buscarPorCriterios")
    public List<Cuenta> buscarCuentasPorCriterios(
            @Validated CriterioBusquedaCuenta criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return cuentaService.buscarPorCriterios(criterioBusqueda);
    }

    @Audit(tipo = Tipo.CON_ADMIN_TARJETA)
    @PreAuthorize("hasPermission('CON_ADMINTARJETA','ANY')")
    @GetMapping(value = "/tarjeta", params = "accion=buscarTodos")
    public List<Tarjeta> buscarTodosTarjeta()
    {
        return tarjetaService.buscarTodos();
    }

    /**
     * Consultas Prepago
     ***/
    @Audit(tipo = Tipo.CON_ADMIN_PERSONA_PP)
    @PreAuthorize("hasPermission('CON_ADMINPER','ANY')")
    @GetMapping(value = "/personaPP", params = "accion=buscarTodos")
    public @ResponseBody List<PersonaPP> buscarTodosPersonaPP()
    {
        return personaPPService.buscarTodos();
    }

    @Audit(tipo = Tipo.CON_ADMIN_PERSONA_PP)
    @PreAuthorize("hasPermission('CON_ADMINPER','ANY')")
    @GetMapping(value = "/personaPP", params = "accion=buscarPorTipoDocumento")
    public List<PersonaPP> buscarClientePorTipoDocumento(
            @Validated CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return personaPPService
                .buscarPorTipoDocumento(criterioBusquedaTipoDocumento);
    }

    @Audit(tipo = Tipo.CON_ADMIN_PERSONA_PP)
    @PreAuthorize("hasPermission('CON_ADMINPER','ANY')")
    @GetMapping(value = "/personaPP", params = "accion=buscarPorCriterios")
    public List<PersonaPP> buscarClientePorCriterios(
            @Validated CriterioBusquedaClientePersona criterioBusqueda,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return personaPPService.buscarPorCriterios(criterioBusqueda);
    }

    @Audit(tipo = Tipo.CON_ADMIN_TARJETA_PP)
    @PreAuthorize("hasPermission('CON_ADMINTARJETA','ANY')")
    @GetMapping(value = "/tarjetaPP", params = "accion=buscarTodos")
    public @ResponseBody List<TarjetaPP> buscarTodosTarjetaPP()
    {
        return tarjetaPPService.buscarTodos();
    }

    @Audit(tipo = Tipo.CON_ADMIN_TARJETA)
    @PreAuthorize("hasPermission('CON_ADMINTARJETA','ANY')")
    @GetMapping(value = "/tarjetaPP", params = "accion=buscarTipoDocumento")
    public List<TarjetaPP> buscarTarjetasPorTipoDocumento(
            CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return tarjetaPPService
                .buscarPorTipoDocumento(criterioBusquedaTipoDocumento);
    }

    @Audit(tipo = Tipo.CON_ADMIN_TARJETA)
    @PreAuthorize("hasPermission('CON_ADMINTARJETA','ANY')")
    @GetMapping(value = "/tarjetaPP", params = "accion=buscarPorCriterios")
    public List<TarjetaPP> buscarTarjetasPorCriterios(
            @Validated CriterioBusquedaTarjeta criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return tarjetaPPService.buscarPorCriterios(criterioBusqueda);
    }

    @Audit(tipo = Tipo.CON_ADMIN_CUENTA_PP)
    @PreAuthorize("hasPermission('CON_ADMINCTA','ANY')")
    @GetMapping(value = "/cuentaPP", params = "accion=buscarTodos")
    public @ResponseBody List<CuentaPP> buscarTodosCuentaPP()
    {
        return cuentaPPService.buscarTodos();
    }

    @Audit(tipo = Tipo.CON_ADMIN_CTA)
    @PreAuthorize("hasPermission('CON_ADMINCTA','ANY')")
    @GetMapping(value = "/cuentaPP", params = "accion=buscarPorCriterios")
    public List<CuentaPP> buscarCuentasPPPorCriterios(
            @Validated CriterioBusquedaCuenta criterioBusqueda, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return cuentaPPService.buscarPorCriterios(criterioBusqueda);
    }

    @Audit(tipo = Tipo.CON_ADMIN_LOTE_PP)
    @PreAuthorize("hasPermission('CON_ADMINLOT','ANY')")
    @GetMapping(value = "/lotePP", params = "accion=buscarTodos")
    public @ResponseBody List<LotePP> buscarTodosLotePP()
    {
        return lotePPService.buscarTodos();
    }

    @Audit(tipo = Tipo.CON_ADMIN_RECARGA_PP)
    @PreAuthorize("hasPermission('CON_ADMINRECARGA','ANY')")
    @GetMapping(value = "/recargaPP", params = "accion=buscarTodos")
    public @ResponseBody List<RecargaPP> buscarTodosRecargaPP()
    {
        return recargaPPService.buscarTodos();
    }

}