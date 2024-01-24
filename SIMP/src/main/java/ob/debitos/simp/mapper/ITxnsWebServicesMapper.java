package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ob.debitos.simp.model.consulta.movimiento.TxnsPreAutorizadas;
import ob.debitos.simp.model.consulta.movimiento.TxnsWebServices;
import ob.debitos.simp.model.criterio.CriterioBusquedaAuthWSPendiente;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionWebServices;
import ob.debitos.simp.model.mantenimiento.ParametroWS;

public interface ITxnsWebServicesMapper
{

    public List<TxnsWebServices> buscarPorCriterio(CriterioBusquedaTransaccionWebServices criterio);

    public TxnsWebServices buscarPorId(@Param("idTransaccion") String idTransaccion);

    public List<TxnsPreAutorizadas> buscarPreAutorizadasPorCriterio(CriterioBusquedaTransaccionWebServices criterio);

    public TxnsPreAutorizadas buscarPreAutorizadasPorId(@Param("id") Integer id);

    public ParametroWS obtenerURLSIMPHub();

    public void mantenerAutorizacionWebServicePendiente(CriterioBusquedaAuthWSPendiente criterio);
    
}
