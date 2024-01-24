package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.IndCorreoTelefono;

public interface IIndCorreoTelefonoService extends IMantenibleService<IndCorreoTelefono>
{

    public List<IndCorreoTelefono> buscarTodos();

    public List<IndCorreoTelefono> buscarPorIndCorreoTelefono(String codigoIndCorreoTelefono);

    public boolean existeIndCorreoTelefono(String codigoIndCorreoTelefono);

    public void registrarIndCorreoTelefono(IndCorreoTelefono indCorreoTelefono);

    public void actualizarIndCorreoTelefono(IndCorreoTelefono indCorreoTelefono);

    public void eliminarIndCorreoTelefono(IndCorreoTelefono indCorreoTelefono);
}
