package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.proceso.SubModulo;

public interface ISubModuloService extends IMantenibleService<SubModulo>
{
    public List<SubModulo> buscarTodos();

    public List<SubModulo> buscarPorCodigo(SubModulo subModulo);

    public List<SubModulo> registrarSubModulo(SubModulo subModulo);

    public List<SubModulo> actualizarSubModulo(SubModulo subModulo);

    public void eliminarSubModulo(SubModulo subModulo);
}