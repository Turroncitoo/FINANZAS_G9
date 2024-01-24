package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaTarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.prepago.TarjetaPP;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITarjetaPPService
{

    public List<TarjetaPP> buscarTodos();

    public List<TarjetaPP> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda);

    public List<TarjetaPP> buscarPorCriterios(CriterioBusquedaTarjeta criterioBusqueda);

    public void exportarTarjetasPPPorCriterios(List<TarjetaPP> list, CriterioBusquedaTarjeta criterioBusquedaTarjeta, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void exportarTarjetasPPPorTipoDocumento(List<TarjetaPP> list, CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
