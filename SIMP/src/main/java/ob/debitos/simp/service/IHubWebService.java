package ob.debitos.simp.service;

import java.util.Map;

import ob.debitos.simp.model.prepago.TarjetaPP;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaFiltroPrepago;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaRangoFechaPrepago;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;
import ob.debitos.simp.model.prepago.wshub.ConsultaMovimientos;
import ob.debitos.simp.model.prepago.wshub.ConsultaSaldo;
import ob.debitos.simp.model.prepago.wshub.ParametrosEntrada;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWS;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWSConsultaCliente;
import ob.debitos.simp.model.prepago.wshub.respuesta.RespuestaWSRegistroCliente;

public interface IHubWebService
{

    ConsultaSaldo buscarSaldo(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumento);
    ConsultaSaldo buscarSaldo(CriterioBusquedaFiltroPrepago criterioBusquedaMovimientosPP);
    ConsultaSaldo buscarSaldo(String codigoSeguimiento);
    
    ConsultaMovimientos buscarMovimientos(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumento);
    ConsultaMovimientos buscarMovimientos(CriterioBusquedaFiltroPrepago criterioBusquedaMovimientosPP);
    ConsultaMovimientos buscarMovimientos(String codigoSeguimiento);
    
    Map<TarjetaPP,RespuestaWS> bloqueoPermanenteTarjetas(CriterioBusquedaRangoFechaPrepago criterioBusquedaRangoFechaPrepago);

    RespuestaWS reasignarTarjeta(ParametrosEntrada parametrosEntrada);
    RespuestaWS bloquearTarjeta(ParametrosEntrada parametrosEntrada);
    RespuestaWS activarTarjeta(ParametrosEntrada parametrosEntrada);
    RespuestaWS recargarTarjeta(ParametrosEntrada parametrosEntrada);
    RespuestaWS asociarTarjeta(ParametrosEntrada parametrosEntrada);
    
    RespuestaWSRegistroCliente registrarCliente(ParametrosEntrada parametrosEntrada);
    
    RespuestaWSConsultaCliente consultarClientePorTarjeta(ParametrosEntrada parametrosEntrada);
    
    
}
