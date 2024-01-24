package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IClientePersonaMapper;
import ob.debitos.simp.model.consulta.administrativa.ClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaClientePersona;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.service.IClientePersonaService;

@Service
public class ClientePersonaService implements IClientePersonaService
{

    private @Autowired IClientePersonaMapper clientePersonaMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClientePersona> buscarTodos()
    {
        return clientePersonaMapper.buscarTodos();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClientePersona> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda)
    {
        return this.clientePersonaMapper.buscarPorTipoDocumento(criterioBusqueda);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClientePersona> buscarPorCriterios(CriterioBusquedaClientePersona criterioBusqueda)
    {
        return this.clientePersonaMapper.buscarPorCriterios(criterioBusqueda);
    }

}