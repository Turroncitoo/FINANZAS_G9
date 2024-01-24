package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.MetodoIdThb;

public interface IMetodoIdThbService extends IMantenibleService<MetodoIdThb>
{

    public List<MetodoIdThb> buscarTodos();

    public List<MetodoIdThb> buscarPorIdMetodoIdThb(String idMetodo);

    public boolean existeMetodoIdThb(String idMetodo);

    public void registrarMetodoIdThb(MetodoIdThb metodoIdThb);

    public void actualizarMetodoIdThb(MetodoIdThb metodoIdThb);

    public void eliminarMetodoIdThb(MetodoIdThb metodoIdThb);
}
