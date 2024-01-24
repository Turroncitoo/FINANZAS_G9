package ob.debitos.simp.service.impl.mantenimiento;

import ob.debitos.simp.mapper.IClienteAfinidadMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ClienteAfinidad;
import ob.debitos.simp.service.IClienteAfinidadService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteAfinidadService extends MantenibleService<ClienteAfinidad> implements IClienteAfinidadService
{

    @SuppressWarnings("unused")
    private IClienteAfinidadMapper clienteAfinidadMapper;

    public ClienteAfinidadService(@Qualifier("IClienteAfinidadMapper") IMantenibleMapper<ClienteAfinidad> mapper)
    {
        super(mapper);
        this.clienteAfinidadMapper = (IClienteAfinidadMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClienteAfinidad> buscarTodos()
    {
        return super.buscar(new ClienteAfinidad(), Verbo.GETS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClienteAfinidad> buscarAsociacionPorClienteAfinidad(String idEmpresa, String idCliente, int idAfinidad)
    {
        ClienteAfinidad clienteAfinidad = ClienteAfinidad.builder().idEmpresa(idEmpresa).idCliente(idCliente).idAfinidad(idAfinidad).build();
        return super.buscar(clienteAfinidad, Verbo.GET);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClienteAfinidad> buscarAsociacionPorCliente(String idEmpresa, String idCliente)
    {
        ClienteAfinidad clienteAfinidad = ClienteAfinidad.builder().idEmpresa(idEmpresa).idCliente(idCliente).build();
        return super.buscar(clienteAfinidad, Verbo.GET_CLI);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClienteAfinidad> buscarAsociacionPorClienteLogo(String idEmpresa, String idCliente, String idLogo)
    {
        ClienteAfinidad clienteAfinidad = ClienteAfinidad.builder().idEmpresa(idEmpresa).idCliente(idCliente).idLogo(idLogo).build();
        return super.buscar(clienteAfinidad, Verbo.GET_CLI_LOGO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ClienteAfinidad> buscarAsociacionPorAfinidad(int idAfinidad)
    {
        ClienteAfinidad clienteAfinidad = ClienteAfinidad.builder().idAfinidad(idAfinidad).build();
        return super.buscar(clienteAfinidad, Verbo.GET_AFI);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existeAsociacionClienteAfinidad(String idEmpresa, String idCliente, int idAfinidad)
    {
        ClienteAfinidad clienteAfinidad = ClienteAfinidad.builder().idEmpresa(idEmpresa).idCliente(idCliente).idAfinidad(idAfinidad).build();
        return !super.buscar(clienteAfinidad, Verbo.GET).isEmpty();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void asociarClienteAfinidad(ClienteAfinidad clienteAfinidad)
    {
        super.mantener(clienteAfinidad, Verbo.INSERT);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarAsociacionClienteAfinidad(ClienteAfinidad clienteAfinidad)
    {
        ClienteAfinidad clienteAfinidadAntiguo = ClienteAfinidad.builder().idEmpresa(clienteAfinidad.getIdEmpresaAntiguo()).idCliente(clienteAfinidad.getIdClienteAntiguo()).idAfinidad(clienteAfinidad.getIdAfinidadAntiguo()).build();
        super.mantener(clienteAfinidadAntiguo, Verbo.REMOVE);
        super.mantener(clienteAfinidad, Verbo.INSERT);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarAsociacionClienteAfinidad(ClienteAfinidad clienteAfinidad)
    {
        super.mantener(clienteAfinidad, Verbo.REMOVE);
    }

}
