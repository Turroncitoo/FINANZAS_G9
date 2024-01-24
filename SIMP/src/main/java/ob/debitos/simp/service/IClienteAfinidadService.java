package ob.debitos.simp.service;

import ob.debitos.simp.model.mantenimiento.ClienteAfinidad;

import java.util.List;

public interface IClienteAfinidadService extends IMantenibleService<ClienteAfinidad>
{

    public List<ClienteAfinidad> buscarTodos();

    public List<ClienteAfinidad> buscarAsociacionPorClienteAfinidad(String idEmpresa, String idCliente, int idAfinidad);

    public List<ClienteAfinidad> buscarAsociacionPorCliente(String idEmpresa, String idCliente);

    public List<ClienteAfinidad> buscarAsociacionPorClienteLogo(String idEmpresa, String idCliente, String idLogo);

    public List<ClienteAfinidad> buscarAsociacionPorAfinidad(int idAfinidad);

    public boolean existeAsociacionClienteAfinidad(String idEmpresa, String idCliente, int idAfinidad);

    public void asociarClienteAfinidad(ClienteAfinidad clienteAfinidad);

    public void actualizarAsociacionClienteAfinidad(ClienteAfinidad clienteAfinidad);

    public void eliminarAsociacionClienteAfinidad(ClienteAfinidad clienteAfinidad);

}
