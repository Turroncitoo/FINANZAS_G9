package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.tarifario.Escenario;

public interface IEscenarioService extends IMantenibleService<Escenario>
{
    public List<Escenario> buscarTodos();

    public List<Escenario> buscarPorCodigo(Escenario escenario);
    
    public boolean existeEscenario(Escenario escenario);

    public List<Escenario> registrarEscenario(Escenario escenario);

    public List<Escenario> actualizarEscenario(Escenario escenario);

    public void eliminarEscenario(Escenario escenario);
}
