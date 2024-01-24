package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.TxnsLiberadas;
import ob.debitos.simp.model.criterio.CriterioBusquedaSaldo;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionLiberada;
import ob.debitos.simp.model.prepago.Saldo;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISaldoService
{

    public List<Saldo> buscarTodos();

    public List<Saldo> buscarPorCriterio(CriterioBusquedaSaldo criterioBusquedaSaldo);

    public List<Saldo> buscarPorTipoDocumento(CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumentoPrepago);

    public void exportarSaldoPorCriterios(List<Saldo> list, CriterioBusquedaSaldo criterioBusquedaSaldo, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void exportarSaldoPorTipoDocumento(List<Saldo> list, CriterioBusquedaTipoDocumentoPrepago criterioBusquedaSaldo, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
