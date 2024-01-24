package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IClienteMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Cliente;
import ob.debitos.simp.service.IClienteService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ClienteService extends MantenibleService<Cliente> implements IClienteService
{
    @SuppressWarnings("unused")
    private IClienteMapper clienteMapper;

    public ClienteService(@Qualifier("IClienteMapper") IMantenibleMapper<Cliente> mapper)
    {
        super(mapper);
        this.clienteMapper = (IClienteMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Cliente> buscarTodos()
    {
        return this.buscar(new Cliente(), Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Cliente> buscarPodIdClienteIdEmpresa(String idCliente, String idEmpresa)
    {
        Cliente cliente = Cliente.builder().idCliente(idCliente).idEmpresa(idEmpresa).build();
        return this.buscar(cliente, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Cliente> buscarPorIdCliente(String idCliente)
    {
        Cliente cliente = Cliente.builder().idCliente(idCliente).build();
        return this.buscar(cliente, Verbo.GET_C);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Cliente> buscarPorIdEmpresa(String idEmpresa)
    {
        Cliente cliente = Cliente.builder().idEmpresa(idEmpresa).build();
        return this.buscar(cliente, Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCliente(String idCliente, String idEmpresa)
    {
        return !this.buscarPodIdClienteIdEmpresa(idCliente, idEmpresa).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCliente(Cliente cliente)
    {
        this.registrar(cliente);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCliente(Cliente cliente)
    {
        this.actualizar(cliente);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCliente(Cliente cliente)
    {
        this.eliminar(cliente);
    }
}