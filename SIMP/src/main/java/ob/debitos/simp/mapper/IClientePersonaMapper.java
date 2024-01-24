package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.administrativa.ClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;

public interface IClientePersonaMapper
{

    public List<ClientePersona> buscarTodos();

    public List<ClientePersona> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda);

    public List<ClientePersona> buscarPorCriterios(CriterioBusquedaClientePersona criterioBusqueda);

}