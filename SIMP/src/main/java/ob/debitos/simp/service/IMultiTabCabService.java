package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.MultiTabCab;

public interface IMultiTabCabService extends IMantenibleService<MultiTabCab>
{

    public List<MultiTabCab> buscarTodos();

    public List<MultiTabCab> buscarPorIdTabla(int idTabla);

    public boolean existeIdTabla(Integer idTabla);

    public void registrarMultiTabCab(MultiTabCab multiTabCab);

    public void actualizarMultiTabCab(MultiTabCab multiTabCab);

    public void eliminarMultiTabCab(MultiTabCab multiTabCab);

}
