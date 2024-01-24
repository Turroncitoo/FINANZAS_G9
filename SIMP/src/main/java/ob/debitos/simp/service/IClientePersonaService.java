package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.consulta.administrativa.ClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;

public interface IClientePersonaService
{

    public List<ClientePersona> buscarTodos();

    public List<ClientePersona> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda);

    public List<ClientePersona> buscarPorCriterios(CriterioBusquedaClientePersona criterioBusqueda);

}
