package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ob.debitos.simp.model.consulta.movimiento.TxnsPreAutorizadas;
import ob.debitos.simp.model.consulta.movimiento.TxnsWebServices;
import ob.debitos.simp.model.criterio.CriterioBusquedaAuthWSPendiente;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionWebServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITxnsWebServicesService
{

    public List<TxnsWebServices> buscarPorCriterio(CriterioBusquedaTransaccionWebServices criterio);

    public TxnsWebServices buscarPorId(String idTransaccion);

    public List<TxnsPreAutorizadas> buscarPreAutorizadasPorCriterio(CriterioBusquedaTransaccionWebServices criterio);

    public TxnsPreAutorizadas buscarPreAutorizadasPorId(Integer id);

    public Map<String, Object> enviarOperacionSimpHub(Map<String, Object> input, String op);

    public Map<String, Object> enviarOperacionSimpHub(String dataInput, String op);

    public void registrarPreAutorizacion(CriterioBusquedaAuthWSPendiente criterio);

    public void autorizar(CriterioBusquedaAuthWSPendiente criterio);

    public void denegar(CriterioBusquedaAuthWSPendiente criterio);

    public void controlExtorno(CriterioBusquedaAuthWSPendiente criterio);

    public void exportarTxnsWebServicesPorCriterios(List<TxnsWebServices> list, CriterioBusquedaTransaccionWebServices criterioBusquedaTransaccionWebServices, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void exportarTxnsPreAutorizadasPorCriterios(List<TxnsPreAutorizadas> list, CriterioBusquedaTransaccionWebServices criterioBusquedaTransaccionWebServices, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
