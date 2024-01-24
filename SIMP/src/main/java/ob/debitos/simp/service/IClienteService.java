package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.Cliente;

public interface IClienteService extends IMantenibleService<Cliente>
{

    public List<Cliente> buscarTodos();

    public List<Cliente> buscarPodIdClienteIdEmpresa(String idCliente, String idEmpresa);

    public List<Cliente> buscarPorIdEmpresa(String idEmpresa);

    public List<Cliente> buscarPorIdCliente(String idCliente);

    public boolean existeCliente(String idCliente, String idEmpresa);

    public void registrarCliente(Cliente cliente);

    public void actualizarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);
}
