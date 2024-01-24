package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.administrativa.Tarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;

public interface ITarjetaMapper
{

    public List<Tarjeta> buscarTodos();

    public List<Tarjeta> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda);

    public List<Tarjeta> buscarPorCriterios(CriterioBusquedaTarjeta criterioBusqueda);

}
