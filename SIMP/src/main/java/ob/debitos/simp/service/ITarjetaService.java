package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.consulta.administrativa.Tarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;

public interface ITarjetaService
{

    public List<Tarjeta> buscarTodos();

    public List<Tarjeta> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda);

    public List<Tarjeta> buscarPorCriterios(CriterioBusquedaTarjeta criterioBusqueda);

}