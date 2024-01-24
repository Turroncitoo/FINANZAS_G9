package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CanalEmisor;

public interface ICanalEmisorService extends IMantenibleService<CanalEmisor>
{

    public List<CanalEmisor> buscarTodos();

    public List<CanalEmisor> buscarPorCodigoCanalEmisor(String codigoCanalEmisor);

    public boolean existeCanalEmisor(String codigoCanalEmisor);

    public void registrarCanalEmisor(CanalEmisor canalEmisor);

    public void actualizarCanalEmisor(CanalEmisor canalEmisor);

    public void eliminarCanalEmisor(CanalEmisor canalEmisor);
}