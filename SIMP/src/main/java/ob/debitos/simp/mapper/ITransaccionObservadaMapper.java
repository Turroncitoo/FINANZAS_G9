package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.ajuste.ActualizacionExtornoDevolucion;
import ob.debitos.simp.model.ajuste.ActualizacionTrace;
import ob.debitos.simp.model.consulta.movimiento.TransaccionObservada;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionObservada;

public interface ITransaccionObservadaMapper
{
    
    public List<TransaccionObservada> buscarTransaccionesObservadasPorCriterios(CriterioBusquedaTransaccionObservada criterioBusqueda);

    public List<TransaccionObservada> buscarTransaccionesObservadasPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);

    public List<TransaccionObservada> buscarConciliacionObservada(CriterioBusquedaTransaccionObservada criterioBusqueda);

    public void actualizarTrace(ActualizacionTrace actualizaTrace);

    public List<ActualizacionExtornoDevolucion> mantenerExtornoDevolucion(ActualizacionExtornoDevolucion actualizaExtornoDev);
    
}