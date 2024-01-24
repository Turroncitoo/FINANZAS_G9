package ob.debitos.simp.controller.wshub.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaFiltroPrepago;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.prepago.wshub.ConsultaMovimientos;
import ob.debitos.simp.model.prepago.wshub.ConsultaSaldo;
import ob.debitos.simp.model.prepago.wshub.ParametrosEntrada;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWS;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWSConsultaCliente;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWSRegistroCliente;
import ob.debitos.simp.service.IHubWebService;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.VisitaConsulta)
@RequestMapping("/prepago/wshub")
public @RestController class WebServiceRestController
{
    @Autowired
    IHubWebService hubWebService;

    /* Consulta de Saldo */

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @GetMapping(value = "/{operacion:consultaSaldo}", params = "accion=buscarPorDocumento")
    public ConsultaSaldo buscarSaldoPorDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumento)
    {
        return hubWebService.buscarSaldo(criterioBusquedaTipoDocumento);
    }

    @Audit(comentario = Comentario.Consulta)
    @GetMapping(value = "/{operacion:consultaSaldo}", params = "accion=buscarPorFiltro")
    public ConsultaSaldo buscarSaldoPorFiltro(CriterioBusquedaFiltroPrepago criterioBusquedaMovimientosPP)
    {
        return hubWebService.buscarSaldo(criterioBusquedaMovimientosPP);
    }

    /* Consulta de Movimientos */

    @Audit(comentario = Comentario.ConsultaTipoDocumento)
    @GetMapping(value = "/{operacion:consultaMovimientos}", params = "accion=buscarPorDocumento")
    public ConsultaMovimientos buscarMovimientosPorDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumento)
    {
        return hubWebService.buscarMovimientos(criterioBusquedaTipoDocumento);
    }

    @Audit(comentario = Comentario.Consulta)
    @GetMapping(value = "/{operacion:consultaMovimientos}", params = "accion=buscarPorFiltro")
    public ConsultaMovimientos buscarMovimientosPorFiltro(CriterioBusquedaFiltroPrepago criterioBusquedaMovimientosPP)
    {
        return hubWebService.buscarMovimientos(criterioBusquedaMovimientosPP);
    }

    /* Operaciones Web Service */

    @Audit(comentario = Comentario.OperacionWebService)
    @GetMapping(value = "/{operacion:reasignarTarjeta}")
    public RespuestaWS reasignarTarjeta(ParametrosEntrada parametrosEntrada)
    {
        return hubWebService.reasignarTarjeta(parametrosEntrada);
    }

    @Audit(comentario = Comentario.OperacionWebService)
    @GetMapping(value = "/{operacion:recargarTarjeta}")
    public RespuestaWS recargarTarjeta(ParametrosEntrada parametrosEntrada)
    {
        return hubWebService.recargarTarjeta(parametrosEntrada);
    }

    @Audit(comentario = Comentario.OperacionWebService)
    @GetMapping(value = "/{operacion:activarTarjeta}")
    public RespuestaWS activarTarjeta(ParametrosEntrada parametrosEntrada)
    {
        return hubWebService.activarTarjeta(parametrosEntrada);
    }

    @Audit(comentario = Comentario.OperacionWebService)
    @GetMapping(value = "/{operacion:bloquearTarjeta}")
    public RespuestaWS bloquearTarjeta(ParametrosEntrada parametrosEntrada)
    {
        return hubWebService.bloquearTarjeta(parametrosEntrada);
    }

    @Audit(comentario = Comentario.OperacionWebService)
    @GetMapping(value = "/{operacion:asociarTarjeta}")
    public RespuestaWS asociarTarjeta(ParametrosEntrada parametrosEntrada)
    {
        return hubWebService.asociarTarjeta(parametrosEntrada);
    }

    @Audit(comentario = Comentario.OperacionWebService)
    @GetMapping(value = "/{operacion:registrarCliente}")
    public RespuestaWSRegistroCliente registrarCliente(ParametrosEntrada parametrosEntrada)
    {
        return hubWebService.registrarCliente(parametrosEntrada);
    }

    @Audit(comentario = Comentario.OperacionWebService)
    @GetMapping(value = "/{operacion:consultarClientePorTarjeta}")
    public RespuestaWSConsultaCliente consultarClientePorTarjeta(ParametrosEntrada parametrosEntrada)
    {
        return hubWebService.consultarClientePorTarjeta(parametrosEntrada);
    }

}
