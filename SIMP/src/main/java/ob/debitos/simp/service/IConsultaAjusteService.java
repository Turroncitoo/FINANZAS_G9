package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.ajuste.TxnsAjuste;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionAjuste;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IConsultaAjusteService
{

    public List<TxnsAjuste> buscarTransaccionesAjustesPorCriterios(CriterioBusquedaTransaccionAjuste criterioBusqueda);

    public List<TxnsAjuste> buscarTransaccionesAjustesPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);

    public void exportarTxnsAjustesPorCriterios(List<TxnsAjuste> list, CriterioBusquedaTransaccionAjuste criterioBusquedaTransaccionAjuste, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void exportarTxnsAjustesPorTipoDocumento(List<TxnsAjuste> list, CriterioBusquedaTipoDocumento criterioBusquedaTransaccionAjuste, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
